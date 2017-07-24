package jinyoung.reservation.dao;

import java.util.*;

import javax.sql.*;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.stereotype.*;

import jinyoung.reservation.domain.*;
import jinyoung.reservation.dto.*;

@Repository
public class ReservationUserCommentDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<ReservationUserComment> rowMapper = BeanPropertyRowMapper
			.newInstance(ReservationUserComment.class);
	private RowMapper<CommentReadingDto> commentRowMapper = BeanPropertyRowMapper.newInstance(CommentReadingDto.class);

	public ReservationUserCommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_user_comment")
				.usingGeneratedKeyColumns("id");

	}

	public Integer insert(ReservationUserComment reservationUserComment) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationUserComment);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	public Double selectAvgScoreByProId(Integer productId) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		Double result;
		try {
			result = jdbc.queryForObject(ReservationUserCommentSqls.SELECT_BY_PRO_ID, params, Double.class);
		} catch (Exception e) {
			result = 0.0;
			return result;
		}
		return result;
	}

	public Integer selectCountByProId(Integer productId) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		return jdbc.queryForObject(ReservationUserCommentSqls.SELECT_COUNT_BY_PRO_ID, params, Integer.class);
	}

	public Collection<CommentReadingDto> selectCommentsByProId(Integer productId, Integer page, Integer count) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("page", page);
		params.put("count", count);
		return jdbc.query(ReservationUserCommentSqls.SELECT_COMMENTS_BY_PRO_ID, params, commentRowMapper);
	}

	public ReservationUserComment selectCommentByUserIdAndProductId(Integer productId, Long userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("userId", userId);
		ReservationUserComment result;
		try {
			result = jdbc.queryForObject(ReservationUserCommentSqls.SELECT_COMMENT_BY_USER_ID_AND_PRODUCT_ID, params, rowMapper);
		} catch (Exception e) {
			return null;
		}
		return result;
	}

}
