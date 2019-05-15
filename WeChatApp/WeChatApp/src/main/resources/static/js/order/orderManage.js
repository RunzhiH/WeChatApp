/**
 * 角色管理
 */
$(function() {
	$("#orderListLi").click(function() {
		$("#checkOrderLi").removeClass("layui-this");
		$("#checkOrderDiv").removeClass("layui-show");

		$("#orderListLi").addClass("layui-this");
		$("#orderListDiv").addClass("layui-show");

		$("#orderDatilLi").css("display", "none");
		$("#orderDatilDiv").css("display", "none");
	});
	$("#checkOrderLi").click(function() {
		$("#orderListLi").removeClass("layui-this");
		$("#orderListDiv").removeClass("layui-show");

		$("#checkOrderLi").addClass("layui-this");
		$("#checkOrderDiv").addClass("layui-show");

		$("#orderDatilLi").css("display", "none");
		$("#orderDatilDiv").css("display", "none");
	});
	if (flag == "orderDatil") {
		$("#orderListLi").removeClass("layui-this");
		$("#checkOrderLi").removeClass("layui-this");
		$("#orderListDiv").removeClass("layui-show");
		$("#checkOrderDiv").removeClass("layui-show");

		$("#orderDatilLi").addClass("layui-this");
		$("#orderDatilDiv").addClass("layui-show");
		$("#orderDatilLi").css("display", "inline-block");
		$("#orderDatilDiv").css("display", "inline-block");
	}
});

/**
 * 进入角色管理界面
 */
function load() {
	window.location.href = "/web/order/orderManage";
}