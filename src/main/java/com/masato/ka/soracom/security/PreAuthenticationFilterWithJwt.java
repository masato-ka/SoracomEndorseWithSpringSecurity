package com.masato.ka.soracom.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;

public class PreAuthenticationFilterWithJwt extends AbstractPreAuthenticatedProcessingFilter {
	
	
	
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		
		String jwtToken = request.getParameter("soracom_endorse_token");
		if(jwtToken==null){
			return "";
		}
		
		return jwtToken;
		
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return "";
	}

}
