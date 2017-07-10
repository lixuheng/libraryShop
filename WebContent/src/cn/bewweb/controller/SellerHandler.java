package cn.bewweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/seller")
public class SellerHandler {

	@RequestMapping("login")
	public String login(){
		
		
		return "seller/login";
	}
	@RequestMapping("regist")
	public String register(){
		
		return "seller/regist";
	}
}
