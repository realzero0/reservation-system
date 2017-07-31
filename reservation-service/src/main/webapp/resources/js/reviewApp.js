(function(window) {
    'use strict';




    function imageMove(e) {
      var inDecre = -dX / Math.abs(dX);
      curImage += inDecre;
      if (curImage < count && curImage >= 0) {
        $countElement.text((curImage + 1) + ' / ' + count);
        isDragging = false;
        $ulEle.animate({
          "left": -inDecre * (move) * curImage + "px"
        }, 'normal');
      } else {
        curImage -= inDecre;
        $ulEle.css({
          "left": originOffset + "px"
        });
        return false;
      }
      if (e.type == 'mousemove') {
        e.preventDefault();
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
          PopUp($('.thumb'));
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

  AjaxGetData.getOffers(); $(document).scroll(function() {
    var maxHeight = $(document).height();
    var currentScroll = $(window).scrollTop() + $(window).height();
    if (maxHeight <= currentScroll + 10) {
      AjaxGetData.setCount(AjaxGetData.getCount() + 1);
      AjaxGetData.getOffers();
    }
  });




})(window);
