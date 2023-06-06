package jmaster.io.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.SearchTicketDTO;
import jmaster.io.demo.dto.TicketDTO;
import jmaster.io.demo.service.DepartmentService;
import jmaster.io.demo.service.TicketService;

@Controller
@RequestMapping("/ticket")
public class TicketController {
	@Autowired
	DepartmentService departmentService;

	@Autowired
	TicketService ticketService;

	@GetMapping("/new")
	public String add(Model model) {
		model.addAttribute("departmentList", departmentService.getAll());
		//return "ticket/add.html";
		return "new-ticket.html";
	}
	
	@PostMapping("/new")
	public String create(@ModelAttribute("ticket") @Valid TicketDTO ticketDTO, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return "new-ticket.html";
		}
		
		ticketService.create(ticketDTO);
		return "redirect:/ticket/search";// GET
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("departmentList", departmentService.getAll());
		model.addAttribute("searchDTO", new SearchDTO());
		return "home.html";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		List<TicketDTO> tickets = ticketService.getAll();
		model.addAttribute("departmentList", tickets);
		model.addAttribute("searchDTO", new SearchDTO());

		return "tickets.html";
	}
	
	@GetMapping("/search")//copy lai user/list
	public String search(@ModelAttribute @Valid SearchTicketDTO searchDTO, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "tickets.html";
		}
		
		PageDTO<List<TicketDTO>> pageUser = ticketService.search(searchDTO);
		model.addAttribute("ticketList", pageUser.getData());
		model.addAttribute("totalPage", pageUser.getTotalPages());
		model.addAttribute("content", pageUser.getTotalPages());
		model.addAttribute("totalElements", pageUser.getTotalElements());

		return "tickets.html";
	}

	@GetMapping("/searchhome")//copy lai user/list
	public String searchHome(@ModelAttribute @Valid SearchTicketDTO searchDTO, BindingResult bindingResult, Model model) {
		model.addAttribute("departmentList", departmentService.getAll());
		if(bindingResult.hasErrors()) {
			return "tickets.html";
		}
		
		PageDTO<List<TicketDTO>> pageUser = ticketService.search(searchDTO);
		model.addAttribute("ticketList", pageUser.getData());
		model.addAttribute("totalPage", pageUser.getTotalPages());
		model.addAttribute("content", pageUser.getTotalPages());
		model.addAttribute("totalElements", pageUser.getTotalElements());

		return "home.html";
	}

	
	@GetMapping("/delete") // ?id=1
	public String delete(@RequestParam("id") int id) {
		ticketService.delete(id);
		return "redirect:/ticket/search";
	}

	@GetMapping("/edit")//?id=1000
	public String edit(@RequestParam("id") int id,Model model) {
		TicketDTO ticketDTO = ticketService.getById(id);
		model.addAttribute("ticket", ticketDTO);
		return "edit-ticket.html";
	}
	
	@PostMapping("/edit")
	public String edit(@ModelAttribute("ticket") @Valid TicketDTO ticketDTO, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()) {
			return "tickets.html";
		}
		ticketService.update(ticketDTO);
		return "redirect:/ticket/search";
	}
	
}
