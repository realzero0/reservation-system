package jinyoung.reservation.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import jinyoung.reservation.dao.*;
import jinyoung.reservation.domain.*;
import jinyoung.reservation.dto.*;
import jinyoung.reservation.service.*;

@Service
public class ReservationInfoServiceImpl implements ReservationInfoService {

	@Autowired
	ReservationInfoDao reservationInfoDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	ProductPriceDao productPriceDao;

	@Autowired
	ReservationUserCommentDao commentDao;

	@Override
	@Transactional(readOnly = false)
	public Integer addReservationInfo(ReservationInfo reservationInfo) {
		reservationInfo.setReservationType(0); // 예약 신청중
		reservationInfo.setCreateDate(new Date());
		reservationInfo.setReservationDate(new Date());
		return reservationInfoDao.insert(reservationInfo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReservationInfo> getReservationInfosByUserId(Long userId) {
		List<ReservationInfo> bookedLists = reservationInfoDao.selectReservationInfosByUserId(userId);
		return bookedLists;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BookedListDto> getBookedListsByUserId(Long userId) {
		List<ReservationInfo> reservationInfos = reservationInfoDao.selectReservationInfosByUserId(userId);
		List<BookedListDto> bookedLists = new LinkedList<>();
		for (ReservationInfo info : reservationInfos) {
			Integer productId = info.getProductId();
			ProductDto product = productDao.selectByProductId(productId);
			List<ProductPrice> productPrices = productPriceDao.selectProductPricesByProductId(productId);
			BookedListDto bookedList = new BookedListDto();
			ReservationUserComment comment = commentDao.selectCommentByUserIdAndProductId(productId, userId);
			if (comment != null) {
				bookedList.setCommentId(comment.getId());
			}
			bookedList.setProductId(productId);
			bookedList.setUserId(info.getUserId());
			bookedList.setBookingNumber(info.getId());
			bookedList.setChildTicketCount(info.getChildTicketCount());
			bookedList.setGeneralTicketCount(info.getGeneralTicketCount());
			bookedList.setYouthTicketCount(info.getYouthTicketCount());
			if (product == null) {
				System.out.println(info.getProductId());
			}
			bookedList.setPlaceName(product.getPlaceName());
			bookedList.setProductName(product.getName());
			bookedList.setReservationType(info.getReservationType());
			Integer totalPrice = 0;
			for (ProductPrice productPrice : productPrices) {
				Integer count = 0;
				switch (productPrice.getPriceType()) {
				case 1:
					count = info.getGeneralTicketCount();
					break;
				case 2:
					count = info.getChildTicketCount();
					break;
				case 3:
					count = info.getYouthTicketCount();
					break;
				}
				totalPrice += count * productPrice.getPrice();
			}
			bookedList.setTotalPrice(totalPrice);
			bookedLists.add(bookedList);
		}
		return bookedLists;
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updateReservationTypeToCancelledByBookingNumber(Integer bookingNumber) {
		ReservationInfo reservationInfo = reservationInfoDao.selectReservationInfoByBookingNumber(bookingNumber);
		reservationInfo.setReservationType(3);
		return reservationInfoDao.update(reservationInfo);
	}

	@Override
	public BookedListDto getBookedListByBookingNumber(Integer bookingNumber) {
		ReservationInfo info = reservationInfoDao.selectReservationInfoByBookingNumber(bookingNumber);
		Integer productId = info.getProductId();
		ProductDto product = productDao.selectByProductId(productId);
		List<ProductPrice> productPrices = productPriceDao.selectProductPricesByProductId(productId);
		BookedListDto bookedList = new BookedListDto();
		ReservationUserComment comment = commentDao.selectCommentByUserIdAndProductId(productId, info.getUserId());
		if (comment != null) {
			bookedList.setCommentId(comment.getId());
		}
		bookedList.setProductId(productId);
		bookedList.setUserId(info.getUserId());
		bookedList.setBookingNumber(info.getId());
		bookedList.setChildTicketCount(info.getChildTicketCount());
		bookedList.setGeneralTicketCount(info.getGeneralTicketCount());
		bookedList.setYouthTicketCount(info.getYouthTicketCount());
		if (product == null) {
			System.out.println(info.getProductId());
		}
		bookedList.setPlaceName(product.getPlaceName());
		bookedList.setProductName(product.getName());
		bookedList.setReservationType(info.getReservationType());
		Integer totalPrice = 0;
		for (ProductPrice productPrice : productPrices) {
			Integer count = 0;
			switch (productPrice.getPriceType()) {
			case 1:
				count = info.getGeneralTicketCount();
				break;
			case 2:
				count = info.getChildTicketCount();
				break;
			case 3:
				count = info.getYouthTicketCount();
				break;
			}
			totalPrice += count * productPrice.getPrice();
		}
		bookedList.setTotalPrice(totalPrice);
		return bookedList;
	}

}
