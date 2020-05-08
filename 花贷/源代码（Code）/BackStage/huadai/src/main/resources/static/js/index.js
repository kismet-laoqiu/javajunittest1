

$(function() {
    $.get('/path/to/resource',(data)=>{
        updateRecords(data);
    });

});



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
/*
let testRecord = [{
    index: 201907041030,
    service:"测试1",
    provider: "XX酒店",
    type: "住宿",
    place: "酒店地址",
    date: "2019/7/2",
    money:"800元",
    staff: "小张",
    id: "*************",
    reason: "出差，******",
    result: "accepted"
},
    {
        index: 201907041030,
        service:"测试2",
        provider: "XX酒店",
        type: "住宿",
        place: "酒店地址",
        date: "2019/7/2",
        money:"800元",
        staff: "小张",
        id: "*************",
        reason: "出差，******",
        result: "pending"
    }];
*/
function updateRecords(records){

    let tbd = $('.staff-table-body');
    tbd.children().remove();
    let newRecords = records.map((item,index)=>{
        let node = $('<tr class="staff-record" id="staffRecord'+ index +'"></tr>');
        node.append('<td>'+ item.index +'</td>' +
            '<td>'+ item.service +'</td>'+
            '<td>'+ item.provider+'</td>'+
            '<td>'+ item.type +'</td>'+
            '<td>'+ item.place +'</td>'+
            '<td>'+ item.date +'</td>'+
            '<td>'+ item.money +'</td>'+
            '<td>'+ item.staff +'</td>'+
            '<td>'+ item.id +'</td>'+
            '<td>'+ item.reason +'</td>'
        );
        if(item.result === 'accepted'){
            node.append('<td class="staff-result-accepted">已接受</td>');
        }
        else if(item.result === 'refused'){
            node.append('<td class="staff-result-refused">已拒绝</td>');
        }
        else{
            node.append(
                '<td class="staff-result-pending">'+
                '<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#acceptModal" data-service-id="'+ index +'">接受</button>'+
                '<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#refuseModal" data-service-id="'+ index +'">拒绝</button>'+
                '</td>');
        }
        return node;
    });
    tbd.append(newRecords);
}










let btn = ''; // '接受'或'拒绝'按钮
$('#acceptModal').on('show.bs.modal', function (event) {
    btn = $(event.relatedTarget); // Button that triggered the modal
});
$('#acceptBtn').on('click',()=>{
    let account = $('#acceptModalAccount').val();
    let password = $('#acceptModalPassword').val();
    let alert = $('#acceptModalAlert');
    console.log(account);
    console.log(password);

    if ( account === 'undefined' || password === '')
    {
        alert.text('账号及密码不得为空');
        alert.attr('class','alert alert-danger show');
        return;
    }


    $.post('/path/to/resource', {                               //提交接受对话框表单数据
        account: account,
        password: password
    }, data =>{
        if(data){                                                                //数据无误
            let a = $('#acceptModal').modal('hide');
            let result = btn.parent();
            result.children().remove();
            result.text('已接受').attr('class','staff-result-accepted');
        }
        else{                                                                      //数据有误
            alert.text('账号或密码错误');
            alert.attr('class','alert alert-danger show');
        }
    });
});
$('#acceptModal').on('hide.bs.modal', function (event) {
    $('#acceptModalAccount').val('');
    $('#acceptModalPassword').val('');
    $('#acceptModalAlert').attr('class','alert alert-danger fade');
});




$('#refuseModal').on('show.bs.modal', function (event) {
    btn = $(event.relatedTarget);
});
$('#refuseBtn').on('click',()=>{
    let reasons = $('input:radio[name=refuseReasons]:checked').val();
    let feedback = $('#refuseTextarea').val();
    let alert = $('#refuseModalAlert');

    console.log(reasons);
    console.log(feedback);

    if ( reasons === '' || feedback === '')
    {
        alert.text('信息不得为空');
        alert.attr('class','alert alert-danger show');
        return;
    }

    $.post('/path/to/resource', {                              //提交拒绝对话框表单数据
        reasons: reasons,
        feedback: feedback
    }, data=>{
        if(state){
            let a = $('#refuseModal').modal('hide');
            let result = btn.parent();
            result.children().remove();
            result.text('已拒绝').attr('class','staff-result-refused');
        }
        else{                                                                      //数据有误
            alert.text('账号或密码错误');
            alert.attr('class','alert alert-danger show');
        }
    });
});
$('#refuseModal').on('hide.bs.modal', function (event) {
    $('input:radio[name=refuseReasons]:checked').val('');
    $('#refuseTextarea').val('');
    alert.attr('class','alert alert-danger fade');
});