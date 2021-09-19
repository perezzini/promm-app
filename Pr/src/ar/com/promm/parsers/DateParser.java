package ar.com.promm.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser
{
	public static Date str2Date(String str)
	{
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.US);
		Date date=null;

		try {
			date = df.parse(str);
		} catch (ParseException ex) {
			/* What to do here? */
		}
		return date;
	}

	public static String printSchedule(Date startTime, Date stopTime)
	{
		DateFormat df = new SimpleDateFormat("HH:mm", Locale.US);
		return df.format(startTime) + " hs - " + df.format(stopTime) + " hs";
	}
}
