$(function(){

    $('#bankInfoSubmit').click(function () {
        var formData = new FormData($("#addForm")[0]);
        $.ajax({
            url: '/bank/bankInfoSubmit',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.state == 200) {
                    alert("修改成功");
                    window.location.reload();
                } else{
                    alert("修改失败");
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
function changeAvatarUrl(obj) {
    var file = obj.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function (e) {    //成功读取文件
        var img = document.getElementById("avatarUrl");
        img.src = e.target.result;   //或 img.src = this.result / e.target == this
        $("#fileName").html(file.name);
    };
}