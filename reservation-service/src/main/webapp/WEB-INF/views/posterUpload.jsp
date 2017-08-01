<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<title>posterUpload</title>
</head>
<body>
	<form method="post" action="/poster" enctype="multipart/form-data">
		<select name="productid">
			<option value="">사진 업로드할 전시 선택</option>
		</select> <br> <input type="file" name="file"><a href="#"
			class="addfile">파일 추가</a><br> <input type="submit" value="등록">
	</form>
</body>
<jsp:include page="include/handlebars-templates.jsp" flush="false" />
<script
	src="/resources/js/node_modules/handlebars/dist/handlebars.min.js"></script>
<script src="/resources/js/node_modules/jquery/dist/jquery.min.js"></script>
<script src="/resources/js/fileUploadApp.js"></script>
</html>