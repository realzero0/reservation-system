package jinyoung.reservation.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import jinyoung.reservation.dto.*;
import jinyoung.reservation.service.*;

@RestController
@RequestMapping("/api/bookedlist")
public class BookedListController {
	
	@Autowired
	ReservationInfoService reservationInfoService;
	
	@PostMapping
	public List<BookedListDto> showBookedListsByUserId(@RequestParam(name="userId") Long userId) {
		return reservationInfoService.getBookedListsByUserId(userId);
	}
}
