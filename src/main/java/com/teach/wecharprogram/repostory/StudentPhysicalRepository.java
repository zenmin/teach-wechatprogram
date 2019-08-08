package com.teach.wecharprogram.repostory;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.DO.StudentPhysicalDo;
import com.teach.wecharprogram.entity.IndexVo;
import com.teach.wecharprogram.entity.StudentPhysical;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/8/7 19:14
 */
public interface StudentPhysicalRepository {

    Pager<StudentPhysicalDo> selectPage(Pager pager, StudentPhysicalDo studentPhysical);

    IndexVo getIndex();
}
