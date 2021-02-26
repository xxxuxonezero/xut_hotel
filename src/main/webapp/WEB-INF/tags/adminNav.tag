<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentMenu" required="true" type="java.lang.String" %>
<link rel="stylesheet" href="<c:url value="/resources/css/nav.css"/>">

<div class="left-menu" id="userMenu">
    <ul class="nav-items">
        <li class="nav-item ${currentMenu.equals("房型管理") ? 'active' : ''}"><a href="${pageContext.request.contextPath}/admin/roomType">房型管理</a></li>
        <li class="nav-item ${currentMenu.equals("订单管理") ? 'active' : ''}"><a href="${pageContext.request.contextPath}/admin/order">订单管理</a></li>
        <li class="nav-item ${currentMenu.equals("客房信息") ? 'active' : ''}"><a href="${pageContext.request.contextPath}/admin/room">客房信息</a></li>
        <li class="nav-item ${currentMenu.equals("用户信息") ? 'active' : ''}"><a href="${pageContext.request.contextPath}/admin/user/userInfo">用户信息</a></li>
    </ul>
</div>

<script>
    function logout() {
        $.ajax({
            url: '${pageContext.request.contextPath}/logout',
            type: 'POST',
            contentType: "application/json"
        })
    }
</script>