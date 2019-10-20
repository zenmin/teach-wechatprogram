package com.teach.wecharprogram.mapper;

import com.teach.wecharprogram.entity.DO.UpScoreDo;
import com.teach.wecharprogram.entity.UpScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-29 22:14:39
 */
public interface UpScoreMapper extends BaseMapper<UpScore> {

    @Select("<script>SELECT s.*,u.name as studentName FROM up_score s left join student u on s.studentId = u.id where s.classesId in " +
            "<foreach collection=\"classesIds\" item=\"id\" open=\"(\" close=\")\" separator=\",\">#{id}</foreach> " +
            " and u.status = 1 " +
            "limit #{size}</script>")
    List<UpScoreDo> selectTopUpFive(@Param("classesIds") Set<Long> classesIds, @Param("size") int size);
}
