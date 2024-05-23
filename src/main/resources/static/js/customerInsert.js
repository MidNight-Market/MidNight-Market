function sendNumber(){
    $("#mail_number").css("display","block");
    $.ajax({
        url:"/customer/mail",
        type:"post",
        dataType:"json",
        data:{"mail" : $("#customerEmail").val()},
        success: function(data){
            alert("인증번호 발송");
            $("#Confirm").attr("value",data);
        }
    });
}

function confirmNumber(){
    var number1 = $("#number").val();
    var number2 = $("#Confirm").val();

    if(number1 === number2){
        alert("인증되었습니다.");
    }else{
        alert("번호가 다릅니다.");
    }
}

let timer;
let isRunning = false;
let leftSec = 180;
let display = document.querySelector('#timer');


function sendAuthNum(){
    if (isRunning){
        clearInterval(timer);
    }
    startTimer(leftSec, display);
}

function startTimer(count, display) {
    let minutes, seconds;
    timer = setInterval(function () {
        minutes = parseInt(count / 60, 10);
        seconds = parseInt(count % 60, 10);
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        display.textContent = minutes + ":" + seconds;
        if (--count < 0) {
            clearInterval(timer);
            display.textContent = "";
            isRunning = false;
        }
    }, 1000);
    isRunning = true;
}
