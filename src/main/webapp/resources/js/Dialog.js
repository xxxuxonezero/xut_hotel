var Dialog = (function (window, $) {
    var timeout = 2000;
    function openDialog() {
        var dialog = document.createElement("div");
        $(dialog).css("position", "absolute");
        $(dialog).css("top", "100px");
        $(dialog).css("right", "100px");
        return dialog;
    }
    function success(msg) {
        var dialog = openDialog();
        $(dialog).text(msg);
        $(dialog).addClass("ui-dialog-success");
        document.body.append(dialog);
        setTimeout(() => {
            $(dialog).remove();
        }, timeout);
    }

    function error(msg) {
        var dialog = openDialog();
        $(dialog).text(msg);
        $(dialog).addClass("ui-dialog-error");
        document.body.append(dialog);
        setTimeout(() => {
            $(dialog).remove();
        }, timeout);
    }
    return {
        success: success,
        error: error
    }
})(window, jQuery);