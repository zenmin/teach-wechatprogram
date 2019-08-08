package com.teach.wecharprogram;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teach.wecharprogram.entity.StudentPhysical;
import com.teach.wecharprogram.util.JSONUtil;
import com.teach.wecharprogram.util.StaticUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/6/22 16:04
 */
public class Test1 {


    @Test
    public void test1() {
        Class clazz = StudentPhysical.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Map> list = Lists.newArrayList();
        for (Field field : declaredFields) {
            Excel annotation = field.getAnnotation(Excel.class);
            if (Objects.nonNull(annotation)) {
                Map<String, String> map = Maps.newLinkedHashMap();
                map.put("text", field.getName());
                map.put("val", annotation.name());
                list.add(map);
            }
        }
        System.out.println(JSONUtil.toJSONString(list));
    }
}
