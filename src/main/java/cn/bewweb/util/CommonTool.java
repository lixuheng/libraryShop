package cn.bewweb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonTool {

	private static final Logger log = LoggerFactory.getLogger(CommonTool.class);

	/**
	 * 字符串日期转时间
	 * 
	 * @param dateStr
	 *            字符串
	 * @return 一个日期或时间
	 */
	public static Date StringToDate(String dateStr) {
		try {
			dateStr = dateStr == null || "".equals(dateStr) ? null : dateStr.trim();
			if (dateStr.length() == 10) {
				return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			} else {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			}
		} catch (ParseException e) {
			log.error("日期格式错误");

		} catch (NullPointerException e) {
			log.error("参数不正确");
		}
		return null;
	}

	/**
	 * 日期转字符串时间
	 * 
	 * @param date
	 *            日期
	 * @param isDateTime
	 *            true表示转换的日期带有时间
	 * @return 字符串展示的日期
	 */
	public static String dateToString(Date date, boolean isDateTime) {
		if (isDateTime)
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		else {
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		}
	}

	/**
	 * 计算时间差
	 * 
	 * @param after
	 *            后一点的时间
	 * @param before
	 *            前一点的时间
	 * @param method
	 *            返回时间间隔的方式,具体有默认值毫秒， 秒s，分 m，时 H ，天d ，月M ，年y；其中月和年是估算值。
	 * @return 时间间隔
	 */
	public static Long dateSUbDate(String after, String before, String method) {
		return dateSUbDate(StringToDate(after),StringToDate(before),method);
	}
	/**
	 * 计算时间差
	 * 
	 * @param after
	 *            后一点的时间
	 * @param before
	 *            前一点的时间
	 * @param method
	 *            返回时间间隔的方式,具体有默认值毫秒， 秒s，分 m，时 H ，天d ，月M ，年y；其中月和年是估算值。
	 * @return 时间间隔
	 */
	public static Long dateSUbDate(Date after, Date before, String method) {
		Long result = 0l;
		result = after.getTime() - before.getTime();
		method = null==method?" ":method.trim();
		switch (method) {
		case "s":
			result /= 1000;
			break;
		case "m":
			result /= (60000);
			break;
		case "H":
			result /= (60000 * 60);
			break;
		case "d":
			result /= (60000 * 60 * 24);
			break;
		case "M":
			result /= (60000 * 60 * 24 * 30);
			break;// 估算值
		case "y":
			result /= (60000 * 60 * 24 * 30 * 12);
			break;// 估算值
		default:
			break;
		}
		return result;
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            当前时间
	 * @param time
	 *            要加的毫秒数
	 * @return 新的时间
	 */
	public static String dateAddTime(String date, Long time) {
		return dateToString(new Date(StringToDate(date).getTime() + time), true);
	}


}
