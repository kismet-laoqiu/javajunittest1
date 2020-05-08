//简单验证输入栏是否为空
function easyCheck(item){
    if(item.val() === '')
    {
        item.addClass('wrong-input');
        item.attr('placeholder','信息不得为空');
        item.focus();
        return false;
    }else{
        if(item.hasClass('wrong-input')){
            item.removeClass('wrong-input');
        }
    }
    return true;
}

//检查输入栏是否为空，不为空则提交数据
let infoBtn = $('#register-info-btn');
infoBtn.on('click',()=>{
    let inputs = $('input');
    let flag = true;
    let checks = inputs.reverse().map((item)=>{
        if(easyCheck(item)){
            return true;
        }
        flag = false;
        return false;
    });
    console.log(flag,checks);
    if(!flag) return;
    let data = "{";
    inputs.map((item,index)=>{
        let key = item.attr('id');
        data += '"' + key + '":"' + item.val() + '"';
        data += index !== 7 ? ',' : '}';
    });
    data = JSON.parse(data);
    console.log(data);

    $.post("path",{
        data: data
    },(data)=>{
        if(data){
            $('#pills-upload-tab').removeClass('disabled').tab('show');
            $('#pills-info-tab').addClass('disabled');
        }
    });
});

//上传文件并检查格式是否正确
$('#uploadFile').on('click', function () {
    $('#registerFile').click()
});

$('#registerFile').on('change', function (e) {
    let trail = $('#registerFile').val();
    let fileTrail = $('#filePath');
    fileTrail.text(trail.split('\\').reverse()[0]);
    let suffix = fileTrail.text().split('.')[1];
    console.log(suffix);
    if(suffix !== 'jpg' && suffix !== 'png' && suffix !== 'pdf'){
        fileTrail.addClass('warning');
    }else {
        fileTrail.hasClass('warning') ? fileTrail.removeClass('warning') : false;
    }
});

//提交上传文件
$('#register-file-btn').on('click',()=>{
        if($('#filePath').hasClass('warning')){
            return;
        }
       $.post("path",{
           data: $('#filePath').text()
       },(data)=>{
           if(data){
               $('#pills-waiting-tab').removeClass('disabled').tab('show');
               $('#pills-upload-tab').addClass('disabled');
           }
       });
});

//等待审核通过
$('#pills-waiting-tab').on('shown.bs.tab', function (e) {
    $.get('',(data)=>{
        $('#userId').text(data.id);
        $('#pills-pass-tab').removeClass('disabled').tab('show');
        $('#pills-waiting-tab').addClass('disabled');
    });
});