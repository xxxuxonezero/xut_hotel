<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    #room-detail{
        width: 1100px;
        height: 100%;
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
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div id="room-detail">
    <div class="product-info">
        <img src="./index1.jpeg" alt="" class="cover">
        <div class="info">
            <span class="type">精品大床房</span>
            <br>
            <span class="detail">有床 不禁烟 有窗 有参食</span>
            <br>
            <span class="price">299.99</span>
            <br>
            <button type="button" class="btn btn-primary" style="position:absolute;bottom: 20px; right: 20px">预定</button>
        </div>
    </div>
    <div class="title-bar flex" style="align-items: center">
        <div class="item-bar inline-center active">商品详情</div>
        <div class="item-bar inline-center">评论</div>
    </div>
    <div class="detail">
        <div class="setting">
            <div class="item inline-center"><div class="icon16 wifi-icon"></div>wifi免费</div>
            <div class="item inline-center"><div class="icon16 wifi-icon"></div>wifi免费</div>
            <div class="item inline-center"><div class="icon16 wifi-icon"></div>wifi免费</div>
        </div>
        <img src="./index1.jpeg" alt="">
        <img src="./index1.jpeg" alt="">
        <img src="./index1.jpeg" alt="">
        <img src="./index1.jpeg" alt="">
        <img src="./index1.jpeg" alt="">
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
<script>
    var id = '${param.id}';
    id = parseInt(id);
    (function () {
        init();
    })();

    function init() {
        if (id) {

        }
    }
</script>
</html>
