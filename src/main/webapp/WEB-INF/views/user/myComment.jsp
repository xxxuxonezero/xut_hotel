<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<c:url value="/resources/css/user-info.css"/>"/>

<jsp:include page="../header.jsp"></jsp:include>
<div class="container" id="commentInfoContainer">
    <nav:nav currentMenu="我的评论"/>
    <div class="right-menu">
        <div class="nav-title mb16">我的评论</div>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
