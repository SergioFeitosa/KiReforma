package com.example.demo.time;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TimeController {

	@RequestMapping("/cadastrarTime")
	public String form() {
		return "time/formTime";
	}
}

