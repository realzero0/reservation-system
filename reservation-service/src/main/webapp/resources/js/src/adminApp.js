(function(window) {
  'use strict';

  function deleteCategory(id) {
    $.ajax({
      type: 'DELETE',
      url: '/api/categories/' + id,
      success: function() {
        $('select > option[value=' + id + ']').remove();
      }
    });
  }
  

  function modifyCategory(id, name) {
    $.ajax({
      type: 'PUT',
      url: '/api/categories/' + id,
      data: JSON.stringify({name : name}),
      contentType: 'application/json',
      success: function() {
        $('select > option[value=' + id + ']').html(name);
        $('#modify_name').val('');
      }
    });
  }

  $('#button_delete').click(function() {
    if ($('select[name=delete_id] option:selected').val() != '') {
      deleteCategory($('select[name=delete_id] option:selected').val())
    }
  });

  $('#button_modify').click(function() {
    if ($('#modify_name').val() !== '') {
      modifyCategory($('select[name=delete_id] option:selected').val(), $('#modify_name').val());
    }
  });

})(window);
