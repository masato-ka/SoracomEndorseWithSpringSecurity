package com.masato.ka.soracom.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SigningKeyResolver;

public class MyUserDetailService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		
		Object credentials = token.getCredentials();
		
		String imsi = "";
		
		SigningKeyResolver resolver = new SoracomSigningKeyResolver();
		try{

			Claims claims = Jwts.parser().requireSubject("soracom-endorse")
				.requireAudience("soracom-endorse-audience")
				.setSigningKeyResolver(resolver)
				.parseClaimsJws((String) credentials).getBody();
			
			LinkedHashMap<String,String> soracomClaim = 
					(LinkedHashMap<String, String>) claims.get("soracom-endorse-claim");
			imsi = (String)soracomClaim.get("imsi");

		} catch (ExpiredJwtException e){
			throw new UsernameNotFoundException("JwtToken was expired.");
		} catch (MalformedJwtException e){
			throw new UsernameNotFoundException("JwtToken was malformed token value.");
		} catch (IllegalArgumentException e){
			throw new UsernameNotFoundException("JWT String argument cannot be null or empty.");
		}
		
		Collection<GrantedAuthority> authorities =new HashSet<GrantedAuthority>() ;
        authorities.add(new AdminAuthority());
        User user = new User(imsi,"",authorities);
		return user;
	}

}
