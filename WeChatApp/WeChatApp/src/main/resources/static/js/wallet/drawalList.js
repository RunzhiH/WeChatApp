$(function() {
	layui.use('table', function() {
		var table = layui.table;
		var tbody = $("#drawalList_tbody");
		$.get("/web/wallet/getWithDrawalList", function(data) {
			if (data != null) {
				tbody.empty();
				$.each(data, function(index, item) {
					var status = "未知状态";
					if (item.withdrawal_status == "0") {
						status = "申请中";
					} else if (item.withdrawal_status == "1") {
						status = "已打款";
					} else if (item.withdrawal_status == "2") {
						status = "已拒绝";
					}
					var td = $("<tr><td>" + item.withdrawal_price + "</td>" + "<td>" + item.create_time_str + "</td>" + "<td>" + status + "</td>" + "<td>" + item.nickname + "</td>" + "<td>" + "<button class='layui-btn layui-btn-xs' onclick=\"show('" + item.withdrawal_record_id + "')\">编辑</button>" + "</td></tr>");
					tbody.append(td);
				});
			}
		});
	});
})
function show(id) {
	if (id != null) {
		window.location.href = "/web/wallet/getWithDrawalInfo/" + id + "?callback=" + getCallback();
	} else {
		layer.alert("请求参数有误，请您稍后再试");
	}
}