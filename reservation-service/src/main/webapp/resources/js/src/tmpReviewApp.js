(function(window) {
  'use strict';
  var fileUploadSource = $("#file-upload-template").html();
  var fileUploadTemplate = Handlebars.compile(fileUploadSource);

  for (var i = 0; i <= 5; i++) {
    $('select[name=score] :last-child').after('<option value="' + i * 0.2 + '">' + i * 0.2 + '</option>');
  }

  function countProducts() {
    $.ajax({
      type: 'GET',
      url: '/api/products/count/1',
      success: function(res) {
        for (var page = 0; page * 4 < res; page++) {
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
          $('select[name=productId] :last-child').after(fileUploadTemplate(res[i]));
        }

      }
    });
  };
  countProducts();

  $('select.product').change(function() {
    $('input.product').val($(this).val());
  });
  $('.addfile').on('click', function() {
    $(this).before('<br><input type="file" name="file">');
  })

})(window);
