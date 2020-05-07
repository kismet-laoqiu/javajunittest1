function signInsuranceProjectSubmit(insuranceProjectId){
    $.ajax({
        url: '/patient/signInsuranceProjectSubmit?insuranceProjectId='+insuranceProjectId,
        type: 'POST',
        data: {},
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.state == 200) {
                alert("投保申请成功");
                var url='/'+"patient"+'/listInsuranceProject';
                $(location).attr('href', url);
            } else{
                alert("投保申请异常");
                window.location.reload();
            }
        },
        error: function () {
            alert("网络异常");
            window.location.reload();
        }
    });
}