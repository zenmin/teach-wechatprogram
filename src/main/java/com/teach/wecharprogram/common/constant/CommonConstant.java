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

    public static final int COMPANY = 1;    //公司

    public static final int PERSON = 2;     //个人

    public static final String UPLOAD_USER_KEY = "ZA";

    public static final int MEDIA_IMAGE = 1;    //图片

    public static final int MEDIA_VIDEO = 2;    //视频

    public static final int MEDIA_MUSIC = 3;    //音频

    public static final String ROLE_ADMIN = "R_ADMIN";       // 管理员
    public static final String ROLE_USER = "R_USER";       // 普通用户
    public static final String ROLE_SUPPLIER = "R_SUPPLIER";   // 供应商
    public static final String ROLE_ORGADMIN = "R_ORGADMIN";   // 旅行社的管理账号


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
        ROLE_ADMIN("R_ADMIN", " 管理员"),
        ROLE_USER("R_USER", "普通用户"),
        ROLE_SUPPLIER("R_SUPPLIER", "供应商"),
        ROLE_ORGADMIN("R_ORGADMIN", "旅行社的管理账号");

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
