
document.getElementById('purchaseButton').addEventListener('click', ()=> {
    let IMP = window.IMP; // Iamport 객체 초기화
    IMP.init('imp53054186'); // 가맹점 식별코드

    IMP.request_pay({
        pg: 'html5_inicis',
        pay_method: 'card',
        merchant_uid: 'merchant_uid' + new Date().getTime(),
        name: `주문명: ${productDTO.productVO.name}`,
        amount:`${document.getElementById('productPrice').innerText}`,
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
});