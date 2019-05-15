/**
 * 权限列表
 */
$(function() {
	// 初始化treegrid 页面表格
	layui.config({
		base : '/treegrid/'
	}).use([ 'laytpl', 'treegrid' ], function() {
		var laytpl = layui.laytpl, treegrid = layui.treegrid;
		treegrid.config.render = function(viewid, data) {
			var view = document.getElementById(viewid).innerHTML;
			return laytpl(view).render(data) || '';
		};

		var treeForm = treegrid.createNew({
			elem : 'serverClassTable',
			view : 'view',
			data : {
				rows : serverClassList
			},
			id : "serverClassId",
			parentid : 'serverClassPid',
			singleSelect : false
		});
		treeForm.build();
	});
	// 操作
	layui.use('form', function() {
		var form = layui.form;
		// 监听提交
		form.on('submit(serverClassSubmit)', function(data) {
			// 校验 TODO
			$.ajax({
				type : "POST",
				data : $("#serverClassForm").serialize(),
				url : "/web/server/setServerClass",
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
			done : function(res) {
				// 上传完毕回调
				console.log(res);
				$("#serverClassImg").attr("src", res.context[0]);
				$("input[name='serverClassImg']").val(res.context[0]);
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
		$("#serverClassId").val(id);
		$.get("/web/server/getServerClass", {
			"server_class_id" : id
		}, function(data) {
			// console.log(data);
			if (null != data) {
				$("input[name='serverClassUrl']").val(data.serverClassUrl);
				$("#serverClassUrl").attr("src", data.serverClassUrl);
				$("input[name='toPage']").val(data.toPage);
				$("textarea[name='serverClassDesc']").val(data.serverClassDesc);
				$("#serverClassPid").val(data.serverClassPid);
				layer.open({
					type : 1,
					title : "更新服务类目",
					fixed : false,
					resize : false,
					shadeClose : true,
					area : [ '500px', '580px' ],
					content : $('#updateServerClass'),
					end : function() {
						location.reload();
					}
				});
			} else {
				layer.alert("获取服务类目数据出错，请您稍后再试");
			}
		});
	}
}
// 开通权限
function add(pid, flag) {
	if (null != pid) {
		if (flag == 0) {
			$("#type").val(1);
			$("#serverClassPid").val(0);
		} else {
			// 设置父id
			$("#type").val(1);
			$("#serverClassPid").val(pid);
		}
		layer.open({
			type : 1,
			title : "添加服务类目",
			fixed : false,
			resize : false,
			shadeClose : true,
			area : [ '500px', '580px' ],
			content : $('#updateServerClass'), // 页面自定义的div，样式自定义
			end : function() {
				location.reload();
			}
		});
	}
}

function del(id) {
	// console.log("===删除id："+id);
	if (null != id) {
		layer.confirm('您确定要删除该服务类目吗？', {
			btn : [ '确认', '返回' ]
		// 按钮
		}, function() {
			$.post("/web/server/delServerClass", {
				"server_class_id" : id
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