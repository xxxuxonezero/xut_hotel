<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<c:url value="/resources/css/user-info.css"/>"/>
<link rel="stylesheet" href="<c:url value="/resources/css/my-comment-order.css"/>"/>

<jsp:include page="../header.jsp"></jsp:include>
<div class="container" id="orderInfoContainer">
    <nav:nav currentMenu="我的订单"/>
    <div class="right-menu">
        <div class="nav-title mb16">我的订单</div>
        <div id="myOrderDiv"></div>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
<jsp:include page="tmpl/editOrder.jsp"/>

<script id="myOrderTmpl" type="text/x-jquery-tmpl">
    <div class="my-order-item" order-id="\${order.id}" style="width:700px">
        <div class="order-info-div">
            <span class="order-number">订单号\${order.uuid}</span>
            <span class="order-time">\${DateUtils.getDateStr(order.checkInTime)}~\${DateUtils.getDateStr(order.checkOutTime)}</span>
            <span class="order-status">\${ORDER_MAP[order.status]}</span>
        </div>
        {%if roomType%}
            <div class="room-type-container">
                <img src="{%if roomType.cover%}\${roomType.cover}{%else%}<c:url value='/resources/images/index1.jpeg'/>{%/if%}" class="cover">
                <div class="info">
                    <div>
                        <span class="type inline-block">\${roomType.type}</span>
                    </div>
                    <div class="detail-info">
                        <span class="detail inline-block mr24">\${roomType.detail}</span>
                        <span class="inline-block price">￥\${roomType.price}</span>
                    </div>
                    <div class="operate-btns">
                        {%if order.status == 1%}
                            <button class="btn btn-primary operate-btn" onclick="comment(\${roomType.id})">评价</button>
                        {%elif order.status == 2%}
                             <button class="btn btn-primary operate-btn" onclick="updateInfo(\${JSON.stringify(order)}, \${JSON.stringify(clients)}, \${JSON.stringify(roomType)})">修改信息</button>
                             <button class="btn btn-primary operate-btn" onclick="cancelOrder(\${order.id})">取消预定</button>
                        {%/if%}
                    </div>
                </div>
            </div>
        {%/if%}
    </div>
</script>
<script>
    var ORDER_MAP = {
        0: "已取消",
        1: "已完成",
        2: "交易中",
        3: "入住中"
    };
    (function () {
        render();
    })();

    function render() {
        var api = new API({
            callback: function (r) {
                if (r.code !== 0) {
                    Dialog.error("获取失败~");
                    return;
                }
                if (r.data) {
                    r.data.forEach(function (obj) {
                        var item = obj.roomType;
                        if (item) {
                            item.detail = "";
                            item.detail = item.bed ? item.detail + item.bed + " " : item.detail;
                            item.detail = item.maxPeople ? item.detail + "最多" + item.maxPeople + "人 " : item.detail;
                            item.detail = item.hasWindow ? item.detail + (item.hasWindow ==  1 ? "有窗" : "无窗") + " " : item.detail;
                            item.detail = item.hasFood ? item.detail + (item.hasFood == 1 ? "有餐食" : "无餐食") + " " : item.detail;
                            item.detail = item.smoke ? item.detail + (item.smoke == 1 ? "禁烟" : "不禁烟") + " " : item.detail;
                            item.detail = item.size ? item.detail + item.size+"平方米 " : item.detail;
                        }
                    });
                    $("#myOrderTmpl").tmpl(r.data).appendTo("#myOrderDiv");
                } else {
                    $("#myOrderDiv").html("<span class='empty-tip'>暂无订单</span>");
                }
            },
            url: "${pageContext.request.contextPath}/user/order/getOrdersByUser",
            errorCallback: function () {
                Dialog.error("获取失败~");
            }
        });
        api.sendFormData();
    }

    function cancelOrder(id) {
        var api = new API({
            url: "${pageContext.request.contextPath}/user/order/cancelOrder",
            method: "POST",
            data: {id: id},
            callback: function (r) {
                if (r.code === 0) {
                    location.reload();
                    return;
                }
                Dialog.error("取消失败");
            }
        });
        api.sendFormData();
    }

    function updateInfo(order, clients, roomType) {
        renderModal(order, clients, roomType);
        $modal.modal();
    }
</script>

