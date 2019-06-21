package com.teach.wecharprogram.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CacheConstant;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.components.business.RedisUtil;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.mapper.UserMapper;
import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-04-17 15:10:45
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public User getOne(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> list(User user) {
        List<User> users = userMapper.selectList(new QueryWrapper<>(user));
        return users;
    }

    @Override
    public Pager listByPage(Pager pager, User user) {
        IPage<User> userIPage = userMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(user));
        return pager.of(userIPage);
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(StaticUtil.md5Hex(user.getPassword()));
        if (Objects.nonNull(user.getId())) {
            userMapper.updateById(user);
        } else {

            String username = user.getUsername();
            Integer count = userMapper.selectCount(new QueryWrapper<User>().eq("username", username));
            if (count > 0) {
                throw new CommonException(DefinedCode.ISEXISTS, "用户名已存在");
            }
            userMapper.insert(user);
        }
        return user;
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
        int i = userMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public User findByInviteCode(Long code) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("inviteCode", code));
    }

    @Override
    public User getLoginUser(String token) {
        String json = redisUtil.get(CacheConstant.USER_TOKEN_CODE + token);
        if (Objects.isNull(json))
            throw new CommonException(DefinedCode.NOTAUTH, "登陆超时，请重新登陆！");
        User user = JSONObject.parseObject(json, User.class);
        return user;
    }

    @Override
    public User findByPhone(String phone) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
    }

    @Override
    public User findByOpenId(String openid) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("openid", openid));
    }

    @Transactional
    @Override
    @Async
    public void updateLoginTime(User user) {
        userMapper.updateById(new User(user.getId(), user.getLastLoginTime(), user.getLastLoginIp()));
    }


}
