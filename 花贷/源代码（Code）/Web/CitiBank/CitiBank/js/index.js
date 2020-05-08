 var businesslicence = "";
 const serviceURL = "http://duanbanyu.picp.net:25345";
 var responseText = "";
 var enterpriseInfo = "";
 var userData = "";

 window.onload=function(){
     businesslicence = localStorage.getItem("businesslicence");
     var xhr = new XMLHttpRequest();
     xhr.open('GET',
         serviceURL + '/index/enterprise?businesslicence='+ businesslicence,
         true
     );
     xhr.send(null);
     xhr.onreadystatechange = function() {//服务器返回值的处理函数，此处使用匿名函数进行实现
         if (xhr.readyState == 4 && xhr.status == 200) {
             responseText = xhr.responseText;
             data = JSON.parse(responseText);



             //关于repay处理
             var repayRecordList = data.repay;

             var serviceClass = ["正常企业贷款", "花贷低息贷款"];
             var repayTbody = document.createElement("tbody");
             for(var i = 0; i < 4;i++){
                 let repayTr = document.createElement("tr");
                 for(var j = 0; j < 3;j++){
                     var repayTd = document.createElement("td");
                     if(j == 0){
                         repayTd.innerHTML = serviceClass[(repayRecordList[i].servicenum)-1];
                     }else if(j == 1){
                         if(parseFloat(repayRecordList[i].money_amount) > 0)
                             repayTd.innerHTML = "偿还："+Math.abs(parseFloat(repayRecordList[i].money_amount))+"元";
                         else
                             repayTd.innerHTML = "借款： "+ Math.abs(parseFloat(repayRecordList[i].money_amount))+"元";
                     }else {
                         repayTd.innerHTML = repayRecordList[i].times;

                     }
                     repayTr.appendChild(repayTd);
                 }
                 repayTbody.append(repayTr);

             }

             var loanDetailsList = data.remain;
             var repayTable = document.getElementById("repayCord");
             repayTable.appendChild(repayTbody);

             //关于loanDetails处理
             var loanDetailsSpan = document.getElementsByClassName('loanDetails');
             // console.log(loanDetailsSpan);
             for(var i = 0; i < loanDetailsSpan.length;i++){
                 loanDetailsSpan[i].innerHTML = loanDetailsList[i];
             }

             //关于record的处理
             var monthRecord = data.record;
             var monthRecordNum = document.getElementsByClassName('home-month-icon');
             var monthRecordMon = document.getElementsByClassName('home-month-money');
             // let monthNum = monthRecord[0].month;
             // console.log(monthNum);
             for(var i = 0; i < monthRecord.length;i++){
                 monthRecordNum[i].innerHTML = monthRecord[i].month;
                 monthRecordMon[i].innerHTML = monthRecord[i].loan_amount;
                 // console.log(monthRecord[i].time);
             }

         }
     };


     var userXHR = new XMLHttpRequest();
     userXHR.open('get',
         serviceURL + '/personalcenter/enterprise?businesslicence=' +businesslicence,
         true
     );
     userXHR.send(null);
     userXHR.onreadystatechange = function () {
         if(userXHR.readyState == 4 && userXHR.status == 200){
             let userDetailResponse = userXHR.responseText;
             let userTemp = JSON.parse(userDetailResponse);
             userData = userTemp.enterprise;
             console.log(userData);
             document.getElementById('userID').innerHTML = userData.accountname;
             document.getElementById('accountname').innerHTML = userData.accountname;
             document.getElementById('companyBusinesslicence').innerHTML = userData.businesslicence;
             document.getElementById('businesslicence').innerHTML = userData.businesslicence;
             document.getElementById('accountowner').innerHTML = userData.accountname;
             document.getElementById('avatar').innerHTML = userData.avatar;
             document.getElementById('depositbank').innerHTML = userData.depositbank;
             document.getElementById('enterprisename').innerHTML = userData.enterprisename;
             document.getElementById('owneridentification').innerHTML = userData.owneridentification;
             document.getElementById('ownernumber').innerHTML = userData.ownernumber;
             document.getElementById('publicaccounts').innerHTML = userData.publicaccounts;
             document.getElementById('certification').innerHTML = userData.certification;
         }
     };


     doItPerSecond();
     // var timer = setInterval(function(){
     //     doItPerSecond();
     // },1000*60)

 };

 //上传文件并检查格式是否正确
 $('#change-avatar').on('click', function () {
     $('#uploadAvatar').click()
 });

 $('#uploadAvatar').on('change', function (e) {
     let trail = document.querySelector('#change-avatar').value;
     let fileTrail = trail.split('\\').reverse()[0];
     let suffix = fileTrail.text().split('.')[1];
     // console.log(suffix);
     if(suffix !== 'jpg' && suffix !== 'png' && suffix !== 'pdf'){
         fileTrail.addClass('warning');
     }else {
         fileTrail.hasClass('warning') ? fileTrail.removeClass('warning') : false;
     }

     var xhr = new XMLHttpRequest();
     xhr.open('GET',
         serviceURL + '/personalcenter/update_avatar?businesslicence='+ businesslicence +"&file="+fileTrail,
         true
     );
     xhr.send(null);
     xhr.onreadystatechange = function () {
         if (xhr.readyState == 4 && xhr.status == 200){
             let userResponse = xhr.responseText;
             userData = JSON.parse(userResponse);
             if(userData.state){
                 document.querySelector("#avatar").setAttribute('src',fileTrail);
             }
         }
     }
 });


