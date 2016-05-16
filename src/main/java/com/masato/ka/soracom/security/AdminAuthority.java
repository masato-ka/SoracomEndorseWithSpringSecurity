package com.masato.ka.soracom.security;

import org.springframework.security.core.GrantedAuthority;

public class AdminAuthority implements GrantedAuthority {

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return "ADMIN";
	}

}
