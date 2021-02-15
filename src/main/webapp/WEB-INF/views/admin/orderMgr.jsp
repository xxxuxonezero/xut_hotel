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

    .select {
        width: 150px;
        height: 30px;
        padding: 5px;
    }

    td {
        vertical-align: middle !important;
    }
</style>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="admin-page flex">
    <nav:adminNav currentMenu="订单管理"></nav:adminNav>
    <div class="right-menu inline-block" style="margin-top: 100px">
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
    </div>
</div>
</body>
<script id="orderList" type="text/x-jquery-tmpl">
        {%each(index, item) data.list%}
            <tr id="\${order.id}">
                <td>\${order.uuid}</td>
                <td>\${roomType.type}</td>
                <td>\${order.price}</td>
                <td>{%if clients%}\${clients.length}{%else%}0{%/if%}</td>
                <td>\${DateUtils.getDateStr(order.checkInTime, "-")}</td>
                <td>\${DateUtils.getDateStr(order.checkOutTime, "-")}</td>
                <td style="color:orange">\${STATUS[order.status]}</td>
                <td>
                    <button class="btn btn-primary" onclick="render(\${JSON.stringify(item)})" data-toggle="modal" data-target="#orderModal">查看</a>
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

<script>
    var STATUS = {
        0: "已取消",
        1: "已完成",
        2: "交易中"
    };
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
    })();
    
    function init() {
        var typeId = $("select[name=typeId]").val();
        var status = $("select[name=status]").val();
        $.ajax({
            url: "orderList",
            type: "get",
            data: {offset:1, status: status, typeId: typeId},
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r.code == 0) {
                    $("#orderTable tbody").remove();
                    var data = [];
                    data = r.data;
                    $("#orderList").tmpl({data: data}).appendTo("#orderTable");
                }
            }
        });
    }


</script>
</html>
