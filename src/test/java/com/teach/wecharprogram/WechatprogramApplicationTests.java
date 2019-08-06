package com.teach.wecharprogram;

import com.teach.wecharprogram.components.business.RedisUtil;
import com.teach.wecharprogram.entity.StudentPhysical;
import com.teach.wecharprogram.mapper.StudentPhysicalMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatprogramApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    StudentPhysicalMapper studentPhysicalMapper;

    @Test
    public void contextLoads() {
//        Long classesId, Long studentId, Double allScore, String date, Long createUid, String createUserName, Date updateTime
        studentPhysicalMapper.insert(new StudentPhysical(1142383170826596354L, 1142394629863948289L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383170826596354L, 1142394630048497666L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383170826596354L, 1142394630795083778L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383170826596354L, 1142394631084490754L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383170826596354L, 1142394631248068609L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383170826596354L, 1142394631512309762L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));

        studentPhysicalMapper.insert(new StudentPhysical(1142383381670064130L, 1142394682703790081L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383381670064130L, 1142394682934476802L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383381670064130L, 1142394683676868609L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383381670064130L, 1142394683911749633L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383381670064130L, 1142394684134047745L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));
        studentPhysicalMapper.insert(new StudentPhysical(1142383381670064130L, 1147458939017781250L, Math.random() * 10d, "2019-07-29", 1142326137901330433L, "高松", new Date()));

    }

}
