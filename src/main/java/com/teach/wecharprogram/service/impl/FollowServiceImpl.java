package com.teach.wecharprogram.service.impl;

import com.google.common.collect.Maps;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.entity.DO.StudentDo;
import com.teach.wecharprogram.repostory.FollowRepository;
import com.teach.wecharprogram.service.FollowService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Follow;
import com.teach.wecharprogram.mapper.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-13 20:43:30
 */

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    FollowMapper followMapper;

    @Autowired
    FollowRepository followRepository;

    @Override
    public Follow getOne(Long id) {
        return followMapper.selectById(id);
    }

    @Override
    public List<Follow> list(Follow follow) {
        List<Follow> follows = followMapper.selectList(new QueryWrapper<>(follow));
        return follows;
    }

    @Override
    public Pager listByPage(Pager pager, Follow follow) {
        IPage<Follow> followIPage = followMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(follow));
        return Pager.of(followIPage);
    }

    @Override
    @Transactional
    public boolean save(Follow follow) {
        // 是否已经关注
        Follow one = followMapper.selectOne(new QueryWrapper<Follow>().eq("studentId", follow.getStudentId()).eq("uid", follow.getUid()));
        if (Objects.nonNull(one)) {
            throw new CommonException(DefinedCode.ISEXISTS, "已关注该学生了！");
        } else {
            followMapper.insert(follow);
            return true;
        }

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
        int i = followMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public Map getMyFollow(Follow follow) {
        List<StudentDo> studentDoList = followRepository.getMyFollow(follow);
        Map<String, Object> result = Maps.newLinkedHashMap();
        // 按年龄分组
        Map<Integer, List<StudentDo>> map = studentDoList.stream().collect(Collectors.groupingBy(StudentDo::getAge));
        // 3 4 5岁
        Set<Map.Entry<Integer, List<StudentDo>>> entries = map.entrySet();
        for (Map.Entry m : entries) {
            Object key = m.getKey();
            if (Integer.parseInt(key.toString()) <= 3) {
                result.put("three", m.getValue());
            }

            if (Integer.parseInt(key.toString()) == 4) {
                result.put("four", m.getValue());
            }

            if (Integer.parseInt(key.toString()) == 5) {
                result.put("five", m.getValue());
            }

            if (Integer.parseInt(key.toString()) == 6) {
                result.put("eight", m.getValue());
            }

            if (Integer.parseInt(key.toString()) > 6) {
                result.put("gtEight", m.getValue());
            }
        }
        return result;
    }


}















