(function(window) {
  'use strict';

  $('.btn_plus_minus.spr_book2.ico_plus3').on('click', function(e) {
    e.preventDefault();
    var $input = $(this).siblings('.count_control_input');
    var k = $input.val();
    $input.val(++k);
    if (k === 1) {
      $(this).siblings('.ico_minus3').removeClass('disabled');
      $input.removeClass('disabled');
    }
    $input.trigger('change');
  });
  $('.btn_plus_minus.spr_book2.ico_minus3').on('click', function(e) {
    e.preventDefault();
    var $input = $(this).siblings('.count_control_input');
    var k = $input.val();
    if (k > 0) {
      $input.val(--k);
      if (k === 0) {
        $(this).addClass('disabled');
        $input.addClass('disabled');
      }
    }
    $input.trigger('change');
  });

  $('.count_control_input').change(function() {
    var $qty = $(this).closest('.qty');
    var totalPrice = $(this).val() * $qty.data('price');
    if (totalPrice > 0) {
      $qty.find('.individual_price').addClass('on_color');
    } else {
      $qty.find('.individual_price').removeClass('on_color');
    }
    $qty.find('.total_price').text(numberFormat(totalPrice));

    function numberFormat(num) {
      return String(num).replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    };
  });


  $('.btn_agreement').on('click', function(e) {
    e.preventDefault();
    var $agreement = $(this).closest('.agreement');
    if ($agreement.hasClass('open')) {
      $agreement.removeClass('open');
      $agreement.find('.btn_text').text('보기');
      $agreement.find('.fn').removeClass('fn-up2');
      $agreement.find('.fn').addClass('fn-down2');
    } else {
      $(this).closest('.agreement').addClass('open');
      $agreement.find('.btn_text').text('닫기');
      $agreement.find('.fn').removeClass('fn-down2');
      $agreement.find('.fn').addClass('fn-up2');
    }
  });

  $('.section_booking_ticket').change(function() {
    var $bookingButton = $('.bk_btn_wrap');
    var regTel = /^[0-9-]{3,13}$/;
    var regEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    if ($('#tel').val() != '' && !regTel.test($('#tel').val())) {
      alert('연락처가 올바르지 않습니다.');
      $('#tel').val('');
    }
    if ($('#email').val() != '' && !regEmail.test($('#email').val())) {
      alert('이메일이 올바르지 않습니다.');
      $('#email').val('');
    }

    var sum = 0;
    $('.count_control_input').each(function() {
      sum += +$(this).val();
    });
    $('.inline_form.last').find('.inline_txt span').text(sum);
    if ($('#name').val() != '' && $('#tel').val() != '' && $('#chk3').is(':checked')) {

      if (sum > 0) {
        $bookingButton.removeClass('disable')
      } else {
        if (!$bookingButton.hasClass('disable')) {
          $bookingButton.addClass('disable');
        }
      }
    } else {
      if (!$bookingButton.hasClass('disable')) {
        $bookingButton.addClass('disable');
      }
    }


  });

  $('.bk_btn').on('click', function() {
    if (!$('.bk_btn_wrap').hasClass('disable')) {
      alert('예약을 진행합니다.');
      var formData = {};
      formData.productId = $('span.title').data('productId');
      formData.userId = $('.section_booking_form').data('user-id');
      var formArray = $('.form_horizontal').serializeArray()
      formData.reservationName = formArray[0].value;
      formData.reservationTel = formArray[1].value;
      formData.reservationEmail = formArray[2].value;
      $('.count_control_input').each(function() {
        switch ($(this).data('price-type')) {
          case 1:
            formData.generalTicketCount = $(this).val();
            break;
          case 2:
            formData.childTicketCount = $(this).val();
            break;
          case 3:
            formData.youthTicketCount = $(this).val();
            break;
        }
      });
      $.ajax({
        type: 'POST',
        url: '/exhibition/' + formData.productId + '/reserve',
        data: formData,
        success: function(res) {
          alert('성공');
          window.location.href = '/';
        }
      });
    }
  })
})(window);
