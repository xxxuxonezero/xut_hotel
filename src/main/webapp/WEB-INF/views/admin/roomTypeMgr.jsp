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
<div class="admin-page flex">
    <nav:adminNav currentMenu="房型管理"></nav:adminNav>
    <div class="right-menu inline-block" style="margin-top: 100px">
        <div class="feature-btn">
            <button type="button" class="btn btn-primary" onclick="batchDelete()">批量删除</button>
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#roomTypeModal" onclick='$("#updateBtn").attr("onclick", "addRoomType(" + TYPE.ADD + ")");'>添加房型</button>
        </div>
        <div class="modal fade" id="roomTypeModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">添加房型</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form role="form" id="roomTypeForm" method="post">
                            <div class="form-group">
                                <label>房型</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入房型" name="type">
                            </div>
                            <div class="form-group">
                                <label>价格</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入价格" name="price">
                            </div>
                            <div class="form-group">
                                <label>入住人数</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入入住人数" name="maxPeople">
                            </div>
                            <div class="form-group">
                                <label>面积</label>
                                <input type="text" class="form-control"
                                       placeholder="请输入面积" name="size">
                            </div>
                            <div class="form-group">
                                <label>是否有餐食</label>
                                <input type="radio" name="hasFood" value="0">无
                                <input type="radio"  name="hasFood" value="1">有
                            </div>
                            <div class="form-group">
                                <label>床</label>
                                <input type="text" class="form-control" name="bed">
                            </div>
                            <div class="form-group">
                                <label>所在楼层</label>
                                <input type="text" class="form-control" name="floor">
                            </div>
                            <div class="form-group">
                                <label>是否有窗</label>
                                <input type="radio" name="hasWindow" value="0">无
                                <input type="radio"  name="hasWindow" value="1">有
                            </div>
                            <div class="form-group">
                                <label>吸烟</label>
                                <input type="radio" name="smoke" value="0">禁烟
                                <input type="radio"  name="smoke" value="1">不禁烟
                            </div>
                        </form>
                        <button type="button" class="btn btn-primary" id="uploadImage">上传图片</button>
                        <div id="imageTable"></div>
                        <div id="fileInputContainer"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" onclick="addRoomType(TYPE.ADD)" id="updateBtn">添加</button>
                    </div>
                </div>
            </div>
        </div>
        <table class="table table-hover" id="roomTypeTable">
            <thead>
            <tr>
                <th><input type="checkbox" onclick="selectAll(this)"></th>
                <th>房型</th>
                <th>价格</th>
                <th>入住人数</th>
                <th>面积</th>
                <th>是否有餐食</th>
                <th>床</th>
                <th>所在楼层</th>
                <th>是否有窗</th>
                <th>吸烟</th>
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
<script id="roomTypeList" type="text/x-jquery-tmpl">
        {%each data%}
            <tr id="\${id}">
                <td><input type="checkbox"></td>
                <td>\${type}</td>
                <td>\${price}</td>
                <td>\${maxPeople}</td>
                <td>\${size}</td>
                <td>{%if hasFood == 0%}否{%elif hasFood == 1%}是{%/if%}</td>
                <td>\${bed}</td>
                <td>\${floor}</td>
                <td>{%if hasWindow == 0%}否{%elif hasWindow == 1%}是{%/if%}</td>
                <td>{%if smoke == 0%}禁烟{%elif smoke == 1%}不禁烟{%/if%}</td>
                <td>
                    <a onclick="editRoomType(\${id})" data-toggle="modal" data-target="#roomTypeModal">编辑</a>
                    <a onclick="deleteRoomType(\${id})">删除</a>
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
        $("#roomTypeModal").find("input[type != 'radio']").val("");
        $("#roomTypeModal").find("input[type = 'radio']:checked").prop("checked", false);
        $("#roomTypeModal").find("label.error").remove();
        $("#imageTable").empty();
        uploader.splice(0, uploader.files.length);
        imgItems = [];
    }
    function addRoomType(type, id) {
        var data = formObject($("#roomTypeForm").serializeArray());
        if ($("#roomTypeForm").valid()) {
            if (checkTypeName(data.type) || type == TYPE.EDIT) {
                data.imgList = [];
                imgItems.forEach(function (item) {
                    data.imgList.push(item.url);
                });
                if (type == TYPE.ADD) {
                    $.ajax({
                        url: "addRoomType",
                        type: "post",
                        data: JSON.stringify(data),
                        dataType: "json",
                        contentType: "application/json",
                        success: function (r){
                            if (r.code == 0) {
                                // var obj = data;
                                // obj.id = r.data;
                                // $("#roomTypeList").tmpl({data: [obj]}).appendTo("#roomTypeTable");
                                location.reload();
                            }
                        }
                    });
                }
                else if (type == TYPE.EDIT) {
                    data.id = id;
                    $.ajax({
                        url: "editRoomType",
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

            } else {
                Dialog.error("房型不可重复");
            }
        }

    }

    (function () {
        $.ajax({
            url: "roomTypeList",
            type: "get",
            data: {offset:1},
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r.code == 0) {
                    $("#roomTypeTable tbody").remove();
                    var data = [];
                    data = r.data;
                    $("#roomTypeList").tmpl({data: data}).appendTo("#roomTypeTable");
                }
            }
        });

        $('body').on('hidden.bs.modal', '.modal', function () {
            clearModal();
        });

        $("#roomTypeForm").validate({
            rules: {
                type:"required",
                price: {
                    required: true,
                    price: true
                },
                maxPeople: {
                    required: true,
                    digits: true
                }
            }
        })
    })();

    function checkTypeName(type) {
        var res;
        $.ajax({
            url: "checkRoomType",
            type: "get",
            contentType: "application/json",
            dataType: "json",
            data: {type: type},
            async: false,
            success: function (r) {
               res = r;
            }
        });
        return res;
    }

    function editRoomType(id) {
        $("#updateBtn").attr("onclick", "addRoomType(" + TYPE.EDIT + ","+id+")");
        $.ajax({
            url: "editRoomType",
            type: "get",
            contentType: "application/json",
            dataType: "json",
            data: {id:id},
            success: function (r) {
                if (r.code == 0) {
                    renderRoomType(r.data);
                }
            }
        })
    }

    function renderRoomType(data) {
        clearModal();
        Object.getOwnPropertyNames(data).forEach(function (name) {
            fillInput(name, data[name]);
        });
        imgItems = [];
        if (data.imgList) {
            data.imgList.forEach(function (item, index) {
                var img = {
                    url: item,
                    id: "img_" + index
                };
                imgItems.push(img);
                $("#imgItemTmpl").tmpl(img).appendTo("#imageTable");
            })
        }
    }

    function fillInput(name, value) {
        if (value != undefined && value != null) {
            var el =  $("#roomTypeForm").find("input[name=" + name + "]");
            if (el.prop("type") == "radio") {
                for (var i = 0; i < el.length; i++){
                    if ($(el[i]).prop("value") == value) {
                        $(el[i]).prop("checked", true);
                        return;
                    }
                }
            }

            el.val(value);
        }
    }

    function selectAll(e) {
        if (e.checked) {
            $("#roomTypeTable tbody").find("input[type='checkbox']").prop("checked", true);
        } else {
            $("#roomTypeTable tbody").find("input[type='checkbox']").prop("checked", false);
        }
    }

    function deleteRoomType(id) {
        if (id) {
            deleteAjax([id]);
        }
    }

    function batchDelete() {
        var els = $("#roomTypeTable tbody").find("input[type='checkbox']");
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
        var data = {ids: ids};
        $.ajax({
            url: "deleteRoomType",
            type: "post",
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            data:{ids:ids} ,
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
