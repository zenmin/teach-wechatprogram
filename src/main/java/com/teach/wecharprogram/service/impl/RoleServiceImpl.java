package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Role;
import com.teach.wecharprogram.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.teach.wecharprogram.util.DateUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-06-15 17:46:36
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role getOne(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> list(User loginUser, Role role) {
        List<Role> list = roleMapper.selectList(new QueryWrapper<>(role));
        // 如果是校长或者管理员才会返回全部角色
        if (loginUser.getRoleCode().equals(CommonConstant.ROLE_HEADMASTER) || loginUser.getRoleCode().equals(CommonConstant.ROLE_ADMIN)) {
            return list;
        } else {
            List<Role> collect = list.stream().filter(o -> !o.getRoleCode().equals(CommonConstant.ROLE_HEADMASTER) && !o.getRoleCode().equals(CommonConstant.ROLE_ADMIN)).collect(Collectors.toList());
            return collect;
        }
    }

    @Override
    public Pager listByPage(Pager pager, Role role) {
        if (StringUtils.isNotBlank(role.getCreateTimeQuery())) {
            role.setCreateTime(DateUtil.parseToDate(role.getCreateTimeQuery()));
        }
        IPage<Role> roleIPage = roleMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(role));
        return Pager.of(roleIPage);
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public Role save(Role role) {
        if (Objects.nonNull(role.getId())) {
            roleMapper.updateById(role);
        } else {
            roleMapper.insert(role);
        }
        return role;
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
        int i = roleMapper.deleteBatchIds(list);
        return i > 0;
    }


}
