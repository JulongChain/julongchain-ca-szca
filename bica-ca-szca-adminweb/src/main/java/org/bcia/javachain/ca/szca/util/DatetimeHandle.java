/*
 *
 * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright © 2018  SZCA. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.bcia.javachain.ca.szca.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


/**
 * 时间基本处理类
 * 
 * @version 1.0
 */
public class DatetimeHandle
{

    /**日期格式："yyyy-MM-dd HH:mm:ss"*/
    public final static String LONG_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

    /**日期格式："yyyy-MM-dd"*/
    public final static String SHORT_TIME_FORMAT_STRING = "yyyy-MM-dd";

    /**
     * 获得当前时间的格式化字符串
     * 
     * @see LONG_TIME_FORMAT_STRING
     * @return java.lang.String
     */
    public static String formatCurrentDate()
    {
        return new SimpleDateFormat(LONG_TIME_FORMAT_STRING).format(new Date());
    }
    
    public static String formatShortCurrentDate()
    {
        return new SimpleDateFormat(SHORT_TIME_FORMAT_STRING).format(new Date());
    }

    /**
     * 将指定时间按format定义的格式转化
     * 
     * @param longDate long 指定时间的毫秒数
     * @param format java.lang.String 格式化字符串
     * @return java.lang.String
     */
    public static String formatDate(long longDate, String format)
    {
        return formatDate(new Date(longDate), format);
    }

    /**
     * 将指定时间按format定义的格式转化
     * 
     * @param stringDate string 指定时间的字符串 字符串格式："yyyy-MM-dd HH:mm:ss"
     * @param format java.lang.String 格式化字符串
     * @return java.lang.String
     */
 
    public static String formatDate(String stringDate, String format)
    {
        long longDate = 0;
        try
        {
            longDate = parseDate(stringDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return formatDate(new Date(longDate), format);
    }

    /**
     * 将指定时间按format定义的格式转化
     * 
     * @param date java.util.Date 指定时间
     * @param format java.lang.String 格式化字符串
     * @return java.lang.String
     */
    public static String formatDate(Date date, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    public static String formatDateToEnglihMonth(Date date)
    {
    	
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d",Locale.ENGLISH);
        return sdf.format(date);
    } 
    

    /**
     * 获得当前系统时间毫秒数
     * 
     * @return long
     */
    public static long getCurrentDate()
    {
        return System.currentTimeMillis();
    }

    /**
     * 将指定符合格式的时间字符串按"yyyy-MM-dd HH:mm:ss"的格式转化为毫秒数
     * 
     * @param date java.lang.String 指定时间
     * @see LONG_TIME_FORMAT_STRING
     * @return long
     */
    public static long parseDate(String date)
        throws ParseException
    {
        if (date == null || date.equals(""))
        {
            return 0L;
        }
        SimpleDateFormat df = new SimpleDateFormat(LONG_TIME_FORMAT_STRING);
        Date d = df.parse(date);
        return d.getTime();
    }
    
   

    /**
     * 将指定符合格式的时间字符串按format定义的格式转化为毫秒数
     * 
     * @param date java.lang.String 指定时间
     * @param format java.lang.String 格式化字符串
     * @return long
     */
    public static long parseDate(String date, String format)
        throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date d = df.parse(date);
        return d.getTime();
    }
    
    
    public static Date parseStringToDate(String date, String format)
    throws ParseException
{
    SimpleDateFormat df = new SimpleDateFormat(format);
    Date d = df.parse(date);
    return d;
}

    /**
     * 将指定时间毫秒数格式化为"yyyy-MM-dd HH:mm:ss"的格式的字符串
     * 
     * @param longDate long 指定时间的毫秒数
     * @see LONG_TIME_FORMAT_STRING
     * @return java.lang.String
     */
    public static String formatDate(long longDate)
        throws ParseException
    {
        Date date = new Date(longDate);
        SimpleDateFormat df = new SimpleDateFormat(LONG_TIME_FORMAT_STRING);
        return df.format(date);
    }

    /** 报表相关时间标志截取 */
    public static String hourFlag(String time)
    {
        return time.substring(0, 13);
    }

    /** 报表相关时间标志截取 */
    public static String dayFlag(String time)
    {
        return time.substring(0, 10);
    }

    /** 报表相关时间标志截取 */
    public static String monthFlag(String time)
    {
        return time.substring(0, 7);
    }

    /** 报表相关时间标志截取 */
    public static String yearFlag(String time)
    {
        return time.substring(0, 4);
    }
    
    

    public static List<Calendar> getTimeDivList(Calendar startCal, Calendar endCal,
                                                int addTimeField, int addStep)
    {
        List<Calendar> _return = new ArrayList<Calendar>();
        for (Calendar loopCal = startCal; !loopCal.after(endCal); loopCal.add(addTimeField, addStep))
        {
            _return.add((Calendar)loopCal.clone());
        }
        return _return;
    }

    /** 得到指定格式的昨天字串 */
    public static String getYestoday(String format)
    {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(System.currentTimeMillis());
        date.add(Calendar.DAY_OF_MONTH, -1);
        String yestoday = DatetimeHandle.formatDate(date.getTime(), format);
        return yestoday;
    }

    /** 得到指定格式的明天字串 */
    public static String getTomorrow(String format)
    {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(System.currentTimeMillis());
        date.add(Calendar.DAY_OF_MONTH, 1);
        String yestoday = DatetimeHandle.formatDate(date.getTime(), format);
        return yestoday;
    }
    
    /** 给指定的日期增加天数 */
    public static Date addDay(Date tempDate,int day)
    {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(tempDate.getTime());
    	date.add(Calendar.DATE, day);
        return new Date(date.getTimeInMillis());
    }

    public static String addYear(String parDate,int day)
    {
 		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parDate);
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			now.add(Calendar.YEAR, day);
	        return formatDate(now.getTime(),SHORT_TIME_FORMAT_STRING);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("日期转换格式异常");
			return null;
		}
		
    }
 
