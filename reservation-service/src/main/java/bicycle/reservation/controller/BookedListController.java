package bicycle.reservation.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import bicycle.reservation.dto.*;
import bicycle.reservation.service.*;

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
