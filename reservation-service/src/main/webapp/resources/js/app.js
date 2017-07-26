(function(window) {
  'use strict';

  // 페이지 로딩 시 읽어옴

  $(document).ready(function() {
    CategoryModule.initProducts();
  });

  //header 영역
  /*
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
  })();
*/


  //promotion slide 영역
  var imageRoller = (function() {
    var imgRolling = function(autoRollingClass) {
      var findElement = $('.' + autoRollingClass).find(' > ul');
      var prev = $('.prev_inn');
      var next = $('.nxt_e');
      var speed = 2000;
      var move = 338;
      var timer = setInterval(moveNextSlide, speed);
      var clickState = false;

      var clearAndSetTimer = function(time) {
        clearInterval(timer);
        timer = setInterval(moveNextSlide, time);
      }
      findElement.on({
        'mouseenter': function() {
          clearInterval(timer);
          setTimeout(function() {
            clearAndSetTimer(speed);
          }, 2000);

        },
        'mouseleave': function() {
          clearAndSetTimer(speed);
        }
      });

      prev.on({
        'click': function() {
          if (clickState === false) {
            clickState = true;
            movePrevSlide();
          }
        },
        'mouseenter': function() {
          clearInterval(timer);
          setTimeout(function() {
            clearAndSetTimer(speed);
          }, 2000);
        },
        'mouseleave': function() {
          clearAndSetTimer(speed);
        }
      });

      next.on({
        'click': function() {
          if (clickState === false) {
            clickState = true;
            moveNextSlide();
          }
        },
        'mouseenter': function() {
          clearInterval(timer);
          setTimeout(function() {
            clearAndSetTimer(speed);
          }, 2000);
        },
        'mouseleave': function() {
          clearAndSetTimer(speed);
        }
      });

      function movePrevSlide() {
        var lastChild = findElement.children().filter(':last-child').clone(true);
        lastChild.prependTo(findElement);
        findElement.css('left', '-' + move + 'px');
        findElement.children().filter(':last-child').remove();
        findElement.animate({
          'left': '0'
        }, 'normal', function() {
          clickState = false;
        });
      }

      function moveNextSlide() {
        var firstChild = findElement.children().filter(':first-child').clone(true);
        firstChild.appendTo(findElement);
        findElement.children().filter(':first-child').remove();
        findElement.css('left', '0');
        findElement.animate({
          'left': '-' + move + 'px'
        }, 'normal', function() {
          clickState = false;
        });
      }
    }
    return {
      imgRolling: imgRolling
    };
  })();
  imageRoller.imgRolling('container_visual');

  // 카테고리 클릭
  var CategoryModule = (function() {
    var categories = new Array();
    var currentCate;
    var page = 0;
    var productCount;
    var itemSource = $("#item-template").html();
    var itemTemplate = Handlebars.compile(itemSource);

    var clickCategory = (function() {
      var currentUrl = location.href;
      var $anchor = $('.anchor');
      $anchor.click(function() {
        if (!($(this).hasClass('active'))) {
          var subUrl = currentUrl.split('?');
          subUrl[0] += '?categoryName=' + $(this).find('span').html();
          history.pushState(null, null, subUrl[0]);
          $('.lst_event_box').empty();
          initProducts();
        }
      });
    })();

    (function() {
      $('.section_event_tab .item').each(function() {
        categories[$(this).find('span').html()] = $(this).data('category');
      });
    })();

    var initProducts = function() {
      page = 0;
      var categoryParam = getParameters('categoryName');
      if (categoryParam !== '' && categories[categoryParam] != undefined) {
        currentCate = categories[categoryParam];
      } else {
        currentCate = 1;
      }
      $('.anchor.active').removeClass('active');
      $('.item[data-category=' + currentCate + ']').find('.anchor').addClass('active');
      getProductsByCate(currentCate, page);
      countProductsByCate(currentCate);
      page++;
    };
    // 1은 전체, 나머지는 모두 카테고리에 따라 select
    var getProductsByCate = function(categoryId, productPage) {
      $.ajax({
        type: 'GET',
        url: '/api/products/cate/' + categoryId + '/page/' + productPage,
        contentType: 'application/json',
        success: function(res) {
          productUlAppendMap(res);
          //addExhibition을 대신하는 코드, 한번에 append 진행
        }
      });
    }
    function productUlAppendMap(res){
      var ulLeft = [];
      var ulRight = [];

      for(var i = 0; i < res.length; i++){
        if(i%2 == 0){
          ulLeft[i/2] = itemTemplate(res[i]);
        } else{
          ulRight[parseInt(i/2)] = itemTemplate(res[i]);
        }
      }
      
      ulLeft = ulLeft.join('');
      ulRight = ulRight.join('');
      $('.wrap_event_box > ul:first-child').append(ulLeft);
      $('.wrap_event_box > ul:first-child + ul').append(ulRight);
    }

    var countProductsByCate = function(categoryId) {
      $.ajax({
        type: 'GET',
        url: '/api/products/count/' + categoryId,
        success: function(res) {
          productCount = res;
          $('span.pink').html(productCount + '개');
        }
      });
    }

    //더보기
    var getMoreProducts = function() {
      if (page * 4 < productCount) {
        getProductsByCate(currentCate, page);
        page++;
      }
    }
    $('.more > .btn').on('click', function() {
      getMoreProducts();
    });

    //스크롤 시 더보기
    $(window).scroll(function() {
      if ($(window).scrollTop() == $(document).height() - $(window).height()) {
        getMoreProducts();
      }
    });

    //get으로 javascript에서 파라미터 받아오기
    var getParameters = function(paramName) {
      // 리턴값을 위한 변수 선언
      var returnValue;

      // 현재 URL 가져오기
      var curUrl = location.href;

      // get 파라미터 값을 가져올 수 있는 ? 를 기점으로 slice 한 후 split 으로 나눔
      var parameters = (curUrl.slice(curUrl.indexOf('?') + 1, curUrl.length)).split('&');

      // 나누어진 값의 비교를 통해 paramName 으로 요청된 데이터의 값만 return
      for (var i = 0; i < parameters.length; i++) {
        var varName = parameters[i].split('=')[0];
        if (varName.toUpperCase() == paramName.toUpperCase()) {
          returnValue = parameters[i].split('=')[1];
          return decodeURIComponent(returnValue);
        }
      }
    };
    return {
      clickCategory: clickCategory,
      initProducts: initProducts
    };
  })();


  //Footer Top
  var FooterModule = (function() {
    $('.lnk_top').click(function() {
      $(this).scrollTop(0);
    })
  })();

})(window);
