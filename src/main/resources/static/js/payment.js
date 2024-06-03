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
        amount: 190,
        buyer_email: paymentDTO.customerId,
        buyer_name: 'nick_name',
        buyer_tel: '010-1234-5678',
        buyer_addr: '인천시 제주도 광역시',
        buyer_postcode: '123-456'
    }, function(rsp) {
        console.log(rsp);
        if (rsp.success) {

            // $.ajax({
            //     type : 'POST',
            //     url : '/productBuy/register',
            //     contentType : 'application/json',
            //     data : JSON.stringify(data),
            //     success: function (response){
            //         console.log("하이항");
            //     }
            // });

            alert('결제가 완료되었습니다.');
            window.location.href = '/';
            // 결제 성공 후 처리
        } else {
            alert('결제에 실패하였습니다. 에러 내용: ' + rsp.error_msg);
            // 결제 실패 후 처리
        }
    });
});
