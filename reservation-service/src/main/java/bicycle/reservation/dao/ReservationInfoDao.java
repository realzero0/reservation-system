package bicycle.reservation.dao;

import java.util.*;

import javax.sql.*;

import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.stereotype.*;

import bicycle.reservation.domain.*;

@Repository
public class ReservationInfoDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<ReservationInfo> rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);

    public ReservationInfoDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_info").usingGeneratedKeyColumns("id");

    }

    public Integer insert(ReservationInfo reservationInfo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfo);
        return insertAction.executeAndReturnKey(params).intValue();

    }

    public List<ReservationInfo> selectReservationInfosByUserId(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return jdbc.query(ReservationInfoSqls.SELECT_RESERVATION_INFOS_BY_USER_ID, params, rowMapper);
    }

    public ReservationInfo selectReservationInfoByBookingNumber(Integer bookingNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", bookingNumber);
        ReservationInfo result;
        try {
            result = jdbc.queryForObject(ReservationInfoSqls.SELECT_RESERVATION_INFO_BY_BOOKING_NUMBER, params, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return result;
    }

    public Integer update(ReservationInfo reservationInfo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfo);
        return jdbc.update(ReservationInfoSqls.UPDATE_RESERVATION_INFO, params);
    }
}	
