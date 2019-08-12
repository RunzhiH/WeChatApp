$(function() {
	layui.use('table', function() {
		var table = layui.table, form = layui.form;

		tableIns = table.render({
			elem : '#uesrList',
			url : '/web/member/getOperatorMemberList',
			method : 'post', // 默认：get请求
			cellMinWidth : 80,
			page : true,
			request : {
				pageName : 'page', // 页码的参数名称，默认：page
				limitName : 'limit' // 每页数据量的参数名，默认：limit
			},
			response : {
				statusName : 'code', // 数据状态的字段名称，默认：code
				statusCode : 200, // 成功的状态码，默认：0
				countName : 'totals', // 数据总数的字段名称，默认：count
				dataName : 'list' // 数据列表的字段名称，默认：data
			},
			cols : [ [ {
				type : 'numbers'
			}, {
				field : 'member_no',
				title : 'ID',
				unresize : true,
				sort : true
			}, {
				field : 'nickname',
				title : '用户名'
			}, {
				field : 'phone',
				title : '手机号'
			}, {
				field : 'operator_type',
				title : '身份',
				align : 'center',
				templet : function(d) {
					var type_str = ""
					if (d.operator_type == 0) {
						type_str = "普通";
					} else if (d.operator_type == 0) {
						type_str = "小队长";
					} else if (d.operator_type == 0) {
						type_str = "经理";
					} else {
						type_str = "未知";
					}
					return type_str;
				}
			}, {
				field : 'is_share',
				title : '是否分润',
				align : 'center',
				templet : '#switchTpl'
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
				// console.log(res);
				// 得到当前页码
				// console.log(curr);
				// 得到数据总量
				// console.log(count);
				pageCurr = curr;
			}
		});
		// 监听是否分润操作
		form.on('switch(is_share_filter)', function(obj) {
			layer.tips(this.value + ' ' + this.name + '：' + obj.elem.checked, obj.othis);
			updateIsShare(obj, this.value, this.name, obj.elem.checked);

		});
		// //监听在职操作
		// form.on('switch(isJobTpl)', function(obj) {
		// console.log(this.value + ' ' + this.name + '：' + obj.elem.checked,
		// obj.othis);
		// var data = obj.data;
		// setJobUser(obj, this.value, this.name, obj.elem.checked);
		// });
		// //监听工具条
		table.on('tool(userTable)', function(obj) {
			var data = obj.data;
			// if(obj.event === 'del'){
			// delUser(data,data.id,data.username);
			// } else if(obj.event === 'edit'){
			// //编辑
			// getUserAndRoles(data,data.id);
			// } else if(obj.event === 'recover'){
			// //恢复
			// recoverUser(data,data.id);
			if (obj.event === 'update') {
				update(data.memberNo);
			}
		});
		// //监听提交
		// form.on('submit(userSubmit)', function(data){
		// // TODO 校验
		// formSubmit(data);
		// return false;
		// });

	});
	// 搜索框
	layui.use([ 'form', 'laydate' ], function() {
		var form = layui.form, layer = layui.layer, laydate = layui.laydate;
		// 日期
		// TODO 数据校验
		// 监听搜索框
		form.on('submit(searchSubmit)', function(data) {
			// 重新加载table
			load(data);
			return false;
		});
	});
})
function updateIsShare(obj, member_no, name, checked) {
	var is_share = checked ? 1 : 0;
	var user_is_share = checked ? "是" : "否"; // 是否分润
	layer.confirm('您确定要把用户：' + member_no + '设置为' + user_is_share + '吗？', {
		btn : [ '确认', '返回' ]
	}, // 按钮
	function() {
		$.post("/web/member/updateIsShare", {
			"member_no" : member_no,
			"is_share" : is_share
		}, function(data) {
			if (data == "ok") { // 回调弹框
				layer.alert("操作成功！", function() {
					layer.closeAll(); // 加载load方法
					load(obj);
				});
			} else {
				layer.alert(data, function() {
					layer.closeAll(); // 加载load方法
					load(obj);// 自定义
				});
			}
		});
	}, function() {
		layer.closeAll(); // 加载load方法
		load(obj);
	});
} // 提交表单
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