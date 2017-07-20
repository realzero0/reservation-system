package jinyoung.reservation.controller;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import jinyoung.reservation.service.*;

@Controller
@RequestMapping("/booked")
public class MyPageController {

	@Autowired
	UsersService usersService;

	@GetMapping("/list")
	public String list(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null) {
			request.setAttribute("user", null);
			return "myreservation";
		} else {
			request.getSession().setAttribute("returnUrl", request.getRequestURL());
			return "redirect:/login";
		}
	}

}
