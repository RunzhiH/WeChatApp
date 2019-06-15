/**
 * 权限列表
 */
$(function() {
	layui.use('table', function() {
		var table = layui.table;
		var tbody = $("#tbody");
		$.get("/web/rotation/getRotationList", function(data) {
			if (data != null) {
				tbody.empty();
				$.each(data, function(index, item) {
					var td = $("<tr><td><img src='" + item.rotationUrl + "'/></td>" + "<td>" + item.toPage + "</td>" + "<td>" + item.rotationDesc + "</td>" + "<td>" + DateUtils.formatDate(item.createTime) + "</td>" + "<td>" + "<button class=\"layui-btn layui-btn-xs\" onclick=\"update('" + item.rotationId + "',0)\">编辑</button>" + "<button class=\"layui-btn layui-btn-danger layui-btn-xs\" onclick=\"del(\'" + item.rotationId + "\')\">删除</button></td></tr>");
					tbody.append(td);
				});
			}
		});
	});
	// 操作
	layui.use('form', function() {
		var form = layui.form;
		// 监听提交
		form.on('submit(rotationSubmit)', function(data) {
			// 校验 TODO
			$.ajax({
				type : "POST",
				data : $("#rotationForm").serialize(),
				url : "/web/rotation/setRotation",
				success : function(data) {
					if (data == "ok") {
						layer.alert("操作成功", function() {
							layer.closeAll();
						});
					} else {
						layer.alert(data);
					}
				},
				error : function(data) {
					layer.alert("操作请求错误，请您稍后再试");
				}
			});
			return false;
		});
		form.render();
	});

	layui.use('upload', function() {
		var upload = layui.upload;

		// 执行实例
		var uploadInst = upload.render({
			elem : '#test-upload-normal' // 绑定元素
			,
			url : '/api/upload/uploadImages' // 上传接口
			,
			size : 0,
			done : function(res) {
				// 上传完毕回调
				console.log(res);
				$("#rotationUrl").attr("src", res.context[0]);
				$("input[name='rotationUrl']").val(res.context[0]);
			},
			error : function() {
				// 请求异常回调
			}
		});
	});

});

function update(id, type) {
	if (null != id) {
		$("#type").val(type);
		$("#rotationId").val(id);
		$.get("/web/rotation/getRotation", {
			"rotation_id" : id
		}, function(data) {
			// console.log(data);
			if (null != data) {
				$("input[name='rotationName']").val(data.rotationName);
				$("input[name='rotationUrl']").val(data.rotationUrl);
				$("#rotationUrl").attr("src", data.rotationUrl);
				$("input[name='toPage']").val(data.rotationLeveltoPage);
				$("textarea[name='rotationDesc']").val(data.rotationDesc);
				layer.open({
					type : 1,
					title : "更新轮播图",
					fixed : false,
					resize : false,
					shadeClose : true,
					area : [ '500px', '580px' ],
					content : $('#updateRotation'),
					end : function() {
						location.reload();
					}
				});
			} else {
				layer.alert("获取轮播图数据出错，请您稍后再试");
			}
		});
	}
}
// 开通权限
function addRotation(pid, type) {
	$("#type").val(type);
	$("#rotationPid").val(pid)
	layer.open({
		type : 1,
		title : "添加轮播图",
		fixed : false,
		resize : false,
		shadeClose : true,
		area : [ '500px', '580px' ],
		content : $('#updateRotation'), // 页面自定义的div，样式自定义
		end : function() {
			location.reload();
		}
	});
}

function del(id) {
	// console.log("===删除id："+id);
	if (null != id) {
		layer.confirm('您确定要删除该服务类目吗？', {
			btn : [ '确认', '返回' ]
		// 按钮
		}, function() {
			$.post("/web/rotation/del", {
				"rotation_id" : id
			}, function(data) {
				if (data == "ok") {
					// 回调弹框
					layer.alert("删除成功！", function() {
						layer.closeAll();
						// 加载load方法
						location.reload();
						;// 自定义
					});
				} else {
					layer.alert(data);// 弹出错误提示
				}
			});
		}, function() {
			layer.closeAll();
		});
	}

}

// 关闭弹框
function close() {
	layer.closeAll();
}