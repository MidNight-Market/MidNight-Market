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

async function sendEmailToServer(email){
    try {
        const url = '/customer/'+email;
        const config = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}