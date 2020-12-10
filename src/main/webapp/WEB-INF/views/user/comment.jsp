<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        background-color: #F2F6FC;
        border-radius: 4px;
        position: relative;
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
        bottom: 0px;
        right: 0px;
    }
</style>

<script id="commentTmpl"  type="text/x-jquery-tmpl">
{%each comments%}
        <div class="comment mt16 mb16" comment-id="\${comment.id}">
            <div class="comment-item flex">
                <div class="user-img mr16">
                    <img src="\${author.avator}" alt="">
                </div>
                <div class="comment-detail">
                    <div class="author mb16">\${author.userName}</div>
                    <div class="content mb16">\${comment.description}</div>
                    <div class="create-time">发表于\${comment.createdTime}</div>
                </div>
                <div class="operate-items">
                    <div class="operate-item inline-block">回复</div>
                    <div class="operate-item inline-block" onclick="deleteComment('\${comment.id}')">删除</div>
                </div>
            </div>
        </div>
   {%/each%}
</script>

<script>
    var typeId = '${param.id}';

    var template = '<div class="mb16 comment-box mt16" style="margin-left: 65px">\n' +
        '            <textarea class="textarea-custom" placeholder="请输入评论..."></textarea>\n' +
        '            <button type="button" class="btn btn-primary" onclick="hideTextArea(this)">评论</button>\n' +
        '        </div>';

    $(".reply").on('click',function (e) {
        $(template).appendTo(".comment[comment-id='1']");
    });

    function hideTextArea(e) {
        $(e).parent().remove();
    }

    function addComment(e) {

    }

    function initComment() {
        $.ajax({
            url: "user/comment/list",
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
                    alert("加载失败...")
                }
            }
        })
    }

    function reply(e, commentId, replyUserId, replyUserName) {
        $.ajax({
            url: "/user/reply/create",
            contentType: "applicaiton/json",
            dataType: "json",
            data: {commentId: commentId, replyUserId: replyUserId, replyUserName: replyUserName,
                userId: userId, description: "gjo"},
            success: function (r) {
                if (r.code == 0) {
                    alert("回复成功");
                    location.reload();
                } else {
                    alert("回复失败");
                }
            }
        })
    }

    function  deleteComment(id) {
        $.ajax({
            url: "user/comment/delete",
            dataType: "json",
            contentType: "application/json",
            data: {id: id},
            success: function (r) {
                if (r.code == 0) {
                    location.reload();
                } else {
                    alert("删除失败");
                }
            }
        })
    }
</script>
