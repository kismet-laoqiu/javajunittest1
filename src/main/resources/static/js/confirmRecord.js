$(function () {
    $('#confirmRecordSubmit').click(function () {
        var data = {"recordId":getUrlParam("recordId")};
        $.ajax({
            url: '/patient/confirmRecordSubmit',
            type: 'POST',
            data: JSON.stringify(data),
            async: false,
            cache: false,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            processData: false,
            success: function (data) {
                if (data.state === 200) {
                    alert("确认成功\n"+"IPFS上病历文件的Hash:"+data.hash);
                    $(location).attr('href', "/patient/home");
                } else {
                    alert("确认失败");
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