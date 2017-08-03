package bicycle.reservation.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bicycle.reservation.domain.Users;
import bicycle.reservation.dto.BookedListDto;
import bicycle.reservation.service.ReservationInfoService;
import bicycle.reservation.service.UsersService;

@Controller
@RequestMapping("/booked")
public class MyPageController {

	@Autowired
	UsersService usersService;
	
	@Autowired
	ReservationInfoService reservationInfoService;

	@GetMapping("/list")
	public String list(HttpServletRequest request) {
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

		return "myreservation";

	}
	
	@PostMapping("/cancel")
	public String cancelReservation(@RequestParam(name = "bookingNumber") Integer bookingNumber, HttpServletRequest request) throws Exception {
		reservationInfoService.updateReservationTypeToCancelledByBookingNumber(bookingNumber);
		return "success";
	
	}
	
}
