package jinyoung.reservation.dao;

import java.util.*;

import javax.sql.*;

import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.*;

import jinyoung.reservation.dto.*;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductDto> rowMapper = BeanPropertyRowMapper.newInstance(ProductDto.class);

	public ProductDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<ProductDto> selectProductsOfAllCategoryInPage(Integer page) {
		Map<String, Object> params = new HashMap<>();
		params.put("page", page);
		return jdbc.query(ProductSqls.SELECT_PRODUCTS_OF_ALL_CATEGORY_IN_PAGE, params, rowMapper);
	}
	
	
	public Collection<ProductDto> selectByCateId(Integer categoryId, Integer page) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("page", page);
		return jdbc.query(ProductSqls.SELECT_BY_CATE_ID_IN_PAGE, params, rowMapper);
	}
	
	
	public Integer countAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(ProductSqls.COUNT_ALL, params, Integer.class);
	}

	public Integer countByCateId(Integer categoryId) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryId", categoryId);
		return jdbc.queryForObject(ProductSqls.COUNT_BY_CATE_ID, params, Integer.class);
	}

	public ProductDto selectByProId(Integer productId) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		ProductDto result;
		try {
			result = jdbc.queryForObject(ProductSqls.SELECT_BY_ID, params, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return result;
	}
}
