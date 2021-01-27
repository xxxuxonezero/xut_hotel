var UserUtils = (function (window, $) {
    function getUserInfo() {
        $.ajax({
            url: url,
            type: 'GET',
            dataType: "json",
            contentType: "application/json",
            success: function (r) {
                if (r && r.code == 0 && r.data) {

                } else {
                    Dialog.error("获取信息失败")
                }
            },
            error: function () {
                Dialog.error("获取信息失败")
            }
        });
    }
})(window, jQuery);