document.querySelector("#pills-user-tab").addEventListener("click",() => {
    //if(userData == "")
    {
        var xhr = new XMLHttpRequest();
        xhr.open('GET',
            serviceURL + '/personalcenter/enterprise?businesslicence='+ businesslicence,
            true
        );
        xhr.send(null);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200){
                let userResponse = xhr.responseText;
                console.log(userData);
                userData = JSON.parse(userResponse);
                console.log(userData.enterprise);


            }
        }
    }



});


 function doItPerSecond() {
     var reimburseXHR = new XMLHttpRequest();
     reimburseXHR.open('get',
         serviceURL + '/reimburse?businesslicence='+businesslicence,
         true
     );
     reimburseXHR.send(null);
     reimburseXHR.onreadystatechange = function () {
         if(reimburseXHR.readyState == 4 && reimburseXHR.status == 200){
             let reimburseResponse = reimburseXHR.responseText;
             let reimburseData = JSON.parse(reimburseResponse);
             // console.log(reimburseData[0].key);
             for(var key in reimburseData){
                 console.log(reimburseData.key);
                 // console.log(reimburseData.key);
             }

             let reimburseTitle = ["number","serviceProvider","label","serviceName","times","value","applicant","idenfication","order_num","state"];
             // console.log(reimburseData[0].serviceProvider);
             var reimburseTbody = document.createElement("tbody");

             for(var i = 0; i < reimburseData.length;i++){
                 let reimburseTr = document.createElement("tr");
                 for(var j = 0; j < 10; j++){
                     var reimburseTd = document.createElement("td");
                     if(j == 0){
                         reimburseTd.innerHTML = i+1;
                     }else if(j == 1){
                                 reimburseTd.innerHTML = reimburseData[i].serviceProvider;
                         // console.log(reimburseData[i]);
                     }else if(j == 2 ){
                         reimburseTd.innerHTML = reimburseData[i].label;
                     }else if(j == 3){
                         reimburseTd.innerHTML = reimburseData[i].serviceName;
                     }else if(j == 4 ){
                         reimburseTd.innerHTML = reimburseData[i].times;
                     }else if(j == 5){
                         reimburseTd.innerHTML = reimburseData[i].value;
                     }else if(j == 6){
                         reimburseTd.innerHTML = reimburseData[i].applicant;
                     }else if(j == 7){
                         reimburseTd.innerHTML = reimburseData[i].idenfication;
                     }else if(j == 8){
                         reimburseTd.innerHTML = reimburseData[i].order_num;
                     }else if(j == 9){
                         if(reimburseData[i].state == 0){
                             // let button1 = document.createElement("button");
                             reimburseTd.innerHTML=
                                 "<button class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" data-target=\"#acceptModal\" data-ordernum=reimburseData[i].order_num >接受</button>" +
                                 "<button class=\"btn btn-default btn-sm refuseBtn\" data-toggle=\"modal\" data-target=\"#refuseModal\" value=reimburseData[i].order_num>拒绝</button>";


                         }else if(reimburseData[i].state == 1){
                             reimburseTd.innerHTML = "已报销";
                         }else if(reimburseData[i].state == 3){
                             reimburseTd.innerHTML = "拒绝报销";
                         }
                     }
                     reimburseTr.appendChild(reimburseTd);
                 }
                 reimburseTbody.append(reimburseTr);

             }

             var reimburseTable = document.getElementById('staff-table');
             reimburseTable.append(reimburseTbody);

         }
     }
 }
// $('button').click(function () {
//     if($('button').hasClass('refuseBtn')){
//         this.parent().innerHTML ="拒绝报销";
//     }
// });




$(".staff-application-type input").click(function(event){
    $(".staff-application-type label").css('color','#212529');
    $(event.target.nextElementSibling).css('color','#1E90FF');
    $.get('/path/to/resource', {                                            //提交筛选数据要求
        data:{
            serviceAttr: $('input:radio[name=serviceAttributes]:checked').val(),
            applicationType: $('input:radio[name=applicationType]:checked').val()
        },
        dataType: 'json'
    },(data)=>{
        updateRecords(data);
    });
});

