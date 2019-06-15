package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.service.LogsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Logs;
import com.teach.wecharprogram.mapper.LogsMapper;
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
 * @Date 2019-06-15 17:29:59
 */

@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    LogsMapper logsMapper;

    @Override
    public Logs getOne(Long id) {
        return logsMapper.selectById(id);
    }

    @Override
    public List<Logs> list(Logs logs) {
        if (StringUtils.isNotBlank(logs.getCreateTimeQuery())) {
            logs.setCreateTime(DateUtil.parseToDate(logs.getCreateTimeQuery()));
        }
        List<Logs> logss = logsMapper.selectList(new QueryWrapper<>(logs));
        return logss;
    }

    @Override
    public Pager listByPage(Pager pager, Logs logs) {
        if (StringUtils.isNotBlank(logs.getCreateTimeQuery())) {
            logs.setCreateTime(DateUtil.parseToDate(logs.getCreateTimeQuery()));
        }
        IPage<Logs> logsIPage = logsMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(logs));
        return pager.of(logsIPage);
    }

    @Override
    @Transactional
    @Async
    public void save(Logs logs) {
        logsMapper.insert(logs);
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
        int i = logsMapper.deleteBatchIds(list);
        return i > 0;
    }


}
