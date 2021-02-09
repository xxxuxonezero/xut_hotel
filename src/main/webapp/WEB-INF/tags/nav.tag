<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentMenu" required="true" type="java.lang.String" %>
<link rel="stylesheet" href="<c:url value="/resources/css/nav.css"/>">

<div class="left-menu" id="userMenu">
    <ul class="nav-items">
        <li class="nav-item ${currentMenu.equals('个人信息') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/user/MyInfo">个人信息</a>
        </li>
        <li class="nav-item ${currentMenu.equals('我的订单') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/user/order/MyOrder">我的订单</a>
        </li>
        <li class="nav-item ${currentMenu.equals('我的评论') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/user/comment/MyComment">我的评论</a>
        </li>
        <li class="nav-item ${currentMenu.equals('修改密码') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/user/ResetPwd">修改密码</a>
        </li>
     </ul>
</div>