<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>用户管理</title>
    <jsp:include page="../common.jsp"></jsp:include>
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
    <nav:adminNav currentMenu="用户信息"></nav:adminNav>
    <div class="right-menu inline-block" style="margin-top: 100px">
        <div class="feature-btn">
            <button type="button" class="btn btn-primary" onclick="batchDelete()">批量删除</button>
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#userModal" onclick='addNewUser()'>添加用户</button>
        </div>
        <div class="modal fade" id="userModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">添加用户</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form role="form" id="userForm" method="post">
                            <div class="form-group">
                                <label>用户名</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入用户名" name="userName">
                            </div>
                            <div class="form-group">
                                <label>真名</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入真名" name="realName">
                            </div>
                            <div class="form-group">
                                <label>权限</label>
                                <select name="type" class="select">
                                    <option value="0">管理员</option>
                                    <option value="1">用户</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>性别</label>
                                <input type="radio" name="sex" value="0">男
                                <input type="radio" name="sex" value="1">女
                            </div>
                            <div class="form-group">
                                <label>简介</label>
                                <textarea type="text" class="form-control"
                                          placeholder="请输入简介" name="introduction"></textarea>
                            </div>
                            <div class="form-group">
                                <label>身份证</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入身份证" name="identificationId" disabled>
                            </div>
                            <div class="form-group">
                                <label>手机</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入手机" name="phone">
                            </div>
                            <div class="form-group none password-info">
                                <label>密码</label>
                                <input type="password" class="form-control"
                                       placeholder="请输入密码" name="password">
                            </div>
                            <input type="text" hidden name="avatar">
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" onclick="addUser(TYPE.ADD)" id="updateBtn">添加</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="mt16">
            <select class="select" id="typeSel" onchange="init()">
                <option value="" checked>按用户类型过滤</option>
                <option value="0">管理员</option>
                <option value="1">用户</option>
            </select>
            <select class="select" id="oldSel" onchange="init()">
                <option value="" checked>按新老用户过滤</option>
                <option value="0">新用户</option>
                <option value="1">老用户</option>
            </select>
        </div>

        <table class="table table-hover" id="userTable">
            <thead>
            <tr>
                <th><input type="checkbox" onclick="selectAll(this)"></th>
                <th>真名</th>
                <th>用户名</th>
                <th>身份证</th>
                <th>性别</th>
                <th>手机号</th>
                <th>权限</th>
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
<script id="userList" type="text/x-jquery-tmpl">
        {%each data%}
            <tr id="\${id}">
                <td><input type="checkbox"></td>
                <td>\${realName}</td>
                <td>\${userName}</td>
                <td>\${identificationId}</td>
                <td>{%if sex == 0%}男{%elif sex == 1%}女{%else%}未知{%/if%}</td>
                <td>\${phone}</td>
                <td>\${AUTH[type]}</td>
                <td>
                    <button class="btn btn-primary" onclick="editUser(\${id})" data-toggle="modal" data-target="#userModal">编辑</button>
                    <button class="btn btn-secondary" onclick="deleteUser(\${id})">删除</button>
                </td>
            </tr>
        {%/each%}
</script>

<script>
    var TYPE = {
        ADD: 1,
        EDIT: 2
    };

    var AUTH = {
        0: "管理员",
        1: "用户"
    };

    var offset = '${param.offset}' ? '${param.offset}' : 1;
    
    function clearModal() {
        $("#userModal").find("input[type != 'radio']").val("");
        $("#userModal").find("input[type = 'radio']:checked").prop("checked", false);
        $("#userModal").find("label.error").remove();
        $("#userModal").find("textarea").val("");
        $("#userModal").find("select").val("0");
    }

    (function () {
        init();

        $('body').on('hidden.bs.modal', '.modal', function () {
            clearModal();
        });

        $("#userForm").validate({
            rules: {
                identificationId: {
                    required:true,
                    identificationId: true
                },
                password: {
                    required: true,
                    rangelength: [8,12]
                },
                userName: "required",
                realName: "required",
                phone: {
                    required: true,
                    phone: true
                }
            }
        })
    })();

    function init() {
        var type = $("#typeSel").val();
        var old = $("#oldSel").val();
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/user/list?offset=" + offset,
            type: "get",
            data: {offset:offset, type: type, old: old},
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r.code == 0) {
                    $("#userTable tbody").remove();
                    var data = [];
                    data = r.data.list;
                    $("#userList").tmpl({data: data}).appendTo("#userTable");
                    var pagination = new Pagination({
                        total: r.data.totalCount,
                        url: '${pageContext.request.contextPath}/admin/user',
                        offset: offset,
                        container: "#paginationContainer"
                    });
                }
            }
        });
    }

    function addUser(type, id) {
        var data = formObject($("#userForm").serializeArray());
        if ($("#userForm").valid()) {
            if (type == TYPE.ADD) {
                $.ajax({
                    url: "addUser",
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
                    url: "editUser",
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

    function addNewUser() {
        $("#updateBtn").attr("onclick", "addUser(" + TYPE.ADD + ")");
        $("#userForm .password-info").removeClass("none");
        $("#userForm input[name='identificationId']").prop("disabled", false);
    }

    function editUser(id) {
        $("#userForm .password-info").addClass("none");
        $("#userForm input[name='identificationId']").prop("disabled", true);
        $("#updateBtn").attr("onclick", "addUser(" + TYPE.EDIT + ","+id+")");
        $.ajax({
            url: "editUser",
            type: "get",
            contentType: "application/json",
            dataType: "json",
            data: {id:id},
            success: function (r) {
                if (r.code == 0) {
                    renderUser(r.data);
                }
            }
        })
    }


    function renderUser(data) {
        clearModal();
        Object.getOwnPropertyNames(data).forEach(function (name) {
            fillInput(name, data[name]);
        });
        imgItems = [];
    }

    function fillInput(name, value) {
        if (value != undefined && value != null) {
            if ($("#userForm").find("input[name=" + name + "]").length != 0) {
                el = $("#userForm").find("input[name=" + name + "]");
                if (el.prop("type") == "radio") {
                    for (var i = 0; i < el.length; i++){
                        if ($(el[i]).prop("value") == value) {
                            $(el[i]).prop("checked", true);
                            return;
                        }
                    }
                }
            } else if ($("#userForm").find("textarea[name=" + name + "]").length != 0) {
                el = $("#userForm").find("textarea[name=" + name + "]");
            } else if ($("#userForm").find("select[name=" + name + "]").length != 0) {
                el = $("#userForm").find("select[name=" + name + "]");
            } else {
                return;
            }
            el.val(value);
        }
    }

    function selectAll(e) {
        if (e.checked) {
            $("#userTable tbody").find("input[type='checkbox']").prop("checked", true);
        } else {
            $("#userTable tbody").find("input[type='checkbox']").prop("checked", false);
        }
    }

    function deleteUser(id) {
        if (id) {
            deleteAjax([id]);
        }
    }

    function batchDelete() {
        var els = $("#userTable tbody").find("input[type='checkbox']");
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
            url: "delete",
            type: "post",
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            data: {ids: ids},
            traditional: true,
            success: function (r) {
                if (r.code == 0) {
                    Dialog.success("删除成功");
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
