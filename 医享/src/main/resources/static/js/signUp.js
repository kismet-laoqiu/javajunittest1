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
    $("#passwordRepeat").blur(function () {
        check();
    });

    function check() {
        if ($('#email').val() === '') {
            $('#tips').text('邮箱不能为空');
            $('#info').show();
            $("#signUpSubmit").attr("disabled", true);
        } else if ($('#password').val() === '') {
            $('#tips').text('密码不能为空');
            $('#info').show();
            $("#signUpSubmit").attr("disabled", true);
        } else if ($('#passwordRepeat').val() === '') {
            $('#tips').text('重复密码不能为空');
            $('#info').show();
            $("#signUpSubmit").attr("disabled", true);
        } else if ($('#passwordRepeat').val() != $('#password').val()) {
            $('#tips').text('两次密码不一致');
            $('#info').show();
            $("#signUpSubmit").attr("disabled", true);
        } else {
            $('#info').hide();
            $("#signUpSubmit").attr("disabled", false);
        }
    }

    $('#signUpSubmit').click(function () {
        var formData = new FormData($("#signUp-form")[0]);
        $.ajax({
            url: '/signUpSubmit',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.state == 200) {
                    var type = $("#type").val();
                    var url='/'+type+'/home';
                    $(location).attr('href', url);
                } else {
                    $('#tips').text('账号已存在');
                    $('#info').show();
                }
            },
            error: function (returndata) {
                $('#tips').text('网络异常');
                $('#info').show();
            }
        });
    })
});