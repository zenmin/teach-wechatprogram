package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.service.UpScoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.UpScore;
import com.teach.wecharprogram.mapper.UpScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.teach.wecharprogram.util.DateUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-29 22:14:39
 */

@Service
public class UpScoreServiceImpl implements UpScoreService {

    @Autowired
    UpScoreMapper upScoreMapper;

    @Override
    public UpScore getOne(Long id) {
        return upScoreMapper.selectById(id);
    }

    @Override
    public List<UpScore> list(UpScore upScore) {
        List<UpScore> upScores = upScoreMapper.selectList(new QueryWrapper<>(upScore));
        return upScores;
    }

    @Override
    public Pager listByPage(Pager pager, UpScore upScore) {
        IPage<UpScore> upScoreIPage = upScoreMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(upScore));
        return Pager.of(upScoreIPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void save(UpScore upScore) {
        if (Objects.nonNull(upScore.getId())) {
            upScoreMapper.updateById(upScore);
        } else {
            // 查询当前学生当前时间  有无记录
            UpScore exists = upScoreMapper.selectOne(new QueryWrapper<UpScore>().eq("studentId", upScore.getStudentId()).eq("date", upScore.getDate()));
            if (Objects.nonNull(exists)) {
                upScore.setId(exists.getId());
                upScoreMapper.updateById(upScore);
            } else {
                upScoreMapper.insert(upScore);
            }
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
        int i = upScoreMapper.deleteBatchIds(list);
        return i > 0;
    }


}
