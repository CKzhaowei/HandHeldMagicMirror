package com.threedai.handheldmagicmirror.utils;

import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 参数检查类
 */
public final class ParamsCheckUtils {

    private ParamsCheckUtils() { }

    /**
     * 检查参数是否为空，如果是空则抛出异常，这样是为了提前中断程序，避免异常往下走的更深。
     * @param reference 传入的类型
     * @param errorMsg 错误信息
     */
    public static <T> void checkNotNull(T reference, @Nullable Object errorMsg) {
        if(reference == null) {
            throw new NullPointerException(String.valueOf(errorMsg));
        }
    }

    /**
     * 检查一组参数
     * @param args 入参
     * @return 是否有空 true为不通过 false为通过
     */
    public static boolean paramsIsLegal(Object... args) {
        boolean flag = true;
        if (args != null) {
            for (Object param : args) {
                //判断是否为Null
                if (param == null) {
                    flag = false;
                    break;
                }
                //字符串等类型还需要做多重判断
                if (param instanceof String && param.toString().trim().length() == 0) {
                    flag = false;
                    break;
                }
                if (param.getClass().isArray() && Array.getLength(param) == 0) {
                    flag = false;
                    break;
                }
                if (param instanceof Collection && ((Collection) param).isEmpty()) {
                    flag = false;
                    break;
                }
                if (param instanceof Map && ((Map) param).isEmpty()) {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

}
