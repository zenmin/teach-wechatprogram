package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.service.StudentPhysicalScoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.StudentPhysicalScore;
import com.teach.wecharprogram.mapper.StudentPhysicalScoreMapper;
import com.teach.wecharprogram.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:38:53
 */

@Service
public class StudentPhysicalScoreServiceImpl implements StudentPhysicalScoreService {

    @Autowired
    StudentPhysicalScoreMapper studentPhysicalScoreMapper;

    @Override
    public StudentPhysicalScore getOne(Long id) {
        return studentPhysicalScoreMapper.selectById(id);
    }

    @Override
    public List<StudentPhysicalScore> list(StudentPhysicalScore studentPhysicalScore) {
        List<StudentPhysicalScore> studentPhysicalScores = studentPhysicalScoreMapper.selectList(new QueryWrapper<>(studentPhysicalScore));
        return studentPhysicalScores;
    }

    @Override
    public Pager listByPage(Pager pager, StudentPhysicalScore studentPhysicalScore) {
        IPage<StudentPhysicalScore> studentPhysicalScoreIPage = studentPhysicalScoreMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(studentPhysicalScore));
        return Pager.of(studentPhysicalScoreIPage);
    }

    @Override
    @Transactional
    public StudentPhysicalScore save(StudentPhysicalScore studentPhysicalScore) {
        Integer timesNum = studentPhysicalScore.getTimesNum();
        if (Objects.isNull(timesNum)) {
            throw new CommonException(DefinedCode.PARAMSERROR, "请填写次数！");
        }
        if (Objects.nonNull(studentPhysicalScore.getId())) {
            studentPhysicalScoreMapper.updateById(studentPhysicalScore);
        } else {
            Long physicalId = studentPhysicalScore.getPhysicalId();
            StaticUtil.validateObject(physicalId);
            studentPhysicalScore.setUpdateTime(new Date());
            // 查当前评测id  当前次数是否存在
            StudentPhysicalScore score = studentPhysicalScoreMapper.selectOne(new QueryWrapper<StudentPhysicalScore>().eq("physicalId", physicalId).eq("timesNum", timesNum));
            if (Objects.isNull(score)) {
                studentPhysicalScoreMapper.insert(studentPhysicalScore);
            } else {
                studentPhysicalScore.setId(score.getId());
                studentPhysicalScoreMapper.updateById(studentPhysicalScore);
            }
        }
        return studentPhysicalScore;
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
        int i = studentPhysicalScoreMapper.deleteBatchIds(list);
        return i > 0;
    }


}
