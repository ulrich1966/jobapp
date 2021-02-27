package de.juli.jobfxclient.converter;

import java.math.BigDecimal;

public class CurrencyConverter implements Converter {

	public static Converter getNewInstance() {
		return new CurrencyConverter();
	}

	@Override
	public Object convert(String value) {
		BigDecimal decimalValue = BigDecimal.valueOf(0);
		try {
			if(value != null) {
				decimalValue = new BigDecimal(value);				
			}
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
		}
		return decimalValue;
	}

	@Override
	public String convert(Object value) {
		return null;
	}

}
