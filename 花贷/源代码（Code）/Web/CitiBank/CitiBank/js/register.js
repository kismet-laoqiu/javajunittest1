//简单验证输入栏是否为空
const serviceURL = "http://duanbanyu.picp.net:25345";
var stringData = "";
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
    alert("pass");
    return true;
}

//检查输入栏是否为空，不为空则提交数据
$('#register-info-btn').click(function () {

    let state = true;
    let listName =
        "enterprisename"+"_"+
        "businesslicence"+"_"+
        "publicaccounts"+"_"+
        "depositbank"+"_"+
        "accountname"+"_"+
        "accountowner"+"_"+
        "owneridentification"+"_"+
        "ownernumber";
    $("input[class='register-form-control']").each(function () {
        if(this.value==''){
            state = false;
            alert("您尚有未填写的内容，请填充完整。");
        }
    });
    if(state == true){
        let listValue ="";
        $("input[class='register-form-control']").each(function () {
            listValue += this.value + "_"
        });
        let listNameArr = listName.split("_");
        let listValueArr = listValue.split("_");
        // let stringData = "";
        for(let i = 0;i < listNameArr.length;i++){
            stringData += listNameArr[i]+"="+listValueArr[i];
            if(i != listNameArr.length-1){
                stringData += "&";
            }
        }
        // alert(stringData);
        $('#pills-upload-tab').removeClass('disabled');
        $('#pills-info-tab').removeClass('active');
        $('#pills-info-tab').addClass('disabled');
        $('#pills-info').removeClass('active', 'show');
        $('#pills-upload-tab').addClass('active');
        $('#pills-file').addClass('active show');
        // let stringData = "businesslicence=4654";
        let httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
        console.log(stringData);
        httpRequest.open('POST', serviceURL + '/regist/enterprise', true); //第二步：打开连接
        httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
        httpRequest.send(stringData);//发送请求 将情头体写在send中
        /**
         * 获取数据后的处理程序
         */
        httpRequest.onreadystatechange = function () {//请求后的回调接口，可将请求成功后要执行的程序写在其中
            if (httpRequest.readyState == 4 && httpRequest.status == 200) {//验证请求是否发送成功
                let json = httpRequest.responseText;//获取到服务端返回的数据

                // alert(json);
                if (json.state == "true") {
                    $('#pills-upload-tab').removeClass('disabled');
                    $('#pills-info-tab').removeClass('active');
                    $('#pills-info-tab').addClass('disabled');
                    $('#pills-info').removeClass('active', 'show');
                    $('#pills-upload-tab').addClass('active');
                    $('#pills-file').addClass('active show');
                }else{
                     // alert(json.msg);
                     return;

                 }
            }
        };
    }
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
    // console.log(suffix);
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
        $('#pills-waiting-tab').removeClass('disabled').tab('show');
        $('#pills-upload-tab').addClass('disabled');
       // $.post(serviceURL+"/regist/upload",{
       //
       //     data: $('#filePath').text()
       // },(data)=>{
       //     if(data.state == "true"){
       //         $('#pills-waiting-tab').removeClass('disabled').tab('show');
       //         $('#pills-upload-tab').addClass('disabled');
       //     }
       //     else{
       //         alert("响应失败")
       //     }
       // });
    // let uploadData = stringData.split("&")[1] +"&"+stringData.split("&")[stringData.split("&").length-1];
    // let httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
    // httpRequest.open('POST', serviceURL + '/regist/upload', true); //第二步：打开连接
    // httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
    // httpRequest.send(uploadData);//发送请求 将情头体写在send中
    // /**
    //  * 获取数据后的处理程序
    //  */
    // httpRequest.onreadystatechange = function () {//请求后的回调接口，可将请求成功后要执行的程序写在其中
    //     if (httpRequest.readyState == 4 && httpRequest.status == 200) {//验证请求是否发送成功
    //         let json = httpRequest.responseText;//获取到服务端返回的数据
    //         // alert(json);
    //         if (json.state == "true") {
    //             $('#pills-waiting-tab').removeClass('disabled').tab('show');
    //             $('#pills-upload-tab').addClass('disabled');
    //         }else{
    //             alert(json.msg);
    //             return;
    //         }
    //     }
    // };
});

//等待审核通过
$('#pills-waiting-tab').on('shown.bs.tab', function (e) {
    // $.get('',(data)=>{
    //     $('#userId').text(data.id);
    //     $('#pills-pass-tab').removeClass('disabled').tab('show');
    //     $('#pills-waiting-tab').addClass('disabled');
    // });
    //setTimeout("window.location.href='enterlogin.html'",5000);
});