package jinyoung.reservation.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import jinyoung.reservation.dao.*;
import jinyoung.reservation.dto.*;
import jinyoung.reservation.service.*;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	private static final Integer PAGING_COUNT = 4;

	@Override
	@Transactional(readOnly = true)
	public ProductDto getByProId(Integer productId) {
		return productDao.selectByProId(productId);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<ProductDto> getByCateId(Integer categoryId, Integer page) {
		page *= PAGING_COUNT;
		return productDao.selectByCateId(categoryId, page);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<ProductDto> getAll(Integer page) {
		page *= PAGING_COUNT;
		return productDao.selectAll(page);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountAll() {
		return productDao.countAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountByCateId(Integer categoryId) {
		return productDao.countByCateId(categoryId);
	}

}
