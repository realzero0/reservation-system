package bicycle.reservation.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import bicycle.reservation.domain.*;
import bicycle.reservation.dto.*;
import bicycle.reservation.service.*;

@Controller
@RequestMapping("/exhibition")
public class ReserveController {

	@Autowired
	private ProductService productDtoService;

	@Autowired
	private ReservationInfoService reservationInfoService;

	@GetMapping("/{productId}/reserve")
	public String reserveProduct(@PathVariable Integer productId, HttpServletRequest request) {		
		// request.getSession().setAttribute("user", null);
		ProductDto productDto = productDtoService.getByProductId(productId);
		List<ProductPrice> prices = productDtoService.getProductPricesByProductId(productId);
		request.setAttribute("product", productDto);
		request.setAttribute("prices", prices);
		return "reserve";
	}

	@PostMapping("/{productId}/reserve")
	public String reserve(@PathVariable Integer productId, @ModelAttribute ReservationInfo reservationInfo) {
		reservationInfoService.addReservationInfo(reservationInfo);
		return "redirect:/"; // 성공 부분
	}

	

}
