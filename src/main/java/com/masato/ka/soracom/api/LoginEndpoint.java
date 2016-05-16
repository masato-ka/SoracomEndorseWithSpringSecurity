package com.masato.ka.soracom.api;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginEndpoint {
	
	@RequestMapping("/login")
	String loginRedirect(){
		return "redirect:https://endorse.soracom.io/?redirect_url=http://localhost:8080/";
	}
	
}
