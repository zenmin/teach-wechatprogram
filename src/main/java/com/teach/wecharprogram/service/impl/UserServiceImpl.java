package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.mapper.UserMapper;
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
* @Date 2019-06-15 18:17:10
*/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getOne(Long id){
        return userMapper.selectById(id);
    }

    @Override
    public List<User> list(User user) {
        if(StringUtils.isNotBlank(user.getCreateTimeQuery())){
            user.setCreateTime(DateUtil.parseToDate(user.getCreateTimeQuery()));
        }
        List<User> users = userMapper.selectList(new QueryWrapper<>(user));
        return users;
    }

    @Override
    public Pager listByPage(Pager pager, User user) {
        if(StringUtils.isNotBlank(user.getCreateTimeQuery())){
            user.setCreateTime(DateUtil.parseToDate(user.getCreateTimeQuery()));
        }
        IPage<User> userIPage = userMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(user));
        return pager.of(userIPage);
    }

    @Override
    @Transactional
    public User save(User user) {
        if(Objects.nonNull(user.getId())){
            userMapper.updateById(user);
        }else {
            userMapper.insert(user);
        }
        return user;
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
        int i = userMapper.deleteBatchIds(list);
        return i > 0;
    }


}
