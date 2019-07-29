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
* @Date 2019-07-29 22:14:39
*/

@Service
public class UpScoreServiceImpl implements UpScoreService {

    @Autowired
    UpScoreMapper up_scoreMapper;

    @Override
    public UpScore getOne(Long id){
        return up_scoreMapper.selectById(id);
    }

    @Override
    public List<UpScore> list(UpScore up_score) {
        List<UpScore> up_scores = up_scoreMapper.selectList(new QueryWrapper<>(up_score));
        return up_scores;
    }

    @Override
    public Pager listByPage(Pager pager, UpScore up_score) {
        IPage<UpScore> up_scoreIPage = up_scoreMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(up_score));
        return Pager.of(up_scoreIPage);
    }

    @Override
    @Transactional
    public UpScore save(UpScore up_score) {
        if(Objects.nonNull(up_score.getId())){
            up_scoreMapper.updateById(up_score);
        }else {
            up_scoreMapper.insert(up_score);
        }
        return up_score;
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
        int i = up_scoreMapper.deleteBatchIds(list);
        return i > 0;
    }


}
