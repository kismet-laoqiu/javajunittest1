$(function () {

    $('#close').click(function () {
        $('#info').hide();
    });

    $("#email").blur(function () {
        check();
    });
    $("#password").blur(function () {
        check();
    });

    function check() {
        if ($('#email').val() === '') {
            $('#tips').text('邮箱不能为空');
            $('#info').show();
            $("#loginSubmit").attr("disabled", true);
        } else if ($('#password').val() === '') {
            $('#tips').text('密码不能为空');
            $('#info').show();
            $("#loginSubmit").attr("disabled", true);
        } else {
            $('#info').hide();
            $("#loginSubmit").attr("disabled", false);
        }
    }

    $('#loginSubmit').click(function () {
        var formData = new FormData($("#login-form")[0]);
        $.ajax({
            url: '/loginSubmit',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.state == 202) {
                    $('#tips').text('邮箱或密码错误');
                    $('#info').show();
                } else if(data.state == 201) {
                    $('#tips').text('账号不存在');
                    $('#info').show();
                }else{
                    var type = $("#type").val();
                    var url='/'+type+'/home';
                    $(location).attr('href', url);
                }
            },
            error: function (returndata) {
                $('#tips').text('网络异常');
                $('#info').show();
            }
        });
    })
});