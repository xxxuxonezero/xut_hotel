window.DateUtils = (function () {
    function getLocalTime(timestamp) {
        return new Date(parseInt(timestamp)).toLocaleString().replace(/:\d{1,2}$/, ' ');
    }

    return {
        getLocalTime: getLocalTime
    }
})();