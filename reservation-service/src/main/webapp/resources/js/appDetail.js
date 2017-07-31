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
  
 /*
  var PopUpModule = {
    $findElement: $('.img-popup-layer.img-viewer'),
    $ulEle: $findElement.find('ul'),
    $thumb: $('.thumb'),

    $countElement: $('.img-popup-layer.count span'),
    itemSource: $('#popup-img-template').html(),
    itemTemplate: Handlebars.compile(itemSource),

    isDragging: false,
    
    curX: ,
    originOffset:,
    images: ,
    curImage: ,
    count: ,
    commentId: ,
    dX: ,
    move: ,
    autoMoveSize: ,
    
    //$findElement.find('img').attr('src', $(this).find('img').attr('src'));
    init: function (){
      this.curImage = 0;
      this.count = 0;
      
      this.images = new Array();
      this.commentId = $(this).attr('id');
      this.count = $(this).siblings('.img_count').text();
      this.$countElement.text('1 / ' + count);

      this.$thumb.on('click', function() {
        $('div.img-popup-layer').show();
        popupImgList();
      });
    },

    popupImgList: function(){
      $.ajax({
        type: 'GET',
        url: '/api/comments/pictures/' + commentId,
        contentType: 'application/json',
        success: function(res) {
          for (var i = 0; i < res.length; i++) {
            this.images[i] = res[i];
          }
          makeList(res);
          bindEvent();
        }
      });
      this.$ulEle.css({
        "left": 0+"px"
      });
    },

    removeLi: function(){
      this.$ulEle.children().remove();
    },

    makeList : function (res){
      var liEle = [];
      for (var i = 0; i < res.length; i++) {
        this.images[i] = res[i];
        liEle[i] = itemTemplate({'fileLocation':'http://220.230.122.151/img/' + images[i]});
      }
      liEle = liEle.join('');
      this.$findElement.find('ul').append(liEle);
      this.move = $ulEle.width();
      this.autoMoveSize = $ulEle.width()/3;
    },
    
     
    bindEvent: function(){
      $(window).resize(function(){
        this.move = this.$ulEle.width();
        this.autoMoveSize = this.$ulEle.width()/3;
      });

      $('.img-popup-layer.exit').on('click', function() {
        $('div.img-popup-layer').hide();
        removeLi();
      });

      this.$ulEle.on('mousedown touchstart', function(e) {
        this.isDragging = true;
          
        if (e.type == 'touchstart') {
          e = e.originalEvent.touches[0];
        }
        this.originOffset = this.offsetLeft;
        this.curX = e.clientX - this.originOffset;
       
      });

      $ulEle.on('mousemove touchmove', function(e) {
        if (this.isDragging) {
          if (e.type == 'touchmove') {
            e = e.originalEvent.touches[0];
          }
          this.dX = e.clientX - this.curX;
          this.$ulEle.css({
            "left": dX+"px"
          });
        }
      });

      $ulEle.on('dragstart',function(){
        return false;
      });

      $(document).on('mouseup touchend', function(e) {
        this.isDragging = false;
        this.dX = e.clientX - this.originOffset - this.curX;
       
        if (dX < -200 || 200 < dX ) {
          if(!imageMove(e))
            return false;
        } else{
       
          this.$ulEle.css({
             "left": this.originOffset+"px"
          });
        }
           
      });
    },

    imageMove: function(e){
      var inDecre = -dX/Math.abs(dX);
      this.curImage += inDecre;
      if (this.curImage < this.count && this.curImage >= 0) {
        this.$countElement.text((this.curImage+1) + ' / ' + this.count);
        this.isDragging = false;
        this.$ulEle.animate({
          "left": -inDecre*(this.move)*this.curImage+"px"
        }, 'normal'
        );
      } else{
        this.curImage -= inDecre;
          this.$ulEle.css({
          "left": this.originOffset+"px"
        });
        return false;
      }
      if (e.type == 'mousemove') {
        e.preventDefault();
      }
    }
  };
*/
  //PopUpModule.init().bind(this);

  var PopUpModule = (function() {
    var $findElement = $('.img-popup-layer.img-viewer');
    var $ulEle = $findElement.find('ul');
    var $thumb = $('.thumb');
    
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
  })();
   

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

    function imageMove(dX){
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
