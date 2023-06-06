package jmaster.io.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	@GetMapping("/hello")
	public String hi() {
		//Map url vào 1 hàm, trả về tên file view
		return "hi.html";
	}
}
