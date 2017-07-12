(function(window) {
  'use strict';
  //header 영역
  $('a.lnk_logo[title=네이버]').click(function() {
    window.location.href = 'http://naver.com/';
  });

  $('a.lnk_logo[title=예약]').click(function() {
    window.location.href = '/';
  });

  $('a.btn_my').click(function() {
    window.location.href = '/booked/list';
  });


  //이미지 슬라이드 부분
  var imageRoller = {
    imgRolling: function(autoRollingClass) {
      var findElement = $('.' + autoRollingClass).find('ul');
      var prev = $('.btn_prev');
      var next = $('.btn_nxt');
      var move = 414;
      var clickState = false;
      var pages = $('.visual_img .item').length;
      var currentPage = 1;

      $('.figure_pagination .num.off span').html(pages);
      prev.on({
        'click': function() {
          if (clickState === false) {
            if(currentPage > 1) {
              currentPage--;
              translate(currentPage);
            }
          }
        }
      });

      next.on({
        'click': function() {
          if (clickState === false) {
            if(currentPage < pages) {
              currentPage++;
              translate(currentPage);
            }
          }
        }
      });

      function translate(page) {
        clickState = true;
        $('.figure_pagination .num:first-child').html(page);
        findElement.animate({
          'left': -(page-1)*move +'px'
        }, 'normal', function() {
          clickState = false;
        });
      }
    }
  }
  imageRoller.imgRolling('container_visual');

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
    if(!$(this).hasClass('active')) {
      $('.section_info_tab .anchor.active').removeClass('active');
      $(this).addClass('active');
      if($(this).find('span').html() === '상세정보') {
        $('.detail_area_wrap').removeClass('hide');
        $('.detail_location').addClass('hide');
      } else {
        $('.detail_area_wrap').addClass('hide');
        $('.detail_location').removeClass('hide');
      }
    }
    $(window).scrollTop($('.section_info_tab').offset().top);
  })

  //Footer Top
  $('.lnk_top').click(function() {
    $(this).scrollTop(0);
  })

})(window);
