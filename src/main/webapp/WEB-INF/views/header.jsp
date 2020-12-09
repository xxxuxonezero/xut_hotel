<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="common.jsp"></jsp:include>
<div id="header">
    <img src="<c:url value="/resources/images/logo.jpg"/>" class="logo">
    <div class="items">
        <div class="header-item inline-block">首页</div>
        <div class="header-item inline-block">房间订购</div>
    </div>
    <div class="login-item">
        <div class="header-item inline-block" data-toggle="modal" data-target="#loginModal">登录</div>
        <div class="header-item inline-block" data-toggle="modal" data-target="#registerModal">注册</div>
        <!--            <div class="user-img">-->
        <!--                <img src="logo.jpg" alt="">-->
        <!--                <ul class="user-items">-->
        <!--                    <li class="user-item">个人信息</li>-->
        <!--                </ul>-->
        <!--            </div>-->
    </div>
</div>
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">登录</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form role="form">
                    <div class="form-group">
                        <label>用户名</label>
                        <input type="text" class="form-control"
                               placeholder="请输入用户名" name="identificationId">
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input type="text" class="form-control"
                               placeholder="请输入密码" name="password">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary">登录</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">注册</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form role="form" id="registerForm">
                    <div class="form-group">
                        <label>昵称</label>
                        <input type="text" class="form-control"
                               placeholder="请输入昵称" name="username">
                    </div>
                    <div class="form-group">
                        <label>真名</label>
                        <input type="text" class="form-control"
                               placeholder="请输入真名" name="realname">
                    </div>
                    <div class="form-group">
                        <label>身份证</label>
                        <input type="text" class="form-control"
                               placeholder="请输入身份证" name="identificationId">
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input type="text" class="form-control"
                               placeholder="请输入密码" name="password">
                    </div>
                    <div class="form-group">
                        <label>手机号</label>
                        <input type="text" class="form-control"
                               placeholder="请输入手机号" name="phone">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary">注册</button>
            </div>
        </div>
    </div>
</div>

<script>
    $().ready(function () {
        $("#registerForm").validate({
            rules: {
                identificationId: {
                    required:true,
                    identificationId: true
                },
                password: {
                    required: true,
                    rangelength: [8,12]
                },
                username: "required",
                realname: "required",
                phone: {
                    required: true,
                    phone: true
                }
            }
        })
    });
</script>