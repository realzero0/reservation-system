<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script
	src="/resources/js/node_modules/handlebars/dist/handlebars.min.js"></script>
<script id="item-template" type="text/x-handlebars-template">
<li class="item"><a href="/exhibition/{{id}}"
	class="item_book">
		<div class="item_preview">
			<img alt="{{name}}" class="img_thumb"
				src="http://220.230.122.151/img/{{fileId}}"> <span
				class="img_border"></span>
		</div>
		<div class="event_txt">
			<h4 class="event_txt_tit">
				<span>{{name}}</span> <small class="sm">
					{{placeName}}</small>
			</h4>
			<p class="event_txt_dsc">{{content}}
		</div>
</a></li>
</script>
<script id="file-upload-template" type="text/x-handlebars-template">
<option value="{{id}}">{{name}}</option>
</script>
<script id="comment-template" type="text/x-handlebars-template">
<li class="list_item">
	<div>
		<div class="review_area">
			<div class="thumb_area">
				<a href="#" class="thumb" title="이미지 크게 보기"> <img
					width="90" height="90" class="img_vertical_top"
					src="/img/{{fileId}}"
					alt="리뷰이미지">
				</a> <span class="img_count">1</span>
			</div>
			<h4 class="resoc_name">{{name}}</h4>
			<p class="review">{{comment}}</p>
		</div>
		<div class="info_area">
			<div class="review_info">
				<span class="grade">{{score}}</span> <span class="name">{{username}}</span>
				<span class="date">{{createDate}} 방문</span>
			</div>
		</div>
	</div>
</li>
</script>
<script id="review-thumnail-template" type="text/x-handlebars-template">
<li class="item" data-file-num="{{fileNum}}"><a href="#" class="anchor"> <span
		class="spr_book ico_del">삭제</span>
</a> <img
	src="{{fileLocation}}"
	width="130" alt="" class="item_thumb"> <span class="img_border"></span>
</li>
</script>
<script id="popup-img-template" type="text/x-handlebars-template">
<li> <img	src="{{fileLocation}}"> </li>
</script>
