function claimInsurancePolicy(recordId){
    $.ajax({
        url: '/patient/claimInsurancePolicy?recordId='+recordId+'&'+'insurancePolicyId='+getUrlParam("insurancePolicyId"),
        type: 'POST',
        data: {},
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.state == 200) {
                alert("申请理赔成功");
                var url='/'+"patient"+'/listClaimRecord';
                $(location).attr('href', url);
            } else{
                alert("申请理赔异常");
                window.location.reload();
            }
        },
        error: function () {
            alert("网络异常");
            window.location.reload();
        }
    });
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}