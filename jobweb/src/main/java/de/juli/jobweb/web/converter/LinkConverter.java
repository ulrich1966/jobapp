package de.juli.jobweb.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("linkConverter")
public class LinkConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String href = (String) value;
		String protocol = "http";
		if(!href.startsWith(protocol)) {
			href = String.format("%s://%s", protocol, href);
		}
		return href;
	}

}
