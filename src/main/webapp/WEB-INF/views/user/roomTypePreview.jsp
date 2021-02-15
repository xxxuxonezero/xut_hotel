<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .main-container{
        width: 900px;
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
        max-width: 200px;
        border-radius: 4px;
    }
    .detail {
        font-size: 12px;
        color: #7F7F7F;
    }
</style>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="main-container min-h100">
    <div class="information-title-bar">
        <div class="line-icon inline-block"></div>
        <span class="information-title">房型选择</span>
    </div>
    <div id="roomTypeList">
    </div>
    <div id="paginationContainer"></div>

</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
<script id="roomTypeItemTmpl" type="text/x-jquery-tmpl">
    {%each data%}
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
    var offset = '${param.offset}' ? '${param.offset}' : 1;

    (function () {
        initData();
    })();

    function initData() {
        $.ajax({
            url: "roomTypeList?offset="+ offset,
            type:"get",
            contentType: "application/json",
            dataType: "json",
            success: function (r) {
                if (r.code == 0) {
                    var data = r.data.list;
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
                    $("#roomTypeItemTmpl").tmpl({data: data}).appendTo("#roomTypeList");
                    var pagination = new Pagination({
                        total: r.data.totalCount,
                        url: '${pageContext.request.contextPath}/roomType',
                        offset: offset,
                        container: "#paginationContainer"
                    });
                } else{
                    Dialog.error("加载失败");
                }
            },
            error: function (err) {
                Dialog.error("加载失败");
            }
        });
    }
</script>
</html>
