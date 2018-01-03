package com.yytx.app.lib.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期操作工具类.
 *
 * @author shimiso
 */

public class DateUtil {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat SDF_MD = new SimpleDateFormat("MM月dd日");

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static long reduce(String date1, String date2, String format) {
        if (Validator.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        Date d1 = DateUtil.getDate(date1, format);
        Date d2 = DateUtil.getDate(date2, format);

        return d2.getTime() - d1.getTime();
    }


    public static String getDateNext(Date date, int dayInterval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayInterval);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        }

        return age;
    }


    public static String getDateStr(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        String d = date2Str(c, "yyyy-MM-dd");
        return d;
    }

    public static String getTimeStr(int HH, int mm) {
        StringBuilder sb = new StringBuilder();
        if (HH < 10) {
            sb.append("0").append(HH);
        } else {
            sb.append(HH);
        }
        sb.append(":");
        if (mm < 10) {
            sb.append("0").append(mm);
        } else {
            sb.append(mm);
        }
        return sb.toString();
    }

    public static Date str2Date(String str) {
        return str2Date(str, null);
    }

    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);

    }

    public static Calendar str2Calendar(String str, String format) {

        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;

    }

    public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
        return date2Str(c, null);
    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }

    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

    public static String getCurrDateStr(String format) {
        SimpleDateFormat sdf;
        if (Validator.isBlank(format)) {
            sdf = new SimpleDateFormat(FORMAT);
        } else {
            sdf = new SimpleDateFormat(format);
        }

        String s = sdf.format(new Date());
        return s;
    }

    public static String getCurrDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        String s = sdf.format(new Date());
        return s;
    }
