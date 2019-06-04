$(function() {
	var option = {
		title : {
			text : ''
		},
		tooltip : {},
		legend : {
			data : [ '数量' ]
		},
		xAxis : {
			data : []
		},
		yAxis : {},
		series : []
	};
	var myChart = echarts.init(document.getElementById('memberLine'));
	// 指定图表的配置项和数据
	myChart.setOption(option);
	$.get("/web/home/getMemberChenageDate", {
		"type" : "1"
	}, function(res) {
		// 基于准备好的dom，初始化echarts实例
		if ("200" == res.status) {
			var Xdata = [];
			var Ydata = [];
			for (var i = 0; i < res.context.length; i++) {
				var data = res.context[i];
				Xdata[i] = data.member_date;
				Ydata[i] = data.count_num;
			}

			var option = {
				title : {
					
				},
				tooltip : {},
				legend : {
					data : [ '数量' ]
				},
				xAxis : {
					data : Xdata
				},
				yAxis : {},
				series : [ {
					name : '数量',
					type : 'line',
					smooth : true,
					data : Ydata
				} ]
			};
			// 使用刚指定的配置项和数据显示图表。
			myChart.setOption(option);
		}
	});
	var orderChart = echarts.init(document.getElementById('orderLine'));
	// 指定图表的配置项和数据
	orderChart.setOption(option);
	$.get("/web/home/getOrderChangeDate", {
		"type" : "1"
	}, function(res) {
		// 基于准备好的dom，初始化echarts实例
		if ("200" == res.status) {
			var Xdata = [];
			var Ydata = [];
			for (var i = 0; i < res.context.length; i++) {
				var data = res.context[i];
				Xdata[i] = data.order_date;
				Ydata[i] = data.count_num;
			}

			var option = {
				title : {
					
				},
				tooltip : {},
				legend : {
					data : [ '数量' ]
				},
				xAxis : {
					data : Xdata
				},
				yAxis : {},
				series : [ {
					name : '数量',
					type : 'line',
					smooth : true,
					data : Ydata
				} ]
			};
			// 使用刚指定的配置项和数据显示图表。
			orderChart.setOption(option);
		}
	});
	var incomeChart = echarts.init(document.getElementById('incomeLine'));
	// 指定图表的配置项和数据
	incomeChart.setOption(option);
	$.get("/web/home/getIncomeChangeDate", {
		"type" : 1
	}, function(res) {
		// 基于准备好的dom，初始化echarts实例
		if ("200" == res.status) {
			var Xdata = [];
			var Ydata = [];
			for (var i = 0; i < res.context.length; i++) {
				var data = res.context[i];
				Xdata[i] = data.order_date;
				Ydata[i] = data.count_num;
			}
			var option = {
				title : {
					
				},
				tooltip : {},
				legend : {
					data : [ '数量' ]
				},
				xAxis : {
					data : Xdata
				},
				yAxis : {},
				series : [ {
					name : '数量',
					type : 'line',
					smooth : true,
					data : Ydata
				} ]
			};
			// 使用刚指定的配置项和数据显示图表。
			incomeChart.setOption(option);
		}
	});
});
function sreachMmeber(type) {
	var myChart = echarts.init(document.getElementById('memberLine'));
	// 指定图表的配置项和数据
	$.get("/web/home/getMemberChenageDate", {
		"type" : type
	}, function(res) {
		// 基于准备好的dom，初始化echarts实例
		if ("200" == res.status) {
			var Xdata = [];
			var Ydata = [];
			for (var i = 0; i < res.context.length; i++) {
				var data = res.context[i];
				Xdata[i] = data.member_date;
				Ydata[i] = data.count_num;
			}
			var option = {
				title : {
					text : '会员增长'
				},
				tooltip : {},
				legend : {
					data : [ '数量' ]
				},
				xAxis : {
					data : Xdata
				},
				yAxis : {},
				series : [ {
					name : '数量',
					type : 'line',
					smooth : true,
					data : Ydata
				} ]
			};
			// 使用刚指定的配置项和数据显示图表。
			myChart.setOption(option);
		}
	});
}
function sreachOrder(type) {
	var orderChart = echarts.init(document.getElementById('orderLine'));
	// 指定图表的配置项和数据
	$.get("/web/home/getOrderChangeDate", {
		"type" : type
	}, function(res) {
		// 基于准备好的dom，初始化echarts实例
		if ("200" == res.status) {
			var Xdata = [];
			var Ydata = [];
			for (var i = 0; i < res.context.length; i++) {
				var data = res.context[i];
				Xdata[i] = data.order_date;
				Ydata[i] = data.count_num;
			}
			var option = {
				title : {
					text : '订单'
				},
				tooltip : {},
				legend : {
					data : [ '数量' ]
				},
				xAxis : {
					data : Xdata
				},
				yAxis : {},
				series : [ {
					name : '数量',
					type : 'line',
					smooth : true,
					data : Ydata
				} ]
			};
			// 使用刚指定的配置项和数据显示图表。
			orderChart.setOption(option);
		}
	});
}
function sreachIncome(type){
	var orderChart = echarts.init(document.getElementById('incomeLine'));
	// 指定图表的配置项和数据
	$.get("/web/home/getIncomeChangeDate", {
		"type" : type
	}, function(res) {
		// 基于准备好的dom，初始化echarts实例
		if ("200" == res.status) {
			var Xdata = [];
			var Ydata = [];
			for (var i = 0; i < res.context.length; i++) {
				var data = res.context[i];
				Xdata[i] = data.order_date;
				Ydata[i] = data.count_num;
			}
			var option = {
				title : {
					text : '收益'
				},
				tooltip : {},
				legend : {
					data : [ '数量' ]
				},
				xAxis : {
					data : Xdata
				},
				yAxis : {},
				series : [ {
					name : '数量',
					type : 'line',
					smooth : true,
					data : Ydata
				} ]
			};
			// 使用刚指定的配置项和数据显示图表。
			orderChart.setOption(option);
		}
	});
}