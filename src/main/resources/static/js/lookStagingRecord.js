$(function () {
    $('#installmentPay').click(function () {
        var cycle = prompt("请输入付款期数", "");
        if (cycle) {
            $.ajax({
                url: '/patient/installmentPaySubmit?recordId=' + getUrlParam("recordId") + "&cycle=" + cycle,
                type: 'POST',
                data: {},
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.state == 200) {
                        alert("支付成功");
                        $(location).attr('href', "/patient/listStagingRecord");
                    } else {
                        alert("支付失败");
                        window.location.reload();
                    }
                },
                error: function () {
                    alert("网络异常");
                    window.location.reload();
                }
            });
        }
    });

    $('#directPay').click(function () {
        $.ajax({
            url: '/patient/directPaySubmit?recordId=' + getUrlParam("recordId") ,
            type: 'POST',
            data: {},
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.state == 200) {
                    alert("支付成功");
                    $(location).attr('href', "/patient/listStagingRecord");
                } else {
                    alert("支付失败");
                    window.location.reload();
                }
            },
            error: function () {
                alert("网络异常");
                window.location.reload();
            }
        });
    });

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    }
});