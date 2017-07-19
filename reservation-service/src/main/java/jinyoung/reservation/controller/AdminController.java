package jinyoung.reservation.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import jinyoung.reservation.domain.*;
import jinyoung.reservation.service.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
	// 카테고리 설정
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/category")
	public String categoryAdmin(HttpServletRequest request) {
		Collection<Category> categories = categoryService.getAll();
		request.setAttribute("categories", categories);
		return "categoryAdmin";
	}

	@PostMapping("/category")
	public String create(@RequestParam(name = "name") String name, HttpServletRequest request)
			throws UnsupportedEncodingException {
		if (name == null || "".equals(name)) {
			return "false";
		} else {
			Category category = new Category();
			category.setName(name);
			categoryService.addCategory(category);
			return "redirect:/admin/category";
		}
	}
	
	//리뷰 설정
	
}
