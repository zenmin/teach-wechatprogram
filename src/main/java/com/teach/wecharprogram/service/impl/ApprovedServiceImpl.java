package com.teach.wecharprogram.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CacheConstant;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.common.constant.RoleConstant;
import com.teach.wecharprogram.components.business.RedisUtil;
import com.teach.wecharprogram.entity.RelUserTypeId;
import com.teach.wecharprogram.entity.Role;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.entity.vo.ApprovedVo;
import com.teach.wecharprogram.mapper.RelUserTypeidMapper;
import com.teach.wecharprogram.service.ApprovedService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Approved;
import com.teach.wecharprogram.mapper.ApprovedMapper;
import com.teach.wecharprogram.service.RelUserTypeIdService;
import com.teach.wecharprogram.service.RoleService;
import com.teach.wecharprogram.service.UserService;
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
        IPage<Approved> approvedIPage = approvedMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(approved));
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
        if (Objects.nonNull(approved.getResultCode()) && approved.getResultCode() != CommonConstant.STATUS_VALID_PROCESS) {
            throw new CommonException(DefinedCode.APPROVED_IS_OK_ERROR, "该审批已经完成，无需再次操作！");
        }

        Long startUserId = approved.getStartUserId();
        String realName = approved.getRealName();
        User user = userService.getOne(startUserId);
        if (approvedVo.getResultCode() == CommonConstant.STATUS_APPROVED_OK) {      // 通过
            Integer type = approved.getType();      //  1教师 2教练 3家长
            String roleId = approved.getRoleId();
            String classesId = approved.getClassesId();

            // 获取权限
            Role role = roleService.getOne(Long.valueOf(roleId));

            // 设置参数
            user.setId(startUserId);
            user.setStatus(CommonConstant.STATUS_OK);
            user.setRoleCode(role.getRoleCode());
            user.setRoleName(role.getRoleName());
            user.setRealName(realName);
            user.setPhone(approved.getPhone());
            if (type == RoleConstant.TEACHER) {
                // 教师关联班级
                // 先查询记录是否存在
                RelUserTypeId relUserTypeId = new RelUserTypeId(startUserId, Long.valueOf(classesId), 2);
                RelUserTypeId one = relUserTypeidMapper.selectOne(new QueryWrapper<>(relUserTypeId));
                if (Objects.isNull(one)) {
                    relUserTypeIdService.save(relUserTypeId);
                }
            } else {     // 教练关联班级 / 家长关联学生
                String[] split = approved.getStudentId().split(",");
                List<String> ids = Arrays.asList(split);
                ids.stream().forEach(o -> relUserTypeIdService.save(new RelUserTypeId(startUserId, Long.valueOf(o), type)));
            }
            approved.setResult("通过");
            approved.setResultCode(CommonConstant.STATUS_OK);
            // 更新用户信息
            userService.save(user);
            user.setPassword(null);
            // 更新用户缓存信息
            String tokenPrefix = CacheConstant.USER_TOKEN_CODE + StaticUtil.getLoginToken(user.getId()) + "/";
            Set<String> keys = redisUtil.getKeys(tokenPrefix);
            if (keys.size() > 0) {
                String token = keys.iterator().next();
                redisUtil.set(token, user, CacheConstant.EXPIRE_LOGON_TIME);
            }
        } else {
            // 不通过
            approved.setResult("不通过");
            approved.setResultCode(CommonConstant.STATUS_APPROVED_ERROR);
            user.setStatus(CommonConstant.STATUS_ERROR);
            user.setRealName(realName);
            user.setPhone(approved.getPhone());
            userService.save(user);
        }
        approved.setEndTime(new Date());
        approved.setOpinion(approvedVo.getOpinion());
        approvedMapper.updateById(approved);
        return true;
    }

}
