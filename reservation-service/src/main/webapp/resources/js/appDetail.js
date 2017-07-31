(function(window) {
  'use strict';
  //header 영역
  var headModule = (function() {
    $(".container_visual").find("img").css('width', '414');
    $(".container_visual").find("img").css('height', '414');
  })();
  $('.bk_btn').on('click', function() {
    window.location.href = window.location.href + '/reserve'
  })
  // 모듈 인스턴스화 필요
  var popup = new PopUp($('.thumb'));
  //이미지 슬라이드 부분
  var ImageRollerModule = (function() {
    var $findElement = $('.container_visual').find('ul');
    var $imageContainer = $('.container_visual');
    var $prev = $('.btn_prev');
    var $next = $('.btn_nxt');
    var move = 414;
    var pages = $('.visual_img .item').length;
    var currentPage = 1;
    var clickState = false;
    var isDragging = false;
    var isChanged = false;
    var curX;
    var images = new Array();
    $('.figure_pagination .num.off span').html(pages);
    $findElement.on('mousedown touchstart', function(e) {
      isDragging = true;
      if (e.type == 'touchstart') {
        e = e.originalEvent.touches[0];
      }
      curX = e.clientX - this.offsetLeft;
    });
    $findElement.on('mousemove touchmove', function(e) {
      if (isDragging && !isChanged) {
        var dX = e.clientX - curX;
        imageMove(dX);
      }
    });
    $findElement.on('mouseup touchend', function(e) {
      isDragging = false;
      isChanged = false;
      translate(currentPage);
    });
    $prev.on({
      'click': function() {
        if (clickState === false) {
          if (currentPage > 1) {
            currentPage--;
            translate(currentPage);
          }
        }
      }
    });
    $next.on({
      'click': function() {
        if (clickState === false) {
          if (currentPage < pages) {
            currentPage++;
            translate(currentPage);
          }
        }
      }
    });
    function imageMove(dX) {
      if (dX > -(currentPage - 1) * move + 30) { // >
        isDragging = false;
        isChanged = true;
        if (currentPage > 1) {
          currentPage--;
          translate(currentPage);
          return;
        }
      } else if (dX < -(currentPage - 1) * move - 30) { // <
        isDragging = false;
        isChanged = true;
        if (currentPage < pages) {
          currentPage++;
          translate(currentPage);
          return;
        }
      }
    }
    function translate(page) {
      clickState = true;
      $('.figure_pagination .num:first-child').html(page);
      $findElement.animate({
        'left': -(page - 1) * move + 'px'
      }, 'normal', function() {
        clickState = false;
      });
    }
  })();
  var buttonModule = (function() {
    // 공유하기
    $('.btn_goto_share').on('click', function() {
      var sharingUrl = encodeURI(encodeURIComponent(window.location.href));
      var title = encodeURI($('.visual_txt').find('span').html());
      document.location = "http://share.naver.com/web/shareView.nhn?url=" + sharingUrl + "&title=" + title;
    });
    //펼쳐보기 클릭 시
    $('.bk_more').on('click', function() {
      if ($('.store_details').hasClass('close3')) {
        $('.store_details').removeClass('close3');
        $('.bk_more._open').addClass('invisible');
        $('.bk_more._close').removeClass('invisible');
      } else {
        $('.store_details').addClass('close3');
        $('.bk_more._open').removeClass('invisible');
        $('.bk_more._close').addClass('invisible');
      }
      $(window).scrollTop($('.section_store_details').offset().top);
    })
    //상세정보, 오시는길
    $('.section_info_tab .anchor').on('click', function() {
      if (!$(this).hasClass('active')) {
        $('.section_info_tab .anchor.active').removeClass('active');
        $(this).addClass('active');
        if ($(this).find('span').html() === '상세정보') {
          $('.detail_area_wrap').removeClass('hide');
          $('.detail_location').addClass('hide');
        } else {
          $('.detail_area_wrap').addClass('hide');
          $('.detail_location').removeClass('hide');
        }
      }
      $(window).scrollTop($('.section_info_tab').offset().top);
    });
    //Footer Top
    $('.lnk_top').click(function() {
      $(this).scrollTop(0);
    });
  })();
  //지도부분
  var Location = (function() {
    var x;
    var y;
    var map = new naver.maps.Map('store_map');
    var myaddress = $('#store_map').data('address'); // 도로명 주소나 지번 주소만 가능 (건물명 불가!!!!)
    naver.maps.Service.geocode({
      address: myaddress
    }, function(status, response) {
      if (status !== naver.maps.Service.Status.OK) {
        return alert(myaddress + '의 검색 결과가 없거나 기타 네트워크 에러');
      }
      var result = response.result;
      // 검색 결과 갯수: result.total
      // 첫번째 결과 결과 주소: result.items[0].address
      // 첫번째 검색 결과 좌표: result.items[0].point.y, result.items[0].point.x
      var myaddr = new naver.maps.Point(result.items[0].point.x, result.items[0].point.y);
      map.setCenter(myaddr); // 검색된 좌표로 지도 이동
      // 마커 표시
      var marker = new naver.maps.Marker({
        position: myaddr,
        map: map
      });
      $('.store_location').on('click', function(e) {
        window.location.href = 'http://map.naver.com/index.nhn?query=' + urlencode(myaddress);
      });
    });
  })();
  $("img.img_thumb").lazyload();
})(window);
