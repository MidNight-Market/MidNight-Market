
document.getElementById('purchaseButton').addEventListener('click', ()=> {
    let IMP = window.IMP; // Iamport 객체 초기화
    IMP.init('imp53054186'); // 가맹점 식별코드

    IMP.request_pay({
        pg: 'html5_inicis',
        pay_method: 'card',
        merchant_uid: 'merchant_uid' + new Date().getTime(),
        name: `${productDTO.productVO.name}`,
        amount:Number(`${document.getElementById('productPrice').innerText.replace(/[,원]/g,"")}`),
        buyer_email: 'email',
        buyer_name: '윤찬웅',
        buyer_tel: '010-1234-5678',
        buyer_addr: 'address',
    }, function(rsp) {
        console.log(rsp);
        //결제 성공 여부
        // if (rsp.success) {
        //     alert('결제가 완료되었습니다.');
        //     window.location.href = '/';
        //     // 결제 성공 후 처리
        // } else {
        //     alert('결제에 실패하였습니다. 에러 내용: ' + rsp.error_msg);
        //     // 결제 실패 후 처리
        // }
    });
});