package com.intplog.mcs.common;

import java.lang.reflect.Field;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/6/5 9:15
 */
public class CheckEntity {

    /**
     * 判断对象: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static boolean checkAllFieldIsNull(Object obj) throws IllegalAccessException {
        boolean flag = true;
        //遍历属性集合
        for (Field fs : obj.getClass().getDeclaredFields()) {
            // 设置属性是可以访问的(私有的也可以)
            fs.setAccessible(true);
            if (fs.get(obj) != null) {
                //只要有个属性不为空,那么就不是所有的属性值都为空
                flag=false;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断对象: 返回ture表示有属性为null  返回false表示所有属性都不是null
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static boolean checkFieldIsNull(Object obj) throws IllegalAccessException {
        boolean flag = false;
        //遍历属性集合
        for (Field fs : obj.getClass().getDeclaredFields()) {
            // 设置属性是可以访问的(私有的也可以)
            fs.setAccessible(true);
            if (fs.get(obj) == null) {
                //只要有个属性不为空,那么就不是所有的属性值都为空
                flag=true;
                break;
            }
        }
        return flag;
    }
}
