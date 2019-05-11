package com.scheduler.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {

	private static final String DATE_FORMAT_WITH_TIME = "dd/MM/yyyy HH:mm:ss a";

	public static String convertFromTimestampToFormStringWithTime(Timestamp dateTime) {
		String time = null;
		if (null != dateTime) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					DATE_FORMAT_WITH_TIME);
			time = dateFormat.format(new Date(dateTime.getTime()));
		}
		return time;
	}
	public static String convertFromTimestampLongToString(long time){
	    Date date = new Date(time);
	    SimpleDateFormat dateFormat = new SimpleDateFormat(
				DATE_FORMAT_WITH_TIME);
	    return dateFormat.format(date);
	}
}
