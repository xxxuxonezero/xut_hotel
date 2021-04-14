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
    #imageTable {
        display: flex;
        align-items: center;
    }
</style>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="admin-page container flex">
    <nav:adminNav currentMenu="酒店卫生"></nav:adminNav>
    <div class="right-menu inline-block">
        <div class="feature-btn">
            <button type="button" class="btn btn-primary" onclick="allocateAllRoom()">一键派送</button>
            <select onchange="getList(1)" id="statusSel" class="select">
                <option value="">全部</option>
                <option value="2">已预定</option>
                <option value="3">已入住</option>
            </select>
        </div>
        <table class="table table-hover" id="healthTable">
            <thead>
            <tr>
                <th>客房号</th>
                <th>房型</th>
                <th>是否入住</th>
                <th>卫生用品（份）</th>
                <th>用品成本</th>
                <th>是否分配卫生员</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td colspan="8">正在加载中...</td>
            </tr>
            </tbody>
        </table>
        <div id="paginationContainer"></div>
    </div>
</div>
</body>
<script id="healthList" type="text/x-jquery-tmpl">
        {%each data%}
            <tr>
                <td>\${roomNumber}</td>
                <td>\${roomTypeDesc}</td>
                <td>\${checkIn ? '是' : '否'}</td>
                <td>\${checkInPeople}</td>
                <td>\${5 * checkInPeople}</td>
                <td>\${allocatePeople ? '已分配' : '未分配'}</td>
                <td>
                    {%if allocatePeople%}<button class="btn secondary-primary" onclick="deleteHealther(
                   \${orderId})">取消卫生员</button>{%/if%}
                    {%if !allocatePeople%}<button class="btn btn-primary" onclick="allocateHealther(
                   \${orderId}, \${roomId})">分配卫生员</button>{%/if%}
                </td>
            </tr>
        {%/each%}
</script>

<script>
    var TYPE = {
        ADD: 1,
        EDIT: 2
    };

    var offset = '${param.offset}' ? '${param.offset}' : 1;

    (function () {
        getList();
    })();

    function getList(page) {
        if (page) {
            offset = page;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/health/getList?offset=" + offset + "&status="+$("#statusSel").val(),
            type: "get",
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r.code == 0) {
                    $("#healthTable tbody").remove();
                    var data = [];
                    data = r.data.list;
                    $("#healthList").tmpl({data: data}).appendTo("#healthTable");
                    var pagination = new Pagination({
                        total: r.data.totalCount,
                        url: '${pageContext.request.contextPath}/admin/health',
                        offset: offset,
                        container: "#paginationContainer"
                    });
                }
            }
        });
    }

    function allocateAllRoom() {
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/health/AllocateAllRoom",
            method: 'post',
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                }
            }
        })
    }

    function deleteHealther(orderId) {
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/health/Delete",
            method: 'post',
            data: {orderId: orderId},
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                }
            }
        })
    }

    function allocateHealther(orderId, roomId) {
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/health/AllocateHealther",
            method: 'post',
            data: {orderId: orderId, roomId: roomId},
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                }
            }
        })
    }

</script>
</html>
