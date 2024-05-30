
document.getElementById('purchaseButton').addEventListener('click', ()=> {
    let IMP = window.IMP; // Iamport 객체 초기화
    const merchant_uid = 'merchant_uid' + new Date().getTime();
    IMP.init('imp53054186'); // 가맹점 식별코드

    //결제금액 사전등록
    $.ajax({
        url: "https://api.iamport.kr/payments/prepare",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            merchant_uid: merchant_uid, // 가맹점 주문번호
            amount:Number(`${document.getElementById('productPrice').innerText.replace(/[,원]/g,"")}`), // 결제 예정금액
        }),
        success: function(response) {
            // 성공 시 실행할 코드

            IMP.request_pay({
                pg: 'html5_inicis',
                pay_method: 'card',
                merchant_uid: merchant_uid,
                name: `주문명: ${productDTO.productVO.name}`,
                amount:Number(`${document.getElementById('productPrice').innerText.replace(/[,원]/g,"")}`),
                buyer_email: 'email',
                buyer_name: 'nick_name',
                buyer_tel: 'tel',
                buyer_addr: 'address',
                buyer_postcode: '123-456'
            }, function(rsp) {
                if (rsp.success) {
                    alert('결제가 완료되었습니다.');
                    window.location.href = '/';
                    // 결제 성공 후 처리
                } else {
                    alert('결제에 실패하였습니다. 에러 내용: ' + rsp.error_msg);
                    // 결제 실패 후 처리
                }
            });

            console.log(response);
        },
        error: function(xhr, status, error) {
            // 실패 시 실행할 코드
            console.error(xhr, status, error+"사전등록과 비교실패");
        }
    });

});