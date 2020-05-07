$(function () {
    $('#installmentSubmit').click(function () {
        $.ajax({
            url: '/patient/installmentSubmit?recordId=' + getUrlParam("recordId") + "&cycle=" + $("#cycle").val(),
            type: 'POST',
            data: {},
            async: false,
            cache: false,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            processData: false,
            success: function (data) {
                if (data.state === 200) {
                    alert("分期付款申请成功");
                    $(location).attr('href', "/patient/payment");
                }else{
                    alert("分期付款申请失败");
                    window.location.reload();
                }
            },
            error: function (returndata) {
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