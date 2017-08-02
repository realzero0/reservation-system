(function(window) {
  'use strict';


  var headModule = (function() {


    var MenuBar = extend(eg.Component, {
      init: function($root) {
        //console.log("이닛");
        this.$card = $('.card');
        this.$rootBlock = $root;
        this.$summary = $root.find('.summary_board');
        this.$allLink = $root.find('.link_summary_board');
        this.$summary.on("click", ".item", this.addHandle.bind(this));

      },
      addHandle: function(e) {
        var $link = $(e.target).closest('.link_summary_board');
        var isClicked = $link.hasClass('on');


        if (!isClicked) {
          this.$allLink.toggleClass('on', false);
          $link.toggleClass('on', true);
          var reservationType = $link.data("reservation-type");
          this.changeCard(reservationType);

        }
      },

      changeCard: function(reservationType) {
        var isAll = (reservationType === 'all');
        this.$card.toggleClass('invisible', !isAll);
        $('.card.' + reservationType).toggleClass('invisible', false);


      }


    });
    var CountCard = extend(eg.Component, {
      init: function($card) {
        this.all = $card.length;
        this.toUse = $card.filter
        $root.on("change", function(e, v) {
          this.$
        }).bind(this);
      },
      addHandle: function(e) {
        var $link = $(e.target).closest('.link_summary_board');
        var isClicked = $link.hasClass('on');
        if (!isClicked) {
          this.$allLink.toggleClass('on', false);
          $link.toggleClass('on', true);
          var reservationType = $link.data("reservation-type");
          $link.trigger("change", {
            reservationType: reservationType
          });

        }
      }

    });
    var MenubarImpl = (function() {


      var $root = $('.my_summary');
      var menuBar = new MenuBar($root);



    })();



    /*
    var $allButton = $('.ico_book2').closest('.item');
    var $toUseButton = $('.ico_book_ss').closest('.item');
    var $usedButton = $('.ico_check').closest('.item');
    var $canceledButton = $('.ico_back').closest('.item');

    $allButton.find('.figure').text($('.card_item').length);
    $toUseButton.find('.figure').text($('.card:not(.used) .card_item').length);
    $usedButton.find('.figure').text($('.ico_check2').closest('.card.used').find('.card_item').length);
    $canceledButton.find('.figure').text($('.ico_cancel').closest('.card.used').find('.card_item').length);
  */
    var addEventHandling = function() {
      $allButton.on('click', function() {
        if (!$(this).find('.link_summary_board').hasClass('on')) {
          $('.card').removeClass('invisible');
          addOn($(this));
        }
      });
      $toUseButton.on('click', function() {
        if (!$(this).find('.link_summary_board').hasClass('on')) {
          $('.card').removeClass('invisible');
          $('.card.used').addClass('invisible');
          addOn($(this));
        }
      });
      $usedButton.on('click', function() {
        if (!$(this).find('.link_summary_board').hasClass('on')) {
          $('.card').addClass('invisible');
          $('.ico_check2').closest('.card.used').removeClass('invisible');
          addOn($(this));
        }
      });
      $canceledButton.on('click', function() {
        if (!$(this).find('.link_summary_board').hasClass('on')) {
          $('.card').addClass('invisible');
          $('.ico_cancel').closest('.card.used').removeClass('invisible');
          addOn($(this));
        }
      });

      function addOn($button) {
        $('.link_summary_board').removeClass('on');
        $button.find('.link_summary_board').addClass('on');
      }
    }

    function init() {
      initVariable();
      addEventHandling();
    }

    return {
      init: init
    }


  })();

  $('.card:not(.used) .booking_cancel').on('click', function(e) {
    e.preventDefault();
    var $article = $(this).closest('.card_item');
    var title = $article.find('.tit').text();
    var $popup = $('.popup_booking_wrapper');
    var bookingNumber = $article.find('.booking_number').data('booking-number');
    $popup.find('.pop_tit span').text(title);
    $popup.find('.btn_green .btn_bottom').on('click', function() {
      alert('취소합니다');
      var formData = {};
      formData.bookingNumber = bookingNumber;
      console.log(formData);
      $.ajax({
        type: 'POST',
        url: '/booked/cancel',
        data: formData,
        success: function(res) {
          alert('성공');
          location.href = '/booked/list';
        },
        error: function(res) {
          alert('실패했습니다.');
          $popup.hide();
        }
      });
    })
    $popup.show();
  });
  $('.card.used .booking_cancel').on('click', function(e) {
    e.preventDefault();
    var $article = $(this).closest('.card_item');
    var bookingNumber = $article.find('.booking_number').data('booking-number');
    location.href = '/review/write?bookingNumber=' + bookingNumber;
  });
  var Popup = (function() {
    var $popup = $('.popup_booking_wrapper');
    $popup.find('.btn_gray').on('click', close);
    $popup.find('.popup_btn_close').on('click', close);

    function close(e) {
      e.preventDefault();
      $popup.hide();
    }
  })();
})(window);
