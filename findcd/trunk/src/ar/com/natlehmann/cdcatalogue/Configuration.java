package ar.com.natlehmann.cdcatalogue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Configuration {
	
	public static final String APPLICATION_CONTEXT_PATH = "/ar/com/natlehmann/cdcatalogue/applicationContext.xml";
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	
	public static Date getStringAsDate(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.parse(date);
	}
	
	public static String getDateAsString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(date);
	}

}
