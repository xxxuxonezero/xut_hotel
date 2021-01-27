<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentMenu" required="true"%>
<link rel="stylesheet" href="<c:url value="/resources/css/nav.css"/>">

<div class="left-menu" id="userMenu">
    <ul class="nav-items">
        <li class="nav-item active" class="${currentMenu == '个人信息' ? 'active' : ''}">个人信息</li>
        <li class="nav-item" class="${currentMenu == '我的订单' ? 'active' : ''}">我的订单</li>
        <li class="nav-item" class="${currentMenu == '我的评论' ? 'active' : ''}">我的评论</li>
        <li class="nav-item" class="${currentMenu == '修改密码' ? 'active' : ''}">修改密码</li>
    </ul>
</div>