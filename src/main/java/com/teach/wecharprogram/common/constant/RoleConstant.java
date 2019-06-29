package com.teach.wecharprogram.common.constant;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/6/29 14:37
 */
public class RoleConstant {

    public static final int TEACHER = 1;

    public static final int TRAIN = 2;

    public static final int FAMILY = 3;

    public enum ROLE {
        ROLE_HEADMASTER1(CommonConstant.ROLE_HEADMASTER, " 校长"),
        ROLE_TEACHER2(CommonConstant.ROLE_TEACHER, "教师"),
        ROLE_TRAIN3(CommonConstant.ROLE_TRAIN, "教练"),
        ROLE_FAMILY4(CommonConstant.ROLE_FAMILY, "家长"),
        ROLE_ADMIN5(CommonConstant.ROLE_ADMIN, "管理员");

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
