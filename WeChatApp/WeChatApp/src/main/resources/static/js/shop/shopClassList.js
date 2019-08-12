/**
 * 权限列表
 */
$(function() {
	layui.use('table', function() {
		var table = layui.table;
		var tbody = $("#tbody");
		$.get("/web/shop/getShopClassList", function(data) {
			if (data != null) {
				tbody.empty();
				$.each(data, function(index, item) {
					var td = $("<tr><td>" + item.shopClassName + "</td>" 
							+ "<td>" + item.shopClassDesc + "</td>" 
							+ "<td><img src='" + item.shopClassPhoto + "'/></td>" 
							+ "<td>" 
							+ "<button class=\"layui-btn layui-btn-xs\" onclick=\"update('" + item.shopClassId + "',0)\">编辑</button>" 
							+ "<button class=\"layui-btn layui-btn-danger layui-btn-xs\" onclick=\"del(\'" + item.shopClassId + "\')\">删除</button></td></tr>");
					tbody.append(td);
				});
			}
		});
	});
	// 操作
	layui.use('form', function() {
		var form = layui.form;
		// 监听提交
		form.on('submit(shopClassSubmit)', function(data) {
			// 校验 TODO
			$.ajax({
				type : "POST",
				data : $("#shopClassForm").serialize(),
				url : "/web/shop/setShopClass",
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
				$("#shopClassPhoto").attr("src", res.context[0]);
				$("input[name='shopClassPhoto']").val(res.context[0]);
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
		$("#shopClassId").val(id);
		$.get("/web/shop/getShopClass", {
			"shop_server_id" : id
		}, function(data) {
			// console.log(data);
			if (null != data) {
				$("input[name='shopClassName']").val(data.shopClassName);
				$("input[name='shopClassPhoto']").val(data.shopClassPhoto);
				$("input[name='ord']").val(data.ord);
				$("#shopClassPhoto").attr("src", data.shopClassPhoto);
				$("textarea[name='shopClassDesc']").val(data.shopClassDesc);
				layer.open({
					type : 1,
					title : "更新门店分类",
					fixed : false,
					resize : false,
					shadeClose : true,
					area : [ '500px', '580px' ],
					content : $('#updateShopClass'),
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
function addShopClass(pid, type) {
	$("#type").val(type);
	layer.open({
		type : 1,
		title : "添加项目",
		fixed : false,
		resize : false,
		shadeClose : true,
		area : [ '500px', '580px' ],
		content : $('#updateShopClass'), // 页面自定义的div，样式自定义
		end : function() {
			location.reload();
		}
	});
}

function del(id) {
	// console.log("===删除id："+id);
	if (null != id) {
		layer.confirm('您确定要删除门店分类吗？', {
			btn : [ '确认', '返回' ]
		// 按钮
		}, function() {
			$.post("/web/shop/delShopClass", {
				"shop_class_id" : id
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