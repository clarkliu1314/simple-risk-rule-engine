var setting = {
	async : {
		enable : true,
		url : getUrl
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeExpand : beforeExpand,
		onAsyncSuccess : onAsyncSuccess,
		onAsyncError : onAsyncError,
		onCheck : onCheck,// 选中时触发的事情
		onClick : onClick
	// 单机触发的时间
	}
};

var log, className = "dark", perCount = 100, perTime = 100;
// 初始化AJAX异步调用时的URL值
function getUrl(treeId, treeNode) {
	var curCount = (treeNode.children) ? treeNode.children.length : 0;
	var getCount = (curCount + perCount) > treeNode.count ? (treeNode.count - curCount)
			: perCount;
	var param = "id=" + treeNode.id;
	return "/ccmis/risk/menu/getByParentId?" + param;// AJAX异步交互的地址
}

// 展开节点异步取子节点之前调用的函数
function beforeExpand(treeId, treeNode) {
	if (!treeNode.isAjaxing) {
		treeNode.times = 1;
		ajaxGetNodes(treeNode, "refresh");
		return true;
	} else {
		alert("正在下载数据中，请稍后展开节点。。。");
		return false;
	}
}

// AJAX异步交互成功时调用的函数
function onAsyncSuccess(event, treeId, treeNode, msg) {
	if (!msg || msg.length == 0) {
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj("rootlist"), totalCount = treeNode.count;
	if (treeNode.children.length < totalCount) {
		setTimeout(function() {
			ajaxGetNodes(treeNode);
		}, perTime);
	} else {
		treeNode.icon = "";
		zTree.updateNode(treeNode);
		zTree.selectNode(treeNode.children[0]);
	}
}

// AJAX异步交互出错时调用的函数
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
		errorThrown) {
	var zTree = $.fn.zTree.getZTreeObj("rootlist");
	alert("异步获取数据出现异常。");
	treeNode.icon = "";
	zTree.updateNode(treeNode);
}

// AJAX交互得到信息
function ajaxGetNodes(treeNode, reloadType) {
	var zTree = $.fn.zTree.getZTreeObj("rootlist");
	if (reloadType == "refresh") {
		treeNode.icon = "../img/loading.gif";// 异步加载数据时显示的图片
		zTree.updateNode(treeNode);
	}
	zTree.reAsyncChildNodes(treeNode, reloadType, true);
}

// 单击是所触发的时间
function onClick(e, treeId, treeNode) {
	var id = treeNode.id;
	var zTree = $.fn.zTree.getZTreeObj("rootlist"), checkCount = zTree
			.getCheckedNodes(true).length;
	if (checkCount > 0) {
		clearCheckedNodes();
	}
	interfaceName = treeNode.interfaceName;
	getRules(id);
	// 触发进行业务逻辑的处理的信息
}

// 当单机选中复选框的时候进行的处理
function onCheck(e, treeId, treeNode) {
	var ids = '';
	var zTree = $.fn.zTree.getZTreeObj("rootlist"), nodes = zTree
			.getCheckedNodes(true);
	for ( var i = 0, l = nodes.length; i < l; i++) {
		ids += nodes[i].id;
		if (i != l - 1)
			ids += ",";
	}
	// 把得到的已选择的节点ID集合进行其他处理
}

// 清空已经选中的树的数据
function clearCheckedNodes() {
	var zTree = $.fn.zTree.getZTreeObj("rootlist"), nodes = zTree
			.getCheckedNodes(true);
	for ( var i = 0, l = nodes.length; i < l; i++) {
		nodes[i].checked = false;
		zTree.updateNode(nodes[i]);
	}
}

// 进行树中已经选中的统计的函数
function count() {
	var zTree = $.fn.zTree.getZTreeObj("rootlist"), checkCount = zTree
			.getCheckedNodes(true).length, // 所有选中的节点
	nocheckCount = zTree.getCheckedNodes(false).length, // 所有未选中的
	changeCount = zTree.getChangeCheckedNodes().length;// 新更改的节点
}

function getRootList() {

	$.ajax({
		url : '/ccmis/risk/menu/getRootList',
		type : "POST",
		dataType : 'json',
		success : function(data) {
			$.fn.zTree.init($("#rootlist"), setting, data);
		},
		error : function() {

		}

	});

}
$(document).ready(function() {
	getRootList();

});
