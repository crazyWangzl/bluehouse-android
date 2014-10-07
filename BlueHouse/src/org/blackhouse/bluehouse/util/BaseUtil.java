package org.blackhouse.bluehouse.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BaseUtil {

	public static String convertTime(String time, String format) {
		String newTime = time.replace("T"," ").replace("+0800","");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(newTime);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);// "yyyyÄêMMÔÂdd HH:mm"
			return dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
