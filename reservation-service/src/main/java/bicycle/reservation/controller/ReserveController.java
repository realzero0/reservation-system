package bicycle.reservation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import bicycle.reservation.domain.ProductPrice;
import bicycle.reservation.domain.ReservationInfo;
import bicycle.reservation.dto.ProductDto;
import bicycle.reservation.service.ProductService;
import bicycle.reservation.service.ReservationInfoService;

@Controller
@RequestMapping("/exhibition")
public class ReserveController extends WebMvcConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ReserveController.class);
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
    public String reserve(@PathVariable Integer productId, @Valid ReservationInfo reservationInfo, BindingResult bindingResult) {
        logger.info("==============Reserve 시작==============");
        if (bindingResult.hasErrors()) {
            logger.error("=============Reserve 실패==============");
            return "redirect:/";
        }
        reservationInfoService.addReservationInfo(reservationInfo);
        logger.info("==============Reserve 성공==============");
        return "redirect:/"; // 성공 부분
    }


}
