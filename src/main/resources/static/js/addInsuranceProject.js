$(function(){

    $('#insuranceProjectSubmit').click(function () {
        var formData = new FormData($("#addForm")[0]);
        $.ajax({
            url: '/insurer/insuranceProjectSubmit',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.state == 200) {
                    alert("发布成功\n"+"生成的自动理赔智能合约地址为："+data.contractAddress);
                    var url='/'+"insurer"+'/listInsuranceProject';
                    $(location).attr('href', url);
                } else{
                    alert("发布异常");
                    window.location.reload();
                }
            },
            error: function () {
                alert("网络异常");
                window.location.reload();
            }
        });
    })


});