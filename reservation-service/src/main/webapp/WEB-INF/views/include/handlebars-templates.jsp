<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script id="item-template" type="text/x-handlebars-template">
<li class="item"><a href="/exhibition/{{id}}"
	class="item_book">
		<div class="item_preview">
			<img alt="{{name}}" class="img_thumb"
				src="/resources/img/poster/{{saveFileName}}.jpg"> <span
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