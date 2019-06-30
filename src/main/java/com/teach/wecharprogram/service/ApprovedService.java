package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Approved;
import com.teach.wecharprogram.entity.vo.ApprovedVo;

import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-22 16:36:34
*/

public interface ApprovedService {

    Approved getOne(Long id);

    List<Approved> list(Approved approved);

    Pager listByPage(Pager pager,Approved approved);

    Approved save(Approved approved);

    boolean delete(String ids);

    boolean agree(ApprovedVo approvedVo);

    long count(Approved approved);

}
