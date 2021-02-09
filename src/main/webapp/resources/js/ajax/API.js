(function (root, $) {
    var empty = function () {
    };

    function API(options) {
        this.data = options.data || {};
        this.callback = options.callback || empty;
        this.errorCallback = options.errorCallback || empty;
        this.method = options.method || 'GET';
        this.url = options.url || "";
    }

    API.prototype = {
        sendFormData: function () {
            var self = this;
            $.ajax({
                url: self.url,
                type: self.method,
                data: self.data,
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                success: self.callback,
                error: self.errorCallback
            });
        },

        sendJSONData: function () {
            var self = this;
            $.ajax({
                url: self.url,
                type: self.method,
                data: self.data,
                dataType: "json",
                contentType: "application/json",
                success: self.callback,
                error: self.errorCallback
            });
        }
    }

    root.API = API;
})(window, jQuery);