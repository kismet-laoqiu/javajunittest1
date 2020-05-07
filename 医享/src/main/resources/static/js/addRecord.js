$(function () {
    $('#close').click(function () {
        $('#addForm').hide();
        $('#info').hide();
        $("#patient").val("");
        $("#name").val("");
        $("#gender").val("");
        $("#age").val("");
        $("#nation").val("");
        $("#bloodType").val("");
        $("#diseaseType").val("");
        $("#diagnosticInfo").val("");
    });

    $('#searchSubmit').click(function () {
        var data = {"searchKey":$('#searchKey').val()};
        $.ajax({
            url: '/searchPatientSubmit',
            type: 'POST',
            data: JSON.stringify(data),
            async: false,
            cache: false,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            processData: false,
            success: function (data) {
                if (data.state === 201) {
                    $('#tips').text('账号不存在');
                    $('#addForm').hide();
                    $("#patient").val("");
                    $("#name").val("");
                    $("#gender").val("");
                    $("#age").val("");
                    $("#nation").val("");
                    $("#bloodType").val("");
                    $("#diseaseType").val("");
                    $("#diagnosticInfo").val("");
                    $('#info').show();
                } else {
                    $('#tips').text(data.data.name+"---"+data.data.email);
                    $("#patient").val(data.data.email);
                    $('#addForm').show();
                    $('#info').show();
                }
            },
            error: function () {
                $('#tips').text('网络异常');
                $('#addForm').hide();
                $("#patient").val("");
                $("#name").val("");
                $("#gender").val("");
                $("#age").val("");
                $("#nation").val("");
                $("#bloodType").val("");
                $("#diseaseType").val("");
                $("#diagnosticInfo").val("");
                $('#info').show();
            }
        });
    });

    $('#recordSubmit').click(function () {
        var formData = new FormData($("#addForm")[0]);
        $.ajax({
            url: '/doctor/recordSubmit',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.state === 200) {
                    alert("提交成功，等待患者确认");
                    $(location).attr('href',"/doctor/home");
                } else {
                    alert("提交失败，请重试");
                    window.location.reload();
                }
            },
            error: function () {
                alert("网络异常，请重试");
                window.location.reload();
            }
        });
    })
});