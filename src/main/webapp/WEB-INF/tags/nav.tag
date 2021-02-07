<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentMenu" required="true"%>
<link rel="stylesheet" href="<c:url value="/resources/css/nav.css"/>">

<div class="left-menu" id="userMenu">
    <ul class="nav-items">
        <li class="nav-item active" class="${currentMenu == '个人信息' ? 'active' : ''}">
            <a href="">个人信息</a>
        </li>
        <li class="nav-item active" class="${currentMenu == '个人信息' ? 'active' : ''}">
            <a href="">我的订单</a>
        </li>
        <li class="nav-item active" class="${currentMenu == '个人信息' ? 'active' : ''}">
            <a href="">我的评论</a>
        </li>
        <li class="nav-item active" class="${currentMenu == '个人信息' ? 'active' : ''}">
            <a href="">修改密码</a>
        </li>
     </ul>
</div>