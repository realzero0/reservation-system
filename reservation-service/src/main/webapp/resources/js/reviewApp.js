(function(window) {
  'use strict';


  var $reviewSection = $('.section_review_list');
  $reviewSection.on('click', function(e) {
    if (e.target.className === 'thumb') {
      var commentId = $(this).find('.thumb').attr('id');
      PopupModule.PopupClick(commentId);
    }
  });
  var $popupSection = $('.img-popup-layer.exit');
  $popupSection.on('click', function(e) {
    PopupModule.exitClick();
  });

  var ConvertTimestampModule = (function() {
    var convert = function(timestamp) {
      var d = new Date(timestamp); // Convert the passed timestamp to milliseconds
      //console.log(d);
      var yyyy = d.getFullYear();
      var mm = ('0' + (d.getMonth() + 1)).slice(-2); // Months are zero based. Add leading 0.
      var dd = ('0' + d.getDate()).slice(-2); // Add leading 0.
      var hh = d.getHours();
      var h = hh;
      var min = ('0' + d.getMinutes()).slice(-2); // Add leading 0.
      var ampm = 'AM';
      var time;

      if (hh > 12) {
        h = hh - 12;
        ampm = 'PM';
      } else if (hh === 12) {
        h = 12;
        ampm = 'PM';
      } else if (hh == 0) {
        h = 12;
      }
      // ie: 2013-02-18, 8:35 AM
      //console.log(yyyy);
      time = yyyy + '-' + mm + '-' + dd + ', ' + h + ':' + min + ' ' + ampm;
      return time;
    }
    return {
      convert: convert
    }
  })();


  var PopupModule = (function() {



    var countElement = $('.img-popup-layer.count span');


    function getData(commentId) {
      var arr = [];
      $.ajax({
        type: 'GET',
        url: '/api/comments/pictures/' + commentId,
        contentType: 'application/json',
        success: function(res) {
          console.log("ajax요청성공");
          for (var i = 0; i < res.length; i++) {
            arr[i] = {
              "fileLocation": res[i]
            };
          }
        }
      });
      return arr;
    }

    function addTemplate(arr) {
      var html = "";
      var count = $(this).siblings('.img_count').text();
      var popupSource = $("#popup-img-template").html();
      var popupTemplate = Handlebars.compile(popupSource);
      var findElement = $('.img-popup-layer');
      var $list = findElement.find('ul');
      countElement.text('1 / ' + count);
      for (var i = 0; i < arr.length; i++) {
        arr[i].fileLocation = 'http://220.230.122.151/img/' + arr[i].fileLocation;
        html = html + popupTemplate(arr[i]);
      }
      $list.append(html);
    }

    function PopupClick(commentId) {
      console.log(commentId);
      console.log(getData(commentId).length);
      addTemplate(getData(commentId));
      $('.img-popup-layer').show();
    };

    function exitClick() {

      $list.empty();
      findElement.hide();
    };
    return {
      getData: getData,
      addTemplate: addTemplate,
      PopupClick: PopupClick,
      excitClick: exitClick

    }

  })();


  var ajaxGetData = (function() {
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
            res[i].username = res[i].username.substr(0, 2) + "***";
            res[i].createDate = (ConvertTimestampModule.convert(res[i].createDate));
            html = html + itemTemplate(res[i])
          }
          $('.list_short_review').append(html);

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

  ajaxGetData.getOffers();
  $(document).scroll(function() {
    var maxHeight = $(document).height();
    var currentScroll = $(window).scrollTop() + $(window).height();
    if (maxHeight <= currentScroll + 10) {
      ajaxGetData.setCount(ajaxGetData.getCount() + 1);
      ajaxGetData.getOffers();
    }
  });
})(window);
