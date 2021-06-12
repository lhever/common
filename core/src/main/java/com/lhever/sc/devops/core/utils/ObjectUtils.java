package com.lhever.sc.devops.core.utils;

import io.swagger.models.auth.In;

public class ObjectUtils {
    private static final char PACKAGE_SEPARATOR_CHAR = '.';


    public static <T> T checkNotNull(T arg, String text) {
        if (arg == null) {
            throw new NullPointerException(text);
        }
        return arg;
    }

    public static <T> T cast(Class<T> clazz, Object obj) {
        T cast = clazz.cast(obj);
        return cast;
    }

    public static boolean isTrue(Boolean bool) {
        if (bool != null && bool) {
            return true;
        }
        return false;
    }


    public static boolean equals(Integer a, Integer b) {
        if (a == null && b == null) {
            return true;
        } else if (a == null && b != null) {
            return false;
        } else if (a != null && b == null) {
            return false;
        } else {
            return a.intValue() == b.intValue();
        }
    }


    /**
     * 判断传入的对象，是否都不为空
     * 如果传入的元素是集合类或数组类型，并不进一步检查其中的元素是否为空
     *
     * @param objs
     * @return
     * @author lihong10 2018/12/27 19:52
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 19:52
     * @modify by reason:{原因}
     */
    public static boolean isNoneNull(final Object... objs) {
        return !isAnyNull(objs);
    }

    /**
     * 判断传入的对象列表中是否有空对象，只要某个对象为null, 就返回true
     *
     * @param objs
     * @return
     * @author lihong10 2018/12/27 19:52
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 19:52
     * @modify by reason:{原因}
     */
    public static boolean isAnyNull(final Object... objs) {
        if (objs == null || objs.length == 0) {
            return true;
        }

        for (Object obj : objs) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    public static String simpleClassName(Object o) {
        if (o == null) {
            return "null_object";
        } else {
            return simpleClassName(o.getClass());
        }
    }

    /**
     * Generates a simplified name from a {@link Class}.  Similar to {@link Class#getSimpleName()}, but it works fine
     * with anonymous classes.
     */
    public static String simpleClassName(Class<?> clazz) {
        String className = checkNotNull(clazz, "clazz").getName();
        final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if (lastDotIdx > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }



}
