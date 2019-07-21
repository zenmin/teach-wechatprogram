package com.teach.wecharprogram.repostory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.teach.wecharprogram.entity.DO.CoursePlanCourseDo;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.DO.StudentAssessDo;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/20 18:55
 */
public interface CourseRepository {

    List<CoursePlanCourseDo> getMyPlanByClassesId(Long classesId, Long uid, Integer day);

    List<StudentAssessDo> getAllAssessList(Pager pager, String studentName, String text, Long uid);

}
