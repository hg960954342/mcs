package com.intplog.mcs.utils;

import com.intplog.mcs.bean.viewmodel.LicenseModel;

import static com.intplog.ractools.utils.LicenseMakeUtils.sysDate;

/**
 * @author liaoliming
 * @Date 2019-06-18 20:26
 */
public class LicenseUtils {
    /**
     * 检验授权是否可用(待完善)
     * @param model
     * @return
     */
    public static boolean check(LicenseModel model){
        if (model != null) {
            String endDate = model.getEndDate();
            if(endDate!=null){
                //比较时间是否超期,如果左边大于右边的话返回的是正整数，等于是0，小于的话就是负整数
                //这里有一个bug，就是用户要是反拨了服务器时间，激活码验证的时候可能会通过
                int i = endDate.compareTo(sysDate);
                if(i>0){
                    return true;
                }
            }
        }
        return false;
    }
}
