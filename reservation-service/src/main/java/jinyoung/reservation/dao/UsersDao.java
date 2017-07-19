package jinyoung.reservation.dao;

import java.util.*;

import javax.sql.*;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.stereotype.*;

import jinyoung.reservation.domain.*;

@Repository
public class UsersDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Users> rowMapper = BeanPropertyRowMapper.newInstance(Users.class);

	public UsersDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_user_comment").usingGeneratedKeyColumns("id");

	}

	public Integer insert(Users users) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(users);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public Collection<Users> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(UsersDaoSqls.SELECT_ALL, params, rowMapper);
	}
}
