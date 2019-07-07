package com.teach.wecharprogram.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.*;
import com.teach.wecharprogram.components.business.RedisUtil;
import com.teach.wecharprogram.entity.*;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.vo.UpdateUserVo;
import com.teach.wecharprogram.mapper.*;
import com.teach.wecharprogram.service.*;
import com.teach.wecharprogram.util.JSONUtil;
import com.teach.wecharprogram.util.StaticUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


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

    @Autowired
    RelUserTypeidMapper relUserTypeidMapper;

    @Autowired
    SchoolMapper schoolMapper;

    @Autowired
    ClassesMapper classesMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    ClassesService classesService;

    @Autowired
    RoleService roleService;

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
        userIPage.getRecords().stream().forEach(o -> {
            o.setOpenid(null);
            o.setPassword(null);
        });
        return Pager.of(userIPage);
    }

    @Override
    public User save(User user) {
        if (Objects.nonNull(user.getId())) {
            if (Objects.nonNull(user.getResetPassword()) && user.getResetPassword()) {
                user.setPassword(StaticUtil.md5Hex(CommonConstant.INIT_PASSWORD));
            }
            userMapper.updateById(user);
        } else {
            if (StringUtils.isNotBlank(user.getPassword())) {
                if (StringUtils.isNotBlank(user.getPassword().trim())) {
                    user.setPassword(StaticUtil.md5Hex(user.getPassword()));
                }
            }
            String username = user.getUsername();
            String phone = user.getPhone();
            Integer count = userMapper.selectCount(new QueryWrapper<User>().eq("username", username).or(o -> o.eq("phone", phone)));
            if (count > 0) {
                throw new CommonException(DefinedCode.ISEXISTS, "用户名或手机号已存在");
            }
            user.setRoleName(RoleConstant.ROLE.getName(user.getRoleCode()));
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
    @Transactional(rollbackFor = CommonException.class)
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
        if (Objects.isNull(json)) {
            throw new CommonException(DefinedCode.NOTAUTH, "登陆超时，请重新登陆！");
        }
        User user = JSONObject.parseObject(json, User.class);
        return user;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        String token = request.getHeader(RequestConstant.TOKEN);
        Object attribute = request.getAttribute(token);
        User user = new User();
        try {
            user = StaticUtil.objectMapper.readValue(JSONUtil.toJSONString(attribute), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Integer type = updateUserVo.getType();
        // 验证验证码
//        String key = CacheConstant.LOGIN_PHONE_CODE + updateUserVo.getPhone();
//        String sendCode = redisUtil.get(key);
//        if (!updateUserVo.getCode().equals(sendCode)) {
//            throw new CommonException(DefinedCode.LOGIN_ERROR, "验证码有误或已过期，请重新发送！");
//        }
        // 检查用户状态
        User user = this.getOne(updateUserVo.getId());
        if (user.getStatus() == CommonConstant.STATUS_ERROR) {
            // 查询此用户是否已有审批
            List<Approved> list = approvedService.list(new Approved(updateUserVo.getId(), CommonConstant.STATUS_VALID_PROCESS));
            if (list.size() == 0) {
                // 提交审批
                Approved approved = new Approved("角色申请", updateUserVo.getRealName(), user.getId(), updateUserVo.getRoleName()
                        , updateUserVo.getRoleId(), updateUserVo.getRemark(), null, updateUserVo.getType(),
                        updateUserVo.getClassesId(), updateUserVo.getStudentId(), "审批中",
                        CommonConstant.STATUS_VALID_PROCESS, updateUserVo.getPhone(), updateUserVo.getRealName(), updateUserVo.getClassesName(), updateUserVo.getStudentName());
                approved.setUserText(JSONUtil.toJSONStringNotEmpty(new User(user.getId(), user.getRealName(), user.getImg(), user.getGender(), user.getNickName(), user.getPhone(), user.getStatus())));
                approved.setSchoolId(updateUserVo.getSchoolId());
                approved.setStudentName(updateUserVo.getSchoolName());
                approvedService.save(approved);
                // 更新用户信息
                user.setStatus(CommonConstant.STATUS_VALID_ERROR);
                userMapper.updateById(user);
                // 更新缓存信息
                user.setPassword(null);
                redisUtil.set(CacheConstant.USER_TOKEN_CODE + token, user, CacheConstant.EXPIRE_LOGON_TIME);
            }
        } else {
            // 非新用户审批可以提交多个  这里不查询是否已有审批
            String title = "关联申请";
            // 提交审批
            if (type == CommonConstant.REL_SCHOOL) {
                title = "学校关联申请";
            }
            if (type == CommonConstant.REL_CLASS) {
                title = "班级关联申请";
            }
            if (Objects.nonNull(updateUserVo.getStudentId())) {
                title = "学生关联申请";
            }
            Approved approved = new Approved(title, updateUserVo.getRealName(), user.getId(), updateUserVo.getRoleName()
                    , updateUserVo.getRoleId(), updateUserVo.getRemark(), null, updateUserVo.getType(),
                    updateUserVo.getClassesId(), updateUserVo.getStudentId(), "审批中",
                    CommonConstant.STATUS_VALID_PROCESS, updateUserVo.getPhone(), updateUserVo.getRealName(), updateUserVo.getClassesName(), updateUserVo.getStudentName());
            approved.setSchoolId(updateUserVo.getSchoolId());
            approved.setStudentName(updateUserVo.getSchoolName());
            approved.setUserText(JSONUtil.toJSONStringNotEmpty(new User(user.getId(), user.getRealName(), user.getImg(), user.getGender(), user.getNickName(), user.getPhone(), user.getStatus())));
            approvedService.save(approved);
        }
        return user;
    }

    @Override
    public Object getMyRelInfo(User user) {
        String roleCode = user.getRoleCode();
        Long userId = user.getId();
        int type = CommonConstant.REL_STUDENTS;

        // 校长 / 教练
        if (roleCode.equals(CommonConstant.ROLE_HEADMASTER) || roleCode.equals(CommonConstant.ROLE_TRAIN)) {
            type = CommonConstant.REL_SCHOOL;
            List<RelUserTypeId> relUserTypeIds = relUserTypeidMapper.selectList(new QueryWrapper<RelUserTypeId>().eq("userId", userId).eq("type", type));
            if (relUserTypeIds.size() == 0) {
                throw new CommonException(DefinedCode.NOTFOUND, "当前没有关联的学校！");
            }
            List<Long> ids = relUserTypeIds.stream().map(RelUserTypeId::getOtherId).collect(Collectors.toList());
            List<School> schoolList = schoolMapper.selectList(new QueryWrapper<School>().in("id", ids));
            return schoolList;
        }

        // 教师
        if (roleCode.equals(CommonConstant.ROLE_TEACHER)) {
            type = CommonConstant.REL_CLASS;
            List<RelUserTypeId> relUserTypeIds = relUserTypeidMapper.selectList(new QueryWrapper<RelUserTypeId>().eq("userId", userId).eq("type", type));
            if (relUserTypeIds.size() == 0) {
                throw new CommonException(DefinedCode.NOTFOUND, "当前没有关联的班级！");
            }
            List<Long> ids = relUserTypeIds.stream().map(RelUserTypeId::getOtherId).collect(Collectors.toList());
            List<Classes> classesList = classesMapper.selectList(new QueryWrapper<Classes>().in("id", ids));
            return classesList;
        }

        // 家长
        if (roleCode.equals(CommonConstant.ROLE_FAMILY)) {
            type = CommonConstant.REL_STUDENTS;
            List<RelUserTypeId> relUserTypeIds = relUserTypeidMapper.selectList(new QueryWrapper<RelUserTypeId>().eq("userId", userId).eq("type", type));
            if (relUserTypeIds.size() == 0) {
                throw new CommonException(DefinedCode.NOTFOUND, "当前没有关联的学生！");
            }
            List<Long> ids = relUserTypeIds.stream().map(RelUserTypeId::getOtherId).collect(Collectors.toList());
            List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().in("id", ids));
            return students;
        }
        throw new CommonException(DefinedCode.NOTFOUND, "你暂时没有关联的班级或学生，请联系管理员！");
    }

    @Override
    public boolean selectRole(Long roleId, Long userId) {
        Role one = roleService.getOne(roleId);
        User user = this.getOne(userId);
        user.setRoleCode(one.getRoleCode());
        user.setRoleName(one.getRoleName());
        User save = this.save(user);
        return save != null;
    }

    @Override
    public User findByUserNameAndPwd(String username, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username).eq("password", StaticUtil.md5Hex(password)));
        return user;
    }

    @Transactional(rollbackFor = CommonException.class)
    @Override
    @Async
    public void updateLoginTime(User user) {
        userMapper.updateById(new User(user.getId(), user.getLastLoginTime(), user.getLastLoginIp()));
    }


}
