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
			return "redirect:/admin/category";
		} else {
			Category category = new Category();
			category.setName(name);
			categoryService.addCategory(category);
			return "redirect:/admin/category";
		}
	}

	@GetMapping("/category/delete")
	public String delete(@RequestParam(name = "id") Integer id, HttpServletRequest request) {
		if (id == null) {
			return "redirect:/admin/category";
		} else {
			categoryService.delete(id);
			return "redirect:/admin/category";
		}
	}
	
	@GetMapping("/category/modify")
	public String modify(@ModelAttribute Category category, HttpServletRequest request) {
		if (category.getId() == null || category.getName() == null || "".equals(category.getName())) {
			return "";
		} else {
			categoryService.update(category);
			return "redirect:/admin/category";
		}
	}
}
