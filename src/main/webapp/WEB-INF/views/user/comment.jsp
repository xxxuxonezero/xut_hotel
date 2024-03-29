<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .author {
        font-size: 16px;
        color: mediumpurple;
    }
    .create-time,.operate-item {
        font-size: 12px;
        color: #7F7F7F;
    }
    .comment-item, .reply-item {
        display: flex;
        align-items: center;
        background-color: #F2F6FC;
        border-radius: 4px;
        position: relative;
        padding: 16px;
    }
    .textarea-custom {
        width: 100%;
        padding: 5px;
        background-color: rgba(241,241,241,.98);
        resize: none;
        border-radius: 4px;
        border: none;
    }
    .operate-items {
        position: absolute;
        bottom: 16px;
        right: 16px;
    }
</style>

<script id="commentTmpl"  type="text/x-jquery-tmpl">
{%if comments%}
    {%each comments%}
        <div class="comment mt16 mb16" comment-id="\${comment.id}">
            <div class="comment-item flex">
                <div class="user-img mr16">
                    <img src="{%if author.avatar%}\${author.avatar}{%else%}<c:url value="/resources/images/default_avatar.png"/>{%/if%}" alt="">
                </div>
                <div class="comment-detail">
                    <div class="author mb16">\${author.userName}</div>
                    <div class="content mb16">\${comment.description}</div>
                    <div class="create-time">发表于\${DateUtils.getLocalTime(comment.createdTime)}</div>
                </div>
                <div class="operate-items">
                    <div class="operate-item inline-block" onclick="showReplyBox(\${comment.id},\${JSON.stringify(author)})">回复</div>
                    <div class="operate-item inline-block {%if userId != comment.userId%}none{%/if%}" onclick="deleteComment('\${comment.id}')">删除</div>
                </div>
            </div>
            <div class="replys">
            {%each replys%}
                <div class="reply-item flex" style="margin-left: 65px">
                    <div class="user-img mr16">
                        <img src="{%if author.avatar%}\${author.avatar}{%else%}<c:url value="/resources/images/default_avatar.png"/>{%/if%}" alt="">
                     </div>
                    <div class="comment-detail">
                        <div class="author mb16">\${author.userName}</div>
                        <div class="content mb16"><span style="color:blue">@\${reply.replyUserName} </span>\${reply.description}</div>
                        <div class="create-time">发表于\${DateUtils.getLocalTime(reply.createdTime)}</div>
                    </div>
                    <div class="operate-items">
                        <div class="operate-item inline-block reply" onclick="showReplyBox(\${comment.id},\${JSON.stringify(author)})">回复</div>
                        <div class="operate-item inline-block {%if userId != reply.userId%}none{%/if%}" onclick="deleteReply(\${reply.id})">删除</div>
                    </div>
                </div>
            {%/each%}
            </div>
        </div>
   {%/each%}
   <%--{%else%}--%>
        <%--暂无评论--%>
   {%/if%}
</script>

<script>
    var typeId = '${param.id}';
    var userId = '${user.userId}';

    var template = '<div class="mb16 reply-box mt16" style="margin-left: 65px">\n' +
        '            <textarea class="textarea-custom" placeholder="请输入评论..."></textarea>\n' +
        '            <button type="button" class="btn btn-primary" onclick="hideTextArea(this)">评论</button>\n' +
        '        </div>';

    function showReplyBox(id, author) {
        hideTextArea();
        $(template).appendTo(".comment[comment-id=" + id + "]");
        var el = $(".comment[comment-id=" + id + "] .reply-box");
        el.find(".textarea-custom").prop("placeholder","回复 "+author.userName);
        el.find("button").attr("onclick", "reply(" + id + "," + JSON.stringify(author) + ")");
    }

    function hideTextArea() {
        $(".reply-box").remove();
        $("#commentTextArea textarea").val("");
    }

    function addComment(id, author) {
        var description = $("#commentTextArea textarea").val();
        if (!description) {
            Dialog.error("不可为空");
            return;
        }
        var comment = {typeId: typeId, description: description};
        $.ajax({
            url: "${pageContext.request.contextPath}/user/comment/create",
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(comment),
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                } else {
                    Dialog.error("评论失败");
                }

            }
        });
        hideTextArea();
    }

    function initComment() {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/comment/list",
            dataType: "json",
            contentType: "application/json",
            data:{typeId: typeId, offset: 1},
            success: function (r) {
                if (r.code == 0) {
                    if (r.data) {
                        var data = r.data;
                        var totalCount = data.totalCount;
                        var comments = data.list;
                        $("#commentTmpl").tmpl({comments: comments}).appendTo("#commentList");
                    }
                } else {
                    Dialog.error("加载失败...")
                }
            }
        })
    }

    function reply(id, replyUser) {
        var description = $(".comment[comment-id=" + id + "] .reply-box textarea").val();
        if (!description) {
            Dialog.error("不要为空");
            return;
        }
        hideTextArea();
        var reply = {commentId: id, replyUserId: replyUser.id, replyUserName: replyUser.userName, description: description};
        $.ajax({
            url: "/user/reply/create",
            type: "post",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(reply),
            success: function (r) {
                if (r.code == 0) {
                    Dialog.success("回复成功");
                    location.reload();
                } else {
                    Dialog.error("回复失败");
                }
            }
        })
    }

    function  deleteComment(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/comment/delete",
            type: "post",
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            data: {id: id},
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                } else {
                    Dialog.error("删除失败");
                }
            }
        })
    }

    function  deleteReply(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/reply/delete",
            type: "post",
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            data: {id: id},
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                } else {
                    Dialog.error("删除失败");
                }
            }
        })
    }

</script>
