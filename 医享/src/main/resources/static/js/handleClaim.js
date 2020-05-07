$(function () {
    $('#agree').click(function () {
        var formData = new FormData($("#addForm")[0]);
        $.ajax({
            url: '/insurer/agreeClaim?insurancePolicyId='+getUrlParam("insurancePolicyId"),
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            processData: false,
            success: function (data) {
                if (data.state === 200) {
                    alert("已同意");
                    $(location).attr('href', "/insurer/listHandledClaimRecord");
                } else {
                    alert("同意异常");
                    window.location.reload();
                }
            },
            error: function () {
                alert("网络异常，请重试");
                window.location.reload();
            }
        });
    });

    $('#refuse').click(function () {
        var formData = new FormData($("#addForm")[0]);
        $.ajax({
            url: '/insurer/refuseClaim?insurancePolicyId='+getUrlParam("insurancePolicyId"),
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            processData: false,
            success: function (data) {
                if (data.state === 200) {
                    alert("已拒绝");
                    $(location).attr('href', "/insurer/listHandledClaimRecord");
                } else {
                    alert("拒绝异常");
                    window.location.reload();
                }
            },
            error: function () {
                alert("网络异常，请重试");
                window.location.reload();
            }
        });
    });

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
});