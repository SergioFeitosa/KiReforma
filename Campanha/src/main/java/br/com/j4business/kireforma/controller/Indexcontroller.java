package br.com.j4business.kireforma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Indexcontroller {

	@RequestMapping("/")
	public String IndexController() {
		return "index";
	}
	
}
