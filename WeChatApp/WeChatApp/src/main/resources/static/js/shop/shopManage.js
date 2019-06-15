$(function() {
	$("#shopListLi").click(function() {
		$("#verifyShopLi").removeClass("layui-this");
		$("#verifyShopDiv").removeClass("layui-show");

		$("#shopListLi").addClass("layui-this");
		$("#shopListDiv").addClass("layui-show");

		$("#updateShopLi").css("display", "none");
		$("#updateShopDiv").css("display", "none");
	});
	$("#verifyShopLi").click(function() {
		$("#shopListLi").removeClass("layui-this");
		$("#shopListDiv").removeClass("layui-show");

		$("#verifyShopLi").addClass("layui-this");
		$("#verifyShopDiv").addClass("layui-show");

		$("#updateShopLi").css("display", "none");
		$("#updateShopDiv").css("display", "none");
	});
	if (flag == "updateShop") {
		$("#shopListLi").removeClass("layui-this");
		$("#verifyShopLi").removeClass("layui-this");
		$("#shopListDiv").removeClass("layui-show");
		$("#verifyShopDiv").removeClass("layui-show");

		$("#updateShopLi").addClass("layui-this");
		$("#updateShopDiv").addClass("layui-show");
		$("#updateShopLi").css("display", "inline-block");
		$("#updateShopDiv").css("display", "inline-block");
	}
});


function load() {
	window.location.href = "/web/shop/shopManage";
}