package jinyoung.reservation.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jinyoung.reservation.domain.*;
import jinyoung.reservation.service.*;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public Collection<Category> getAll() {
		Collection<Category> categories = categoryService.getAll();
		return categories;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Category create(@RequestBody Category category) {
		return categoryService.addCategory(category);
	}

	@GetMapping("/{id}")
	public Category selectById(@PathVariable Integer id) {
		return categoryService.get(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Integer deleteById(@PathVariable Integer id) {
		return categoryService.delete(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	Integer update(@PathVariable Integer id, @RequestBody Category category) {
		category.setId(id);
		return categoryService.update(category);
	}
}
