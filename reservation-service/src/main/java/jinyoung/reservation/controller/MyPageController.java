package jinyoung.reservation.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import jinyoung.reservation.domain.*;
import jinyoung.reservation.dto.*;
import jinyoung.reservation.service.*;

@Controller
@RequestMapping("/booked")
public class MyPageController {

	@Autowired
	UsersService usersService;
	
	@Autowired
	ReservationInfoService reservationInfoService;

	@GetMapping("/list")
	public String list(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null) {
			Users user = (Users) request.getSession().getAttribute("user");
			List<BookedListDto> bookedLists = reservationInfoService.getBookedListsByUserId(user.getId());
			List<BookedListDto> bookedLists0 = new LinkedList<>();
			List<BookedListDto> bookedLists1 = new LinkedList<>();
			List<BookedListDto> bookedLists2 = new LinkedList<>();
			List<BookedListDto> bookedLists3 = new LinkedList<>();
			for(BookedListDto bookedList : bookedLists) {
				switch (bookedList.getReservationType()) {
				case 0:
					bookedLists0.add(bookedList);
					break;
				case 1:
					bookedLists1.add(bookedList);
					break;
				case 2:
					bookedLists2.add(bookedList);
					break;
				case 3:
					bookedLists3.add(bookedList);
					break;
				}
			}
			request.setAttribute("bookedLists", bookedLists);
			request.setAttribute("bookedLists0", bookedLists0);
			request.setAttribute("bookedLists1", bookedLists1);
			request.setAttribute("bookedLists2", bookedLists2);
			request.setAttribute("bookedLists3", bookedLists3);
//			request.getSession().setAttribute("user", null);
			return "myreservation";
		} else {
			request.getSession().setAttribute("returnUrl", request.getRequestURL());
			return "redirect:/login";
		}
	}
	
	@PostMapping("/cancel")
	public String cancelReservation(@RequestParam(name = "bookingNumber") Integer bookingNumber, HttpServletRequest request) throws Exception {
		if (request.getSession().getAttribute("user") != null) {
			reservationInfoService.updateReservationTypeToCancelledByBookingNumber(bookingNumber);
			return "success";
		} else {
			throw new Exception();
		}
	}
	
}
