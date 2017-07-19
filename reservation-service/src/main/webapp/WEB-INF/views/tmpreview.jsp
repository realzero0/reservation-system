<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<title>review추가</title>
</head>
<body>
	<form method="post" action="/review" enctype="multipart/form-data">
		<select name="productId">
			<option value="">리뷰할 product 선택</option>
		</select> <select name="userId">
			<option value="">리뷰할 ID 선택</option>
			<c:forEach var="user" items="${users}">
				<option value="${user.id}">${user.username}</option>
			</c:forEach>
		</select> <br> <select name="score">
			<option value="">리뷰할 점수 선택</option>
		</select> <br> 리뷰 코멘트 입력
		<textarea name="comment" COLS=70 ROWS=20></textarea>
		<br> <input type="file" name="file"><a href="#"
			class="addfile">파일 추가</a><br> <input type="submit" value="등록">
	</form>
</body>
<jsp:include page="include/handlebars-templates.jsp" flush="false" />

<script src="/resources/js/node_modules/jquery/dist/jquery.min.js"></script>
<script src="/resources/js/tmpReviewApp.js"></script>
</html>