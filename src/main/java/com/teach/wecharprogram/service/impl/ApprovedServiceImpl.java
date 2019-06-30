package com.teach.wecharprogram.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CacheConstant;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.common.constant.RoleConstant;
import com.teach.wecharprogram.components.business.RedisUtil;
import com.teach.wecharprogram.entity.*;
import com.teach.wecharprogram.entity.vo.ApprovedVo;
import com.teach.wecharprogram.mapper.RelUserTypeidMapper;
import com.teach.wecharprogram.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.mapper.ApprovedMapper;
import com.teach.wecharprogram.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-06-22 16:36:34
 */

@Service
public class ApprovedServiceImpl implements ApprovedService {

    @Autowired
    ApprovedMapper approvedMapper;

    @Autowired
    UserService userService;

    @Autowired
    RelUserTypeIdService relUserTypeIdService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RelUserTypeidMapper relUserTypeidMapper;

    @Autowired
    RoleService roleService;

    @Override
    public Approved getOne(Long id) {
        return approvedMapper.selectById(id);
    }

    @Override
    public List<Approved> list(Approved approved) {
        List<Approved> approveds = approvedMapper.selectList(new QueryWrapper<>(approved));
        return approveds;
    }

    @Override
    public Pager listByPage(Pager pager, Approved approved) {
        IPage<Approved> approvedIPage = approvedMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(approved).orderByDesc("createTime"));
        return Pager.of(approvedIPage);
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public Approved save(Approved approved) {
        if (Objects.nonNull(approved.getId())) {
            approvedMapper.updateById(approved);
        } else {
            approvedMapper.insert(approved);
        }
        return approved;
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public boolean delete(String ids) {
        List<Long> list = Lists.newArrayList();
        if (ids.indexOf(",") != -1) {
            List<String> asList = Arrays.asList(ids.split(","));
            asList.stream().forEach(o -> list.add(Long.valueOf(o)));
        } else {
            list.add(Long.valueOf(ids));
        }
        int i = approvedMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public boolean agree(ApprovedVo approvedVo) {
        // 取审批信息
        Approved approved = this.getOne(approvedVo.getId());
        if (Objects.isNull(approved)) {
            throw new CommonException(DefinedCode.DATA_NOT_EXISTS_ERROR, "该申请不存在！");
        }
        if (Objects.nonNull(approved.getResultCode()) && approved.getResultCode() != CommonConstant.STATUS_VALID_PROCESS) {
            throw new CommonException(DefinedCode.APPROVED_IS_OK_ERROR, "该审批已经完成，无需再次操作！");
        }
        Integer type = approved.getType();      //  1教师 2教练 3家长
        String classesId = approved.getClassesId();
        Long startUserId = approved.getStartUserId();
        String realName = approved.getRealName();
        User user = userService.getOne(startUserId);
        // 如果是新用户  才会更新用户信息
        if (user.getStatus() == CommonConstant.STATUS_ERROR) {
            if (approvedVo.getResultCode() == CommonConstant.STATUS_APPROVED_OK) {      // 通过
                String roleId = approved.getRoleId();
                // 获取权限
                Role role = roleService.getOne(Long.valueOf(roleId));

                // 设置参数
                user.setId(startUserId);
                user.setStatus(CommonConstant.STATUS_OK);
                user.setRoleCode(role.getRoleCode());
                user.setRoleName(role.getRoleName());
                user.setRealName(realName);
                user.setPhone(approved.getPhone());
                // 执行关联
                approved = this.relUserToOtherId(type, startUserId, classesId, approved);
                approved.setResult("通过");
                approved.setResultCode(CommonConstant.STATUS_OK);
            } else {
                // 不通过
                approved.setResult("不通过");
                approved.setResultCode(CommonConstant.STATUS_APPROVED_ERROR);
                user.setStatus(CommonConstant.STATUS_ERROR);
                user.setRealName(realName);
                user.setPhone(approved.getPhone());
            }
            // 更新用户信息
            userService.save(user);
        } else {
            if (approvedVo.getResultCode() == CommonConstant.STATUS_APPROVED_OK) {
                // 通过   执行关联
                approved = this.relUserToOtherId(type, startUserId, classesId, approved);
                approved.setResult("通过");
                approved.setResultCode(CommonConstant.STATUS_OK);
            } else {
                // 不通过
                approved.setResult("不通过");
                approved.setResultCode(CommonConstant.STATUS_APPROVED_ERROR);
            }
        }
        // 更新审批信息
        approved.setEndTime(new Date());
        approved.setOpinion(approvedVo.getOpinion());
        approvedMapper.updateById(approved);
        return true;
    }

    @Override
    public long count(Approved approved) {
        return approvedMapper.selectCount(new QueryWrapper<>(approved));
    }

    private Approved relUserToOtherId(Integer type, Long startUserId, String classesId, Approved approved) {
        if (type == RoleConstant.TEACHER) {
            // 教师关联班级
            // 先查询记录是否存在
            RelUserTypeId relUserTypeId = new RelUserTypeId(startUserId, Long.valueOf(classesId), CommonConstant.REL_CLASS);
            RelUserTypeId one = relUserTypeidMapper.selectOne(new QueryWrapper<>(relUserTypeId));
            if (Objects.isNull(one)) {
                relUserTypeIdService.save(relUserTypeId);
            }
        } else if (type == RoleConstant.TRAIN) {
            // 教练关联学校
            String[] split = approved.getSchoolId().split(",");
            List<String> ids = Arrays.asList(split);
            ids.stream().forEach(o -> {
                // 先查询记录是否存在
                RelUserTypeId relUserTypeId = new RelUserTypeId(startUserId, Long.valueOf(o), CommonConstant.REL_SCHOOL);
                List<RelUserTypeId> list = relUserTypeIdService.list(relUserTypeId);
                if (list.size() == 0) {
                    relUserTypeIdService.save(relUserTypeId);
                }
            });
        } else {     /// 家长关联学生
            String[] split = approved.getStudentId().split(",");
            List<String> ids = Arrays.asList(split);
            ids.stream().forEach(o -> {
                // 先查询记录是否存在
                RelUserTypeId relUserTypeId = new RelUserTypeId(startUserId, Long.valueOf(o), CommonConstant.REL_STUDENTS);
                List<RelUserTypeId> list = relUserTypeIdService.list(relUserTypeId);
                if (list.size() == 0) {
                    relUserTypeIdService.save(relUserTypeId);
                }
            });
        }
        return approved;
    }

}
