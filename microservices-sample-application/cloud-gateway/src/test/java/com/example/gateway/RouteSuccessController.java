package com.example.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteSuccessController {
	@GetMapping("/success/test")
	public String success() {
		return "success";
	}
}
