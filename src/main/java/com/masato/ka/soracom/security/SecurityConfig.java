package com.masato.ka.soracom.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration @EnableWebSecurity @EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> 
                authenticationUserDetailsService() {
        return new MyUserDetailService();
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider 
                    preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider provider
            = new PreAuthenticatedAuthenticationProvider();

        provider.
            setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());

        provider.
            setUserDetailsChecker(new AccountStatusUserDetailsChecker());

        return provider;
    }

    @Bean
    public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter()
            throws Exception {
        PreAuthenticationFilterWithJwt filter = new PreAuthenticationFilterWithJwt();

        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/login").permitAll()
        	.anyRequest().fullyAuthenticated()
        .and().authorizeRequests().antMatchers("/").permitAll().anyRequest()
        	.authenticated()
        .and().addFilter(preAuthenticatedProcessingFilter());
    }
    
	/*@Override
	protected void configure(HttpSecurity http) throws Exception{
//		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
		http.authorizeRequests().anyRequest().authenticated();//.and().httpBasic().and().addFilterAfter(new AuthenticationFilter(),BasicAuthenticationFilter.class);
	}*/
	/*
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {        
        auth.authenticationProvider(authenticationProviderImpl);        
    }  */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
  //          .userDetailsService(userDetailsService);
	}
}
