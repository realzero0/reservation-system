
  var PopUpModule = {
    $findElement: $('.img-popup-layer.img-viewer'),
    $ulEle: $findElement.find('ul'),
    $thumb: $('.thumb'),

    $countElement: $('.img-popup-layer.count span'),
    itemSource: $('#popup-img-template').html(),
    itemTemplate: Handlebars.compile(itemSource),

    isDragging: false,
    
    curX: null,
    originOffset: null,
    images: null,
    curImage: null,
    count: null,
    commentId: null,
    dX: null,
    move: null,
    autoMoveSize: null,
    
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

  //PopUpModule.init().bind(this);