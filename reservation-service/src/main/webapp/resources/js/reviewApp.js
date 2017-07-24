(function(window) {
  'use strict';

  //이미지 슬라이드 부분
  //드래그 방지
  $(document).on("dragstart", function(e) {
    console.log("d" + e);
    return false;
  });

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

              if (dX > 100) {
                curImage++;
                if (curImage < count) {
                  $(this).find('img').attr('src', '/img/' + images[curImage]);
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
              } else if (dX < -76) {
                curImage--;
                if (curImage >= 0) {
                  $(this).find('img').attr('src', '/img/' + images[curImage]);
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

})(window);
