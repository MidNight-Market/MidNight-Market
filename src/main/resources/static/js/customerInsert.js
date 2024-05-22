let timer;
let isRunning = false;
let leftSec = 180;
let display = document.querySelector('#timer');

document.getElementById('checkE').addEventListener('click', ()=> {
    let email = document.getElementById('e').value;
    console.log(email);
    document.getElementById('divE').style.display = "";
    document.getElementById('timer').style.display = "";
    sendAuthNum();
});

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
