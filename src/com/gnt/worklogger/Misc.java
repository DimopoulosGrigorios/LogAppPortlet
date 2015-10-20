package com.gnt.worklogger;

import java.util.Calendar;
import java.util.Date;


public class Misc {
	public static final String PERSISTENCE_UNIT = "WorkLogger";
    private static Calendar cal;
    
    static {
		cal = Calendar.getInstance();
        cal.setTime(new Date());                     // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
    }

    public static Date todayWithZeroTime() {
        return cal.getTime();
    }
}