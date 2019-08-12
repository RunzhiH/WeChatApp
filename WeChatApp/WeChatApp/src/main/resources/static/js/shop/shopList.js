/**
 * 角色列表
 */
$(function() {
	layui.use('table', function() {
		var table = layui.table;
		var tbody = $("#tbody");
		$.get("/web/shop/getShopList", function(data) {
			if (data != null) {
				tbody.empty();
				$.each(data, function(index, item) {
					var td = $("<tr><td>" + item.shopName + "</td>" + "<td>" + item.shopAddress + "</td>" + "<td>" + item.shopDescribe + "</td>" + "<td>" + DateUtils.formatDate(item.createTime) + "</td>" + "<td>" + "<button class='layui-btn layui-btn-xs' onclick=\"updateShop('" + item.shopId + "')\">编辑</button>" + "<button class='layui-btn layui-btn-danger layui-btn-xs' onclick=\"delShop('" + item.shopId + "')\">删除</button></td></tr>");
					tbody.append(td);
				});
			}
		});
	});
});

function delShop(id) {
	$.post("/web/shop/delShop",{},function(data){
		if (data == "ok") {
			layer.alert("操作成功", function() {
				layer.closeAll();
				load();
			});
		} else {
			layer.alert(data);
		}
	});
}
function updateShop(id) {
	if (id != null) {
		window.location.href = "/web/shop/updateShop/" + id + "?callback=" + getCallback();
	} else {
		layer.alert("请求参数有误，请您稍后再试");
	}
}
