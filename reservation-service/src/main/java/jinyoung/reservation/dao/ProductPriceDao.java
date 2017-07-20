package jinyoung.reservation.dao;

import java.util.*;

import javax.sql.*;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.stereotype.*;

import jinyoung.reservation.domain.*;

@Repository
public class ProductPriceDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<ProductPrice> rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);

	public ProductPriceDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("product_price")
				.usingGeneratedKeyColumns("id");
	}

	public Integer insert(ProductPrice productPrice) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(productPrice);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	public List<ProductPrice> selectProductPricesByProductId(Integer productId) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		return jdbc.query(ProductPriceSqls.SELECT_PRODUCT_PRICES_BY_PRODUCT_ID, params, rowMapper);
	}
}