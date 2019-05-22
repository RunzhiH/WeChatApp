/**
 * 角色管理
 */
$(function() {
	$("#refundOrderListLi").click(function() {
		$("#checkRefundOrderLi").removeClass("layui-this");
		$("#checkRefundOrderDiv").removeClass("layui-show");

		$("#refundOrderListLi").addClass("layui-this");
		$("#refundOrderListDiv").addClass("layui-show");

		$("#refundOrderDatilLi").css("display", "none");
		$("#refundOrderDatilDiv").css("display", "none");
	});
	$("#checkRefundOrderLi").click(function() {
		$("#refundOrderListLi").removeClass("layui-this");
		$("#refundOrderListDiv").removeClass("layui-show");

		$("#checkRefundOrderLi").addClass("layui-this");
		$("#checkRefundOrderDiv").addClass("layui-show");

		$("#refundOrderDatilLi").css("display", "none");
		$("#refundOrderDatilDiv").css("display", "none");
	});
	if (flag == "refundOrderDatil") {
		$("#refundOrderListLi").removeClass("layui-this");
		$("#checkRefundOrderLi").removeClass("layui-this");
		$("#refundOrderListDiv").removeClass("layui-show");
		$("#checkRefundOrderDiv").removeClass("layui-show");

		$("#refundOrderDatilLi").addClass("layui-this");
		$("#refundOrderDatilDiv").addClass("layui-show");
		$("#refundOrderDatilLi").css("display", "inline-block");
		$("#refundOrderDatilDiv").css("display", "inline-block");
	}
});

/**
 * 进入角色管理界面
 */
function load() {
	window.location.href = "/web/order/refundOrderManage";
}