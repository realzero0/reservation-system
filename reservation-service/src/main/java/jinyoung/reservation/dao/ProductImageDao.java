package jinyoung.reservation.dao;

import java.util.*;

import javax.sql.*;

import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.stereotype.*;

import jinyoung.reservation.domain.*;

@Repository
public class ProductImageDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<ProductImage> rowMapper = BeanPropertyRowMapper.newInstance(ProductImage.class);

	public ProductImageDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("product_image").usingGeneratedKeyColumns("id");

	}

	public Integer insert(ProductImage productImage) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(productImage);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public Integer selectAFileIdByProductId(Integer productId) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		Integer result;
		try {
			result = jdbc.queryForObject(ProductImageSqls.SELECT_A_FILE_ID_BY_PRODUCT_ID, params, Integer.class);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
		return result;
	}
}
