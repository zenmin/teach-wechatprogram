package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.entity.RelUserTypeId;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.entity.vo.ApprovedVo;
import com.teach.wecharprogram.service.ApprovedService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Approved;
import com.teach.wecharprogram.mapper.ApprovedMapper;
import com.teach.wecharprogram.service.RelUserTypeIdService;
import com.teach.wecharprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


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
        return pager.of(approvedIPage);
    }

    @Override
    @Transactional
    public Approved save(Approved approved) {
        if (Objects.nonNull(approved.getId())) {
            approvedMapper.updateById(approved);
        } else {
            approvedMapper.insert(approved);
        }
        return approved;
    }

    @Override
    @Transactional
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
    @Transactional
    public boolean agree(ApprovedVo approvedVo) {
        try {
            // 取审批信息
            Approved approved = this.getOne(approvedVo.getId());
            if (approved.getResultCode() == CommonConstant.STATUS_VALID_ERROR) {
                throw new CommonException(DefinedCode.APPROVED_IS_OK_ERROR, "该审批已经完成，无需再次操作！");
            }

            Integer type = approved.getType();      //  1教师 2教练 3家长
            Long startUserId = approved.getStartUserId();
            String roleName = approved.getRoleName();
            String roleId = approved.getRoleId();
            String classesId = approved.getClassesId();
            User user = new User();
            user.setId(startUserId);
            user.setStatus(CommonConstant.STATUS_OK);
            user.setRoleCode(roleId);
            user.setRoleName(roleName);

            if (type == 1) {     // 教师
                relUserTypeIdService.save(new RelUserTypeId(startUserId, Long.valueOf(classesId), 2));
            } else {     // 教练 / 家长
                String[] split = classesId.split(",");
                List<String> ids = Arrays.asList(split);
                ids.stream().forEach(o -> relUserTypeIdService.save(new RelUserTypeId(startUserId, Long.valueOf(o), 2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
