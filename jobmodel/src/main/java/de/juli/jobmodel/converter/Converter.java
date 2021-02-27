package de.juli.jobmodel.converter;

public interface Converter {
	public Object getAsObject(String value);

	public String getAsString(Object value);

}
