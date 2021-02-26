<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../common.jsp"></jsp:include>
    <link rel="stylesheet" href="<c:url value="/resources/css/admin.css"/>">
</head>
<style>
    .detail-field {
        padding: 10px;
    }
</style>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="admin-page container flex">
    <nav:adminNav currentMenu="订单管理"></nav:adminNav>
    <div class="right-menu inline-block">
        <button class="btn btn-primary mb16" data-toggle="modal" data-target="#editOrderModal">添加订单</button>
        <div class="feature-btn">
            <select class="select" name="typeId" onchange="init()">
                <option value="" checked>按房型过滤</option>
                <c:forEach items="${types}" var="item">
                    <option value="${item.id}">${item.type}</option>
                </c:forEach>
            </select>
            <select name="status" class="select" onchange="init()">
                <option value="" checked>按状态过滤</option>
                <option value="0">已取消</option>
                <option value="1">已完成</option>
                <option value="2">交易中</option>
                <option value="3">已入住</option>
            </select>
        </div>
        <div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">订单信息</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                    </div>
                </div>
            </div>
        </div>
        <table class="table table-hover" id="orderTable">
            <thead>
            <tr>
                <th>订单号</th>
                <th>房型</th>
                <th>价格</th>
                <th>入住人数</th>
                <th>入住时间</th>
                <th>离店时间</th>
                <th>入住房间</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td colspan="7">正在加载中...</td>
            </tr>
            </tbody>
        </table>
        <div id="paginationContainer"></div>
    </div>
</div>
</body>
<script id="orderList" type="text/x-jquery-tmpl">
        {%each(index, item) data%}
            <tr id="\${order.id}">
                <td>\${order.uuid}</td>
                <td>\${roomType.type}</td>
                <td>\${order.price}</td>
                <td>{%if clients%}\${clients.length}{%else%}0{%/if%}</td>
                <td>\${DateUtils.getDateStr(order.checkInTime, "-")}</td>
                <td>\${DateUtils.getDateStr(order.checkOutTime, "-")}</td>
                <td>{%if roomData%}\${roomData.roomNumber}{%/if%}</td>
                <td style="color:orange">\${STATUS[order.status]}</td>
                <td>
                    <button class="btn btn-primary" onclick="render(\${JSON.stringify(item)})" data-toggle="modal" data-target="#orderModal">查看</button>
                    {%if !(order.status == 0 || order.status == 1)%}
                        <button class="btn btn-primary" data-toggle="modal" data-target="#editOrderStatusModal" onclick="changeStatus(\${order.id})">变更状态</button>
                        {%if !roomData%}
                            <button class="btn btn-primary" data-toggle="modal" data-target="#allocateModal" onclick="allocateRoom(\${order.id}, '\${DateUtils.getDateStr(order.checkInTime, "-")}', '\${DateUtils.getDateStr(order.checkOutTime, "-")}', \${order.roomTypeId})">分配房间</button>
                        {%/if%}
                    {%/if%}
                </td>
            </tr>
        {%/each%}
</script>

<script id="orderDetailTmpl" type="text/x-jquery-tmpl">
    <div class="detail-field">
        <span>订单号：</span>
        <span>\${order.uuid}</span>
    </div>
    <div class="detail-field">
        <span>房型：</span>
        <span>\${roomType.type}</span>
    </div>
    <div class="detail-field">
        <span>价格：</span>
        <span>\${order.price}</span>
    </div>
    <hr>
    {%each clients%}
        <div class="detail-field">
            <div><span>名字：</span><span>\${realName}</span></div>
            <div><span>身份证：</span><span>\${identificationId}</span></div>
            <div><span>手机号：</span><span>\${phone}</span></div>
        </div>
        <hr>
    {%/each%}
   <div class="detail-field">
        <span>入住时间：</span>
        <span>\${DateUtils.getDateStr(order.checkInTime, "-")}</span>
    </div>
    <div class="detail-field">
        <span>离店时间：</span>
        <span>\${DateUtils.getDateStr(order.checkOutTime, "-")}</span>
    </div>
    <div class="detail-field">
        <span>状态：</span>
        <span>\${STATUS[order.status]}</span>
    </div>
</script>

<jsp:include page="tmpl/editOrder.jsp"/>

<script>
    var STATUS = {
        0: "已取消",
        1: "已完成",
        2: "交易中",
        3: "已入住"
    };

    var offset = '${param.offset}' ? '${param.offset}' : 1;
    function clearModal() {
        $("#orderModal").find("input[type != 'radio']").val("");
        $("#orderModal").find("input[type = 'radio']:checked").prop("checked", false);
        $("#orderModal").find("label.error").remove();
    }

    function render(data) {
        clearModal();
        $("#orderModal .modal-body").empty();

        $("#orderDetailTmpl").tmpl(data).appendTo("#orderModal .modal-body");
    }

    (function () {
        init();

        $('body').on('hidden.bs.modal', '.modal', function () {
            clearModal();
        });

        initModal();
    })();
    
    function init() {
        var typeId = $("select[name=typeId]").val();
        var status = $("select[name=status]").val();
        $.ajax({
            url: "orderList?offset=" + offset,
            type: "get",
            data: {offset: offset, status: status, typeId: typeId},
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r.code == 0) {
                    $("#orderTable tbody").remove();
                    var data = [];
                    data = r.data.list;
                    $("#orderList").tmpl({data: data}).appendTo("#orderTable");
                    var pagination = new Pagination({
                        total: r.data.totalCount,
                        url: '${pageContext.request.contextPath}/admin/order',
                        offset: offset,
                        container: "#paginationContainer"
                    });
                }
            }
        });
    }

    function allocateRoom(id, checkInTime, checkOutTime, roomTypeId) {
        $("#allocateModal").data("orderId", id);
        $("#allocateBtn").prop("disabled", false);
        var data = {
            checkInTime: checkInTime,
            checkOutTime: checkOutTime,
            roomTypeId: roomTypeId
        };

        var api = new API({
            url: "${pageContext.request.contextPath}/admin/order/getRooms",
            data: JSON.stringify(data),
            method: 'POST',
            callback: function (r) {
                var $sel = $("#roomSel");
                $sel.empty();
                if (r.code === 0 && r.data && r.data.length > 0) {
                    for (var i = 0; i < r.data.length; i++) {
                        var $option = $('<option></option>');
                        $option.prop("value", r.data[i].id);
                        $option.text(r.data[i].roomNumber);
                        $sel.append($option);
                    }
                } else {
                    $("#allocateBtn").prop("disabled", true);
                }
            }
        });
        api.sendJSONData();
    }

    function changeStatus(id) {
        $("#editOrderStatusModal").data("orderId", id)
    }


</script>
</html>
