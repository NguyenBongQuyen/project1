package jmaster.io.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String login() {
		//Map url vào 1 hàm, trả về tên file view
		return "login.html";
	}
	
	@PostMapping("/login")
	public String login(HttpSession session,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		if (username.equals("admin") && password.equals("123")) {
			//Thành công
			session.setAttribute("username", username);
			return ("redirect:/hello");
		} else {
			return ("redirect:/login"); //login lại
		}
	}
}
