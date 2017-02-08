package de.rgse.timecap.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

	public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN);
	
	private DateParser(){}
	
	public static String parseDate(Date date) {
		return DATE_FORMAT.format(date);
	}
	
	public static Date parseDate(String date) throws ParseException {
		return DATE_FORMAT.parse(date);
	}
}
