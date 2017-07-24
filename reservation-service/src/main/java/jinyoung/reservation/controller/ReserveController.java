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
@RequestMapping("/exhibition")
public class ReserveController {

	@Autowired
	private ProductService productDtoService;

	@Autowired
	private ReservationInfoService reservationInfoService;

	@GetMapping("/{productId}/reserve")
	public String reserveProduct(@PathVariable Integer productId, HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null) {
			// request.getSession().setAttribute("user", null);
			ProductDto productDto = productDtoService.getByProId(productId);
			List<ProductPrice> prices = productDtoService.getProductPricesByProductId(productId);
			request.setAttribute("product", productDto);
			request.setAttribute("prices", prices);
			return "reserve";
		} else {
			request.getSession().setAttribute("returnUrl", request.getRequestURL());
			return "redirect:/login";
		}
	}

	@PostMapping("/{productId}/reserve")
	public String reserve(@PathVariable Integer productId, @ModelAttribute ReservationInfo reservationInfo) {
		reservationInfoService.addReservationInfo(reservationInfo);
		return "redirect:/"; // 성공 부분
	}

	

}
