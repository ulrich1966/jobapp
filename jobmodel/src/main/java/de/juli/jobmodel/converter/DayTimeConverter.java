package de.juli.jobmodel.converter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DayTimeConverter implements Converter {

	@Override
	public Object getAsObject(String value) {
		return null;
	}

	@Override
	public String getAsString(Object value) {
		String result = null;
		if(value != null) {
			Date date = (Date) value;
			LocalDate localDate = date.toLocalDate();
			result = localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		}
		return result;
	}
}
