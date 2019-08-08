package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.entity.IndexVo;
import com.teach.wecharprogram.repostory.StudentPhysicalRepository;
import com.teach.wecharprogram.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/8/8 20:31
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    StudentPhysicalRepository studentPhysicalRepository;

    @Override
    public IndexVo getIndex() {
        IndexVo index = studentPhysicalRepository.getIndex();
        return index;
    }


}
