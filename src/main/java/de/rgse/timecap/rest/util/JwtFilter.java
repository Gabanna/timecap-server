package de.rgse.timecap.rest.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

//@WebFilter(filterName = "JwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogManager().getLogger(JwtFilter.class.getSimpleName());

	private JwtHandler jwtHandler = new JwtHandler();

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String token = request.getHeader("Authorization");
		if (token != null && !token.isEmpty()) {
			try {
				jwtHandler.validateToken(token);
				chain.doFilter(request, response);

			} catch (UnsupportedJwtException | MalformedJwtException e) {
				response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());

			} catch (SignatureException | ExpiredJwtException e) {
				response.setStatus(Response.Status.FORBIDDEN.getStatusCode());

				if (e instanceof SignatureException) {
					String remoteAddr = request.getRemoteAddr();
					LOGGER.log(Level.SEVERE, String.format("Manipulated JWT detected from address %s", remoteAddr), e);
				}
			}

		} else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
