(function(window) {
	'use strict';

	function deleteCategory(id) {
		$.ajax({
			url : './category/delete',
			data : 'id='+id,
			success: function(res) {
				$('select > option[value=' + id + ']').remove();
			},
			dataType : 'text'
		});
	}

	function modifyCategory(id, name) {
		$.ajax({
			url : './category/modify',
			data : 'id='+id+'&name='+name,
			success: function(res) {
				$('select > option[value=' + id + ']').html(name);
				$('#modify_name').val('');
			},
			dataType : 'text'
		});
	}

	$('#button_delete').click(function() {
		if($('select[name=delete_id] option:selected').val() != '') {
			deleteCategory($('select[name=delete_id] option:selected').val())
		}
	});

	$('#button_modify').click(function() {
		if($('#modify_name').val() != '') {
			modifyCategory($('select[name=delete_id] option:selected').val(), $('#modify_name').val());
		}
	});

})(window);
