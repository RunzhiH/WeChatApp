/**
 * 角色列表
 */
$(function() {
	layui.use('table', function() {
		var table = layui.table, form = layui.form;

		tableIns = table.render({
			elem : '#orderList',
			url : '/web/order/getOrderList',
			method : 'POST' // 默认：get请求
			,
			cellMinWidth : 80,
			page : true,
			request : {
				pageName : 'page' // 页码的参数名称，默认：page
				,
				limitName : 'limit' // 每页数据量的参数名，默认：limit
			},
			response : {
				statusName : 'code' // 数据状态的字段名称，默认：code
				,
				statusCode : 200 // 成功的状态码，默认：0
				,
				countName : 'totals' // 数据总数的字段名称，默认：count
				,
				dataName : 'list' // 数据列表的字段名称，默认：data
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
				field : 'order_price',
				title : '项目参考价',
			}, {
				field : 'pay_price',
				title : '实际支付价格',
			}, {
				field : 'order_status_tr',
				title : '订单状态',
			}, {
				field : 'create_time_str',
				title : '下单时间',
				align : 'center'
			}, {
				fixed : 'right',
				title : '操作',
				width : 140,
				align : 'center',
				toolbar : '#optBar'
			} ] ],

			done : function(res, curr, count) {
				// 如果是异步请求数据方式，res即为你接口返回的信息。
				// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
				pageCurr = curr;
			}
		});
		// 监听工具条
		table.on('tool(orderTable)', function(obj) {
			var data = obj.data;
			if (obj.event === 'datil') {
				getOrderDatil(data.order_id);
			}
		});
	});
	// 搜索框
	layui.use([ 'form', 'laydate' ], function() {
		var form = layui.form, layer = layui.layer, laydate = layui.laydate;
		// 日期
		laydate.render({
			elem : '#startTime'
		});
		laydate.render({
			elem : '#endTime'
		});
		// TODO 数据校验
		// 监听搜索框
		form.on('submit(searchOrderSubmit)', function(obj) {
			// 重新加载table
			tableIns.reload('orderList', {
				where : obj.field,
				page : {
					curr : pageCurr
				// 从当前页码开始
				}
			});
			return false;
		});
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