package jinyoung.reservation.service;

import java.util.*;

import jinyoung.reservation.domain.*;
import jinyoung.reservation.dto.*;

public interface ReservationInfoService {
	public Integer addReservationInfo(ReservationInfo reservationInfo);

	public List<ReservationInfo> getReservationInfosByUserId(Long userId);

	public List<BookedListDto> getBookedListsByUserId(Long userId);
	
	public BookedListDto getBookedListByBookingNumber(Integer bookingNumber);

	public Integer updateReservationTypeToCancelledByBookingNumber(Integer bookingNumber);
}
