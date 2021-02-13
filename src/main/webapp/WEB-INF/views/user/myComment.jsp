<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<c:url value="/resources/css/user-info.css"/>"/>
<link rel="stylesheet" href="<c:url value="/resources/css/my-comment.css?v=1.0.0"/>"/>

<jsp:include page="../header.jsp"></jsp:include>
<div class="container" id="commentInfoContainer">
    <nav:nav currentMenu="我的评论"/>
    <div class="right-menu">
        <div class="nav-title mb16">我的评论</div>
        <div id="myCommentDiv"></div>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>

<script id="myCommentTmpl" type="text/x-jquery-tmpl">
    <div class="my-comment-item" comment-id="\${comment.id}">
        <div class="operate-items">
            <a class="operate-item" onclick="update(\${comment.id})">编辑</a>
            <a class="operate-item" onclick="deleteComment(\${comment.id})">删除</a>
        </div>
        <div class="publish-time-div">
            <span class="publish-time">发表于\${DateUtils.getLocalTime(comment.createdTime)}</span>
        </div>
        <div class="comment-description">
            {%html comment.description%}
        </div>
        {%if roomType%}
            <div class="room-type-container" onclick="window.location.href='${pageContext.request.contextPath}/roomTypeDetail?id=\${roomType.id}'">
                <img src="{%if roomType.cover%}\${roomType.cover}{%else%}<c:url value='/resources/images/index1.jpeg'/>{%/if%}" class="cover">
                <div class="info">
                    <span class="type inline-block">\${roomType.type}</span>
                    <br>
                    <span class="detail inline-block">\${roomType.detail}</span>
                </div>
            </div>
        {%/if%}
    </div>
</script>
<script>

    (function () {
        render();
    })();

    function render() {
        var api = new API({
            callback: function (r) {
                if (r.code !== 0) {
                    Dialog.error("获取失败~");
                    return;
                }
                if (r.data) {
                    r.data.forEach(function (obj) {
                        var item = obj.roomType;
                        if (item) {
                            item.detail = "";
                            item.detail = item.bed ? item.detail + item.bed + " " : item.detail;
                            item.detail = item.maxPeople ? item.detail + "最多" + item.maxPeople + "人 " : item.detail;
                            item.detail = item.hasWindow ? item.detail + (item.hasWindow ==  1 ? "有窗" : "无窗") + " " : item.detail;
                            item.detail = item.hasFood ? item.detail + (item.hasFood == 1 ? "有餐食" : "无餐食") + " " : item.detail;
                            item.detail = item.smoke ? item.detail + (item.smoke == 1 ? "禁烟" : "不禁烟") + " " : item.detail;
                            item.detail = item.size ? item.detail + item.size+"平方米 " : item.detail;
                        }
                    });
                    $("#myCommentTmpl").tmpl(r.data).appendTo("#myCommentDiv");
                } else {
                    $("#myCommentDiv").html("<span class='empty-tip'>暂无评论</span>");
                }
            },
            url: "${pageContext.request.contextPath}/user/comment/getListByUser",
            errorCallback: function () {
                Dialog.error("获取失败~");
            }
        });
        api.sendFormData();
    }

    function update(id) {
        var $item = $(".my-comment-item[comment-id=" + id + "]");
        var content = $item.find(".comment-description").html();
        var $input = $('<textarea class="edit-description" cols="30" rows="10"></textarea>');
        $input.html(content);
        $item.append($input);
    }

    function deleteComment(id) {
        var $item = $(".my-comment-item[comment-id=" + id + "]");
        var api = new API({
            url: "${pageContext.request.contextPath}/user/comment/delete",
            method: 'POST',
            data: {id: id},
            callback: function (r) {
                if (r.code === 0) {
                    Dialog.success("删除成功~");
                    $item.remove();
                    return;
                }
                Dialog.error("删除失败~");
            },
            errorCallback: function () {
                Dialog.error("删除失败~");
            }
        });

        api.sendFormData();
    }
</script>
