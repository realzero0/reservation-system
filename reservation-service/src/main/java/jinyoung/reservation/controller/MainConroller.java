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
@RequestMapping("/")
public class MainConroller {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductDtoService productDtoService;
	
	@Autowired
	private ProductImageDtoService productImageDtoService;
	
	@GetMapping
	public String index(HttpServletRequest request) {
		Collection<Category> categories = categoryService.getAll();
		request.setAttribute("categories", categories);
		return "index";
	}
	
	@GetMapping("/exhibition/{productId}")
	public String getDetailPage(@PathVariable Integer productId ,HttpServletRequest request) {
		ProductDto productDto = productDtoService.getByProId(productId);
		Collection<ProductImageDto> productImages = productImageDtoService.getByProId(productId);
		request.setAttribute("productDto", productDto);
		request.setAttribute("productImages", productImages);
		return "productdetail";
	}
}
