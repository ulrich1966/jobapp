package de.juli.jobweb.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter("trueFalseConverter")
public class TrueFaleConverter implements Converter {
	private static final Logger LOG = LoggerFactory.getLogger(TrueFaleConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		boolean cast = false;
		try {
			if (value instanceof Boolean) {
				cast = (boolean) value;
				if (cast) {
					return "ja";
				}
				if (!cast) {
					return "nein";
				}
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			LOG.error("{}kann nicht in ein boolen umgewandelt werden!\n{}", value, e.getLocalizedMessage());
		}
		return value.toString();
	}
}
