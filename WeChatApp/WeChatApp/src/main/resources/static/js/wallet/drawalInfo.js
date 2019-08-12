$(function() {
	layui.use('laytpl', function() {
		var laytpl = layui.laytpl;
		// 第三步：渲染模版
		var data = this.data;
		var getTpl = document.getElementById('button').innerHTML, view = document.getElementById('view');
		laytpl(getTpl).render(data, function(html) {
			view.innerHTML = html;
		});
	});
})

var agree = function() {
	var id = $("#record_id").val();
	if (id) {
		$.post("/web/wallet/agreeWithDrawal", {
			drawal_id : id
		}, function(res) {
			if (res = "ok") {
				layer.alert("操作成功", function() {
					location.reload();
				});
			} else {
				layer.alert(res);
			}
		});
	}
}
var notAgree = function() {
	var html = "<div class=\"layui-form-item\">" + "<label class=\"layui-form-label\">拒绝原因:</label>" + "<div class=\"layui-input-block\">" + "  <textarea type=\"text\"  id=\"desc\"  placeholder=\"请输入拒绝原因\" class=\"layui-textarea\"> </textarea>" + "</div>" + "</div>"

	layer.open({
		title : '拒绝提现',
		type : 1,
		content : html,
		btn : [ '确定', '取消' ],
		yes : function() {
			var id = $("#record_id").val();
			if (id) {
				$.post("/web/wallet/notAgreeWithDrawal", {
					drawal_id : id,
					desc : $("desc").val()
				}, function(res) {
					if (res = "ok") {
						layer.alert("操作成功");
					} else {
						layer.alert(res);
					}
				});
			}
		},
		area : [ '300px', '200px' ],
		end : function() {
			location.reload();
		}
	});
}
