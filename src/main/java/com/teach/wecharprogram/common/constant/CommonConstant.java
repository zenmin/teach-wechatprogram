package com.teach.wecharprogram.common.constant;


/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 21:11
 */
public class CommonConstant {

    public static final String TOKEN_KEY = "TEACH";

    public static final String INIT_PASSWORD = "123456";

    public static final int STATUS_OK = 1;  // ok

    public static final int STATUS_ERROR = 0;   // error

    public static final int STATUS_VALID_ERROR = 2;   // 角色信息待验证 / 角色审批不通过

    public static final String ROLE_HEADMASTER = "R_HEADMASTER";        //	校长
    public static final String ROLE_TEACHER    = "R_TEACHER";        // 教师
    public static final String ROLE_TRAIN      = "R_TRAIN";          // 教练
    public static final String ROLE_FAMILY     = "R_FAMILY";         // 家长
    public static final String ROLE_ADMIN      = "R_ADMIN";        // 超级管理员

    public static final int STATUS_APPROVED_ERROR = 0;  // 审批不通过

    public static final int STATUS_APPROVED_OK = 1;     // 审批通过

    public static final int STATUS_VALID_PROCESS = 2;   // 审批中

    public static final int REL_SCHOOL = 1;  // 学校

    public static final int REL_CLASS = 2;  // 班级

    public static final int REL_STUDENTS = 3;  // 学生 家长
}
