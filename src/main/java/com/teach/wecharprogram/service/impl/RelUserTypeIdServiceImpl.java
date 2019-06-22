package com.teach.wecharprogram.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.teach.wecharprogram.entity.RelUserTypeId;
import com.teach.wecharprogram.service.RelUserTypeIdService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.mapper.Rel_user_typeidMapper;
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
* @Date 2019-06-22 17:57:58
*/

@Service
public class RelUserTypeIdServiceImpl implements RelUserTypeIdService {

    @Autowired
    Rel_user_typeidMapper rel_user_typeidMapper;

    @Override
    public RelUserTypeId getOne(Long id){
        return rel_user_typeidMapper.selectById(id);
    }

    @Override
    public List<RelUserTypeId> list(RelUserTypeId RelUserTypeId) {
        if(StringUtils.isNotBlank(RelUserTypeId.getCreateTimeQuery())){
            RelUserTypeId.setCreateTime(DateUtil.parseToDate(RelUserTypeId.getCreateTimeQuery()));
        }
        List<RelUserTypeId> rel_user_typeids = rel_user_typeidMapper.selectList(new QueryWrapper<>(RelUserTypeId));
        return rel_user_typeids;
    }

    @Override
    public Pager listByPage(Pager pager, RelUserTypeId RelUserTypeId) {
        if(StringUtils.isNotBlank(RelUserTypeId.getCreateTimeQuery())){
            RelUserTypeId.setCreateTime(DateUtil.parseToDate(RelUserTypeId.getCreateTimeQuery()));
        }
        IPage<RelUserTypeId> rel_user_typeidIPage = rel_user_typeidMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(RelUserTypeId));
        return pager.of(rel_user_typeidIPage);
    }

    @Override
    @Transactional
    public RelUserTypeId save(RelUserTypeId RelUserTypeId) {
        if(Objects.nonNull(RelUserTypeId.getId())){
            rel_user_typeidMapper.updateById(RelUserTypeId);
        }else {
            rel_user_typeidMapper.insert(RelUserTypeId);
        }
        return RelUserTypeId;
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
        int i = rel_user_typeidMapper.deleteBatchIds(list);
        return i > 0;
    }


}
