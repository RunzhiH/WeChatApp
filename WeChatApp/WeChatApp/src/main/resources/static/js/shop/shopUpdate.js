$(function() {
	layui.use([ 'form', 'layer' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		// 监听提交
		form.on('submit(updateShopSumbit)', function(data) {
			$.ajax({
				type : "POST",
				data : $("#updateShopForm").serialize(),
				url : "/web/shop/setShop",
				success : function(data) {
					if (data == "ok") {
						layer.alert("操作成功", function() {
							layer.closeAll();
							load();
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
			url : "/api/shopClass/getShopClassList",
			success : function(data) {
				var div = $("#shopClassId");
				if ("200" == data.status) {
					// select.empty();
					$.each(data.context, function(index, item) {
						var input = $("<input lay-skin=\"primary\" name=\"shopClassId\" title=\"" + item.shop_class_name + "\" type=\"checkbox\" value=\"" + item.shop_class_id + "\">");
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
			elem : '#uploadShopLogo', // 绑定元素
			url : '/api/upload/uploadImages',// 上传接口
			size : 0,
			done : function(res) {
				// 上传完毕回调
				console.log(res);
				$("#shopLogoUrl").attr("src", res.context[0]);
				$("input[name='shopLogoUrl']").val(res.context[0]);
			},
			error : function() {
				// 请求异常回调
			}
		});
	});
})