package jinyoung.reservation.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import jinyoung.reservation.dao.*;
import jinyoung.reservation.domain.*;
import jinyoung.reservation.service.*;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryDao categoryDao;

	@Override
	@Transactional(readOnly = true)
	public Category get(Integer id) {
		return categoryDao.selectById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Category addCategory(Category category) {
		Integer insert = categoryDao.insert(category);
		category.setId(insert);
		return category;
	}

	@Override
	@Transactional(readOnly = false)
	public Integer delete(Integer id) {
		return categoryDao.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer update(Category category) {
		return categoryDao.update(category);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Category> getAll() {
		return categoryDao.selectAll();
	}

}
