(function(window) {
  'use strict';
  var fileUploadSource = $("#file-upload-template").html();
  var fileUploadTemplate = Handlebars.compile(fileUploadSource);
  function countProducts() {
    $.ajax({
      type: 'GET',
      url: '/api/products/count/1',
      success: function(res) {
        for(var page = 0; page*4 < res; page++) {
          getProducts(page);
        }
      }
    });
  }
  var getProducts = function(page) {
    $.ajax({
      type: 'GET',
      url: '/api/products/cate/1/page/' + page,
      contentType: 'application/json',
      success: function(res) {
        for (var i = 0; i < res.length; i++) {
          $('select[name=productid]').append(fileUploadTemplate(res[i]));
        }

      }
    });
  };
  countProducts();

  $('.addfile').on('click',function() {
    $(this).before('<br><input type="file" name="file"><br>');
  })

})(window);
