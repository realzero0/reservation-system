<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<div class="event">
			<div class="section_visual">
				<div class="group_visual">
					<div class="container_visual">
						<div class="prev_e">
							<div class="prev_inn">
								<a href="#" class="btn_pre_e" title="이전"> <i
									class="spr_book_event spr_event_pre">이전</i>
								</a>
							</div>
						</div>
						<div class="nxt_e">
							<div class="nxt_inn">
								<a href="#" class="btn_nxt_e" title="다음"> <i
									class="spr_book_event spr_event_nxt">다음</i>
								</a>
							</div>
						</div>
						<div>
							<div class="container_visual">
								<!-- [D] 이전,다음 버튼을 클릭할때마다 캐러셀 형태로 순환 됨 --->
								<ul class="visual_img">
									<li class="item"
										style="background-image: url(http://naverbooking.phinf.naver.net/20170209_66/1486628146913la6nC_JPEG/image.jpg); width: 338px;">
										<a href="#"> <span class="img_btm_border"></span> <span
											class="img_right_border"></span> <span class="img_bg_gra"></span>
											<div class="event_txt">
												<h4 class="event_txt_tit"></h4>
												<p class="event_txt_adr"></p>
												<p class="event_txt_dsc"></p>
											</div>
										</a>
									</li>
									<li class="item"
										style="background-image: url(http://naverbooking.phinf.naver.net/20170119_48/1484802596907hmVDm_JPEG/image.jpg); width: 338px;">
										<a href="#"> <span class="img_btm_border"></span> <span
											class="img_right_border"></span> <span class="img_bg_gra"></span>
											<div class="event_txt">
												<h4 class="event_txt_tit">뮤지컬-김종욱찾기 네이버 예약</h4>
												<p class="event_txt_adr">대학로 쁘띠첼씨어터</p>
												<p class="event_txt_dsc">네이버 예매시, 손크림/발크림(중 랜덤)을 드립니다</p>
											</div>
										</a>
									</li>
									<li class="item"
										style="background-image: url(http://naverbooking.phinf.naver.net/20170209_66/1486628146913la6nC_JPEG/image.jpg); width: 338px;">
										<a href="#"> <span class="img_btm_border"></span> <span
											class="img_right_border"></span> <span class="img_bg_gra"></span>
											<div class="event_txt">
												<h4 class="event_txt_tit"></h4>
												<p class="event_txt_adr"></p>
												<p class="event_txt_dsc"></p>
											</div>
										</a>
									</li>
								</ul>
							</div>
							<span class="nxt_fix"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="section_event_tab">
				<ul class="event_tab_lst tab_lst_min">
					<!-- [D] 활성화 된 anchor는 active 추가 -->
					<c:forEach var="item" items="${categories}" varStatus="status">
						<li class="item" data-category="${item.id}"><c:choose>
								<c:when test="${status.first}">
									<a class="anchor">
								</c:when>
								<c:when test="${not status.last}">
									<a class="anchor">
								</c:when>
								<c:otherwise>
									<a class="anchor last">
								</c:otherwise>
							</c:choose><span>${item.name}</span> </a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="section_event_lst">
				<p class="event_lst_txt">
					바로 예매 가능한 전시, 공연, 행사가 <span class="pink"> <!-- 40개 -->
					</span> 있습니다
				</p>
				<div class="wrap_event_box">
					<!-- [D] lst_event_box 가 2컬럼으로 좌우로 나뉨, 더보기를 클릭할때마다 좌우 ul에 li가 추가됨 -->
					<ul class="lst_event_box">

					</ul>
					<ul class="lst_event_box">

					</ul>
					<!-- 더보기 -->
					<div class="more">
						<button class="btn">
							<span>더보기</span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="include/mainFooter.jsp" flush="false" />
</body>
<jsp:include page="include/handlebars-templates.jsp" flush="false" />
<script	src="/resources/js/node_modules/handlebars/dist/handlebars.min.js"></script>
<script src="/resources/js/node_modules/jquery/dist/jquery.min.js"></script>
<script src="/resources/js/app.js"></script>
</html>
