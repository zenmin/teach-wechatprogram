package com.teach.wecharprogram.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/8/8 20:31
 */
@Data
public class IndexVo {

    Long schoolNum;

    Long classesNum;

    Long studentNum;

    Long userNum;

    public static IndexVo of(List<Long> counts) {
        IndexVo indexVo = new IndexVo();
        indexVo.setClassesNum(counts.get(0));
        indexVo.setStudentNum(counts.get(1));
        indexVo.setUserNum(counts.get(2));
        indexVo.setSchoolNum(counts.get(3));
        return indexVo;
    }
}
