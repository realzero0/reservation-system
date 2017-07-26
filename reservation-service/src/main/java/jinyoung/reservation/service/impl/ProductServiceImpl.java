package jinyoung.reservation.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import jinyoung.reservation.dao.*;
import jinyoung.reservation.domain.*;
import jinyoung.reservation.dto.*;
import jinyoung.reservation.service.*;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	@Autowired
	ProductPriceDao productPriceDao;
	
	@Autowired
	ProductImageDao productImageDao;

	private static final Integer PAGING_COUNT = 4;

	@Override
	@Transactional(readOnly = true)
	public ProductDto getByProductId(Integer productId) {
		ProductDto product = productDao.selectByProductId(productId);
		product.setFileId(productImageDao.selectAFileIdByProductId(productId));
		return product;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDto> getByCategoryId(Integer categoryId, Integer page) {
		page *= PAGING_COUNT;
		List<ProductDto> productList = productDao.selectByCateId(categoryId, page);
		for(int i = 0; i<productList.size(); i++) {
			Integer productId = productList.get(i).getId();
			productList.get(i).setFileId(productImageDao.selectAFileIdByProductId(productId));
		}
		return productList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDto> getAll(Integer page) {
		page *= PAGING_COUNT;
		List<ProductDto> productList = productDao.selectProductsOfAllCategoryInPage(page);
		for(int i = 0; i<productList.size(); i++) {
			Integer productId = productList.get(i).getId();
			productList.get(i).setFileId(productImageDao.selectAFileIdByProductId(productId));
		}
		return productList;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountAll() {
		return productDao.countAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountByCateId(Integer categoryId) {
		return productDao.countByCategoryId(categoryId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductPrice> getProductPricesByProductId(Integer productId) {
		return productPriceDao.selectProductPricesByProductId(productId);
	}
	
	
}
