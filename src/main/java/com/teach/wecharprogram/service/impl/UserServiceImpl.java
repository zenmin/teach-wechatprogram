package com.teach.wecharprogram.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CacheConstant;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.components.business.RedisUtil;
import com.teach.wecharprogram.entity.Approved;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.entity.vo.UpdateUserVo;
import com.teach.wecharprogram.mapper.UserMapper;
import com.teach.wecharprogram.service.ApprovedService;
import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;


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

    @Autowired
    @Lazy
    ApprovedService approvedService;

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
    public User save(User user) {
        user.setPassword(StaticUtil.md5Hex(user.getPassword()));
        if (Objects.nonNull(user.getId())) {
            userMapper.updateById(user);
        } else {

            String username = user.getUsername();
            String phone = user.getPhone();
            Integer count = userMapper.selectCount(new QueryWrapper<User>().eq("username", username).or(o -> o.eq("phone", phone)));
            if (count > 0) {
                throw new CommonException(DefinedCode.ISEXISTS, "用户名或手机号已存在");
            }
            userMapper.insert(user);
        }
        // 更新用户缓存信息
        user = this.getOne(user.getId());
        String tokenPrefix = CacheConstant.USER_TOKEN_CODE + StaticUtil.getLoginToken(user.getId()) + "/";
        Set<String> keys = redisUtil.getKeys(tokenPrefix);
        if (keys.size() > 0) {
            String token = keys.iterator().next();
            user.setPassword(null);
            redisUtil.set(token, user, CacheConstant.EXPIRE_LOGON_TIME);
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

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public User updateUserData(UpdateUserVo updateUserVo, String token) {
        // 验证验证码
        String key = CacheConstant.LOGIN_PHONE_CODE + updateUserVo.getPhone();
        String sendCode = redisUtil.get(key);
        if (!updateUserVo.getCode().equals(sendCode)) {
            throw new CommonException(DefinedCode.LOGIN_ERROR, "验证码有误或已过期，请重新发送！");
        }
        // 检查用户状态
        User user = this.getOne(updateUserVo.getId());
        if (user.getStatus() == CommonConstant.STATUS_VALID_ERROR) {
            // 查询此用户是否已有审批
            List<Approved> list = approvedService.list(new Approved(updateUserVo.getId()));
            if (list.size() == 0) {
                // 提交审批
                approvedService.save(new Approved("角色申请", updateUserVo.getRealName(), user.getId(), updateUserVo.getRoleName()
                        , updateUserVo.getRoleId(), updateUserVo.getRemark(), "待审批", updateUserVo.getType(),
                        updateUserVo.getClassesId(), updateUserVo.getStudentId(), "审批中", 2,updateUserVo.getPhone(),updateUserVo.getRealName()));
            }
        }
        // 更新用户信息
        user.setStatus(CommonConstant.STATUS_VALID_ERROR);
        userMapper.updateById(user);
        // 更新缓存信息
        user.setPassword(null);
        redisUtil.set(token, user, CacheConstant.EXPIRE_LOGON_TIME);
        return user;
    }

    @Transactional(rollbackFor = CommonException.class)
    @Override
    @Async
    public void updateLoginTime(User user) {
        userMapper.updateById(new User(user.getId(), user.getLastLoginTime(), user.getLastLoginIp()));
    }


}
