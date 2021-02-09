<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<c:url value="/resources/css/user-info.css"/>"/>

<jsp:include page="../header.jsp"></jsp:include>
<div class="container" id="resetPasswordContainer">
    <nav:nav currentMenu="修改密码"/>
    <div class="right-menu">
        <div class="nav-title mb16">修改密码</div>
        <div class="info-items" id="resetPwdForm">
            <div class="info-item">
                <label class="mr8">新&nbsp;&nbsp;密&nbsp;&nbsp;码</label>
                <input type="password" class="xut-input pwd1" name="newPassword">
                <label class="error-tip none"></label>
            </div>
            <div class="info-item">
                <label class="mr8">重复密码</label>
                <input type="password" class="xut-input pwd2" name="repeatNewPassword">
                <label class="error-tip none"></label>
            </div>
            <button class="btn btn-primary reset-btn" disabled onclick="resetPwd()">重置密码</button>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>

<script>
    var $form = $("#resetPwdForm");


    $(".pwd1").on("input", function (e) {
        var val = $(this).val();
        var $errorTip = $(this).parent().find(".error-tip");
        if (val.length < 8 || val.length > 12) {
            $errorTip.text("请输入正确的密码");
            $errorTip.show();
            $(".reset-btn").prop("disabled", true);
            return;
        }
        $errorTip.hide();
    });

    $(".pwd2").on("input", function () {
        var val1 = $(".pwd1").val();
        var val2 = $(this).val();
        var $errorTip = $(this).parent().find(".error-tip");
        if (val2.length < 8 || val2.length > 12) {
            $errorTip.text("请输入正确的密码");
            $errorTip.show();
            $(".reset-btn").prop("disabled", true);
            return;
        }
        if (val1 !== val2) {
            $errorTip.text("请输入与之前相同的密码");
            $errorTip.show();
            $(".reset-btn").prop("disabled", true);
            return;
        }
        $errorTip.hide();
        $(".reset-btn").prop("disabled", false);
    });

    function resetPwd() {
        var newPassword1 = $("input[name='newPassword']").val();
        var newPassword2 = $("input[name='repeatNewPassword']").val();
        if (!(newPassword1 === newPassword2 && newPassword1.length >= 8 && newPassword1.length <= 12)) {
            Dialog.error("重置失败");
            return;
        }
        var api = new API({
            url: "${pageContext.request.contextPath}/account/resetPassword",
            data: {password: newPassword1},
            method: 'POST',
            callback: function () {
                Dialog.success("更新成功");
            },
            errorCallback: function () {
                Dialog.error("更新失败");
            }
        });

        api.sendFormData();
    }
</script>

