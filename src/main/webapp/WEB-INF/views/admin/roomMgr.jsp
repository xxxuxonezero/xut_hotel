<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags" %>
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
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="admin-page flex">
    <nav:adminNav currentMenu="客房信息"></nav:adminNav>
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
        <div id="paginationContainer"></div>
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
                    <button class="btn btn-primary" onclick="editRoom(\${id})" data-toggle="modal" data-target="#roomModal">编辑</button>
                    <button class="btn btn-secondary" onclick="deleteRoom(\${id})">删除</button>
                </td>
            </tr>
        {%/each%}
</script>

<script>
    var TYPE = {
        ADD: 1,
        EDIT: 2
    };

    var offset = '${param.offset}' ? '${param.offset}' : 1;

    function clearModal() {
        $("#roomModal").find("input[type != 'radio']").val("");
        $("#roomModal").find("input[type = 'radio']:checked").prop("checked", false);
        $("#roomModal").find("label.error").remove();
        $("#imageTable").empty();
        uploader.splice(0, uploader.files.length);
        imgItems = [];
    }
    function addRoom(type, id) {
        var data = formObject($("#roomForm").serializeArray());
        if ($("#roomForm").valid()) {
            if (type == TYPE.ADD) {
                $.ajax({
                    url: "addRoom",
                    type: "post",
                    data: JSON.stringify(data),
                    dataType: "json",
                    contentType: "application/json",
                    success: function (r) {
                        if (r.code == 0) {
                            location.reload();
                        }
                    }
                });
            }
            else if (type == TYPE.EDIT) {
                data.id = id;
                $.ajax({
                    url: "editRoom",
                    type: "post",
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(data),
                    success: function (r) {
                        if (r.code == 0) {
                            location.reload();
                        } else {
                            Dialog.error("更新失败...")
                        }
                    },
                    error: function (err) {
                        Dialog.error("更新失败...");
                    }
                })
            }
        }

    }

    (function () {
        $.ajax({
            url: "roomList?offset=" + offset,
            type: "get",
            data: {offset:1},
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r.code == 0) {
                    $("#roomTable tbody").remove();
                    var data = [];
                    data = r.data.list;
                    $("#roomList").tmpl({data: data}).appendTo("#roomTable");
                    var pagination = new Pagination({
                        total: r.data.totalCount,
                        url: '${pageContext.request.contextPath}/admin/room',
                        offset: offset,
                        container: "#paginationContainer"
                    });
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

    function editRoom(id) {
        $("#updateBtn").attr("onclick", "addRoom(" + TYPE.EDIT + ","+id+")");
        $.ajax({
            url: "editRoom",
            type: "get",
            contentType: "application/json",
            dataType: "json",
            data: {id:id},
            success: function (r) {
                if (r.code == 0) {
                    renderRoom(r.data);
                }
            }
        })
    }

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

    function selectAll(e) {
        if (e.checked) {
            $("#roomTable tbody").find("input[type='checkbox']").prop("checked", true);
        } else {
            $("#roomTable tbody").find("input[type='checkbox']").prop("checked", false);
        }
    }

    function deleteRoom(id) {
        if (id) {
            deleteAjax([id]);
        }
    }

    function batchDelete() {
        var els = $("#roomTable tbody").find("input[type='checkbox']");
        var ids = [];
        for (var i = 0; i < els.length; i++) {
            if ($(els[i]).prop("checked")) {
                var id = $(els[i]).parents("tr").prop("id");
                ids.push(id);
            }
        }
        if (ids.length > 0) {
            deleteAjax(ids);
        }
    }

    function deleteAjax(ids) {
        $.ajax({
            url: "deleteRoom",
            type: "post",
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            data: {ids: ids},
            traditional: true,
            success: function (r) {
                if (r.code == 0) {
                    Dialog.error("删除成功");
                } else {
                    Dialog.error("删除失败");
                }
                location.reload();
            },
            error: function (err) {
                Dialog.error("删除失败");
            }
        })
    }

</script>
</html>
