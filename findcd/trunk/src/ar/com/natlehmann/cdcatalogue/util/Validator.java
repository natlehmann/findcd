package ar.com.natlehmann.cdcatalogue.util;

public class Validator {
	
	public static boolean isNotNull(String value) {		
		return (value != null && !value.trim().equals(""));
	}
	
	public static boolean isNull(String value) {
		return !isNotNull(value);
	}

}
