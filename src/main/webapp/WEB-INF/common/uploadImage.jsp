<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .img-show {
        display: inline-flex;
        width: 120px;
        height: 120px;
        justify-content: center;
        align-items: center;
        position: relative;
    }
    .img-show img {
        max-height: 100%;
        max-width: 100%;
    }
    .mask {
        position: absolute;
        top: 0;
        left: 0;
        width: 120px;
        height: 120px;
        background: rgba(119,136,153,.15);
    }
</style>
<script src="<c:url value="/resources/js/plupload.full.min.js"/> "></script>
<script src="<c:url value="/resources/js/qiniu.min.js"/> "></script>
<script src="<c:url value="/resources/js/moxie.js"/> "></script>
<script src="<c:url value="/resources/js/plupload.dev.js"/> "></script>

<script id="imgItemTmpl" type="text/x-jquery-tmpl">
    <div class="img-show" id="\${id}" onclick="removeItem('\${id}')" onmouseenter="showMask(this)" onmouseleave="hideMask(this)">
        <img src="\${url}">
        <div class="mask none"></div>
    </div>
</script>
<script type="text/javascript">
    var uploader = Qiniu.uploader(getUploaderConfig());
    var imgItems = [];
    var SUFFIX = "xut_";
    var DOMAIN = "http://xuxuxuonezero.top/";

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
            multi_selection: true,
            withCredentials: true,
            init: {
                "FilesAdded":function(up, files) {
                    var limit = limit ? limit : 9;
                    var isCountValid = up.files.length <= limit;
                    if (!isCountValid) {
                        up.splice(up.files.length - files.length, files.length);
                        return;
                    }
                    plupload.each(files, function(file) {
                        // file.isRendered = isCountValid ? false : true;
                        var reader = new FileReader();
                        reader.onload = function (e) {
                            var item = {
                                id: file.id,
                                url: e.target.result
                            };
                            $("#imgItemTmpl").tmpl(item).appendTo("#imageTable");
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
                    imgItems.push(item);
                    $("#" + file.id + " img").attr("src", item.url);
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

    function removeItem(id) {
        if (uploader.getFile(id)) {
            uploader.removeFile(uploader.getFile(id));
        }
        for (var i = 0; i < imgItems.length; i++) {
            if (imgItems[i].id == id) {
                imgItems.splice(i, 1);
                break;
            }
        }
        $("#" + id).remove();
    }

    function showMask(e) {
        $(e).find(".mask").removeClass("none");
    }
    function hideMask(e) {
        $(e).find(".mask").addClass("none");
    }
</script>