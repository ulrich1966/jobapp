package de.juli.jobweb.web.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter("sqlDateConverter")
public class SqlDateConverter implements Converter {
	private static final Logger LOG = LoggerFactory.getLogger(SqlDateConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return new String("convert Object");
	}

	/**
	 * Wandelt ein java.sql.Date in ein java.util.Date und Formatiert dieses 
	 * Gibt das Datum als String zurueck. 
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		java.sql.Date sqlDate = null;
		java.util.Date date = null;
		DateFormat df = null;
		
		try {
			if(value instanceof java.sql.Date) {
				sqlDate = (java.sql.Date) value;
				date = new java.util.Date(sqlDate.getTime());
				df = new SimpleDateFormat( "dd.MM.yy" );				
			}
		} catch (ClassCastException e) {
			LOG.error(e.getMessage());
		}
		
		return df.format(date);
	}

}
