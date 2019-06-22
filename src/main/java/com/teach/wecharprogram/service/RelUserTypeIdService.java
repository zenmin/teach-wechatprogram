package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.RelUserTypeId;

import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-22 17:57:58
*/

public interface RelUserTypeIdService {

    RelUserTypeId getOne(Long id);

    List<RelUserTypeId> list(RelUserTypeId RelUserTypeId);

    Pager listByPage(Pager pager,RelUserTypeId RelUserTypeId);

    RelUserTypeId save(RelUserTypeId RelUserTypeId);

    boolean delete(String ids);

}
