//사용자가 주문서 페이지를 벗어나려 할 경우
window.addEventListener('beforeunload', function (event) {
    // 확인 메시지를 설정합니다.
    const confirmationMessage = '정말로 이 페이지를 떠나시겠습니까?';

    // 이벤트에 설정합니다.
    event.returnValue = confirmationMessage;

    return confirmationMessage;
});


$(document).ready(function () {
console.log('사전검증 함수');
    $.ajax({
        url: "/payment/prepare",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            merchantUid: paymentDTO.merchantUid, // 가맹점 주문번호
            payPrice: paymentDTO.payPrice // 결제 예정금액
        })
    });
});

document.getElementById('purchaseButton').addEventListener('click', (e)=> {

    e.preventDefault(); //이벤트 취소
    console.log('결제 버튼 클릭');

    const data = {

     merchantUid : paymentDTO.merchantUid,
     amount : paymentDTO.payPrice,
     tel : '010-1234-5678',
     address : '경기도 인천시 제주도구'
    }

    console.log(data);

    let IMP = window.IMP; // Iamport 객체 초기화
    IMP.init('imp53054186'); // 가맹점 식별코드

    IMP.request_pay({
        pg: 'html5_inicis',
        pay_method: 'card',
        merchant_uid: paymentDTO.merchantUid,
        name: paymentDTO.payDescription,
        amount: paymentDTO.payPrice,
        buyer_email: paymentDTO.customerId,
        buyer_name: 'nick_name',
        buyer_tel: '010-1234-5678',
        buyer_addr: '인천시 제주도 광역시',
        buyer_postcode: '123-456'
    }, function(rsp) {
        console.log(rsp);
        if (rsp.success) {
            $.ajax({
                url: '/payment/validate',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    impUid: rsp.imp_uid,
                    merchantUid: rsp.merchant_uid,
                }),
            }).done(function (data){
                //사후 검증 완료 후 
                //성공하면 DB데이터 세부사항 저장해야 한다 : 주소,전화번호, 결제방식 등등 업데이트
                alert('결제가 성공적으로 완료되었습니다');
                location.href = '/payment/success'
            })
        } else {
            alert('결제에 실패하였습니다. 에러 내용: ' + rsp.error_msg);
            // 결제 실패 후 처리
        }
    });
});
