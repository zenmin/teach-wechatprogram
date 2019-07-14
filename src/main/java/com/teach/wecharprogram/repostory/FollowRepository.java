package com.teach.wecharprogram.repostory;

import com.teach.wecharprogram.entity.DO.StudentDo;
import com.teach.wecharprogram.entity.Follow;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/14 11:47
 */
public interface FollowRepository {

    List<StudentDo> getMyFollow(Follow follow);

}
