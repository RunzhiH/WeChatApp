$(document).ready(function() {
	getDataList();
});
function getDataList() {
	layui.use('form', function() {
		var form = layui.form;
		// 请求
		$.ajax({
			type : 'GET',
			url : "/api/server/getLeveL1ServerClass",
			success : function(data) {
				var select = $("select[name=serverClassId1]");
				if ("200" == data.status) {
					// select.empty();
					$.each(data.context, function(index, item) {
						var option = $("<option value='" + item.serverClassId + "' onclick=\"getNextLeveLList('" + item.serverClassId + "'," + item.serverClassLevel + ")\" >" + item.serverClassName + "</option>");
						select.append(option);
					});
				}
				form.render('select');
			}
		});
	});
}

/**
 * 权限列表
 */
$(function() {
	layui.use('table', function() {
		var table = layui.table;
		var tbody = $("#tbody");
		$.get("/web/exper/getAllExperList", function(data) {
			if (data != null) {
				tbody.empty();
				$.each(data, function(index, item) {
					var td = $("<tr><td>" + item.title + "</td>" + "<td><img src='" + item.experience_photo + "'/></td>" + "<td>" + item.experience_desc + "</td>" + "<td>" + item.create_time_str + "</td>" + "<td>" + item.nickname + "</td>" + "<td>" + "<button class=\"layui-btn layui-btn-xs\" onclick=\"update('" + item.experience_id + "',0)\">编辑</button>" + "<button class=\"layui-btn layui-btn-danger layui-btn-xs\" onclick=\"del(\'" + item.experience_id + "\')\">删除</button></td></tr>");
					tbody.append(td);
				});
			}
		});
	});
	// 操作
	layui.use('form', function() {
		var form = layui.form;
		// 监听提交
		form.on('submit(experienceSubmit)', function(data) {
			// 校验 TODO
			$.ajax({
				type : "POST",
				data : $("#experForm").serialize(),
				url : "/web/exper/setExper",
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
		form.on('select(serverClassId1)', function(data) {
			var id = (data.value);
			getNextLeveLList(id, 1);

			getShopServerList(id);
		});
		form.on('select(serverClassId2)', function(data) {
			var id = (data.value);
			getNextLeveLList(id, 2)
		});
		form.on('select(serverClassId3)', function(data) {
			var id = (data.value);
			getNextLeveLList(id, 3)
		});
		form.on('select(serverClassId4)', function(data) {
			var id = (data.value);
			var lev = 1
			getNextLeveLList(id, 4)
		});
		form.on('select(serverClassId5)', function(data) {
			var id = (data.value);
			getNextLeveLList(id, 5)
		});
		form.render();
	});

	layui.use('upload', function() {
		var upload = layui.upload;

		// 执行实例
		var uploadInst = upload.render({
			elem : '#test-upload-normal', // 绑定元素
			url : '/api/upload/uploadImages',// 上传接口
			done : function(res) {
				// 上传完毕回调
				console.log(res);
				$("#experiencePhoto").attr("src", res.context[0]);
				$("input[name='experiencePhoto']").val(res.context[0]);
			},
			error : function() {
				// 请求异常回调
			}
		});

		// 执行实例
		var uploadInst2 = upload.render({
			elem : '#server_before_photo', // 绑定元素
			url : '/api/upload/uploadImages',// 上传接口
			done : function(res) {
				// 上传完毕回调
				console.log(res);
				$("#serverBeforePhoto").attr("src", res.context[0]);
				$("input[name='serverBeforePhoto']").val(res.context[0]);
			},
			error : function() {
				// 请求异常回调
			}
		});

		// 执行实例
		var uploadInst3 = upload.render({
			elem : '#server_after_photo', // 绑定元素
			url : '/api/upload/uploadImages',// 上传接口
			multiple : true,
			number : 3,
			done : function(res) {
				// 上传完毕回调
				console.log(res);
				var imageList = res.context;
				var imageList_str = $("input[name='serverAfterPhoto']").val();
				$("input[name='serverAfterPhoto']").parent().append("<img src='" + res.context[0] + "' style='width: 140px'>");
				imageList_str += res.context[0] + ",";
				$("input[name='serverAfterPhoto']").val(imageList_str);
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
		$("#experienceId").val(id);
		$.get("/web/exper/getExper", {
			"epxer_id" : id
		}, function(data) {
			// console.log(data);
			if (null != data) {
				$("input[name='title']").val(data.title);
				$("input[name='experiencePhoto']").val(data.experiencePhoto);
				$("#experiencePhoto").attr("src", data.experiencePhoto);
				$("input[name='serverBeforePhoto']").val(data.serverBeforePhoto);
				$("#serverBeforePhoto").attr("src", data.serverBeforePhoto);
				$("input[name='serverAfterPhoto']").val(data.serverAfterPhoto);
				if (data.serverAfterPhoto) {
					var image_list = data.serverAfterPhoto.split(",");
					for (var i = 0; i < image_list.length; i++) {
						if (image_list[i] != "") {
							$("input[name='serverAfterPhoto']").parent().append("<img src='" + image_list[i] + "' style='width: 140px'>");

						}
					}
				}

				$("textarea[name='experienceDesc']").val(data.experienceDesc);
				layer.open({
					type : 1,
					title : "更新轮播图",
					fixed : false,
					resize : false,
					shadeClose : true,
					area : [ '500px', '580px' ],
					content : $('#updateExper'),
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
function deleteImages() {
	var img_list = $("input[name='serverAfterPhoto']").parent().children("img");
	img_list.remove();
	$("input[name='serverAfterPhoto']").val("");
}

// 开通权限
function addExper() {
	$("#type").val(1);
	layer.open({
		type : 1,
		title : "添加轮播图",
		fixed : false,
		resize : false,
		shadeClose : true,
		area : [ '500px', '580px' ],
		content : $('#updateExper'), // 页面自定义的div，样式自定义
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
			$.post("/web/exper/delExper", {
				"exper_id" : id
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
function getNextLeveLList(id, lev) {
	layui.use('form', function() {
		var form = layui.form;
		if (id) {
			// 请求
			$.ajax({
				type : 'GET',
				url : "/api/server/getNextLevelServerClassList",
				data : {
					server_class_id : id
				},
				success : function(data) {
					if ("200" == data.status) {
						var select = $("select[name=serverClassId" + (lev + 1) + "]");
						select.empty();
						select.append("<option value=\"\" ></option>");
						$.each(data.context, function(index, item) {
							var option = $("<option value='" + item.serverClassId + "' onclick=\"getNextLeveLList('" + item.serverClassId + "','" + item.serverClassLevel + "')\" >" + item.serverClassName + "</option>");
							select.append(option);
						});
						form.render('select');
					}
				}
			});
		} else {
			var select = $("select[name=serverClassId" + (lev + 1) + "]");
			select.empty();
			select.append("<option value=\"\" ></option>");
			form.render('select');
		}
	});
}
function getShopServerList(id) {
	layui.use('form', function() {
		var form = layui.form;
		if (id) {
			// 请求
			$.ajax({
				type : 'GET',
				url : "/api/server/getServerListByClassId",
				data : {
					server_class_id : id
				},
				success : function(data) {
					if ("200" == data.status) {
						var select = $("select[name='shopServerId']");
						select.empty();
						select.append("<option value=\"\" ></option>");
						$.each(data.context, function(index, item) {
							var option = $("<option value='" + item.shop_server_id + "' >" + item.server_name + "</option>");
							select.append(option);
						});
						form.render('select');
					}
				}
			});
		}
	});
}