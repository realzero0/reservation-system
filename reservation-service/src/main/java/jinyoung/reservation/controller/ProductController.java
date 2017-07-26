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
	private ProductService productService;
	
	@Autowired
	private FileService productImageDtoService;

	@GetMapping("/{productId}")
	public ProductDto selectById(@PathVariable Integer productId) {
		return productService.getByProductId(productId);
	}
	
	@GetMapping("/{productId}/images")
	public Collection<ImageDto> selectImagesById(@PathVariable Integer productId) {
		return productImageDtoService.getImagesByProId(productId);
	}
	
	@GetMapping("/cate/{categoryId}/page/{page}")
	public Collection<ProductDto> selectByCateId(@PathVariable Integer categoryId, @PathVariable Integer page) {
		if(categoryId == 1) {
			return productService.getAll(page);
		} else {
			return productService.getByCategoryId(categoryId, page);
		}
	}

	@GetMapping("/count/{categoryId}")
	public Integer countByCateId(@PathVariable Integer categoryId) {
		if(categoryId == 1) {
			return productService.getCountAll();
		} else {
			return productService.getCountByCateId(categoryId);
		}
	}
}
