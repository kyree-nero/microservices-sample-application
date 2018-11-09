package com.example.gateway;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class LoginOnDownstreamForbiddenOrUnauthorizedFilter extends ZuulFilter {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private AuthenticationEntryPoint authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	@Override
	public boolean shouldFilter() {
		if(RequestContext.getCurrentContext().getRequest().getHeader("Cookie") == null) {
			return 
					RequestContext.getCurrentContext().getResponse().getStatus() == HttpStatus.FORBIDDEN.value() ||
					RequestContext.getCurrentContext().getResponse().getStatus() == HttpStatus.UNAUTHORIZED.value()
			;		
		}
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();
		
		requestCache.saveRequest(request, response);
	 
		String exceptionText = "DownStream service " + request.getRequestURI() + " responded with a status code 401/403";
		log.debug(exceptionText);
		
		try {
			authenticationEntryPoint.commence(request, response, new InsufficientAuthenticationException(exceptionText));
		}catch(IOException | ServletException e) {
			
		}
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 500;
	}

}
