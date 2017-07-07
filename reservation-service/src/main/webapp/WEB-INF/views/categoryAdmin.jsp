<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/admin/category" method="post">
		카테고리 명 : <input type="text" name="name"><br> <input
			type="submit" value="추가">
	</form>
	<br> 삭제하기
	<br>
	<select name="delete_id">
		<option value="">삭제/수정항목</option>
		<c:forEach var="category" items="${categories}">
			<option value="${category.id}">${category.name}</option>
		</c:forEach>
	</select>
	
	<input type="button" id="button_delete" value="삭제">
	<br><br>
	<input type="text" id="modify_name">
	<input type="button" id="button_modify" value="수정">
	
	
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="/resources/js/app.js"></script>
</body>
</html>