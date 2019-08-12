/**
 * 角色列表
 */
$(function() {
	layui.use('table', function() {
		var table = layui.table;
		var tbody = $("#verifyShop_tbody");
		$.get("/web/shop/getShopList", {
			shop_status : 0
		}, function(data) {
			if (data != null) {
				tbody.empty();
				$.each(data, function(index, item) {
					var td = $("<tr><td>" + item.shopName + "</td>" + "<td>" + item.shopAddress + "</td>" + "<td>" + item.shopDescribe + "</td>" + "<td>" + DateUtils.formatDate(item.createTime) + "</td>" + "<td>" + "<button class='layui-btn layui-btn-xs' onclick=\"agreeShop('" + item.shopId + "')\">编辑</button>" + "<button class='layui-btn layui-btn-danger layui-btn-xs' onclick=\"delShop('" + item.shopId + "')\">删除</button></td></tr>");
					tbody.append(td);
				});
			}
		});
	});
});

function delShop(id) {
	$.post("/web/shop/delShop", {
		shop_id : id
	}, function(data) {
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
function agreeShop(id) {
	if (id) {
		$.post("/web/shop/agreeShop", {
			shop_id : id
		}, function(data) {
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

}