$(".staff-service-attr input").click(function(event){
    $(".staff-service-attr label").css('color','#212529');
    $(event.target.nextElementSibling).css('color','#1E90FF');
    $.get('/path/to/resource', {
        data:{
            serviceAttr: $('input:radio[name=serviceAttributes]:checked').val(),
            applicationType: $('input:radio[name=applicationType]:checked').val()
        },
        dataType: 'json'
    },(data)=>{
        updateRecords(data);
    });
});



// function updateRecords(records){

    // let tbd = $('.staff-table-body');
    // tbd.children().remove();
    // let newRecords = records.map((item,index)=>{
    //     let node = $('<tr class="staff-record" id="staffRecord'+ index +'"></tr>');
    //     node.append('<td>'+ item.index +'</td>' +
    //         '<td>'+ item.service +'</td>'+
    //         '<td>'+ item.provider+'</td>'+
    //         '<td>'+ item.type +'</td>'+
    //         '<td>'+ item.place +'</td>'+
    //         '<td>'+ item.date +'</td>'+
    //         '<td>'+ item.money +'</td>'+
    //         '<td>'+ item.staff +'</td>'+
    //         '<td>'+ item.id +'</td>'+
    //         '<td>'+ item.reason +'</td>'
    //     );
    //     if(item.result === 'accepted'){
    //         node.append('<td class="staff-result-accepted">已接受</td>');
    //     }
    //     else if(item.result === 'refused'){
    //         node.append('<td class="staff-result-refused">已拒绝</td>');
    //     }
    //     else{
    //         node.append(
    //             '<td class="staff-result-pending">'+
    //             '<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#acceptModal" data-service-id="'+ index +'">接受</button>'+
    //             '<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#refuseModal" data-service-id="'+ index +'">拒绝</button>'+
    //             '</td>');
    //     }
    //     return node;
    // });
    // tbd.append(newRecords);
// }










let btn = ''; // '接受'或'拒绝'按钮

$('#acceptBtn').click(function () {
    let account = document.querySelector('#acceptModalAccount').value;
    let password = document.querySelector('#acceptModalPassword').value;
    console.log(account);
    console.log(password);


    if ( account === 'undefined' || password === '') {
        alert('账号及密码不得为空');
    }

    else {
        let httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
        httpRequest.open('GET', serviceURL + '/confirm_reimburse?order_num='+ordernum, true); //第二步：打开连接
        httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
        httpRequest.send(null);//发送请求 将情头体写在send中
        /**
         * 获取数据后的处理程序
         */
        httpRequest.onreadystatechange = function () {//请求后的回调接口，可将请求成功后要执行的程序写在其中
            if (httpRequest.readyState === 4 && httpRequest.status === 200) {//验证请求是否发送成功
                let res = JSON.parse(httpRequest.responseText);//获取到服务端返回的数据
                console.log(res);
                if (res.state == "true") {
                    let reimburseTd = btn.parent;
                    reimburseTd.text("已报销");
                    reimburseTr.appendChild(reimburseTd);
                }else{
                    console.log("请求失败");
                }
            }
        };
    }

});

let ordernum = '';
 $('#acceptModal').on('show.bs.modal', function (event) {
     btn = $(event.relatedTarget); // Button that triggered the modal
     ordernum = $(event.relatedTarget).data("ordernum");
 });

 $('#refuseModal').on("show.bs.modal",function (event) {
     btn = $(event.relatedTarget); // Button that triggered the modal
     ordernum = $(event.relatedTarget).data("ordernum");
 });




$('#refuseModal').on('show.bs.modal', function (event) {
    btn = $(event.relatedTarget);
});
$('#refuseBtn').on('click',()=>{
    let description = document.querySelector('#refuseModal input:checked').value;
    let reimburseXHR = new XMLHttpRequest();
    reimburseXHR.open('get',
        serviceURL + '/refuse_reimburse?order_num='+ordernum+'&description='+description,
        true
    );
    reimburseXHR.send(null);
    reimburseXHR.onreadystatechange = function () {
        if (reimburseXHR.readyState == 4 && reimburseXHR.status == 200) {
            let res = reimburseXHR.responseText;
            res = JSON.parse(res);
            if(res.state){
                let reimburseTd = btn.parent;
                reimburseTd.text("拒绝报销");
                reimburseTr.appendChild(reimburseTd);
            }
            else{
                console.log(res.msg);
            }
        }
    }
});

 $('#acceptModal').on('hide.bs.modal', function (event) {
     $('#acceptModalAccount').val('');
     $('#acceptModalPassword').val('');
     $('#acceptModalAlert').attr('class','alert alert-danger fade');
 });

 $('#refuseModal').on('hide.bs.modal', function (event) {
    $('input:radio[name=refuseReasons]:checked').val('');
    $('#refuseTextarea').val('');
    alert.attr('class','alert alert-danger fade');
});