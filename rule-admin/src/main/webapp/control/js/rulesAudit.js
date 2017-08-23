function getRules(id) {
//	$(".datebox :text").attr("readonly", "readonly");
	menuId = id;
	rules_grid(id,'');
	$("#add").remove();//去掉新增按钮
}

function openRules(rulesId, flag) {
	getDetail(rulesId, flag , 'audit');				
	$(':input').attr('readonly','readonly');
	$('#reason').removeAttr('readonly');//将input设为只读，area也被设置为只读。
	removeAddoRdel($('#handlers tr'));
	$('.combobox-f').combobox('disable');
}


function save() {
	saveRules();
}


// 删除新增行
function removeAddoRdel(doc) {
	var size = doc.size();
	$(doc).each(function(i, val) {
		if (i == 0)
			$(val).find('a[name="del"]').attr('style', 'display:none');
		if (i < size - 1)
			$(val).find('a[name="add"]').attr('style', 'display:none');
		if (i == size - 1)
			$(val).find('a[name="add"]').attr('style', '');

	})
}


function getValue(selector){
	try{
		var value = selector.combobox('getValue');
		if(value == undefined)
		value = selecotr.val();
		return value;
	}catch(e){
		return selector.val();
	}
	
}

function auditThrough() {

	var no = $('#rulesNo').val();

	$.ajax({
		url : '/ccmis/risk/rulesAudit/auditThrough',
		type : "POST",
		dataType : 'json',
		data : 'rulesNo=' + no + '&operateType=' + operateType,
		success : function(data) {
			if (data == 0)
				getRules(menuId)
			else {
				alert('通知更新异常!');
				return;
			}

		},
		error : function() {

		}

	});
	$('#editRules').attr('style', 'display: none;');

}

function auditRefuse() {

	var no = $('#rulesNo').val();

	$.ajax({
		url : '/ccmis/risk/rulesAudit/auditRefuse',
		type : "POST",
		dataType : 'json',
		data : 'rulesNo=' + no,
		success : function(data) {
			getRules(menuId)

		},
		error : function() {

		}

	});
	$('#editRules').attr('style', 'display: none;');
}



function refuseAndEdit(){
	
	var no = $('#rulesNo').val();
    var reason = $('#reason').val();
	$.ajax({
		url : '/ccmis/risk/rulesAudit/refuseAndEdit',
		type : "POST",
		dataType : 'json',
		data : 'rulesNo=' + no+'&reason='+reason,
		success : function(data) {
			getRules(menuId)

		},
		error : function() {

		}

	});
	$('#editRules').attr('style', 'display: none;');

	
} 

function saveReason(){
	refuseAndEdit();
	$('#dlg').dialog('close')
}

function reasonCancel(){
	$('#dlg').dialog('close')
	$('reason').attr('value','');
}

function cleanDoc() {
	$('#rulesNo').attr('value', '');
	$('#rulesName').attr('value', '');
	$('#priority').attr('value', '');
	$('#startTime').attr("value", '');
	$('#endTime').attr("value", '');
	$('#startInterval').attr('value','');
	$('#endInterval').attr('value','');
	$('#conditions').html('');
	$('#handlers').html('');
}

$(function (){
	$('#dlg').dialog('close');
})