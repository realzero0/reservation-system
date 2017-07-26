(function(window) {
  'use strict';
  //header 영역
  var headModule = (function() {
    $('a.lnk_logo[title=네이버]').click(function() {
      window.location.href = 'http://naver.com/';
    });

    $('a.lnk_logo[title=예약]').click(function() {
      window.location.href = '/';
    });

    $('a.btn_my').click(function() {
      window.location.href = '/booked/list';
    });

    $(".container_visual").find("img").css('width', '414');
    $(".container_visual").find("img").css('height', '414');
  })();

  $('.bk_btn').on('click', function() {
    window.location.href = window.location.href + '/reserve'
  })



  // 모듈 인스턴스화 필요
  var Popup = (function() {
    $('.thumb').on('click', function() {
      var count = $(this).siblings('.img_count').text();
      var commentId = $(this).attr('id');
      var countElement = $('.img-popup-layer.count span')
      countElement.text('1 / ' + count);
      $('div.img-popup-layer').show();

      var findElement = $('.img-popup-layer.img-viewer');
      var isDragging = false;
      var isChanged = false;
      var curX;
      var originOffset;
      var images = new Array();
      var curImage = 0;
      findElement.find('img').attr('src', $(this).find('img').attr('src'));
      $.ajax({
        type: 'GET',
        url: '/api/comments/pictures/' + commentId,
        contentType: 'application/json',
        success: function(res) {
          for (var i = 0; i < res.length; i++) {
            images[i] = res[i];
          }
          if (count > 1) {
            findElement.on('mousedown touchstart', function(e) {
              isDragging = true;
              if (e.type == 'touchstart') {
                e = e.originalEvent.touches[0];
              }
              curX = e.clientX - this.offsetLeft;
              originOffset = this.offsetLeft;
            });
            findElement.on('mousemove touchmove', function(e) {
              if (isDragging && !isChanged) {

                if (e.type == 'touchmove') {
                  e = e.originalEvent.touches[0];
                }
                var dX = e.clientX - curX;
                if (dX > 0) {
                  if (curImage === count - 1) {
                    return;
                  }
                }
                if (dX < 24) {
                  if (curImage === 0) {
                    return;
                  }
                }

                if (dX > 60) {
                  curImage++;
                  if (curImage < count) {
                    $(this).find('img').attr('src', 'http://220.230.122.151/img/' + images[curImage]);
                    countElement.text((curImage + 1) + ' / ' + count);
                    isDragging = false;
                    isChanged = true;
                    findElement.css({
                      "left": originOffset + "px"
                    });
                    return;
                  } else {
                    curImage = count - 1;
                  }
                } else if (dX < -36) {
                  curImage--;
                  if (curImage >= 0) {
                    $(this).find('img').attr('src', 'http://220.230.122.151/img/' + images[curImage]);
                    countElement.text((curImage + 1) + ' / ' + count);
                    isDragging = false;
                    isChanged = true;
                    findElement.css({
                      "left": originOffset + "px"
                    });
                    return;
                  } else {
                    curImage = 0;
                  }

                }

                findElement.css({
                  "left": dX + "px"
                });
                if (e.type == 'mousemove') {
                  e.preventDefault();
                }
              }
            });
            $(document).on('mouseup touchend', function(e) {
              isDragging = false;
              isChanged = false;
              findElement.css({
                "left": originOffset + "px"
              });
            });
          }
        }
      });
      $('.img-popup-layer.exit').on('click', function() {
        $('div.img-popup-layer').hide();
      })

    });
  })();


  //이미지 슬라이드 부분
  var imageRoller = (function() {
    var imgRolling = function(autoRollingClass) {
      var findElement = $('.' + autoRollingClass).find('ul');
      var prev = $('.btn_prev');
      var next = $('.btn_nxt');
      var move = 414;
      var clickState = false;
      var pages = $('.visual_img .item').length;
      var currentPage = 1;
      var isDragging = false;
      var isChanged = false;
      var curX;
      var images = new Array();
      if (pages > 1) {
        findElement.on('mousedown touchstart', function(e) {
          isDragging = true;
          if (e.type == 'touchstart') {
            e = e.originalEvent.touches[0];
          }
          curX = e.clientX - this.offsetLeft;
        });
        findElement.on('mousemove touchmove', function(e) {
          if (isDragging && !isChanged) {

            if (e.type == 'touchmove') {
              e = e.originalEvent.touches[0];
            }
            var dX = e.clientX - curX;
            if (dX > -(currentPage - 1) * move + 30) {
              isDragging = false;
              isChanged = true;
              if (currentPage > 1) {
                currentPage--;
                translate(currentPage);
                return;
              }
            } else if (dX < -(currentPage - 1) * move - 30) {
              isDragging = false;
              isChanged = true;

              if (currentPage < pages) {
                currentPage++;
                translate(currentPage);
                return;
              }
            }

            findElement.css({
              "left": dX + "px"
            });
            if (e.type == 'mousemove') {
              e.preventDefault();
            }
          }
        });
        findElement.on('mouseup touchend', function(e) {
          isDragging = false;
          isChanged = false;
          translate(currentPage);
        });
      }
      $('.figure_pagination .num.off span').html(pages);
      prev.on({
        'click': function() {
          if (clickState === false) {
            if (currentPage > 1) {
              currentPage--;
              translate(currentPage);
            }
          }
        }
      });

      next.on({
        'click': function() {
          if (clickState === false) {
            if (currentPage < pages) {
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
          'left': -(page - 1) * move + 'px'
        }, 'normal', function() {
          clickState = false;
        });
      }
    };
    return {
      imgRolling: imgRolling
    };
  })();

  imageRoller.imgRolling('container_visual');



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

})(window);
