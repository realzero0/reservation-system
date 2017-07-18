package jinyoung.reservation.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/booked")
public class MyPageController {

	@GetMapping("/list")
	public String list() {
		return "myreservation";
	}
		
}
