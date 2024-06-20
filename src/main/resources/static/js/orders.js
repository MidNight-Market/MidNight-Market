console.log(paymentDTO);

let point = document.getElementById('point'); //포인트
let pointInput = document.getElementById('point-input'); //포인트 인풋
let ordersPay = document.getElementById('ordersPay'); //주문 금액
let productPay = document.getElementById('productPay'); //상품금액
let discountPay = document.getElementById('discountPay'); //상품할인금액
let couponDiscount = document.getElementById('couponDiscount'); //쿠폰할인
let pointDiscount = document.getElementById('pointDiscount'); //포인트할인
let lastPay = document.getElementById('lastPay');//최종 결제 금액

point.innerText = formatCurrency(pointVal);
ordersPay.innerText = formatCurrency(paymentDTO.payPrice);
productPay.innerText = formatCurrency(paymentDTO.originalPrice);
discountPay.innerText = '- ' + formatCurrency(paymentDTO.originalPrice - paymentDTO.payPrice);


document.getElementById('point-input').addEventListener('input', (e) => {
    let usePoint = Number(e.target.value);

    lastPay.innerText = formatCurrency(cleanCurrencyString(lastPay.innerText) - e.target.value); //최종 결제금액 변경
    point.innerText = formatCurrency(cleanCurrencyString(point.innerText) - e.target.value); //잔여 포인트값 변경

    if (usePoint > pointVal) { //현재 보유한 포인트보다 많이 입력을 한다면
        e.target.value = pointVal; //value값 변경
        point.innerText = '0원'; //잔여 포인트값 변경
        pointDiscount.innerText = formatCurrency(pointVal) //픽시드값 변경
        lastPay.innerText = formatCurrency(paymentDTO.payPrice); //최종결제금액 변경
        return;
    }


})

//포인트 모두 사용 버튼 클릭 시
document.getElementById('all-in').addEventListener('click', () => {
    point.innerText = '0 원';
    pointInput.value = pointVal; //인풋값 변경
    pointDiscount.innerText = formatCurrency(pointVal) //픽시드값 변경
    lastPay.innerText = formatCurrency((cleanCurrencyString(lastPay.innerText) - pointVal)); //최종결제금액 변경
});


//쉼표와 원을 붙여주는 함수
function formatCurrency(number) {
    return number.toLocaleString() + " 원";
}

// 쉼표와 '원' 제거하고 숫자로 형변환 리턴 함수
function cleanCurrencyString(currencyString) {
    return Number(currencyString.replace(/,/g, '').replace(/원/g, ''));
}


