$(function() {
	layui.use('table', function() {
		var table = layui.table, form = layui.form;

		tableIns = table.render({
			elem : '#checkRefundOrderList',
			url : '/web/order/getCheckRefundOrderList',
			method : 'POST', // 默认：get请求
			cellMinWidth : 80,
			page : true,
			request : {
				pageName : 'page', // 页码的参数名称，默认：page
				limitName : 'limit' // 每页数据量的参数名，默认：limit
			},
			response : {
				statusName : 'code',// 数据状态的字段名称，默认：code
				statusCode : 200,// 成功的状态码，默认：0
				countName : 'totals', // 数据总数的字段名称，默认：count
				dataName : 'list',// 数据列表的字段名称，默认：data
			},
			cols : [ [ {
				field : 'order_code',

				title : '订单编号',
			}, {
				field : 'server_name',

				title : '服务项目'
			}, {
				field : 'order_nickname',

				title : '下单人',
			}, {
				field : 'server_member',

				title : '技术人员'
			}, {
				field : 'server_address',
				title : '服务地点'

			}, {
				field : 'refund_order_desc',
				title : '申请说明',
			}, {
				field : 'pay_price',
				title : '实际支付价格',
			}, {
				field : 'refund_order_status',
				title : '售后状态',
			}, {
				field : 'create_time_str',
				title : '申请时间',
				align : 'center'
			}, {
				fixed : 'right',
				title : '操作',
				width : 140,
				align : 'center',
				toolbar : '#CoptBar'
			} ] ],

			done : function(res, curr, count) {
				// 如果是异步请求数据方式，res即为你接口返回的信息。
				// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
				pageCurr = curr;
			}
		});
		// 监听工具条
		table.on('tool(refundOrderCheckTable)', function(obj) {
			var data = obj.data;
			if (obj.event === 'update') {
				openUpdateOrder(data.order_id);
			} else if (obj.event === 'close') {
				closeOrder(data.order_id);
			}
		});
	});
	// 搜索框
	layui.use([ 'form', 'laydate' ], function() {
		var form = layui.form, layer = layui.layer, laydate = layui.laydate;
		// 日期
		laydate.render({
			elem : '#SstartTime'
		});
		laydate.render({
			elem : '#SendTime'
		});
		// TODO 数据校验
		// 监听搜索框
		form.on('submit(searchCheckSubmit)', function(obj) {
			// 重新加载table
			tableIns.reload('checkRefundOrderList',{
				where : obj.field,
				page : {
					curr : pageCurr
				// 从当前页码开始
				}
			});
			return false;
		});

		form.on('submit(orderSubmit)', function() {
			$.ajax({
				type : "POST",
				data : $("#orderForm").serialize(),
				url : "/web/order/updateServerMember",
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
		})
	});

});
// 订单详情
function getOrderDatil(id) {
	if (id != null) {
		window.location.href = "/web/order/orderDatil/" + id + "?callback=" + getCallback();
	} else {
		layer.alert("请求参数有误，请您稍后再试");
	}
}
function load(obj) {
	// 重新加载table
	tableIns.reload({
		where : obj.field,
		page : {
			curr : pageCurr
		// 从当前页码开始
		}
	});
}

// 修改订单
function openUpdateOrder(id) {
	layui.use([ 'form' ], function() {
		var form = layui.form, layer = layui.layer;

		$.ajax({
			type : 'GET',
			url : "/web/order/getOrder",
			data : {
				"order_id" : id
			},
			success : function(data) {
				if (data != null) {
					$("#orderId").val(data.orderId);
					$("#UorderCode").val(data.orderCode);
					$("#UpayPrice").val(data.orderPrice);
					//$("#serverMember").val(data.serverMember);
					$.ajax({
						type : 'GET',
						url : "/web/server/getServerMember",
						data : {
							"order_id" : id
						},
						success : function(data) {
							var select = $("select[name=serverMemberNo]");
								// select.empty();
								$.each(data, function(index, item) {
									var option = $("<option value='" + item.member_no + "'\" >" + item.server_name_phone + "</option>");
									select.append(option);
								});
							form.render('select');
						}
					});
				} else {
					// 弹出错误提示
					layer.alert("获取订单错误", function() {
						layer.closeAll();
					});
				}
			}
		});
		top.layer.open({
			title : "订单修改",
			fixed : false,
			resize : false,
			shadeClose : true,
			area : [ '550px' ],
			content : $('#updateOrder'),
			end : function() {
				location.reload();
			}
		});
	});
}
function closeOrder(id) {
	if (id) {
		layer.alert('是否关闭订单', {
			btn : [ '确定', '取消' ],
			yes : function(index, layero) {
				$.post("/web/order/closeOrderById", {
					"order_id" : id
				}, function(data) {
					if(data=='ok'){
						layer.alert('操作成功',function(){
							location.reload();
						});
					}else{
						layer.alert(data);
					}
				});
			}
		});
	}
}