(function(window) {
  'use strict';


  function PopUpModule($thumb) {
    var $findElement = $('.img-popup-layer.img-viewer');
    var $ulEle = $findElement.find('ul');

    var $countElement;
    var itemSource = $('#popup-img-template').html();
    var itemTemplate = Handlebars.compile(itemSource);
    var isDragging = false;
    var curX;
    var originOffset;
    var images;
    var curImage;
    var count;
    var commentId;
    var dX;
    var move;
    var autoMoveSize;
    //$findElement.find('img').attr('src', $(this).find('img').attr('src'));
    function init($th){
      curImage = 0;
      count = 0;
      $countElement = $('.img-popup-layer.count span');
      images = new Array();
      commentId = $th.attr('id');
      count = $th.siblings('.img_count').text();
      $countElement.text('1 / ' + count);
    }

    $thumb.on('click', function() {
      init($(this));
      console.log();
      $('div.img-popup-layer').show();
      popupImgList();
    });

    $(window).resize(function(){
      console.log("window size change");
      move = $ulEle.width();
      autoMoveSize = $ulEle.width()/3;
    });

    $('.img-popup-layer.exit').on('click', function() {
      $('div.img-popup-layer').hide();
      removeLi();
    });

    function popupImgList(){
      $.ajax({
        type: 'GET',
        url: '/api/comments/pictures/' + commentId,
        contentType: 'application/json',
        success: function(res) {
          for (var i = 0; i < res.length; i++) {
            images[i] = res[i];
          }
          console.log(images);
          makeList(res);
          bindtouchEvent();
        }
      });
      $ulEle.css({
        "left": 0+"px"
      });
    }

    function removeLi(){
      $ulEle.children().remove();
    }

    function makeList(res){
      var liEle = [];
      for (var i = 0; i < res.length; i++) {
        images[i] = res[i];
        liEle[i] = itemTemplate({'fileLocation':'http://220.230.122.151/img/' + images[i]});
      }
      liEle = liEle.join('');
      $findElement.find('ul').append(liEle);
      move = $ulEle.width();
      autoMoveSize = $ulEle.width()/3;
    }


    function bindtouchEvent(){

      $ulEle.on('mousedown touchstart', function(e) {
        isDragging = true;

        if (e.type == 'touchstart') {
          e = e.originalEvent.touches[0];
        }
        originOffset = this.offsetLeft;
        curX = e.clientX - originOffset;

      });

      $ulEle.on('mousemove touchmove', function(e) {
        if (isDragging) {
          if (e.type == 'touchmove') {
            e = e.originalEvent.touches[0];
          }
          dX = e.clientX - curX;
          $ulEle.css({
            "left": dX+"px"
          });
        }
      });

      $ulEle.on('dragstart',function(){
        return false;
      });

      $(document).on('mouseup touchend', function(e) {
        isDragging = false;
        dX = e.clientX-originOffset - curX;

        if (dX < -200 || 200 < dX ) {
          if(!imageMove(e))
            return false;
        } else{

          $ulEle.css({
             "left": originOffset+"px"
          });
        }

      });
    }

    function imageMove(e){
      var inDecre = -dX/Math.abs(dX);
      curImage += inDecre;
      if (curImage < count && curImage >= 0) {
        $countElement.text((curImage+1) + ' / ' + count);
        isDragging = false;
        $ulEle.animate({
          "left": -inDecre*(move)*curImage+"px"
        }, 'normal'
        );
      } else{
        curImage -= inDecre;
          $ulEle.css({
          "left": originOffset+"px"
        });
        return false;
      }
      if (e.type == 'mousemove') {
        e.preventDefault();
      }
    }

  }


var converter = new ConvertTimestamp();

  var AjaxGetData = (function() {
    var limitCount = 0;
    var productId = window.location.href.split("/")[4];
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
            res[i].score = res[i].score * 5 + ".0";
            res[i].username = res[i].username + "***";
            res[i].createDate = converter.convert(res[i].createDate);
            html = html + itemTemplate(res[i])
          }
          $('.list_short_review').append(html);
          PopUpModule($('.thumb'));
          getDataSize = res.length;
        }
      });
    };
    var setCount = function(n) {
      limitCount = n;
    };
    var getCount = function() {
      return limitCount;
    };
    var getOffers = function() {
      if (getDataSize != 0) {
        return getData();
      } else {
        console.log("데이터 수신멈춰");
      }
    };
    return {
      getOffers: getOffers,
      setCount: setCount,
      getCount: getCount
    }
  })();

  AjaxGetData.getOffers();
  $(document).scroll(function() {
    var maxHeight = $(document).height();
    var currentScroll = $(window).scrollTop() + $(window).height();
    if (maxHeight <= currentScroll + 10) {
      AjaxGetData.setCount(AjaxGetData.getCount() + 1);
      AjaxGetData.getOffers();
    }
  });




})(window);
