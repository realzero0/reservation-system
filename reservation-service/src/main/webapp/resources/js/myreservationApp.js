(function(window) {
  'use strict';

 
    var headModule = (function() {
	 /*
	$('a.lnk_logo[title=네이버]').click(function() {
    
      window.location.href = 'http://naver.com/';
    });

    $('a.lnk_logo[title=예약]').click(function() {
      window.location.href = '/';
    });

    $('a.btn_my').click(function() {
      window.location.href = '/booked/list';
    });
    */
    var $allButton = $('.ico_book2').closest('.item');
    var $toUseButton = $('.ico_book_ss').closest('.item');
    var $usedButton = $('.ico_check').closest('.item');
    var $canceledButton = $('.ico_back').closest('.item');
    $allButton.find('.figure').text($('.card_item').length);
    $toUseButton.find('.figure').text($('.card:not(.used) .card_item').length);
    $usedButton.find('.figure').text($('.ico_check2').closest('.card.used').find('.card_item').length);
    $canceledButton.find('.figure').text($('.ico_cancel').closest('.card.used').find('.card_item').length);
    $allButton.on('click', function() {
      if(!$(this).find('.link_summary_board').hasClass('on')) {
        $('.card').removeClass('invisible');
        addOn($(this));
      }
    });
    $toUseButton.on('click', function() {
      if(!$(this).find('.link_summary_board').hasClass('on')) {
        $('.card').removeClass('invisible');
        $('.card.used').addClass('invisible');
        addOn($(this));
      }
    });
    $usedButton.on('click', function() {
      if(!$(this).find('.link_summary_board').hasClass('on')) {
        $('.card').addClass('invisible');
        $('.ico_check2').closest('.card.used').removeClass('invisible');
        addOn($(this));
      }
    });
    $canceledButton.on('click', function() {
      if(!$(this).find('.link_summary_board').hasClass('on')) {
        $('.card').addClass('invisible');
        $('.ico_cancel').closest('.card.used').removeClass('invisible');
        addOn($(this));
      }
    });
    function addOn($button) {
      $('.link_summary_board').removeClass('on');
      $button.find('.link_summary_board').addClass('on');
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
          location.href='/booked/list';
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
    location.href='/review/write?bookingNumber=' + bookingNumber;
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
