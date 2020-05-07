$(function () {
    $('#issueSubmit').click(function () {
        $.ajax({
            url: "/bank/issueSubmit?money=" + $("#inputMoney").val(),
            type: 'POST',
            data: {},
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.state == 200) {
                    alert("发布成功");
                    window.location.reload();
                } else {
                    alert("发布失败");
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