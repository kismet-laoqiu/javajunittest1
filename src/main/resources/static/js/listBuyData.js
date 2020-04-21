function buyData(id) {
    $.ajax({
        url: "/research/buyDataSubmit?type=" + id,
        type: 'POST',
        data: {},
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.state == 200) {
                alert("购买成功");
                var url = '/' + "research" + '/listPurchasedData';
                $(location).attr('href', url);
            } else {
                alert("购买失败");
                window.location.reload();
            }
        },
        error: function () {
            alert("网络异常");
            window.location.reload();
        }
    });
}