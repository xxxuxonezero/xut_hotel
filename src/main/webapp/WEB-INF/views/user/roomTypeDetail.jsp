<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    #roomDetail{
        width: 1100px;
        margin: 0 auto;
    }
    .product-info {
        display: flex;
        justify-content: flex-start;
        height: 400px;
        width: 100%;
        padding: 5px;
        margin: 16px 0;
        position: relative;
    }
    .setting {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
    }
    .detail img {
        max-width: 100%;
    }
    .type {
        font-size: 40px;
        font-weight: 500;
        color: #000;
        padding: 20px 0;
        display: block;
    }
    .field {
        padding: 5px;
    }
    .client-item {
        margin: 10px 0;
        position: relative;
        border: 1px solid #000000;
        border-radius: 8px;
    }
    .client-item .close {
        position: absolute;
        font-size: 16px;
        right: 0;
        top: 0;
        display: inline-block;
    }
    .add-btn {
        background: #1b6d85;
        height: 32px;
        line-height: 32px;
        text-align: center;
        width: 80px;
        color: white;
        border-radius: 4px;
        cursor: pointer;
    }
</style>
<link rel="stylesheet" href="<c:url value="/resources/css/roomType.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/icon.css"/> ">
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div id="roomDetail">
</div>
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
                     </div>
                    <div class="form-group">
                        <label>离店时间</label>
                        <input type="date" class="form-control"
                               name="checkOutTime">
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
                <button type="button" class="btn btn-secondary" onclick="order()" >预定</button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="comment.jsp"></jsp:include>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
<script id="roomTypeDetailTmpl" type="x-text/jquery-tmpl">
    <div class="product-info">
        <img src="{%if roomType.cover%}\${roomType.cover}{%else%}<c:url value='/resources/images/index1.jpeg'/>{%/if%}" class="cover">
            <div class="info">
                <span class="type">\${roomType.type}</span>
                <br>
                <span class="detail">\${roomType.detail}</span>
                <br>
                <span class="price">\${roomType.price}</span>
            <br>
            <button type="button" class="btn btn-primary" style="position:absolute;bottom: 20px; right: 20px"  data-toggle="modal" data-target="#orderModal" >预定</button>
        </div>
    </div>
    <div class="title-bar flex mb16" style="align-items: center">
        <div class="item-bar inline-center active detail-bar" onclick="toggleDetail(this)">商品详情</div>
        <div class="item-bar inline-center comment-bar" onclick="toggleComment(this)">评论</div>
    </div>
    <div id="contentContainer">
        <div class="detail">
            {%if setting%}
            <div class="setting">
                {%if setting.wifi%}
                    <div class="item inline-center"><div class="icon16 wifi-icon"></div>\${setting.wifi}</div>
                {%/if%}
                {%if setting.food%}
                    <div class="item inline-center"><div class="icon16 food-icon"></div>\${setting.food}</div>
                {%/if%}
                {%if setting.spot%}
                    <div class="item inline-center"><div class="icon16 spot-icon"></div>\${setting.spot}</div>
                {%/if%}
                {%if setting.airConditioner%}
                <div class="item inline-center"><div class="icon16 air-conditioner-icon"></div>\${setting.airConditioner}</div>
                {%/if%}
                {%if setting.facilities%}
                    <div class="item inline-center"><div class="icon16 facilities-icon"></div>\${setting.facilities}</div>
                {%/if%}
            </div>
            {%/if%}
            {%each(index, item)  roomType.imgList%}
                <img src="\${item}" alt="">
            {%/each%}
        </div>
        <div class="comment-container mt16 none">
    <div id="commentTextArea" class="mb16 comment-box">
        <textarea class="textarea-custom" placeholder="请输入评论..."></textarea>
        <button type="button" class="btn btn-primary" onclick="addComment()">评论</button>
    </div>
    <div id="commentList">
    </div>
</div>
    </div>
</script>
<script id="clientTmpl" type="text/x-jquery-tmpl">
    <div class="client-item">
    <span>入住人\${index}</span>
        <span class="close" onclick="deleteClient(this)">X</span>
        <div  class="field">名字：<input type="text" class="realname "/></div>
        <div class="field">身份证：<input type="text" class="identificationId"/></div>
        <div class="field">手机：<input type="text" class="phone"/></div>
    </div>
</script>
<script>
    var id = '${param.id}';
    var commentInit = false;
    var index = 1;
    var roomType;

    id = parseInt(id);
    (function () {
        init();
        initModal();
    })();

    function initModal() {
        $("#clientTmpl").tmpl().appendTo("#clientList");
    }

    function addClient() {
        ++index;
        if (index > roomType.maxPeople) {
            index = roomType.maxPeople;
            alert("人数已满");
            return;
        }
        $("#clientTmpl").tmpl().appendTo("#clientList");
        return false;
    }

    function deleteClient(e) {
        index--;
        $(e).parent().remove();
    }

    function order() {
        var items = document.querySelectorAll("#clientList .client-item");
        var clients = [];
        for(var i = 0; i < items.length; i++) {
            var client = {};
            client.realName = $(items[i]).find(".realname").val();
            client.identificationId = $(items[i]).find(".identificationId").val();
            client.phone = $(items[i]).find(".phone").val();
            clients.push(client);
        }
        if (clients.length <= 0) {
            alert("入住人不能为空");
            return;
        }
        var order = formObject($("#orderForm").serializeArray());
        order.roomTypeId = id;
        order.price = roomType.price;
        $.ajax({
            url: "/order/create",
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: {order: order, clients: clients},
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                }
            }
        })
    }

    function init() {
        if (id) {
            $.ajax({
                url: "/roomType/detail",
                contentType: "applicaiton/json",
                dataType: "json",
                type: "get",
                data: {id: id},
                success: function (r) {
                    if (r.code == 0) {
                        roomType = r.data.roomType;
                        setDetail(r.data.roomType);
                        $("#roomTypeDetailTmpl").tmpl(r.data).appendTo("#roomDetail");
                    } else {
                        alert("加载失败");
                    }
                }
            })

        }
    }

    function setDetail(item) {
        item.detail = "";
        item.detail = item.bed ? item.detail + item.bed + " " : item.detail;
        item.detail = item.maxPeople ? item.detail + "最多" + item.maxPeople + "人 " : item.detail;
        item.detail = item.hasWindow ? item.detail + (item.hasWindow ==  1 ? "有窗" : "无窗") + " " : item.detail;
        item.detail = item.hasFood ? item.detail + (item.hasFood == 1 ? "有餐食" : "无餐食") + " " : item.detail;
        item.detail = item.smoke ? item.detail + (item.smoke == 1 ? "禁烟" : "不禁烟") + " " : item.detail;
        item.detail = item.size ? item.detail + item.size+"平方米 " : item.detail;
    }

    function toggleComment(e) {
        if (!commentInit) {
            initComment();
            commentInit = true;
        }
        $(".comment-bar").addClass("active");
        $(".detail-bar").removeClass("active");
        $(".comment-container").removeClass("none");
        $("#contentContainer .detail").addClass("none");
    }

    function toggleDetail(e) {
        $(".comment-bar").removeClass("active");
        $(".detail-bar").addClass("active");
        $(".comment-container").addClass("none");
        $("#contentContainer .detail").removeClass("none");
    }
</script>
</html>
