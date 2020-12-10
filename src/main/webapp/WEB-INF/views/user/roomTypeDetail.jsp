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
</style>
<link rel="stylesheet" href="<c:url value="/resources/css/roomType.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/icon.css"/> ">
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div id="roomDetail">
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
            <button type="button" class="btn btn-primary" style="position:absolute;bottom: 20px; right: 20px">预定</button>
        </div>
    </div>
    <div class="title-bar flex" style="align-items: center">
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
        <div class="comment" comment-id="1">
            <div class="comment-item flex">
                <div class="user-img mr16">
                    <img src="logo.jpg" alt="">
                </div>
                <div class="comment-detail">
                    <div class="author mb16">onezero</div>
                    <div class="content mb16">这个房子好漂亮啊</div>
                    <div class="create-time">发表于2020/12/10 12:20:35</div>
                </div>
                <div class="operate-items">
                    <div class="operate-item inline-block reply">回复</div>
                    <div class="operate-item inline-block">删除</div>
                </div>
            </div>
            <div class="replys">
                <div class="reply-item flex" style="margin-left: 65px">
                    <div class="user-img mr16">
                        <img src="logo.jpg" alt="">
                    </div>
                    <div class="comment-detail">
                        <div class="author mb16">onezero</div>
                        <div class="content mb16">这个房子好漂亮啊</div>
                        <div class="create-time">发表于2020/12/10 12:20:35</div>
                    </div>
                    <div class="operate-items">
                        <div class="operate-item inline-block reply">回复</div>
                        <div class="operate-item inline-block">删除</div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
    </div>
</script>
<script>
    var id = '${param.id}';
    var commentInit = false;

    id = parseInt(id);
    (function () {
        init();
    })();

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