    public static void main(String[] args)
        throws ParseException
    {
    	System.out.println(getLastDay("2015-0-01"));
        
//        Calendar stCal = Calendar.getInstance();
        
//        stCal.setTimeInMillis(DatetimeHandle.parseDate("2009-01", "yyyy-MM"));
//        Calendar edCal = Calendar.getInstance();
//        edCal.setTimeInMillis(DatetimeHandle.parseDate("2009-12", "yyyy-MM"));
//        List<Calendar> list = getTimeDivList(stCal, edCal, Calendar.MONTH, 1);
//
//        Object obj = new Date();
//
//        Iterator<Calendar> ite = list.iterator();
//        while (ite.hasNext())
//        {
  
            
//        }
    }
    /**
     * 得到本周周一
     * 
     * @return yyyy-MM-dd
     */

    
    public static String getMondayOfThisWeek() {
    	  Calendar c = Calendar.getInstance();
    	  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
    	  if (day_of_week == 0)
    	  day_of_week = 7;
    	  c.add(Calendar.DATE, -day_of_week + 1);
    	  return formatDate(c.getTime(),SHORT_TIME_FORMAT_STRING);
    	 }
    
    /**
     * 得到本周周日
     * 
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
     Calendar c = Calendar.getInstance();
     int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
     if (day_of_week == 0)
      day_of_week = 7;
     c.add(Calendar.DATE, -day_of_week + 7);
     return formatDate(c.getTime(),SHORT_TIME_FORMAT_STRING);
    }

    
    /**
     * 得到月
     * 
     * @return yyyy-MM-dd
     * @throws ParseException 
     */
    public static int getDateMonth(String parDate) {
 		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parDate);
			 Calendar now = Calendar.getInstance();
	         now.setTime(date);
	          int month = now.get(Calendar.MONTH) + 1; // 0-based!
	          return month;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
        
      }
    
    public static int getYear(String parDate){
     	 try {
			 Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parDate);
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date);
			 calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));     
			return calendar.get(Calendar.YEAR); 
		} catch (ParseException e) {
 			e.printStackTrace();
			return -1;
		}
    }
    
    public static String getLastDay(String parDate)  {
    	 try {
			 Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parDate);
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date);
			 calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));     
			 return formatDate(calendar.getTime(),"yyyy-MM-dd" );     

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
     }
    
    public static String getMonthFirstDay(String parDate)  {
   	 try {
			 Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parDate);
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date);
			 calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
 			 return formatDate(calendar.getTime(),"yyyy-MM-dd" );     

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
 

    
    /*
     * 字符串“2012-05-10 14:12:50” + 天数
     * 返回字符串Date
     */
    
    public static  String getDayDifference(String preDate){// 只取 大概值
        try {
         Date beginDate=   DatetimeHandle.parseStringToDate(preDate, DatetimeHandle.LONG_TIME_FORMAT_STRING);
         long l=new Date().getTime() -beginDate.getTime();
         long day=l/(24*60*60*1000);
         if(day==0){
             return "今天";
         }else if(day==1){
             return "昨天";
         }else if(day==1){
             return "前天";
         }else{
             return Long.toString(day)+"天前";   
         }
          } catch (ParseException e) {
              e.printStackTrace();
              return null;
         }
        
     }
   
    public static Date getDate(Date date, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        //        SimpleDateFormat sdf = new SimpleDateFormat(LONG_TIME_FORMAT_STRING);
        //        sdf.format(calendar.getTime());
        return calendar.getTime();
    }
    public static Date getDateAddSeceord(Date date, int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        //        SimpleDateFormat sdf = new SimpleDateFormat(LONG_TIME_FORMAT_STRING);
        //        sdf.format(calendar.getTime());
        return calendar.getTime();
    }
    public static Date getDifferenceDate(Date date, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23); 
        calendar.set(Calendar.MINUTE,59); 
        calendar.set(Calendar.SECOND,59); 
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }
    public static int getCurrentYear(){
    	Calendar a=Calendar.getInstance();
    	return a.get(Calendar.YEAR); 
    }
    public static int getCurrentWeek(){
    	Calendar cl=Calendar.getInstance();
    	return cl.get(Calendar.WEEK_OF_YEAR); 
     }
    
    public static boolean comparison(Date date1,Date date2){
    	Calendar calendar=Calendar.getInstance();
        calendar.setTime(date1);
        Calendar calendar2=Calendar.getInstance();
        calendar2.setTime(date2);
    	return calendar.before(calendar2); 
     }
    //获取指定时间的前num个小时/天的时间字符串
    public static String diffTimeHour(Date time,int num){
		Calendar curr = Calendar.getInstance();
		curr.setTime(time);
		curr.set(Calendar.DATE,curr.get(Calendar.DATE)-num);
		return new SimpleDateFormat(LONG_TIME_FORMAT_STRING).format(curr.getTime());
	}

    public static boolean  isAfterNow(String date,int vi){
        try {
            if(date == null) {
                return false;
            }

            Date  createDate= DatetimeHandle.getDateAddSeceord(DatetimeHandle.parseStringToDate(date, DatetimeHandle.LONG_TIME_FORMAT_STRING), vi);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(createDate);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(new Date());
            return calendar.after(calendar2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
