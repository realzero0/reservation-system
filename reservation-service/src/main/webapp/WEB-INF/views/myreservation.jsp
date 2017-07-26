<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<jsp:include page="include/mainTitle.jsp" flush="false" />
<link href="/resources/css/style.css" rel="stylesheet">
</head>

<body>
	<div id="container">
		<jsp:include page="include/mainHeader.jsp" flush="false" />
		<hr>
		<div class="ct">
			<div class="section_my">
				<!-- 예약 현황 -->
				<div class="my_summary">
					<ul class="summary_board">
						<li class="item">
							<!--[D] 선택 후 .on 추가 link_summary_board --> <a href="#"
							class="link_summary_board on"> <i class="spr_book2 ico_book2"></i>
								<em class="tit">전체</em> <span class="figure">4</span>
						</a>
						</li>
						<li class="item"><a href="#" class="link_summary_board">
								<i class="spr_book2 ico_book_ss"></i> <em class="tit">이용예정</em>
								<span class="figure">2</span>
						</a></li>
						<li class="item"><a href="#" class="link_summary_board">
								<i class="spr_book2 ico_check"></i> <em class="tit">이용완료</em> <span
								class="figure">1</span>
						</a></li>
						<li class="item"><a href="#" class="link_summary_board">
								<i class="spr_book2 ico_back"></i> <em class="tit">취소·환불</em> <span
								class="figure">1</span>
						</a></li>
					</ul>
				</div>
				<!--// 예약 현황 -->
				<c:if test="${!empty bookedLists}">
					<!-- 내 예약 리스트 -->
					<div class="wrap_mylist">

						<ul class="list_cards" ng-if="bookedLists.length > 0">
							<!--[D] 예약확정: .confirmed, 취소된 예약&이용완료: .used 추가 card -->
							<c:if test="${!empty bookedLists0}">
								<li class="card">
									<div class=link_booking_details>
										<div class="card_header">
											<div class="left"></div>
											<div class="middle">
												<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book2 -->
												<i class="spr_book2 ico_clock"></i> <span class="tit">예약
													신청중</span>
											</div>
											<div class="right"></div>
										</div>
									</div> <c:forEach var="bookedList" items="${bookedLists0}">
										<article class="card_item">
											<a href="#" class="link_booking_details">
												<div class="card_body">
													<div class="left"></div>
													<div class="middle">
														<div class="card_detail">
															<em class="booking_number"
																data-booking-number="${bookedList.bookingNumber}">No.${bookedList.bookingNumber}</em>
															<h4 class="tit">${bookedList.productName}</h4>
															<ul class="detail">
																<li class="item"><span class="item_tit">일정</span> <em
																	class="item_dsc"> 2000.0.00.(월)2000.0.00.(일) </em></li>
																<li class="item"><span class="item_tit">내역</span> <em
																	class="item_dsc"> <c:if
																			test="${!empty bookedList.generalTicketCount}">
																어른 ${bookedList.generalTicketCount}장 
																</c:if> <c:if test="${!empty bookedList.youthTicketCount}">
																청소년 ${bookedList.youthTicketCount}장 
																</c:if> <c:if test="${!empty bookedList.childTicketCount}">
																어린이 ${bookedList.childTicketCount}장 
																</c:if> <c:if
																			test="${empty bookedList.generalTicketCount and empty bookedList.youthTicketCount and empty bookedList.childTicketCount}">
																 내역이 없습니다. 
																</c:if>
																</em></li>
																<li class="item"><span class="item_tit">상품</span> <em
																	class="item_dsc"> 내역이 없습니다. </em></li>
																<li class="item"><span class="item_tit">업체</span> <em
																	class="item_dsc"> ${bookedList.placeName } </em></li>
															</ul>
															<div class="price_summary">
																<span class="price_tit">결제 예정금액</span> <em
																	class="price_amount"> <span><fmt:formatNumber
																			value="${bookedList.totalPrice}" groupingUsed="true" /></span>
																	<span class="unit">원</span>
																</em>
															</div>
															<!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->
															<div class="booking_cancel">
																<button class="btn">
																	<span>취소</span>
																</button>
															</div>
														</div>
													</div>
													<div class="right"></div>
												</div>
												<div class="card_footer">
													<div class="left"></div>
													<div class="middle"></div>
													<div class="right"></div>
												</div>
											</a> <a href="#"
												class="fn fn-share1 naver-splugin btn_goto_share"
												title="공유하기"></a>
										</article>
									</c:forEach>
								</li>
							</c:if>
							<c:if test="${!empty bookedLists1}">
								<li class="card confirmed">
									<div class="link_booking_details">
										<div class="card_header">
											<div class="left"></div>
											<div class="middle">
												<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
												<i class="spr_book2 ico_check2"></i> <span class="tit">예약
													확정</span>
											</div>
											<div class="right"></div>
										</div>
									</div> <c:forEach var="bookedList" items="${bookedLists1}">
										<article class="card_item">
											<a href="#" class="link_booking_details">
												<div class="card_body">
													<div class="left"></div>
													<div class="middle">
														<div class="card_detail">
															<em class="booking_number"
																data-booking-number="${bookedList.bookingNumber}">No.${bookedList.bookingNumber}</em>
															<h4 class="tit">${bookedList.productName}</h4>
															<ul class="detail">
																<li class="item"><span class="item_tit">일정</span> <em
																	class="item_dsc"> 2000.0.00.(월)2000.0.00.(일) </em></li>
																<li class="item"><span class="item_tit">내역</span> <em
																	class="item_dsc"> <c:if
																			test="${!empty bookedList.generalTicketCount}">
																어른 ${bookedList.generalTicketCount}장 
																</c:if> <c:if test="${!empty bookedList.youthTicketCount}">
																청소년 ${bookedList.youthTicketCount}장 
																</c:if> <c:if test="${!empty bookedList.childTicketCount}">
																어린이 ${bookedList.childTicketCount}장 
																</c:if> <c:if
																			test="${empty bookedList.generalTicketCount and empty bookedList.youthTicketCount and empty bookedList.childTicketCount}">
																 내역이 없습니다. 
																</c:if>
																</em></li>
																<li class="item"><span class="item_tit">상품</span> <em
																	class="item_dsc"> 내역이 없습니다. </em></li>
																<li class="item"><span class="item_tit">업체</span> <em
																	class="item_dsc"> ${bookedList.placeName } </em></li>
															</ul>
															<div class="price_summary">
																<span class="price_tit">결제 예정금액</span> <em
																	class="price_amount"> <span><fmt:formatNumber
																			value="${bookedList.totalPrice}" groupingUsed="true" /></span>
																	<span class="unit">원</span>
																</em>
															</div>
															<!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->
															<div class="booking_cancel">
																<button class="btn">
																	<span>취소</span>
																</button>
															</div>
														</div>
													</div>
													<div class="right"></div>
												</div>
												<div class="card_footer">
													<div class="left"></div>
													<div class="middle"></div>
													<div class="right"></div>
												</div>
											</a> <a href="#"
												class="fn fn-share1 naver-splugin btn_goto_share"
												title="공유하기"></a>
										</article>
									</c:forEach>
								</li>
							</c:if>
							<c:if test="${!empty bookedLists2}">
								<li class="card used">
									<div class="link_booking_details">
										<div class="card_header">
											<div class="left"></div>
											<div class="middle">
												<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
												<i class="spr_book2 ico_check2"></i> <span class="tit">이용
													완료</span>
											</div>
											<div class="right"></div>
										</div>
									</div> <c:forEach var="bookedList" items="${bookedLists2}">
										<article class="card_item">
											<a href="#" class="link_booking_details">
												<div class="card_body">
													<div class="left"></div>
													<div class="middle">
														<div class="card_detail">
															<em class="booking_number"
																data-booking-number="${bookedList.bookingNumber}">No.${bookedList.bookingNumber}</em>
															<h4 class="tit">${bookedList.productName}</h4>
															<ul class="detail">
																<li class="item"><span class="item_tit">일정</span> <em
																	class="item_dsc"> 2000.0.00.(월)2000.0.00.(일) </em></li>
																<li class="item"><span class="item_tit">내역</span> <em
																	class="item_dsc"> <c:if
																			test="${!empty bookedList.generalTicketCount}">
																어른 ${bookedList.generalTicketCount}장 
																</c:if> <c:if test="${!empty bookedList.youthTicketCount}">
																청소년 ${bookedList.youthTicketCount}장 
																</c:if> <c:if test="${!empty bookedList.childTicketCount}">
																어린이 ${bookedList.childTicketCount}장 
																</c:if> <c:if
																			test="${empty bookedList.generalTicketCount and empty bookedList.youthTicketCount and empty bookedList.childTicketCount}">
																 내역이 없습니다. 
																</c:if>
																</em></li>
																<li class="item"><span class="item_tit">상품</span> <em
																	class="item_dsc"> 내역이 없습니다. </em></li>
																<li class="item"><span class="item_tit">업체</span> <em
																	class="item_dsc"> ${bookedList.placeName } </em></li>
															</ul>
															<div class="price_summary">
																<span class="price_tit">결제 예정금액</span> <em
																	class="price_amount"> <span><fmt:formatNumber
																			value="${bookedList.totalPrice}" groupingUsed="true" /></span>
																	<span class="unit">원</span>
																</em>
															</div>
															<div class="booking_cancel">
																<button class="btn">
																	<span>예매자 리뷰 남기기</span>
																</button>
															</div>
														</div>
													</div>
													<div class="right"></div>
												</div>
												<div class="card_footer">
													<div class="left"></div>
													<div class="middle"></div>
													<div class="right"></div>
												</div>
											</a> <a href="#"
												class="fn fn-share1 naver-splugin btn_goto_share"
												title="공유하기"></a>
										</article>
									</c:forEach>
								</li>
							</c:if>
							<c:if test="${!empty bookedLists3}">
								<li class="card used">
									<div class="link_booking_details">
										<div class="card_header">
											<div class="left"></div>
											<div class="middle">
												<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
												<i class="spr_book2 ico_cancel"></i> <span class="tit">취소된
													예약</span>
											</div>
											<div class="right"></div>
										</div>
									</div> <c:forEach var="bookedList" items="${bookedLists3}">
										<article class="card_item">
											<a href="#" class="link_booking_details">
												<div class="card_body">
													<div class="left"></div>
													<div class="middle">
														<div class="card_detail">
															<em class="booking_number"
																data-booking-number="${bookedList.bookingNumber}">No.${bookedList.bookingNumber}</em>
															<h4 class="tit">${bookedList.productName}</h4>
															<ul class="detail">
																<li class="item"><span class="item_tit">일정</span> <em
																	class="item_dsc"> 2000.0.00.(월)2000.0.00.(일) </em></li>
																<li class="item"><span class="item_tit">내역</span> <em
																	class="item_dsc"> <c:if
																			test="${!empty bookedList.generalTicketCount}">
																어른 ${bookedList.generalTicketCount}장 
																</c:if> <c:if test="${!empty bookedList.youthTicketCount}">
																청소년 ${bookedList.youthTicketCount}장 
																</c:if> <c:if test="${!empty bookedList.childTicketCount}">
																어린이 ${bookedList.childTicketCount}장 
																</c:if> <c:if
																			test="${empty bookedList.generalTicketCount and empty bookedList.youthTicketCount and empty bookedList.childTicketCount}">
																 내역이 없습니다. 
																</c:if>
																</em></li>
																<li class="item"><span class="item_tit">상품</span> <em
																	class="item_dsc"> 내역이 없습니다. </em></li>
																<li class="item"><span class="item_tit">업체</span> <em
																	class="item_dsc"> ${bookedList.placeName } </em></li>
															</ul>
															<div class="price_summary">
																<span class="price_tit">결제 예정금액</span> <em
																	class="price_amount"> <span><fmt:formatNumber
																			value="${bookedList.totalPrice}" groupingUsed="true" /></span>
																	<span class="unit">원</span>
																</em>
															</div>
														</div>
													</div>
													<div class="right"></div>
												</div>
												<div class="card_footer">
													<div class="left"></div>
													<div class="middle"></div>
													<div class="right"></div>
												</div>
											</a> <a href="#"
												class="fn fn-share1 naver-splugin btn_goto_share"
												title="공유하기"></a>
										</article>
									</c:forEach>
								</li>
							</c:if>
						</ul>
					</div>
					<!--// 내 예약 리스트 -->
				</c:if>
				<c:if test="${empty bookedLists}">
					<!-- 예약 리스트 없음 -->
					<div class="err">
						<i class="spr_book ico_info_nolist"></i>
						<h1 class="tit">예약 리스트가 없습니다</h1>
					</div>
					<!--// 예약 리스트 없음 -->
				</c:if>
			</div>
		</div>
		<hr>
	</div>
	<jsp:include page="include/mainFooter.jsp" flush="false" />

	<!-- 취소 팝업 -->
	<!-- [D] 활성화 display:block, 아니오 버튼 or 닫기 버튼 클릭 시 숨김 display:none; -->
	<div class="popup_booking_wrapper" style="display: none;">
		<div class="dimm_dark" style="display: block"></div>
		<div class="popup_booking refund">
			<h1 class="pop_tit">
				<span>서비스명/상품명</span> <small class="sm">2000.0.00.(월)2000.0.00.(일)</small>
			</h1>
			<div class="nomember_alert">
				<p>취소하시겠습니까?</p>
			</div>
			<div class="pop_bottom_btnarea">
				<div class="btn_gray">
					<a href="#" class="btn_bottom"><span>아니오</span></a>
				</div>
				<div class="btn_green">
					<a href="#" class="btn_bottom"><span>예</span></a>
				</div>
			</div>
			<!-- 닫기 -->
			<a href="#" class="popup_btn_close" title="close"> <i
				class="spr_book2 ico_cls"></i>
			</a>
			<!--// 닫기 -->
		</div>
	</div>
	<!--// 취소 팝업 -->

</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/resources/js/myreservationApp.js"></script>
</html>