<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .main-container{
        width: 900px;
        height: 100%;
        margin: 0 auto;
    }
    #roomTypeList .item {
        display: flex;
        justify-content: flex-start;
        width: 100%;
        padding: 5px;
        margin: 16px 0;
    }
    #roomTypeList .item:hover{
        box-shadow: 10px 5px 10px #7F7F7F;
        cursor: pointer;
    }
    .price {
        font-size: 30px;
        font-weight: 600;
        color: orange;
    }
    .info {
        font-size: 16px;
        margin-left: 32px;
    }
    .cover {
        max-height: 240px;
        max-width: 240px;
        border-radius: 4px;
    }
    .detail {
        font-size: 12px;
        color: #7F7F7F;
    }
</style>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="main-container">
    <div class="information-title-bar">
        <div class="line-icon inline-block"></div>
        <span class="information-title">房型选择</span>
    </div>
    <div id="roomTypeList">
        <div class="item">
            <img src="<c:url value="/resources/images/index1.jpeg"/>" class="cover">
            <div class="info">
                <span class="type">精品大床房</span>
                <br>
                <span class="detail">有床 不禁烟 有窗 有参食</span>
                <br>
                <span class="price">299.99</span>
            </div>
        </div>
    </div>

</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
<script id="roomTypeItemTmpl" type="text/x-jquery-tmpl">
    {%each %}
        <div class="item" onclick="window.location.href='roomTypeDetail?id=\${id}'">
            <img src="{%if cover%}\${cover}{%else%}<c:url value='/resources/images/index1.jpeg'/>{%/if%}" class="cover">
            <div class="info">
                <span class="type">\${type}</span>
                <br>
                <span class="detail">\${detail}</span>
                <br>
                <span class="price">\${price}</span>
            </div>
        </div>
   {%/each%}
</script>
<script>
    (function () {
        initData();
    })();

    function initData() {
        $.ajax({
            url: "roomTypeList",
            type:"get",
            contentType: "application/json",
            dataType: "json",
            success: function (r) {
                if (r.code == 0) {
                    var data = r.data;
                    if (data) {
                        data.forEach(function (item) {
                            item.detail = "";
                            item.detail = item.bed ? item.detail + item.bed + " " : item.detail;
                            item.detail = item.maxPeople ? item.detail + "最多" + item.maxPeople + "人 " : item.detail;
                            item.detail = item.hasWindow ? item.detail + (item.hasWindow ==  1 ? "有窗" : "无窗") + " " : item.detail;
                            item.detail = item.hasFood ? item.detail + (item.hasFood == 1 ? "有餐食" : "无餐食") + " " : item.detail;
                            item.detail = item.smoke ? item.detail + (item.smoke == 1 ? "禁烟" : "不禁烟") + " " : item.detail;
                            item.detail = item.size ? item.detail + item.size+"平方米 " : item.detail;
                        })
                    }
                    $("#roomTypeItemTmpl").tmpl({data: r.data}).appendTo("#roomTypeList");
                } else{
                    alert("加载失败");
                }
            },
            error: function (err) {
                alert("加载失败");
            }
        });
    }
</script>
</html>
