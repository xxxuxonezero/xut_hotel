function formJson(arr) {
    var o = {};
    arr.forEach(function (item) {
        o[item.name] = item.value ? item.value : null;
    });
    return JSON.stringify(o);
}

function formObject(arr) {
    var o = {};
    arr.forEach(function (item) {
        o[item.name] = item.value ? item.value : null;
    });
    return o;
}