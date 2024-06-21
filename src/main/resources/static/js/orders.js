// 변수 선언부
let pointText = document.getElementById('point'); //포인트
let pointInputText = document.getElementById('point-input'); //포인트 인풋
let ordersPayText = document.getElementById('ordersPay'); //주문 금액
let productPayText = document.getElementById('productPay'); //상품금액
let discountPayText = document.getElementById('discountPay'); //상품할인금액
let pointDiscountText = document.getElementById('pointDiscount'); //포인트할인
let lastPayText = document.getElementById('lastPay');//최종 결제 금액


//변수 선언부



var couponModal = document.getElementById("couponModal");
var btn = document.getElementById("couponSelect");
var span = document.getElementsByClassName("closeModal")[0];
btn.onclick = function() {
    couponModal.style.display = "flex";
}
span.onclick = function() {
    couponModal.style.display = "none";
}
couponModal.onclick = function(event) {
    if (event.target == couponModal) {
        couponModal.style.display = "none";
    }
}
document.getElementById('selectCoupon').addEventListener('click', (e) => {
    // 선택된 라디오 버튼 찾기
    const selectedCoupon = document.querySelector('input[name="coupon"]:checked');
    if (selectedCoupon) {
        const couponName = selectedCoupon.getAttribute('data-name');
        const discountAmount = selectedCoupon.getAttribute('data-discount');
        const pointValue = Number(document.getElementById('point-input').value);
        if(Number(discountAmount) + pointValue > (paymentDTO.payPrice )){ //결제할 값보다 클경우 취소
            alert('쿠폰금액이 결제할 금액을 초과하여 사용할 수 없습니다.');
            return;
        }
        couponDiscount = Number(discountAmount); //쿠폰사용 금액 저장
        document.getElementById('couponDiscount').innerText ='-' + Number(discountAmount).toLocaleString() + '원';
        document.getElementById('lastPay').innerText = (paymentDTO.payPrice - Number(discountAmount) - Number(pointValue)).toLocaleString() + '원';
        point.innerText = (paymentDTO.payPrice - Number(discountAmount)) > pointVal ? pointVal.toLocaleString() + '원' : (paymentDTO.payPrice - Number(discountAmount)).toLocaleString() + '원';
        const couponSelectionDiv = document.getElementById('couponSelection');
        couponSelectionDiv.innerHTML = `<span style="font-size: 18px; font-weight: 400; width: auto">선택된 쿠폰 : ${couponName}</span><br><span style="font-size: 18px; font-weight: 400; width: auto">할인 가격: ${discountAmount}원</span>`;
        couponSelectionDiv.style.display = 'block';
        couponModal.style.display = "none";
    } else {
        alert('쿠폰을 선택하세요.');
    }
})



window.onload = function () {
    function onClick() {
        document.querySelector('.modal_wrap1').style.display = 'block';
        document.querySelector('.black_bg1').style.display = 'block';
    }

    function offClick() {
        document.querySelector('.modal_wrap1').style.display = 'none';
        document.querySelector('.black_bg1').style.display = 'none';
    }

    document.getElementById('modal_btn1').addEventListener('click', onClick);

    document.querySelector('.modal_close1').addEventListener('click', offClick);

}

// Modal을 가져옵니다.
var modals = document.getElementsByClassName("modal");
// Modal을 띄우는 클래스 이름을 가져옵니다.
var btns = document.getElementsByClassName("btn");
// Modal을 닫는 close 클래스를 가져옵니다.
var spanes = document.getElementsByClassName("close");
var funcs = [];

// Modal을 띄우고 닫는 클릭 이벤트를 정의한 함수
function Modal(num) {
    return function () {
        // 해당 클래스의 내용을 클릭하면 Modal을 띄웁니다.
        btns[num].onclick = function () {
            modals[num].style.display = "block";
            console.log(num);
        };

        // <span> 태그(X 버튼)를 클릭하면 Modal이 닫습니다.
        spanes[num].onclick = function () {
            modals[num].style.display = "none";
        };
    };
}

// 원하는 Modal 수만큼 Modal 함수를 호출해서 funcs 함수에 정의합니다.
for (var i = 0; i < btns.length; i++) {
    funcs[i] = Modal(i);
}

// 원하는 Modal 수만큼 funcs 함수를 호출합니다.
for (var j = 0; j < btns.length; j++) {
    funcs[j]();
}

// Modal 영역 밖을 클릭하면 Modal을 닫습니다.
window.onclick = function (event) {
    if (event.target.className == "modal") {
        event.target.style.display = "none";
    }
};

console.log(paymentDTO);



document.getElementById('point-input').addEventListener('input', (e) => { //포인트를 변경할 경우 이벤트
    let usePoint = Number(e.target.value);

    if (usePoint > pointVal || maxPoint < usePoint ) { //현재 보유한 포인트보다 많이 입력을 한다면
        e.target.value = pointVal; //value값 변경
        point.innerText = '0원'; //잔여 포인트값 변경
        pointDiscount.innerText = formatCurrency(pointVal) //픽시드값 변경
        lastPay.innerText = formatCurrency(1234); //최종결제금액 변경
        return;
    }

    lastPay.innerText = formatCurrency(paymentDTO.payPrice - couponDiscount - usePoint); //최종 결제금액 변경
    point.innerText = formatCurrency(paymentDTO.payPrice - couponDiscount  - usePoint); //잔여 포인트값 변경





})

//포인트 모두 사용 버튼 클릭 시
document.getElementById('all-in').addEventListener('click', () => {
    point.innerText = '0 원';
    pointInput.value = maxPoint; //인풋값 변경
    pointDiscount.innerText = formatCurrency(maxPoint) //픽시드값 변경
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


