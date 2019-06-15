package com.teach.wecharprogram.service.impl;

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


/**
* Create Code Generator
* @Author ZengMin
* @Date 2019-06-15 17:46:36
*/

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role getOne(Long id){
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> list(Role role) {
        if(StringUtils.isNotBlank(role.getCreateTimeQuery())){
            role.setCreateTime(DateUtil.parseToDate(role.getCreateTimeQuery()));
        }
        List<Role> roles = roleMapper.selectList(new QueryWrapper<>(role));
        return roles;
    }

    @Override
    public Pager listByPage(Pager pager, Role role) {
        if(StringUtils.isNotBlank(role.getCreateTimeQuery())){
            role.setCreateTime(DateUtil.parseToDate(role.getCreateTimeQuery()));
        }
        IPage<Role> roleIPage = roleMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(role));
        return pager.of(roleIPage);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        if(Objects.nonNull(role.getId())){
            roleMapper.updateById(role);
        }else {
            roleMapper.insert(role);
        }
        return role;
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        List<Long> list = Lists.newArrayList();
        if(ids.indexOf(",") != -1){
            List<String> asList = Arrays.asList(ids.split(","));
            asList.stream().forEach(o -> list.add(Long.valueOf(o)));
        }else {
            list.add(Long.valueOf(ids));
        }
        int i = roleMapper.deleteBatchIds(list);
        return i > 0;
    }


}
