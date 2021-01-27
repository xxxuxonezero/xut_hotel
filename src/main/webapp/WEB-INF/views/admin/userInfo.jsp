<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../common.jsp"></jsp:include>
    <link rel="stylesheet" href="<c:url value="/resources/css/admin.css"/>">
</head>
<style>
    #imageTable {
        display: flex;
        align-items: center;
    }
</style>
<body>
<div class="admin-page flex">
    <div class="left-menu inline-block">
        <ul class="nav nav-pills nav-stacked admin-menu inline-block">
            <li><a onclick="window.location.href = 'roomType'">房型管理</a></li>
            <li><a href="order">订单管理</a></li>
            <li><a onclick="window.location.href = 'room'">客房信息</a></li>
            <li class="active"><a onclick="window.location.href = 'userInfo'">用户信息</a></li>
            <li><a href="#">退出</a></li>
        </ul>
    </div>
    <div class="right-menu inline-block" style="margin-top: 100px">
        <div class="feature-btn">
            <button type="button" class="btn btn-primary" onclick="batchDelete()">批量删除</button>
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#roomModal" onclick='$("#updateBtn").attr("onclick", "addRoom(" + TYPE.ADD + ")");'>添加客房</button>
        </div>
        <div class="modal fade" id="roomModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">添加客房</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form role="form" id="roomForm" method="post">
                            <div class="form-group">
                                <label>房号</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入房号" name="roomNumber">
                            </div>
                            <div class="form-group">
                                <label>房型</label>
                                <select class="form-control" name="typeId">
                                    <c:forEach items="${types}" var="item">
                                        <option value="${item.id}">${item.type}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>所在楼层</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入楼层" name="floorNum">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" onclick="addRoom(TYPE.ADD)" id="updateBtn">添加</button>
                    </div>
                </div>
            </div>
        </div>
        <table class="table table-hover" id="roomTable">
            <thead>
            <tr>
                <th><input type="checkbox" onclick="selectAll(this)"></th>
                <th>客房号</th>
                <th>房型</th>
                <th>床</th>
                <th>可住人数</th>
                <th>楼层</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td colspan="7">正在加载中...</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
<jsp:include page="/WEB-INF/common/uploadImage.jsp"></jsp:include>
<script id="roomList" type="text/x-jquery-tmpl">
        {%each data%}
            <tr id="\${id}">
                <td><input type="checkbox"></td>
                <td>\${roomNumber}</td>
                <td>\${type}</td>
                <td>\${bed}</td>
                <td>\${maxPeople}</td>
                <td>\${floorNum}</td>
                <td>\${size}</td>
                <td>
                    <a onclick="editRoom(\${id})" data-toggle="modal" data-target="#roomModal">编辑</a>
                    <a onclick="deleteRoom(\${id})">删除</a>
                </td>
            </tr>
        {%/each%}
</script>

<script>
    var TYPE = {
        ADD: 1,
        EDIT: 2
    };
    function clearModal() {
        $("#roomModal").find("input[type != 'radio']").val("");
        $("#roomModal").find("input[type = 'radio']:checked").prop("checked", false);
        $("#roomModal").find("label.error").remove();
    }

    (function () {
        $.ajax({
            url: "roomList",
            type: "get",
            data: {offset:1},
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r.code == 0) {
                    $("#roomTable tbody").remove();
                    var data = [];
                    data = r.data;
                    $("#roomList").tmpl({data: data}).appendTo("#roomTable");
                }
            }
        });

        $('body').on('hidden.bs.modal', '.modal', function () {
            clearModal();
        });

        $("#roomForm").validate({
            rules: {
                typeId:"required",
                roomNumber: "required",
                floorNum: "required"
            }
        })
    })();


    function renderRoom(data) {
        clearModal();
        Object.getOwnPropertyNames(data).forEach(function (name) {
            fillInput(name, data[name]);
        });
        imgItems = [];
    }

    function fillInput(name, value) {
        if (value != undefined && value != null) {
            var el =  $("#roomForm").find("input[name=" + name + "]");
            if (el.length == 0) {
                el = $("#roomForm").find("select[name=" + name + "]");
            }
            if (el.length == 0) {
                return;
            }
            el.val(value);
        }
    }

</script>
</html>
