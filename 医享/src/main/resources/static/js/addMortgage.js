$(function () {
    $('#mortgageInfoSubmit').click(function () {
        var formData = new FormData($("#addForm")[0]);
        $.ajax({
            url: '/patient/addMortgageSubmit',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.state == 200) {
                    alert("贷款材料提交成功");
                    var url = '/' + "patient" + '/listMortgageRecord';
                    $(location).attr('href', url);
                } else {
                    alert("贷款材料提交失败");
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
function loadFile(file){
    $("#fileName").html(file.name);
}