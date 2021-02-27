package de.juli.jobweb.web.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateConverter")
public class DateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return new String("convert Object");
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Date date = new Date((long) value);
		DateFormat df = new SimpleDateFormat( "dd.MM.yy" );
		return df.format(date);
	}

}
