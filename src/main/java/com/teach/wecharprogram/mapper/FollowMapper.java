package com.teach.wecharprogram.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.teach.wecharprogram.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-07-13 20:43:30
*/

public interface FollowMapper extends BaseMapper<Follow> {

    /**
     * 我的关注列表
     * @param page
     * @param queryWrapper
     * @return
     */
    @Override
    @Select("")
    IPage<Follow> selectPage(IPage<Follow> page, Wrapper<Follow> queryWrapper);
}
