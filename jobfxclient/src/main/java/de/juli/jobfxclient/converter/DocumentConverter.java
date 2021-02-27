package de.juli.jobfxclient.converter;

public class DocumentConverter implements Converter {

	public static Converter getNewInstance() {
		return new DocumentConverter();
	}

	@Override
	public Object convert(String value) {
		value = value.replace(".odt", "doc");
		return value;
	}

	@Override
	public String convert(Object value) {
		return null;
	}

}
