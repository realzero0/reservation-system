package jinyoung.reservation.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import jinyoung.reservation.dto.*;
import jinyoung.reservation.service.*;

@RestController
@RequestMapping("api/products")
public class ProductController {

	@Autowired
	private ProductDtoService productDtoService;
	
	@Autowired
	private ProductImageDtoService productImageDtoService;

	@GetMapping("/{productId}")
	public ProductDto selectById(@PathVariable Integer productId) {
		return productDtoService.getByProId(productId);
	}
	
	@GetMapping("/{productId}/images")
	public Collection<ProductImageDto> selectImagesById(@PathVariable Integer productId) {
		return productImageDtoService.getByProId(productId);
	}
	
	@GetMapping("/cate/{categoryId}/page/{page}")
	public Collection<ProductDto> selectByCateId(@PathVariable Integer categoryId, @PathVariable Integer page) {
		if(categoryId == 1) {
			return productDtoService.getAll(page);
		} else {
			return productDtoService.getByCateId(categoryId, page);
		}
	}

	@GetMapping("/count/{categoryId}")
	public Integer countByCateId(@PathVariable Integer categoryId) {
		if(categoryId == 1) {
			return productDtoService.getCountAll();
		} else {
			return productDtoService.getCountByCateId(categoryId);
		}
	}
}
