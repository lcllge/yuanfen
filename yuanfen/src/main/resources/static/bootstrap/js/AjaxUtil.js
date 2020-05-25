// 基于layer的Ajax封装的数据层工具类

function applyFor(url, params, sucFun, failFun, errFun) {
    var index = layer.load();
    $.ajax({
        url: url,
        type: 'post',
        data: params,
        success: function (success) {
            layer.close(index);
            if (success.code + "" === "200") {
                if (typeof sucFun === "function") {
                    sucFun(success.data);
                }
            } else {
                if (typeof failFun === "function") {
                    failFun(success.data, success.code);
                } else {
                    layer.msg(success.data, {icon: 7});
                }
            }
        }, error: function () {
            layer.close(index);
            if (typeof errFun === "function") {
                errFun();
            } else {
                layer.msg("系统错误", {icon: 7});
            }
        }
    });
}

function applySync(url, params, sucFun, failFun, errFun) {
    let index = layer.load();
    $.ajax({
        url: url,
        type: 'post',
        data: params,
        async: false,
        success: function (success) {
            layer.close(index);
            if (success.code + "" === "200") {
                if (typeof sucFun === "function") {
                    sucFun(success.data);
                }
            } else {
                if (typeof failFun === "function") {
                    failFun(success.data);
                } else {
                    layer.msg(success.data, {icon: 7});
                }
            }
        }, error: function () {
            layer.close(index);
            if (typeof errFun === "function") {
                errFun();
            } else {
                layer.msg("系统错误", {icon: 7});
            }
        }
    });
}


function AjaxPost(Url, JsonData, LodingFun, ReturnFun) {
    $.ajax({
        type: "post",
        url: Url,
        data: JsonData,
        dataType: 'json',
        async: 'false',
        beforeSend: LodingFun,
        error: function () {
            AjaxError({"Status": "Erro", "Erro": "500"});
        },
        success: ReturnFun
    });
}

function ajaxPost(Url, JsonData, LodingFun, ReturnFun, FailFun) {
    $.ajax({
        type: "post",
        url: Url,
        data: JsonData,
        dataType: 'json',
        async: true,
        beforeSend: LodingFun,
        error: FailFun,
        success: ReturnFun
    });
}

function ajaxSyncPost(Url, JsonData, LodingFun, ReturnFun, FailFun) {
    $.ajax({
        type: "post",
        url: Url,
        data: JsonData,
        dataType: 'json',
        async: false,
        beforeSend: LodingFun,
        error: FailFun,
        success: ReturnFun
    });
}


//Ajax 错误返回处理
function AjaxError(e) {
    switch (e.code) {
        case "201":
            ErrorAlert("失败");
            break;
        case "404":
            WarnAlert("未找到");
            break;
        case "500":
            ErrorAlert("系统错误");
            break;
        case "403":
            WarnAlert("未授权");
            break;
        default:
            layer.msg("未知错误！");
    }
}


//错误提示弹出
function ErrorAlert(e) {
    var index = layer.alert(e, {
        icon: 5,
        time: 2000,
        offset: 'auto',
        closeBtn: 0,
        title: '错误',
        btn: [],
        anim: 5,
        shade: 0
    });
    layer.style(index, {
        color: '#777'
    });
}


//警告弹出
function WarnAlert(e) {
    var index = layer.alert(e, {
        icon: 0,
        time: 2000,
        offset: 'auto',
        closeBtn: 0,
        title: '警告',
        btn: [],
        anim: 5,
        shade: 0
    });
    layer.style(index, {
        color: '#777'
    });
}


//错误提示弹出
function SuccessAlert(e) {
    var index = layer.alert(e, {
        icon: 1,
        time: 2000,
        offset: 'auto',
        closeBtn: 0,
        title: '成功',
        btn: [],
        anim: 5,
        shade: 0
    });
    layer.style(index, {
        color: '#777'
    });
}