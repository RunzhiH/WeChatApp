/**
 * 角色列表
 */
$(function() {
    layui.use('table', function(){
        var table = layui.table;
        var tbody=$("#tbody");
        $.get("/web/shop/getShopList",function(data){
            if(data!=null){
                tbody.empty();
                $.each(data, function (index, item) {
                    var td=$("<tr><td>"+item.shopName+"</td>"
                    +"<td>"+item.shopAddress+"</td>"
                    +"<td>"+item.shopDescribe+"</td>"
                    +"<td>"+DateUtils.formatDate(item.createTime)+"</td>"
                    +"<td>"
                    +"<button class='layui-btn layui-btn-xs' onclick='updateRole("+item.shopId+")'>编辑</button>"
                        +"<button class='layui-btn layui-btn-danger layui-btn-xs' onclick='delRole("+item.shopId+")'>删除</button></td></tr>");
                    tbody.append(td);
                });
            }
        });
    });
});


