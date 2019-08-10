package com.teach.wecharprogram.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.service.LogsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Logs;
import com.teach.wecharprogram.mapper.LogsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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
@Slf4j
public class LogsServiceImpl implements LogsService {

    @Autowired
    LogsMapper logsMapper;

    /**
     * 每周一任务 清理访问日志  清理七天前的访问日志
     */
    @Scheduled(cron = "* 10 0 * * MON")
    public void everyWeekExecute() {
        log.info("清理访问日志开始...");
        long l = DateUtil.plusDays(System.currentTimeMillis(), -7L);
        String s = DateUtil.millisToDateTime(l, "yyyy-MM-dd");
        logsMapper.delete(new UpdateWrapper<Logs>().lt("date", s));
        log.info("清理访问日志结束...");
    }

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
        return Pager.of(logsIPage);
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    @Async
    public void save(Logs logs) {
        logsMapper.insert(logs);
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
        int i = logsMapper.deleteBatchIds(list);
        return i > 0;
    }


}
