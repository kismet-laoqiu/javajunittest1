$(function () {
    $('#close').click(function () {
        $('#t').hide();
        $('#info').hide();
        $("#tb").empty();
    });

    $('#searchSubmit').click(function () {
        var data = {"searchKey":$('#searchKey').val()};
        $.ajax({
            url: '/searchUnApplyRecordSubmit',
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
                    $('#info').show();
                } else {
                    $('#tips').text(data.data.name+"---"+data.data.email);
                    $("#patient").val(data.data.email);
                    $("#tb").empty();
                    var s=""
                    $.each(data.records, function (index, record) {
                        s =s+ "<tr>\n" +
                            "<td>"+(index+1)+"</td>\n" +
                            "<th>"+record.patient.name+"</th>\n" +
                            "<th>"+record.patient.gender+"</th>\n" +
                            "<th>"+record.patient.age+"</th>\n" +
                            "<th>"+record.doctor.doctorInfo.hospital+"</th>\n" +
                            "<th>"+record.doctor.doctorInfo.department+"</th>\n" +
                            "<th>"+getFormatDate(record.createTime)+"</th>\n" +
                            "<th><a href=\"/doctor/sendApply?recordId="+record.id+"\">申请查看</a></th>\n" +
                            "</tr>";
                    });
                    $("#tb").append(s);
                    $('#t').show();
                    $('#info').show();
                }
            },
            error: function () {
                $('#tips').text('网络异常');
                $('#info').show();
            }
        });
    });
    function getFormatDate(t){
        var nowDate = new Date(t);
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
        var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
        var hour = nowDate.getHours()< 10 ? "0" + nowDate.getHours() : nowDate.getHours();
        var minute = nowDate.getMinutes()< 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();
        var second = nowDate.getSeconds()< 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();
        return year + "年" + month + "月" + date+"日"+" "+hour+":"+minute+":"+second;
    }
});