package jmaster.io.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jmaster.io.demo.dto.DepartmentDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.service.DepartmentService;
import jmaster.io.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired //DI
	UserService userService;
	
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/new")
	public String newUser(Model model) {
		PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
		model.addAttribute("user", new UserDTO());
		model.addAttribute("departmentList", pageDTO.getData());
		return "new-user.html";
	}
	
	@PostMapping("/new")
	public String newUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult, Model model) throws Exception {
		if (bindingResult.hasErrors()) {
			PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
			model.addAttribute("departmentList", pageDTO.getData());
			return "new-user.html"; //khi có lỗi xảy ra, return view
		}
		
		if(!userDTO.getFile().isEmpty()) {
			// ten file upload
			String filename = userDTO.getFile().getOriginalFilename();
			// luu lai file vao o cung may chu
			File saveFile = new File("D:/" + filename);
			userDTO.getFile().transferTo(saveFile);
			// lay ten file luu xuong DATABASE
			userDTO.setAvatarURL(filename);
		}
		userService.create(userDTO);
		return "redirect:/user/list"; //get
	}
	
	@GetMapping("/download") //?filename=abc.jpg
	public String download(@RequestParam("filename") String filename, HttpServletResponse resp) throws Exception {
		if (Strings.isNotEmpty(filename) && !"null".equals(filename)) {
			File file = new File("D:/" + filename);
			Files.copy(file.toPath(), resp.getOutputStream());
		}
		
		return "redirect:/user/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		userService.delete(id);
		return "redirect:/user/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		List<UserDTO> users = userService.getAll();
		
		model.addAttribute("userList", users);
		
		model.addAttribute("searchDTO", new SearchDTO());
		
		return "users.html";
	}
	
	@GetMapping("/search")
	public String search(Model model, @ModelAttribute("searchDTO") @Valid SearchDTO searchDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "users.html"; //khi có lỗi xảy ra, return view
		}
		
		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);
		
		model.addAttribute("userList", pageUser.getData());
		model.addAttribute("totalPage", pageUser.getTotalPages());
		model.addAttribute("totalElements", pageUser.getTotalElements());
		model.addAttribute("searchDTO", searchDTO);
		
		return "users.html";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam("id") int id, Model model) {
		UserDTO userDTO = userService.getById(id);
		PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
		model.addAttribute("departmentList", pageDTO.getData());
		model.addAttribute("user", userDTO);//đẩy thông tin user qua view
		
		return "edit-user.html";
	}
	
	@PostMapping("/edit")
	public String edit(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
			model.addAttribute("departmentList", pageDTO.getData());
			return "edit-user.html";
		}
		
		userService.update(userDTO);
		return "redirect:/user/list";
	}
}
