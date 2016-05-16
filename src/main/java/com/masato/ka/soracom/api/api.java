package com.masato.ka.soracom.api;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class api {
	@RequestMapping
	String check(Principal principal){
		if(principal == null){
			return "Faile authenticate";
		}
		String result = principal.getName();
		if(result == null){
			return "Faile authenticate";
		}
		return principal.getName();
	}	
	
	@RequestMapping("/api/v1/info")
	String getInfo(){
		return "Success authentcation via soracom";
	}
	
}
