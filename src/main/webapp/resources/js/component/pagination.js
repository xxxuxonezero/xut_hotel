(function (root, $) {

    var WRAPPER = '<ul class="pagination pagination-lg"></ul>';
    var PRE = '<li class="previous"><a href="#">Previous</a></li>';
    var NEXT = '<li class="next"><a href="#">Next</a></li>';
    var ITEM = '<li><a href="#"></a></li>'

    function Pagination(options) {
        this.total = options.total;
        this.pageSize = options.pageSize ? options.pageSize : 10;
        this.offset = options.offset ? options.offset : 1;
        this.url = options.url;
        this.container = options.container;

        this.init(options);
    }

    Pagination.prototype = {
        init: function (options) {
            var page = Math.ceil(this.total / this.pageSize);
            this.$view = $(WRAPPER);
            $(this.container).empty();
            $(this.container).append(this.$view);
            for (var i = 1; i <= page; i++) {
                var $item = $(ITEM);
                $item.find("a").text(i);
                $item.find("a").prop("href", this.url + "?offset=" + i);
                if (i == this.offset) {
                    $item.addClass("active");
                }
                $item.appendTo(this.$view);
            }
        },
        next: function () {

        },
        pre: function () {

        },
        initEvent: function () {

        }
    }

    root.Pagination = Pagination;
})(window, jQuery);