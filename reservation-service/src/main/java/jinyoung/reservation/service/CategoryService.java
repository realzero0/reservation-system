package jinyoung.reservation.service;

import java.util.*;

import jinyoung.reservation.domain.*;

public interface CategoryService {
	public Category get(Integer id);

	public Category addCategory(Category category);

	public Integer delete(Integer id);

	public Integer update(Category category);

	public Collection<Category> getAll();

}
