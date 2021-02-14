window.DateUtils = (function () {
    function getLocalTime(timestamp) {
        return new Date(parseInt(timestamp)).toLocaleString().replace(/:\d{1,2}$/, ' ');
    }

    function getLocalDate(timestamp, breakWord) {
        var date = new Date(parseInt(timestamp));
        var str = "";
        str += date.getFullYear() + "/";
        var month = date.getMonth() + 1;
        str += (month < 10 ? ("0" + month) : month) + "/";
        str += date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        if (breakWord) {
            str = str.replaceAll("/", breakWord);
        }
        return str;
    }

    function compare(date1, date2) {
        if (date1 && date2) {
            var t1 = new Date(date1).getTime();
            var t2 = new Date(date2).getTime();
            if (t1 < t2) {
                return -1;
            } else if (t1 == t2) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    return {
        getLocalTime: getLocalTime,
        getDateStr: getLocalDate,
        compare: compare
    }
})();