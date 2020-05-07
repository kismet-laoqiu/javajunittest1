function deleteInsuranceProject(insuranceProjectId){
    $.ajax({
        url: '/insurer/deleteInsuranceProject?insuranceProjectId='+insuranceProjectId,
        type: 'POST',
        data: {},
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.state == 200) {
                alert("删除成功");
                window.location.reload();
            } else{
                alert("删除异常");
                window.location.reload();
            }
        },
        error: function () {
            alert("网络异常");
            window.location.reload();
        }
    });
}