package com.housebooking.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.housebooking.app.model.TicketModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.housebooking.app.model.Announcement;
import com.housebooking.app.model.UserModel;
import com.housebooking.app.service.AdminService;
import com.housebooking.app.service.HomeService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private HomeService homeService;

	@GetMapping("/admin")
	public String getAdminWelcomePage(@ModelAttribute("user") UserModel user, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if (messages == null) {
			messages = new ArrayList<>();
		}
		model.addAttribute("sessionMessages", messages);
		UserModel userdata = homeService.findUser(messages.get(0));
		model.addAttribute("role", userdata.getUsertype());
		return "admin/welcomeadmin";
	}

	@GetMapping("/announcement")
	public String getAnnouncementPage(Model model) {

		Announcement announcement = new Announcement();

		model.addAttribute("announcement", announcement);
		return "admin/addannouncement";

	}

	@PostMapping("/postAnnouncement")
	public String postAnnouncement(@ModelAttribute("announcement") Announcement announcement) {

		adminService.addAnnouncement(announcement);

		return "redirect:/admin";

	}

	@GetMapping("/viewTickets")
	public String viewTickets(Model model) {

		List<TicketModel> tickets = adminService.findAllTickets();
		model.addAttribute("tickets",tickets);
		return "admin/viewtickets";

	}

	@GetMapping("/removeTicket/{id}")
	public String removeTicket(Model model, @PathVariable("id") Long id) {
		System.out.println("id==== "+id);
		adminService.removeTicket(id);
		return "redirect:/admin";

	}

}
