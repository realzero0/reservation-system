requirejs.config({
    baseUrl: '/resources/js',
    paths: {
        jquery: 'node_modules/jquery/dist/jquery.min',
        eg: 'node_modules/egjs/dist/eg',
        extend: 'AMD/extend'
    },
    shim:{
      'eg' : {
        deps: ['jquery'],
        exports: 'eg'
      },
      'extend' : {
        deps: ['eg', 'jquery'],
        exports: 'extend'
      }
    }
});
requirejs(['jquery', 'eg', 'extend'], function($, eg, extend) {
      'use strict';
      console.log(extend);
      var extend = extend;
      console.log(eg.Component);
      var MenuBar = extend(eg.Component, {
          init: function ($root) {
              this.$card = $('.card');
              this.$rootBlock = $root;
              this.$summary = $root.find('.summary_board');
              this.$allLink = $root.find('.link_summary_board');
              this.$summary.on("click", ".item", this.addHandle.bind(this));
          },
          addHandle: function (e) {
              var $link = $(e.target).closest('.link_summary_board');
              var isClicked = $link.hasClass('on');

              if (!isClicked) {
                  this.$allLink.toggleClass('on', false);
                  $link.toggleClass('on', true);
                  var reservationType = $link.data("reservation-type");
                  this.changeCard(reservationType);
              }
          },

          changeCard: function (reservationType) {
              var isAll = (reservationType === 'all');
              this.$card.toggleClass('invisible', !isAll);
              $('.card.' + reservationType).toggleClass('invisible', false);


          }
      });

      var CountCard = extend(eg.Component, {
          init: function ($card) {
              this.all = $card.length;
              this.toUse = $card.filter
              $root.on("change", function (e, v) {
                  this.$
              }).bind(this);
          },
          addHandle: function (e) {
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

      var MenubarImpl = (function () {
          var $root = $('.my_summary');
          var menuBar = new MenuBar($root);
      })();
  //btn_gray btn_green
      var CancelCard = extend(eg.Component, {

          init: function ($_toUse) {
              this.$popup = $('.popup_booking_wrapper');
              this.$popUpTit = this.$popup.find(".pop_tit span");
              this.$btnConfirm = this.$popup.find('._confirm');
              this.$btnCancel = this.$popup.find('._cancel');
              this.bookingNumber = 0;
              $_toUse.on("click", this.bringData.bind(this));
              this.$btnCancel.on("click", this.closePopup.bind(this));
              this.$btnConfirm.on("click", this.postAjax.bind(this));
          },
          bringData: function (e) {
              var $cardDetail = $(e.target).closest(".card_detail");
              var title = $cardDetail.find('.tit').text();

              this.bookingNumber = $cardDetail.find('.booking_number').data('booking-number');

              this.$popUpTit.text(title);
              this.$popup.show();
          },
          closePopup: function () {
              this.$popup.hide();
          },
          postAjax: function () {
              var formData = {};
              formData.bookingNumber = this.bookingNumber;
              console.log(formData);

              $.ajax({
                  type: 'POST',
                  url: '/booked/cancel',
                  data: formData,
                  success: function (res) {
                      alert('성공');
                      location.href = '/booked/list';
                  },
                  error: function (res) {
                      alert('실패했습니다.');
                      $popup.hide();
                  }
              });
          }
      });

      var CancelCardImpl = (function () {
          var cancelCard = new CancelCard($("._toUse"));
      })();

});
// requirejs(['jquery', 'eg', 'extend'], function ($, eg, extend) {
//
// });
