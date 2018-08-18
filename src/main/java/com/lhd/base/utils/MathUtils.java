package com.lhd.base.utils;
/*
 * 项目名:    BlueToothTest
 * 包名       com.lhd.base.tools
 * 文件名:    MathUtils
 * 创建者:    YHF
 * 创建时间:  2018/7/6 0006 on 14:34
 * 描述:     TODO
 */

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathUtils {


    //判断是否全是数字
    static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    //判断email格式是否正确
    static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /*
  将传入的double 转为 保留两位小数的str
   */
    public String getDoubleDecimalPoint(double db) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        return df.format(db);
    }

}
