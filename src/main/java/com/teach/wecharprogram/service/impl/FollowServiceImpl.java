package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.common.CommonException;
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
import org.apache.commons.lang3.StringUtils;
import com.teach.wecharprogram.util.DateUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
* Create Code Generator
* @Author ZengMin
* @Date 2019-06-15 18:37:25
*/

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    FollowMapper followMapper;

    @Override
    public Follow getOne(Long id){
        return followMapper.selectById(id);
    }

    @Override
    public List<Follow> list(Follow follow) {
        if(StringUtils.isNotBlank(follow.getCreateTimeQuery())){
            follow.setCreateTime(DateUtil.parseToDate(follow.getCreateTimeQuery()));
        }
        List<Follow> follows = followMapper.selectList(new QueryWrapper<>(follow));
        return follows;
    }

    @Override
    public Pager listByPage(Pager pager, Follow follow) {
        if(StringUtils.isNotBlank(follow.getCreateTimeQuery())){
            follow.setCreateTime(DateUtil.parseToDate(follow.getCreateTimeQuery()));
        }
        IPage<Follow> followIPage = followMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(follow));
        return Pager.of(followIPage);
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public Follow save(Follow follow) {
        if(Objects.nonNull(follow.getId())){
            followMapper.updateById(follow);
        }else {
            followMapper.insert(follow);
        }
        return follow;
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public boolean delete(String ids) {
        List<Long> list = Lists.newArrayList();
        if(ids.indexOf(",") != -1){
            List<String> asList = Arrays.asList(ids.split(","));
            asList.stream().forEach(o -> list.add(Long.valueOf(o)));
        }else {
            list.add(Long.valueOf(ids));
        }
        int i = followMapper.deleteBatchIds(list);
        return i > 0;
    }


}
