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
public class UsersDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Users> rowMapper = BeanPropertyRowMapper.newInstance(Users.class);

	public UsersDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("users").usingGeneratedKeyColumns("id");

	}

	public Integer insert(Users users) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(users);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public Collection<Users> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(UsersDaoSqls.SELECT_ALL, params, rowMapper);
	}
	
	public Users selectById(Long userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		Users result;
		try {
			result = jdbc.queryForObject(UsersDaoSqls.SELECT_BY_ID, params, rowMapper);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
		return result;
	}
	
	public Users selectBySnsId(String userSnsId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userSnsId", userSnsId);
		Users result;
		try {
			result = jdbc.queryForObject(UsersDaoSqls.SELECT_BY_SNS_ID, params, rowMapper);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
		return result;
	}
}
