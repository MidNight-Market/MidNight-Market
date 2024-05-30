
document.getElementById('purchaseButton').addEventListener('click', ()=> {

    const data = {

     merchentUid : 'merchent_uid' + new Date().getTime(),
     amount : Number(
        `${document.getElementById('productPrice')
            .innerText.replace(/[,원]/g,"")}`),
     productId : Number(`${productDTO.productVO.id}`),
        customerId : 'oco0217@gmail.com',
     tel : '010-1234-5678',
     address : '경기도 인천시 제주도구'
    }

    console.log(data);

    let IMP = window.IMP; // Iamport 객체 초기화
    IMP.init('imp53054186'); // 가맹점 식별코드

    IMP.request_pay({
        pg: 'html5_inicis',
        pay_method: 'card',
        merchant_uid: data.mercant_uid,
        name: `${productDTO.productVO.name}`,
        amount:Number(`${document.getElementById('productPrice').innerText.replace(/[,원]/g,"")}`),
        buyer_email: data.customerId,
        buyer_name: 'nick_name',
        buyer_tel: data.tel,
        buyer_addr: data.address,
        buyer_postcode: '123-456'
    }, function(rsp) {
        console.log(rsp);
        if (rsp.success) {

            $.ajax({
                type : 'POST',
                url : '/productBuy/register',
                contentType : 'application/json',
                data : JSON.stringify(data),
                success: function (response){
                    console.log("하이항");
                }
            });

            alert('결제가 완료되었습니다.');
            window.location.href = '/';
            // 결제 성공 후 처리
        } else {
            alert('결제에 실패하였습니다. 에러 내용: ' + rsp.error_msg);
            // 결제 실패 후 처리
        }
    });
});
