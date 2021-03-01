package de.juli.jobapp.jobweb.web.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {
	private static final Logger LOG = LoggerFactory.getLogger(SessionListener.class);

	public void sessionCreated(HttpSessionEvent event) {
		LOG.debug("\n\tCreated session {}: ", this.getClass().getName(), event.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		String lastAccess = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date(event.getSession().getLastAccessedTime()));
		System.out.println(this.getClass().getName()+" Expired session " + event.getSession().getId() + ". Last access = " + lastAccess);
	}
}