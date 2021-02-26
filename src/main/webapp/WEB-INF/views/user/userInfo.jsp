<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<c:url value="/resources/css/user-info.css"/>"/>
<style>
    #uploadImage {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 200px;
        height: 200px;
        border: 1px dotted #9d9d9d;
    }
    #uploadImage img {
        max-height: 100%;
        max-width: 100%;
    }
</style>

<jsp:include page="../header.jsp"></jsp:include>
<div class="container" id="userInfoContainer">
    <nav:nav currentMenu="个人信息"/>
    <div class="right-menu">
        <div class="nav-title mb16">个人信息</div>
        <div class="info-items">
            <form action="" class="user-infos">
                <div class="info-item">
                    <label class="mr8">我的昵称</label>
                    <input type="text" class="xut-input" name="userName">
                </div>
                <div class="info-item">
                    <label class="mr8">我的真名</label>
                    <input type="text" class="xut-input" name="realName" checked>
                </div>
                <div class="info-item">
                    <label class="mr8">我的性别</label>
                    <input type="radio" name="sex" value="0">男
                    <input type="radio" name="sex" value="1">女
                </div>
                <div class="info-item">
                    <label class="mr8">我的简介</label>
                    <textarea cols="30" rows="10" name="introduction"></textarea>
                </div>
                <div class="info-item">
                    <label class="mr8">身&nbsp;&nbsp;份&nbsp;&nbsp;证</label>
                    <input type="text" class="xut-input" disabled name="identificationId">
                </div>
                <div class="info-item">
                    <label class="mr8">我的手机</label>
                    <input type="tel" class="xut-input" name="phone" disabled>
                </div>
                <input type="hidden" name="avatar">
            </form>
            <label class="mr8">我的头像</label><div class="inline-block" id="uploadImage"><img src="" alt=""></div>
            <div id="fileInputContainer" class="none"></div>
            <button class="btn btn-primary mt16" onclick="update()">更新</button>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script>
    (function () {
        getUserInfo();
    })();

    function getUserInfo() {
        $.ajax({
            url: '${pageContext.request.contextPath}/account/getUserInfo',
            type: 'GET',
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r && r.code == 0 && r.data) {
                    var data = r.data;
                    $("input[name='userName']").val(data.userName);
                    $("input[name='realName']").val(data.realName);
                    $("input[name='sex'][value=" + data.sex + "]").prop("checked", true);
                    $("textarea[name='introduction']").val(data.introduction);
                    $("input[name='phone']").val(data.phone);
                    $("input[name='identificationId']").val(data.identificationId);
                    $("#uploadImage img").prop("src", data.avatar);
                } else {
                    Dialog.error("获取信息失败")
                }
            },
            error: function () {
                Dialog.error("获取信息失败")
            }
        });
    }

    function update() {
        var data = formObject($("form.user-infos").serializeArray());
        $.ajax({
            url: "${pageContext.request.contextPath}/account/update",
            contentType:"application/json",
            dataType: "json",
            data: JSON.stringify(data),
            type: 'POST',
            success: function (r) {
                if (r.code == 0) {
                    Dialog.success("更新成功");
                } else {
                    Dialog.error("更新失败");
                }
            },
            error: function () {
                Dialog.error("更新失败");
            }
        })
    }
</script>

<script>
    var uploader = Qiniu.uploader(getUploaderConfig(null, null, 1));
    var imgItems = [];
    var SUFFIX = "xut_";
    var DOMAIN = "http://xuxuxuonezero.top/";
    var $avatar = $("#uploadImage img");

    function getUploaderConfig(button, container, limit) {
        var config = {
            runtimes: 'html5,flash,html4',
            browse_button: button ? button : "uploadImage",
            max_retries: 0,
            container: container ? container : 'fileInputContainer',
            uptoken_url: "${pageContext.request.contextPath}/getToken",
            get_new_uptoken: false,
            domain: "xuxuxuonezero.top",
            max_file_size: '1000mb',
            chunk_size: '4mb',
            auto_start: true,
            disable_statistics_report: true,
            multi_selection: false,
            withCredentials: true,
            init: {
                "FilesAdded":function(up, files) {
                    plupload.each(files, function(file) {
                        var reader = new FileReader();
                        reader.onload = function (e) {
                            var item = {
                                id: file.id,
                                url: e.target.result
                            };
                            $avatar.prop("src", item.url);
                        };
                        reader.readAsDataURL(file.getNative());
                    });
                },
                'BeforeUpload' : function(up, file) {
                },
                'UploadProgress' : function(up, file) {
                },
                'UploadComplete' : function() {
                },
                'FileUploaded' : function(up, file, info) {
                    info = JSON.parse(info);
                    var item = {
                        url: DOMAIN + info.key,
                        id: file.id
                    };
                    $avatar.prop("src", item.url);
                    $("form.user-infos").find("input[name='avatar']").val(item.url);
                },
                'Error' : function(up, err, errTip) {
                },
                'Key': function(up, file) {
                    var timestamp = Date.parse(new Date());
                    return SUFFIX + timestamp + file.name;
                }
            }
        };
        return config;
    }
</script>

