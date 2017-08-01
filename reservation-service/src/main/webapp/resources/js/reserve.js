(function(window) {
  'use strict';

  var Ticket = extend(eg.Component, {
    init: function($countEle) {
      this.$countEle = $countEle;
      this.$input = $countEle.find('input');
      this.$plus = $countEle.find('.ico_plus3');
      this.$minus = $countEle.find('.ico_minus3');
      this.$qty = $countEle.closest('.qty');
      this.$individualPrice = this.$qty.find('.individual_price');
      this.$priceResult = this.$qty.find('.total_price');
      this.price = $countEle.closest('.qty').data('price');
      this.$plus.on('click', this.plus.bind(this));
      this.$minus.on('click', this.minus.bind(this));
    },
    plus: function(e) {
      e.preventDefault();
      console.log('plus');
      var count = this.getCount();
      this.setCount(++count);
      this.setTotalPrice();
      if (count === 1) {
        this.$minus.removeClass('disabled');
        this.$input.removeClass('disabled');
      }
      this.$input.trigger('change', {
        'count': count
      });
    },
    minus: function(e) {
      e.preventDefault();
      var count = this.getCount();
      if (count > 0) {
        this.setCount(--count);
        this.setTotalPrice();
        if (count === 0) {
          this.$minus.addClass('disabled');
          this.$input.addClass('disabled');
        }
      }
      this.$input.trigger('change', {
        'count': count
      });
    },
    getCount: function() {
      return parseInt(this.$input.val(), 10);
    },
    setCount: function(count) {
      this.$input.val(count);
    },
    getTotalPrice: function() {
      return this.getCount() * this.price;
    },
    setTotalPrice: function() {
      var totalPrice = this.getTotalPrice();
      if (totalPrice > 0) {
        this.$individualPrice.addClass('on_color');
      } else {
        this.$individualPrice.removeClass('on_color');
      }
      this.$priceResult.text(this.numberFormat(totalPrice));
    },
    numberFormat: function(num) {
      return String(num).replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    }
  });

  var TicketImpl = (function() {
    $('.clearfix').each(function(i, v) {
      var ticket = new Ticket($(v));
    });
  })();



  var ReserveVerifier = (function() {
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
          },
          error: function(res) {
            alert('실패');
          }
        });
      }
    });
  })();




  var AgreementImpl = (function() {
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
  })();

})(window);
