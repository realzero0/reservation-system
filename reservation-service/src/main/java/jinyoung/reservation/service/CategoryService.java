package jinyoung.reservation.service;

import java.util.*;

import jinyoung.reservation.domain.*;

public interface CategoryService {
	public Category get(String name);

	public Category addCategory(Category category);

	public int delete(Integer id);

	public int update(Category category);

	public Collection<Category> getAll();
}