//	/**
//	 * 获得当前日期的字符串格式
//	 * 
//	 * @param format
//	 * @return
//	 */
//	public static String getCurDateStr(String format) {
//		Calendar c = Calendar.getInstance();
//		return date2Str(c, format);
//	}

    // 格式到秒
    public static String getMillon(long time) {

        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time);

    }

    // 格式到天
    public static String getDay(long time) {

        return new SimpleDateFormat("yyyy-MM-dd").format(time);

    }

    // 格式到分钟
    public static String getMinute(long time) {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(time);

    }

    // 格式到毫秒
    public static String getSMillon(long time) {

        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);

    }


    public static void calcuInterval(String start, String end, CallbackTime callback) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(start);
            Date d2 = format.parse(end);
            calcuInterval(d1, d2, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void calcuInterval(Date start, Date end, CallbackTime callback) {
        try {
            //毫秒ms
            long diff = end.getTime() - start.getTime();

            long seconds = diff / 1000 % 60;
            long minutes = diff / (60 * 1000) % 60;
            long hours = diff / (60 * 60 * 1000) % 24;
            long days = diff / (24 * 60 * 60 * 1000);
            callback.onCallback(days, hours, minutes, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getTimeIntervalStr(Date start, Date end) {

        return getTimeIntervalStr(start.getTime(), end.getTime());
    }

    public static String getTimeIntervalStr(long start, long end) {
        StringBuilder sb = new StringBuilder();
        try {
            //毫秒ms
            long diff = end - start;

            long seconds = diff / 1000 % 60;
            long minutes = diff / (60 * 1000) % 60;
            long hours = diff / (60 * 60 * 1000) % 24;
            long days = diff / (24 * 60 * 60 * 1000);

            if (days > 0) {
                sb.append(days).append("天");
            }
            if (hours > 0) {
                sb.append(hours).append("小时");
            }
            if (minutes > 0) {
                sb.append(minutes).append("分");
            }
            if (seconds > 0) {
                sb.append(seconds).append("秒");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getTimeInterval(long start, long end) {
        StringBuilder sb = new StringBuilder();
        try {
            //毫秒ms
            long diff = end - start;

            long seconds = diff / 1000 % 60;
            long minutes = diff / (60 * 1000) % 60;
            long hours = diff / (60 * 60 * 1000) % 24;
            long days = diff / (24 * 60 * 60 * 1000);

            if (days > 0) {
                if (days > 9) {
                    sb.append(days).append(":");
                } else {
                    sb.append("0").append(days).append(":");
                }
            }
            if (hours > 9) {
                sb.append(hours).append(" : ");
            } else if(hours>=0){
                sb.append("0").append(hours).append(" : ");
            }
            if (minutes > 9) {
                sb.append(minutes).append(" : ");
            } else if(minutes>=0) {
                sb.append("0").append(minutes).append(" : ");
            }if (seconds > 9) {
                sb.append(seconds);
            } else if(seconds>=0){
                sb.append("0").append(seconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getWeek(Date date) {

        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int days = cal.get(Calendar.DAY_OF_WEEK);
        return weeks[days - 1];
    }

    public interface CallbackTime {
        void onCallback(long days, long hours, long minutes, long seconds);
    }


    public static Date getDate(String dateStr, String format) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {

            date = sdf.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 截取年 2012-01-01
     *
     * @param date
     * @return
     */
    public static String getYear(String date) {
        String year = null;
        try {
            year = date.substring(0, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return year;
    }

    //20140501
    public static String getMonth(String date) {
        String month = null;
        try {
            month = date.substring(5, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return month;
    }

    public static String getDay(String date) {
        String day = null;
        try {
            day = date.substring(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * @param birthDate
     * @return
     */
    public static CharSequence getAge(String birthDate) {
        return null;
    }

    public static String getInterval(int seconds) { //传入的时间格式必须类似于2012-8-21 17:53:20这样的格式
        String interval = null;

        //用现在距离1970年的时间间隔new Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔  
        long time = new Date().getTime() / 1000 - seconds;// 得出的时间间隔是秒

        if (time / 3600 < 24 && time / 3600 >= 1) {
            //如果时间间隔小于24小时则显示多少小时前
            int h = (int) (time / 3600);//得出的时间间隔的单位是小时
            interval = h + "小时前";

        } else if (time / 60 < 60 && time / 60 >= 1) {
            //如果时间间隔小于60分钟则显示多少分钟前
            int m = (int) (time / 60);//得出的时间间隔的单位是分钟
            interval = m + "分钟前";
        } else if (time < 60 && time >= 10) {
            //如果时间间隔小于60秒则显示多少秒前
            interval = time + "秒前";
        } else if (time < 10 && time >= 0) {
            //如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒
            interval = "刚刚";
        } else {
            //大于24小时，则显示正常的时间，但是不显示秒  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            interval = sdf.format(seconds * 1000);
        }
        return interval;
    }

    /**
     * 加分钟
     *
     * @param startTime
     * @param mm
     * @return
     */
    public static Date addMinute(Date startTime, int mm) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.MINUTE, mm);
        return calendar.getTime();
    }

    /**
     * 加天数
     *
     * @param startTime
     * @param day
     * @return
     */
    public static Date addDay(Date startTime, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 加月份
     *
     * @param startTime
     * @param mm
     * @return
     */
    public static Date addMM(Date startTime, int mm) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.MONTH, mm);
        return calendar.getTime();
    }

    public static int getCurrSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }


    /**
     * 计算两个日期之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            date1 = str2Date(date2Str(date1, "yyyy-MM-dd 00:00"), "yyy-MM-dd HH:mm");
            date2 = str2Date(date2Str(date2, "yyyy-MM-dd 00:00"), "yyy-MM-dd HH:mm");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(date2);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        }
        return 0;
    }

    /**
     * 获取当前时间的yyyy-MM-dd字符串格式
     *
     * @return
     */
    public static String getCurrentDateForSDF() {
        return getCurrDateStr("yyyy-MM-dd");
    }

    /**
     * 获取指定calendar时间的yyyy-MM-dd字符串形式
     *
     * @param c
     * @return
     */
    public static String getCalendarStr(Calendar c) {
        return date2Str(c, "yyyy-MM-dd");
    }

    /**
     * 获取特殊形式的日期（5月1日）
     *
     * @return
     */
    public static String getMMDDSpecial(String dateStr) {
        Date date = str2Date(dateStr, "yyyy-MM-dd");
        return date2Str(date, "MM月dd日");
    }

    /**
     * 获取特殊形式的时间(hh:mm）
     *
     * @param dateStr
     * @return
     */
    public static String getHHMMSpecial(String dateStr) {
        Date date = str2Date(dateStr, "yyyy-MM-dd");
        return date2Str(date, "hh:mm");
    }

    /**
     * 获取特殊形式的时间(hh:mm）
     *
     * @param dateStr
     * @return
     */
    public static String getHHMMFromDate(String dateStr) {
        Date date = str2Date(dateStr, "yyyy-MM-dd HH:mm:ss");
        return date2Str(date, "HH:mm");
    }

    /**
     * 获取特殊形式的日期（05-01）
     *
     * @return
     */
    public static String getMMDD(String dateStr) {
        Date date = str2Date(dateStr, "yyyy-MM-dd");
        return date2Str(date, "MM-dd");
    }

    /**
     * 计算日期是星期几
     *
     * @param dateStr
     * @return
     */
    public static String calculateWeek(String dateStr) {
        Date date = str2Date(dateStr, "yyyy-MM-dd");
        return getWeek(date);
    }

    /**
     * 判断两个时间的大小
     *
     * @param time1 范围的开始时间
     * @param time2 要判断是否在范围中的时间
     * @param time3 范围的结束时间
     * @return
     * @throws ParseException
     */
    public static boolean compareTime(String time1, String time2, String time3) throws ParseException {

        DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();

        c1.setTime(df.parse(time1));
        c2.setTime(df.parse(time2));
        // 暂时规定为24:00 用户只能输入至24:00
        c3.setTime(df.parse("24:00"));

        if ((time1.compareTo(time2) <= 0) && (time2.compareTo(time3) <= 0)) {
            //time2在规定的时间范围内
            return false;
        }
        // time2不再规定的时间范围内
        return true;
    }

    /**
     * 判断time2是否在时间区间范围内
     *
     * @param time1 范围的开始时间
     * @param time2 要判断是否在范围中的时间
     * @param time3 范围的结束时间
     * @return
     * @throws ParseException
     */
    public static boolean compareCalendarTimePeriod(String time1, String time2, String time3) throws ParseException {

        DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        Calendar c4 = Calendar.getInstance();
        Calendar c5 = Calendar.getInstance();

        // 暂时规定为24:00 用户只能输入至24:00
        c4.setTime(df.parse("23:59"));
        c5.setTime(df.parse("00:00"));

        if (Validator.isNotEmpty(time1) && Validator.isNotEmpty(time2)) {
            c1.setTime(df.parse(time1));
            c2.setTime(df.parse(time2));
            if ((c1.compareTo(c2) <= 0) && (c2.compareTo(c4) <= 0)) {
                //验证非正常,需要担保的时间段
                return false;
            } else {
                if (Validator.isNotEmpty(time3)) {
                    c3.setTime(df.parse(time3));
                    if ((c5.compareTo(c2) <= 0) && (c2.compareTo(c3) <= 0)) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }

    /**
     * 计算两个日期之间所有的日期
     *
     * @param begin
     * @param end
     * @return
     */
    public static List<Date> getBetweenDates(Date begin, Date end) {
        List<Date> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while (begin.getTime() <= end.getTime()) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }

    /**
     * 判断两个日期之间的大小
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(String date1, String date2) {
        try {
            Date dt1 = SDF.parse(date1);
            Date dt2 = SDF.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

}
