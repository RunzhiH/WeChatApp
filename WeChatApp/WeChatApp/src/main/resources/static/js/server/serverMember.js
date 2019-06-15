$(function() {
	layui.use('table', function() {
		var table = layui.table;
		var tbody = $("#tbody");
		$.get("/web/server/getAllServerMemberList", function(data) {
			if (data != null) {
				tbody.empty();
				$.each(data, function(index, item) {

					var order_takes_type = item.order_takes_type;
					if ("0" == order_takes_type) {
						order_takes_type = "不接单";
					} else if ("1" == order_takes_type) {
						order_takes_type = "手动接单";
					} else if ("2" == order_takes_type) {
						order_takes_type = "自动接单";
					}

					var in_prohi = item.in_prohi;
					if ("1" == in_prohi) {
						in_prohi = "禁单中";
					} else {
						in_prohi = "无";
					}

					var td = $("<tr><td>" + item.member_no + "</td>" + "<td>" + item.nickname + "</td>" + "<td><img src='" + item.photo + "'/></td>" + "<td>" + item.phone + "</td>" + "<td>" + order_takes_type + "</td>" + "<td>" + in_prohi + "</td>" + "<td>" + "<button class=\"layui-btn layui-btn-xs\" onclick=\"update('" + item.member_no + "',0)\">编辑</button>" + "<button class=\"layui-btn layui-btn-danger layui-btn-xs\" onclick=\"del(\'" + item.member_no + "\')\">删除</button></td></tr>");
					tbody.append(td);
				});
			}
		});
	});
	// 提交
	layui.use('form', function() {
		var form = layui.form;
		// 监听提交
		form.on('submit(serverMemberSubmit)', function(data) {
			// 校验 TODO
			$.ajax({
				type : "POST",
				data : $("#serverForm").serialize(),
				url : "/web/server/setServerMember",
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
		$.ajax({
			type : 'GET',
			url : "/api/server/getLeveL1ServerClass",
			success : function(data) {
				var div = $("#serverClassIdStr");
				if ("200" == data.status) {
					// select.empty();
					$.each(data.context, function(index, item) {
						var input = $("<input lay-skin=\"primary\" name=\"serverClassIdStr\" title=\"" + item.serverClassName + "\" type=\"checkbox\" value=\"" + item.serverClassId + "\">");
						div.append(input);
					});
				}
				form.render();
			}
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
			size :0,
			done : function(res) {
				// 上传完毕回调
				console.log(res);
				$("#photo").attr("src", res.context[0]);
				$("input[name='photo']").val(res.context[0]);
			},
			error : function() {
				// 请求异常回调
			}
		});
		var uploadInst2 = upload.render({
			elem : '#upload_normal' // 绑定元素
			,
			url : '/api/upload/uploadImages' // 上传接口
			,
			multiple : true,
			number : 3,
			size :0,
			done : function(res) {
				// 上传完毕回调
				var imageList = res.context;
				var imageList_str = $("input[name='photoDesc']").val();
				$("input[name='photoDesc']").parent().append("<img src='" + res.context[0] + "' style='width: 140px'>");
				imageList_str += res.context[0] + ",";
				$("input[name='photoDesc']").val(imageList_str);
			},
			error : function() {
				// 请求异常回调
			}
		});
	});

});

function update(member_no, type) {
	if (member_no) {
		$("#type").val(type);
		$("#member_no").val(member_no);
		$("#member_no").attr("readonly","readonly");
		$.get("/web/server/getServerMember", {
			"member_no" : member_no
		}, function(data) {
			// console.log(data);
			if (null != data) {
				layui.use('form', function() {
					var form = layui.form;
					$("input[name='phone']").val(data.phone);
					$("input[name='nickname']").val(data.nickname);
					$("#photo").attr("src", data.photo);
					$("input[name='photo']").val(data.photo);
					$("input[name='lat']").val(data.lat);
					$("input[name='lon']").val(data.lon);
					$("input[name='shortDesc']").val(data.shortDesc);
					var serverClassIdStr = data.serverClassIdStr;
					var serverClassId_arr = serverClassIdStr.split(",");
					var serverClassId_cb = $("input[name=serverClassIdStr]");
					for (var i = 0; i < serverClassId_cb.length; i++) {
						for (var j = 0; j < serverClassId_arr.length; j++) {
							if ($(serverClassId_cb[i]).val() == serverClassId_arr[i]) {
								$(serverClassId_cb[i]).attr("checked", "true");
							}
						}
					}
					$(":radio[name='orderTakesType'][value='" + data.orderTakesType + "']").prop("checked", "checked");
					$("textarea[name='serverMemberDesc']").val(data.serverMemberDesc);
					$("input[name='serverAfterPhoto']").val(data.serverAfterPhoto);
					if (data.photoDesc) {
						var image_list = data.photoDesc.split(",");
						for (var i = 0; i < image_list.length; i++) {
							if (image_list[i] != "") {
								$("input[name='photoDesc']").parent().append("<img src='" + image_list[i] + "' style='width: 140px'>");

							}
						}
					}
					if(data.lat){
				        var marker = new AMap.Marker({
				        	 position: [data.lon,data.lat],//位置
				        })
				        map.add(marker);//添加到地图
				        map.setCenter([data.lon,data.lat]);//设为地图中心
					}
					form.render();
				})
				layer.open({
					type : 1,
					title : "更新轮播图",
					fixed : false,
					resize : false,
					shadeClose : true,
					area : [ '800px', '580px' ],
					content : $('#updateServerMember'),
					end : function() {
						location.reload();
					}
				});
			} else {
				layer.alert("获取技术人员出错，请您稍后再试");
			}
		});
	}
}

function addServerMember() {
	$("#type").val(1);
	layer.open({
		type : 1,
		title : "添加技术人员",
		fixed : false,
		resize : false,
		shadeClose : true,
		area : [ '800px', '580px' ],
		content : $('#updateServerMember'), // 页面自定义的div，样式自定义
		end : function() {
			location.reload();
		}
	});
}

function del(id) {
	// console.log("===删除id："+id);
	if (null != id) {
		layer.confirm('您确定要删除该技术人员吗？', {
			btn : [ '确认', '返回' ]
		// 按钮
		}, function() {
			$.post("/web/server/delServerMember", {
				"member_no" : member_no
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
