$(function () {
    $('#rechargeSubmit').click(function () {
        $.ajax({
            url: "/research/rechargeSubmit?money=" + $("#inputMoney").val(),
            type: 'POST',
            data: {},
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.state == 200) {
                    alert("充值成功");
                    var url = '/' + "research" + '/userInfo';
                    $(location).attr('href', url);
                } else {
                    alert("充值失败");
                    window.location.reload();
                }
            },
            error: function () {
                alert("网络异常");
                window.location.reload();
            }
        });
    });
});