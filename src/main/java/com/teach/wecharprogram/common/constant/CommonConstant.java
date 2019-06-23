package com.teach.wecharprogram.common.constant;


/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 21:11
 */
public class CommonConstant {

    public static final String TOKEN_KEY = "ZhuoAn";

    public static final String INIT_PASSWORD = "123456";

    public static final int STATUS_OK = 1;  // ok

    public static final int STATUS_ERROR = 0;   // error

    public static final int STATUS_VALID_ERROR = 2;   // 角色信息待验证 / 角色审批不通过

    public static final String ROLE_HEADMASTER = "R_HEADMASTER";        //	校长
    public static final String ROLE_TEACHER = "R_TEACHER";     // 教师
    public static final String ROLE_TRAIN = "R_TRAIN";    // 教练
    public static final String ROLE_FAMILY = "R_FAMILY";    // 家长
    public static final String ROLE_ADMIN = "R_ADMIN";        // 超级管理员


    public enum MEDIA_TYPE {
        MEDIA_IMAGE(1, "图片"),
        MEDIA_VIDEO(2, "视频"),
        MEDIA_MUSIC(3, "音频");

        private Integer code;

        private String value;

        MEDIA_TYPE(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public static String getValue(Integer code) {
            MEDIA_TYPE[] values = MEDIA_TYPE.values();
            for (MEDIA_TYPE m : values) {
                if (m.code == code) {
                    return m.value;
                }
            }
            return null;
        }
    }

    public enum ROLE {
        ROLE_HEADMASTER1(ROLE_HEADMASTER, " 校长"),
        ROLE_TEACHER2(ROLE_TEACHER, "教师"),
        ROLE_TRAIN3(ROLE_TRAIN, "教练"),
        ROLE_FAMILY4(ROLE_FAMILY, "家长"),
        ROLE_ADMIN5(ROLE_ADMIN, "管理员");

        private String code;

        private String name;

        ROLE(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getName(String code) {
            ROLE[] values = ROLE.values();
            for (ROLE m : values) {
                if (m.code.equals(code)) {
                    return m.name;
                }
            }
            return null;
        }

        public static String getCode(String name) {
            ROLE[] values = ROLE.values();
            for (ROLE m : values) {
                if (m.code.equals(name)) {
                    return m.name;
                }
            }
            return null;
        }
    }

}
