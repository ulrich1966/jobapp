package de.juli.jobapp.jobweb.web.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobweb.web.Session;

public class LoginFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);

	@Inject
	private Session session;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Boolean login = session.getLogin();
		LOG.debug("Loged in: {}", login);
		chain.doFilter(request, response);
    }
}
