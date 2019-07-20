package com.teach.wecharprogram.repostory;

import com.teach.wecharprogram.entity.DO.CoursePlanCourseDo;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/20 18:55
 */
public interface CourseRepository {

    List<CoursePlanCourseDo> getMyPlanByClassesId(Long classesId, Long uid, Integer day);

}
