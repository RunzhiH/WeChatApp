$(function() {
	$("#drawalListLi").click(function() {
		$("#verifyDrawalLi").removeClass("layui-this");
		$("#verifyDrawalDiv").removeClass("layui-show");

		$("#drawalListLi").addClass("layui-this");
		$("#drawalListDiv").addClass("layui-show");

		$("#drawalInfoLi").css("display", "none");
		$("#drawalInfoDiv").css("display", "none");
	});
	$("#verifyDrawalLi").click(function() {
		$("#drawalListLi").removeClass("layui-this");
		$("#drawalListDiv").removeClass("layui-show");

		$("#verifyDrawalLi").addClass("layui-this");
		$("#verifyDrawalDiv").addClass("layui-show");

		$("#drawalInfoLi").css("display", "none");
		$("#drawalInfoDiv").css("display", "none");
	});
	if (flag == "drawalInfo") {
		$("#drawalListLi").removeClass("layui-this");
		$("#verifyDrawalLi").removeClass("layui-this");
		$("#drawalListDiv").removeClass("layui-show");
		$("#verifyDrawalDiv").removeClass("layui-show");

		$("#drawalInfoLi").addClass("layui-this");
		$("#drawalInfoDiv").addClass("layui-show");
		$("#drawalInfoLi").css("display", "inline-block");
		$("#drawalInfoDiv").css("display", "inline-block");
	}
});

function load() {
	window.location.href = "/web/wallet/drawalManage";
}
