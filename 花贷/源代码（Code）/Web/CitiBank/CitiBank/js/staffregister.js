
const serviceURL = "http://duanbanyu.picp.net:25345";


let verification = false;

document.querySelector('#verification-send-btn').addEventListener('click',()=>{


    let input = document.querySelector("#phonenum");

    let httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
    httpRequest.open('post', serviceURL + '/regist/SMS?phonenum='+input.value, true); //第二步：打开连接
    httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
    httpRequest.send(JSON.stringify(phonenum));//发送请求 将情头体写在send中

    httpRequest.onreadystatechange = function () {//请求后的回调接口，可将请求成功后要执行的程序写在其中
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {//验证请求是否发送成功
            let json = httpRequest.responseText;
            console.log(json);
            json = JSON.parse(json);
            console.log(json);
            if (json.state) {
                console.log(msg);

            }else{
                console.log(json.msg);
                return;

            }
        }
    };

});

//检查输入栏是否为空，不为空则提交数据
let infoBtn = $('#register-info-btn');
infoBtn.on('click',()=>{

    let state = true;
    let stringData = '';
    let listName =
        "username"+"_"+
        "password"+"_"+
        "identification"+"_"+
        "enterprisename"+"_"+
        "card"+"_"+
        "cardpassword"+"_"+
        "phonenum"+"_"+
        "vercode";
    $("input[class='register-form-control']").each(function () {
        if(this.value==''){
            state = false;
            alert("您尚有未填写的内容，请填充完整。");
        }
    });

    if(state == true) {
        let listValue = "";
        $("input[class='register-form-control']").each(function () {
            listValue += this.value + "_"
        });
        let listNameArr = listName.split("_");
        let listValueArr = listValue.split("_");
        // let stringData = "";
        for (let i = 0; i < listNameArr.length; i++) {
            stringData += listNameArr[i] + "=" + listValueArr[i];
            if (i != listNameArr.length - 1) {
                stringData += "&";
            }
        }
    }
    console.log(stringData);

    let res = '{"msg":"请等待信息审核结果","state":"true","url":"https://sandbox.apihub.citi.com/gcb/api/authCode/oauth2/authorize?response_type=code&client_id=f89aa408-e3f9-4a35-bdf1-c63754871af4&scope=personal_domestic_transfers meta_data customers_profiles&countryCode=HK&businessCode=GCB&locale=en_US&state=12093&redirect_uri=http://duanbanyu.picp.net:25345/bindCard"}';
    res = JSON.parse(res);
    console.log(res.url);
    setTimeout("window.location.href="+"'"+res.url+"'",1000);


    let httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
    httpRequest.open('post', serviceURL + '/regist/person', true); //第二步：打开连接
    httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
    httpRequest.send(stringData);//发送请求 将情头体写在send中
    /**
     * 获取数据后的处理程序
     */
    httpRequest.onreadystatechange = function () {//请求后的回调接口，可将请求成功后要执行的程序写在其中
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {//验证请求是否发送成功
            let res = JSON.parse(httpRequest.responseText);//获取到服务端返回的数据
            console.log(res);
            if (res.state == "true") {
                setTimeout("window.location.href="+"'"+res.url+"'",1000);
                console.log("请求成功");
                $('#pills-pass-tab').removeClass('disabled').tab('show');
                $('#pills-info-tab').addClass('disabled');
            }else{
                console.log("请求失败");
            }
        }
    };

});

$('#register-pass-btn').on('click',()=>{
    setTimeout("window.location.href='staff.html'",3000);
});