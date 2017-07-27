(function(window) {
  'use strict';

  //이미지 슬라이드 부분
  //드래그 방지
  $(document).on("dragstart", function(e) {
    return false;
  });
  var popupSource = $("#popup-img-template").html();
  var popupTemplate = Handlebars.compile(popupSource);
  var findElement = $('.img-popup-layer');
  var $list = findElement.find('ul');
  $('.img-popup-layer.exit').on('click', function() {
    $list.empty();
    findElement.hide();
  });
  $('.thumb').on('click', function() {
    var count = $(this).siblings('.img_count').text();
    var commentId = $(this).attr('id');
    var countElement = $('.img-popup-layer.count span')
    countElement.text('1 / ' + count);



    var isDragging = false;
    var isChanged = false;
    var curX;
    var originOffset;
    var images = new Array();
    var curImage = 0;
    var clickState = false;
    var file = {};
    $.ajax({
      type: 'GET',
      url: '/api/comments/pictures/' + commentId,
      contentType: 'application/json',
      success: function(res) {
        for (var i = 0; i < res.length; i++) {
          images[i] = res[i];
          file.fileLocation = 'http://220.230.122.151/img/' + images[i];
          var appendData = popupTemplate(file);
          $list.append(appendData);
        }
        $('.img-popup-layer').show();
      },
      error: function() {
        alert('에러가 발생했습니다.');
      }
    });



    findElement.on('mousedown touchstart', function(e) {
      isDragging = true;
      if (e.type == 'touchstart') {
        e = e.originalEvent.touches[0];
      }
      curX = e.clientX - this.offsetLeft;
    });
    findElement.on('mousemove touchmove', function(e) {
      if (isDragging && !isChanged && !clickState) {

        if (e.type == 'touchmove') {
          e = e.originalEvent.touches[0];
        }
        var dX = e.clientX - curX;
        if (dX > 30) {
          isDragging = false;
          isChanged = true;
          console.log('좌로움직임');
          if (curImage > 1) {
            curImage--;
            translate(curImage);
            return;
          }
        } else if (dX < -30) {
          isDragging = false;
          isChanged = true;
          console.log('우로움직임');
          if (curImage < images.length) {
            curImage++;
            translate(curImage);
            return;
          }
        }

        $list.css({
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
      translate(curImage);
    });


    function translate(page) {
      clickState = true;
      $list.animate({
        'left': -(page - 1) * 100 + '%'
      }, 'normal', function() {
        clickState = false;
      });
    }
  });

  /* 성찬 작업 */

  //option
  /*
  append할 위치 변수

  */

  /*           module test
  var ajaxSet = function ajax(params) {
    this.options = Object.assign({}, { // 빈객체가 타겟. {}
      method: "GET",
      url: null,
      contentType: "application/json",
      success: function(res) {}
    }, params); //params 인자로넘어가서 전달되고 맨아래 있는거처럼 사용하면됨.(덮어씌워지는거)
  }

  var AjaxGetDataModule = (function() {
    this.options = ajaxForm;
    var getData = function() {
      $.ajaxForm
    };
    var getOffers = function() {
      return getData();
    }
    return {
      getOffers: getOffers
    }
  })();

    AjaxGetDataModule(new ajaxSet({
      url: 'api/comments/1/0',
      method: 'GET'
    })).getOffers();

*/

  var ajaxGetData = (function() {
    var limitCount = 0;
    var productId = window.location.href.substr(33, 1);
    var itemSource = $("#comment-template").html();
    var itemTemplate = Handlebars.compile(itemSource);
    var getDataSize;
    var getData = function() {
      var html = "";
      $.ajax({
        type: 'GET',
        url: '/api/comments/' + productId + '/' + limitCount,
        contentType: 'application/json',
        success: function(res) {
          console.log(limitCount + "번째 요청 전달");
          for (var i = 0; i < res.length; i++) {
            html = html + itemTemplate(res[i])
          }
          $('.list_short_review').append(html);
          getDataSize = res.length;
        }
      });
    };
    var getOffers = function(){
      limitCount++;
      if(getDataSize != 0){
          return getData();
      }
      else{
        console.log("데이터 수신멈춰");
      }
    }
    return{
      getOffers: getOffers
    }

  })();
  ajaxGetData.getOffers();

  $(document).scroll(function() {
    var maxHeight = $(document).height();
    var currentScroll = $(window).scrollTop() + $(window).height();

    if (maxHeight <= currentScroll + 10) {
      ajaxGetData.getOffers();
    }
  });
})(window);
