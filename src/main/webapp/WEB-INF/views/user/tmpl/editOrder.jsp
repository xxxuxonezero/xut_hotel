<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<link rel="stylesheet" href="<c:url value="/resources/css/roomType.css?v=1.0.0"/>">
<div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">预定房间</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form role="form" id="orderForm" method="post">
                    <div id="clientList">

                    </div>
                    <div class="add-btn" onclick="addClient()">添加</div>
                    <div class="form-group">
                        <label>入住时间</label>
                        <input type="date" class="form-control"
                               name="checkInTime">
                        <label class="error-tip none">入住时间需晚于当前时间</label>
                    </div>
                    <div class="form-group">
                        <label>离店时间</label>
                        <input type="date" class="form-control"
                               name="checkOutTime">
                        <label class="error-tip none">离店时间需晚于入住时间</label>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <textarea class="form-control"
                                  name="note"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-secondary order-btn" onclick="order()" >预定</button>
            </div>
        </div>
    </div>
</div>

<script id="clientTmpl" type="text/x-jquery-tmpl">
    <div class="client-item">
    <span>入住人\${index}</span>
        <span class="close" onclick="deleteClient(this)">X</span>
        <form action="" class="client-form">
            <div  class="field">名字：<input type="text" class="realname" value="\${realName}" name="realName"/></div>
            <div class="field">身份证：<input type="text" class="identificationId" value="\${identificationId}" name="identificationId"/></div>
            <div class="field">手机：<input type="text" class="phone" value="\${phone}" name="phone"/></div>
        </form>
    </div>
</script>

<script>
    var ADD = 1;
    var UPDATE = 2;

    var user = {
        realName: '${user.realName}',
        identificationId: '${user.identificationId}',
        phone: '${user.phone}'
    };
    var ITEM = {
        realName: "",
        identificationId: "",
        phone: ""
    };

    var $modal = $("#orderModal");
    var index = 1;

    function initModal() {
        var $view = $("#clientTmpl").tmpl([user]).appendTo("#clientList");

        initValidEvent($view.find(".client-form"))
    }

    function renderModal(order, clients, roomType) {
        $("#clientList").empty();
        var $view = $("#clientTmpl").tmpl(clients).appendTo("#clientList");
        initValidEvent($view.find(".client-form"));
        $modal.find(".add-btn").attr("onclick", "addClient(" + roomType.maxPeople + ")");
        console.log(DateUtils.getDateStr(order.checkInTime, "-"));
        $modal.find("input[name='checkInTime']").val(DateUtils.getDateStr(order.checkInTime, "-"));
        $modal.find("input[name='checkOutTime']").val(DateUtils.getDateStr(order.checkOutTime, "-"));
        $modal.find("textarea[name='note']").val(order.note ? order.note : "");
        $modal.find(".order-btn").text("编辑");
        $modal.find(".order-btn").attr("onclick", "order("+ JSON.stringify(order) + "," + JSON.stringify(roomType) +")");
    }

    $("#orderForm").validate({
        rules: {
            checkInTime:{
                required: true
            },
            checkOutTime: {
                required: true
            }
        }
    });

    function initValidEvent($form) {
        $form.validate({
            rules:{
                realName:{
                    required: true,
                    maxlength: 20
                },
                identificationId: {
                    required: true,
                    identificationId: true
                },
                phone: {
                    required: true,
                    phone: true
                }
            }
        })
    }

    function addClient(maxPeople) {
        ++index;
        maxPeople = maxPeople ? maxPeople : roomType.maxPeople;
        if (index > maxPeople) {
            index = maxPeople;
            Dialog.error("人数已满");
            return;
        }
        var $view = $("#clientTmpl").tmpl(ITEM);
        $view.appendTo("#clientList");
        initValidEvent($view.find(".client-form"));
        return false;
    }

    function deleteClient(e) {
        if ($(".client-item").length <= 1) {
            Dialog.error("入住人数至少为1");
            return false;
        }
        index--;
        $(e).parent().remove();
    }

    function order(order, roomType) {
        if (!$("#orderModal").find(".error-tip").hasClass("none") || !$("#orderForm").valid()) {
            Dialog.error("请完善信息");
            return;
        }
        var flag = order ? true : false;
        if (!order) {
            order = {};
            order.roomTypeId = id;
            order.price = window.roomType.price;
        }
        var order = Object.assign(order, formObject($("#orderForm").serializeArray()));
        console.log("${pageContext.request.contextPath}/user/order/" + (flag ? "updateOrder" : "create"))
        $.ajax({
            url: "${pageContext.request.contextPath}/user/order/" + (flag ? "updateOrder" : "create"),
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({order: order, clients: getClients()}),
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                    return;
                }
                Dialog.error("更新失败~")
            }
        })
    }

    function getClients() {
        var items = document.querySelectorAll("#clientList .client-item");
        var forms = $("#clientList .client-form");
        for (var i = 0; i < forms.length; i++) {
            if (!$(forms[i]).valid()) {
                Dialog.error("请完善信息");
                return;
            }
        }
        var clients = [];
        for(var i = 0; i < items.length; i++) {
            var client = {};
            client.realName = $(items[i]).find(".realname").val().trim();
            client.identificationId = $(items[i]).find(".identificationId").val().trim();
            client.phone = $(items[i]).find(".phone").val().trim();
            clients.push(client);
        }
        if (clients.length <= 0) {
            Dialog.error("入住人不能为空");
            return;
        }
        return clients;
    }

    $("input[name='checkInTime']").on("change", function () {
        var $err = $(this).parent().find(".error-tip");
        if (DateUtils.compare($(this).val(), new Date()) < 0 ) {
            $err.removeClass("none");
            return;
        }
        if (!$err.hasClass("none")) {
            $err.addClass("none");
        }

    });

    $("input[name='checkOutTime']").on("change", function () {
        var checkInTime = $("input[name='checkInTime']").val();
        var $err = $(this).parent().find(".error-tip")
        if (DateUtils.compare($(this).val(), checkInTime) <= 0) {
            $err.removeClass("none");
            return;
        }
        if (!$err.hasClass("none")) {
            $err.addClass("none");
        }
    });
</script>