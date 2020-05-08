var serviceURL = "http://duanbanyu.picp.net:25345";
var identification = '';

window.onload = function(){
    let identification = localStorage.getItem('identification');
    let xhr = new XMLHttpRequest();
    //http://duanbanyu.picp.net:25345/images/mallstation/1.jpeg
    xhr.open('GET',
        serviceURL + '/index/person?identification='+ identification,
        true
    );
    xhr.send(null);
    xhr.onreadystatechange = function() {//服务器返回值的处理函数，此处使用匿名函数进行实现
        if (xhr.readyState == 4 && xhr.status == 200) {
            let res = JSON.parse(xhr.responseText);
            // let list_information = res.list_information;//概况，消费贷和现金贷各花费了多少
            let list_card = res.list_card; // 银行卡
            // let list_record = res.list_record;
            // let mini_repay = res.mini_repay;
            // let list_service = res.list_service;
            // let list_separate = list_separate;
            // let list_repay = res.list_repay;
            // let list_operation = res.list_operation;


        }
    }
};

document.querySelector('#pills-user-tab').addEventListener("click",()=>{

    let identification = localStorage.getItem('identification');
    let xhr = new XMLHttpRequest();
    xhr.open('GET',
        serviceURL + '/personalcenter/person?identification='+ identification,
        true
    );
    xhr.send(null);
    xhr.onreadystatechange = function() {//服务器返回值的处理函数，此处使用匿名函数进行实现
        if (xhr.readyState == 4 && xhr.status == 200) {
            let data = JSON.parse(xhr.responseText);
            let userData = data.person;
            document.getElementById('enterprisename').innerHTML = userData.enterprisename;
            document.getElementById('owneridentification').innerHTML = userData.identification;
            document.getElementById('username').innerHTML = userData.username;
            document.getElementById('businesslicence').innerHTML = userData.businesslicence;
            document.getElementById('accountowner').innerHTML = userData.accountname;
            document.getElementById('ownernumber').innerHTML = userData.phonenum;


        }
    }
};




//实现单选样式
$(".shop-search-attr input").click(function(event) {
    $(".shop-search-attr label").css('color', '#212529');
    $(event.target.nextElementSibling).css('color', '#1E90FF');
});

$(".shop-loan-pattern").click(function(event) {
    $(".shop-loan-pattern").removeClass('shop-loan-pattern-checked');
    var that = $(this);
    that.addClass('shop-loan-pattern-checked');
    console.log(that.attr('data-value'));
});

//检查输入
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

$(".input-group-text").on('click',() =>{
    let option = $('input[name=shopAttrs]:checked').val() || 0;
    let kw = $('#shop-search-input').val() || '';
    console.log(option,kw);
    /*$.get('/path/to/resource', {
        data:{
            attribute: option,
            keyword: kw
        }
    },(data)=>{
        /!*跳转至搜索页面*!/
    });*/
});


let cashMoney = document.getElementById('cashMoney').innerHTML;
let cashPercent = document.getElementById('cashPercent').innerHTML;
let cashBalance = document.getElementById('cashBalance').innerHTML;

let Money = document.getElementById('consumeMoney').innerHTML;
let Percent = document.getElementById('consumePercent').innerHTML;
let Balance = document.getElementById('consumeBalance').innerHTML;

let cashModalMoney = document.getElementsByClassName('cashModalMoney');
let consumeModalMoney = document.getElementsByClassName('consumeModalMoney');
for(let i = 0;i < cashModalMoney.length;i++){
    cashModalMoney[i].innerHTML = cashMoney;
}
for(let i = 0;i <consumeModalMoney.length;i++){
    consumeModalMoney[i].innerHTML = Money;
}
$('#toConfirm_1').click(function () {
   $('#cashModal').modal('hide');
   $('#cashRepayConfir').modal('show');
});
$('#toConfirm_2').click(function () {
    $('#consumeModal').modal('hide');
    $('#consumeRepayConfir').modal('show');
});
$('#cashZeroConfir').click(function () {
   document.getElementById('cashMoney').innerHTML = "0";
   document.getElementById('cashBalance').innerHTML = parseFloat(cashBalance) + parseFloat(cashMoney);
});
$('#repayZeroConfir').click(function () {
    document.getElementById('consumeMoney').innerHTML = "0";
    document.getElementById('consumeBalance').innerHTML = parseFloat(Balance) + parseFloat(Money);

});

let shopInfo = {};

$('#shopLoanPatternBtn').on('click',() =>{
    let loanPattern = $('.shop-loan-pattern-checked').attr('data-value');
    console.log(loanPattern);
    shopInfo.loanPattern = loanPattern;
    $('#paymentModal').modal('show');
});

$('#paymentModalBtn').on('click',()=>{
    let paymentTerm = $('input[name=paymentTerms]:checked').val();
    console.log(paymentTerm);
    shopInfo.paymentTerm = paymentTerm;
    $('#cardModal').modal('show');
});

$('#cardModalBtn').on('click', () =>{
    let cardOption = $('input[name=cardOptions]:checked').val();
    console.log(cardOption);
    shopInfo.cardOption = cardOption;
    $('#passModal').modal('show');
});

$('#passModalBtn').on('click', () =>{
    console.log(shopInfo,'发送请求');
    /*$.post('/path/to/resource', {
        data:{
            shopInfo: shopInfo
        }
    },(data)=>{
        /!*成功提示*!/
    });*/
});
