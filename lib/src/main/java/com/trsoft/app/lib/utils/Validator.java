package com.trsoft.app.lib.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 验证类
 */

public class Validator {
    //region 验证

    /**
     * 判断是否为空字符
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        boolean is = true;
        is = str == null || str.trim().equals("");
        return is;
    }

    /**
     * 判断字符是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        boolean is = str == null || str.trim().equals("");
        return is;
    }

    /**
     * 判断不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {

        return !isBlank(str);
    }

    /**
     * 判断是否为浮点数或者整数
     *
     * @param str
     * @return true Or false
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断是否为正确的邮件格式
     *
     * @param str
     * @return boolean
     */
    public static boolean isEmail(String str) {
        if (isBlank(str)) {
            return false;
        }
        return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
    }

    /**
     * 判断字符串是否为合法手机号 11位 13 14 15 18开头
     *
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern p = Pattern.compile("^(1[3,4,5,7,8][0-9])\\d{8}$");
        Matcher m = p.matcher(str);

        return m.matches();
    }

    /**
     * 验证身份证号码
     *
     * @param str
     * @return
     */
    public static boolean isIdentifyCardNumber(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern p = Pattern.compile("^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$");
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 判断是不是手机号
     *
     * @param str
     * @return
     */
    public static boolean isNotMobile(String str) {

        return !isMobile(str);
    }

    /**
     * 由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间
     * ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$
     * 分开来注释一下：
     * ^ 匹配一行的开头位置
     * (?![0-9]+$) 预测该位置后面不全是数字
     * (?![a-zA-Z]+$) 预测该位置后面不全是字母
     * [0-9A-Za-z] {8,16} 由8-16位数字或这字母组成
     * $ 匹配行结尾位置
     * 注：(?!xxxx) 是正则表达式的负向零宽断言一种形式，标识预该位置后不是xxxx字符。
     *
     * @param str
     * @return
     */
    public static boolean isNumAndChar6_20(String str) {
        if (isBlank(str)) {
            return false;
        }
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        return str.matches(regex);
    }

    /**
     * 判断字符串是数字并且位数在传入参数的范围内
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean isNumber(String str, int min, int max) {
        if (isBlank(str)) {
            return false;
        }
        if (min > 0 && max > 0 && min < max) {
            String regex = "^[0-9]{" + String.valueOf(min) + "," + String.valueOf(max) + "}$";
            return str.matches(regex);
        } else {
            return false;
        }
    }


    public static boolean isCharCountOk(String str, int min, int max) {
        int count = str.length();
        int countChinest = getChineseCharacterCount(str);

        int count2 = count + countChinest;//一个汉子顶二个字母
        return count2 > min && count2 < max;
    }
    //endregion
    //region 字符串处理

    /**
     * 得到字符串中的中文字符
     *
     * @param str
     * @return
     */
    public static int getChineseCharacterCount(String str) {
        int ccCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isChinese(str.charAt(i))) {
                ccCount++;
            }
        }

        return ccCount;
    }

    /**
     * 判断字符串是否包括英文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!isChinese(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为中文
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 转换为 驼峰命名法的方法  get set
     * @param fildeName
     * @return
     */
    private static String getMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 保留几位小数
     * @return
     */
    public static String format(Double x, Integer maximumFractionDigits) {
//        String s= null;
//        try{
//            NumberFormat ddf1= NumberFormat.getNumberInstance() ;
//            if(maximumFractionDigits==null){
//                ddf1.setMaximumFractionDigits(2);
//            }else{
//                ddf1.setMaximumFractionDigits(maximumFractionDigits);
//            }
//            s = ddf1.format(x);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        if(x != null){
            DecimalFormat df   = new DecimalFormat("######0.00");
            return df.format(x);
        }else{
            return "0";
        }
    }

    /**
     * 判断字符串是否合法
     * @return
     */
    public static String stringFilter(String str)throws PatternSyntaxException {
        if(str != null) {
            // 只允许字母、数字和汉字
            String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("").trim();
        }else{
            return str;
        }
    }
    //endregion

}
