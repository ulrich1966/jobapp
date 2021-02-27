package de.juli.jobweb.web.app;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("apphome")
@RequestScoped
public class IndexBean extends WebBean {
	private static final Logger LOG = LoggerFactory.getLogger(IndexBean.class);
	private static final long serialVersionUID = 1L;
	
	public IndexBean() {
	}
		
	/**
	 * Bevor die View geraendert wird auf eventuelle Fehlermeldungen 
	 * pruefen
	 */
	@Override
	public void preRender(ComponentSystemEvent event){
		LOG.debug("{}", "\napphome Startseite");
		super.preRender(event);
	}

	public void init() {
	}
}
