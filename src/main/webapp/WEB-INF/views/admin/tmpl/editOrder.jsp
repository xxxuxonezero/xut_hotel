<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<link rel="stylesheet" href="<c:url value="/resources/css/roomType.css?v=1.0.0"/>">
<div class="modal fade" id="editOrderModal" tabindex="-1" role="dialog" aria-hidden="true">
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
                    <div class="form-group">
                        <label>房型</label>
                        <select class="form-control" name="typeId" id="typeSel">
                            <c:forEach items="${types}" var="item">
                                <option value="${item.id}" data-maxPeople="${item.maxPeople}">${item.type}</option>
                            </c:forEach>
                        </select>
                    </div>
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


<div class="modal fade" id="editOrderStatusModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">变更状态</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label>状态</label>
                        <select id="statusSel" class="form-control">
                            <option value="0">已取消</option>
                            <option value="1">已关闭</option>
                            <option value="3">已入住</option>
                        </select>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-secondary order-btn" onclick="updateStatus()">更新</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="allocateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">分配房间</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>分配房间</label>
                    <select class="form-control" name="roomId" id="roomSel">
                    </select>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-secondary order-btn" onclick="checkIn()" id="allocateBtn">分配房间</button>
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

    var ITEM = {
        realName: "",
        identificationId: "",
        phone: ""
    };

    var $modal = $("#orderModal");
    var index = 1;
    var roomType;

    function initModal() {
        var $view = $("#clientTmpl").tmpl([ITEM]).appendTo("#clientList");

        initValidEvent($view.find(".client-form"))
    }

    $("#typeSel").on("change", function (e) {
        var maxPeople = $("#editOrderModal select[name='typeId'] option:selected").data("maxpeople");
        var num = $("#clientList .client-item").length;
        if (num > maxPeople) {
            $("#clientList .client-item:nth-child(" + maxPeople + ")").nextAll().remove();
        }
    });

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

    function addClient() {
        ++index;
        maxPeople = $("#editOrderModal select[name='typeId'] option:selected").data("maxpeople");
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
        if (!$("#editOrderModal").find(".error-tip").hasClass("none") || !$("#orderForm").valid()) {
            Dialog.error("请完善信息");
            return;
        }
        var flag = order ? true : false;
        if (!order) {
            order = {};
            order.roomTypeId =  $("#editOrderModal select[name='typeId'] option:selected").val();
        }
        var order = Object.assign(order, formObject($("#orderForm").serializeArray()));
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/order/" + (flag ? "updateOrder" : "create"),
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({order: order, clients: getClients()}),
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                    return;
                }
                if (r.msg) {
                    Dialog.error(r.msg)
                } else{
                    Dialog.error("更新失败~")
                }

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

    function checkIn() {
        var roomId = $("#roomSel option:selected").val()
        var orderId = $("#allocateModal").data("orderId")
        var api = new API({
            url: '${pageContext.request.contextPath}/admin/allocateRoom',
            method: 'POST',
            data: JSON.stringify({roomId: roomId, orderId: orderId}),
            callback: function (r) {
                if (r.code === 0) {
                    location.reload();
                }
            }
        });
        api.sendJSONData();
    }

    function updateStatus() {
        var status = $("#statusSel option:selected").val()
        var orderId = $("#editOrderStatusModal").data("orderId")
        var api = new API({
            url: '${pageContext.request.contextPath}/admin/order/updateStatus',
            method: 'POST',
            data: {status: status, id: orderId},
            callback: function (r) {
                if (r.code === 0) {
                    location.reload();
                }
            }
        });
        api.sendFormData();
    }

</script>