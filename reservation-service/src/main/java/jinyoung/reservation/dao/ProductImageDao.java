package jinyoung.reservation.dao;

import javax.sql.*;

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
}
