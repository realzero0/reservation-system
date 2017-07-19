package jinyoung.reservation.dao;

import java.util.*;

import javax.sql.*;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.stereotype.*;

import jinyoung.reservation.domain.*;

@Repository
public class ReservationUserCommentImageDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<ReservationUserCommentImage> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserCommentImage.class);

	public ReservationUserCommentImageDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_user_comment_image").usingGeneratedKeyColumns("id");

	}

	public Integer insert(ReservationUserCommentImage reservationUserCommentImage) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationUserCommentImage);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public Collection<ReservationUserCommentImage> getByComId(Integer commentId) {
		Map<String, Object> params = new HashMap<>();
		params.put("commentId", commentId);
		return jdbc.query(ReservationUserCommentImageSqls.SELECT_BY_COMMENT_ID, params, rowMapper);
	}
}
