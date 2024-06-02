

let productQty = document.getElementById('productQty');
let productPrice = document.getElementById('productPrice');

// + 또는 - 를 눌렀을 경우 가격 계산 
document.addEventListener('click',(e)=>{

    //수량 추가 버튼
    if(e.target.id == '+'){

        productQty.innerText = Number(productQty.innerText) + 1; //수량 +1

        productPrice.innerText =
            (Number(productDTO.productVO.price) * Number(productQty.innerText))
                .toLocaleString('ko-KR') + ' 원';
    }

    //수량 감소 버튼
    if(e.target.id == '-'){
        //console.log('추가');
        productQty.innerText = Number(productQty.innerText) - 1; //수량 -1
        productPrice.innerText =
            (Number(productDTO.productVO.price) * Number(productQty.innerText))
            .toLocaleString('ko-KR') + ' 원';
    }


    //현재 주문할 수량이 잔여수량보다 크다면 + 비활성화
    if(Number(productQty.innerText) != productDTO.productVO.totalQty){
        document.getElementById('+').disabled = true;
        document.getElementById('max-quantity-notice').innerText = '최대 수량입니다.'
    }
    
    //주문개수가 1보다 커지면 - 버튼 활성화
    if(parseInt(productQty.innerText) > 1){
        document.getElementById('-').disabled = false;
    }else{
        document.getElementById('-').disabled = true;
    }

    //주문개수가 잔여개수랑 같아질때 버튼 비활성화
    if(Number(productQty.innerText) != productDTO.productVO.totalQty){
        document.getElementById('+').disabled = false;
        document.getElementById('max-quantity-notice').innerText = ''
    }else{
        document.getElementById('+').disabled = true;
        document.getElementById('max-quantity-notice').innerText = '최대수량 입니다';
    }

});





//장바쿠니 버튼을 클릭했을 경우
document.getElementById('basketButton').addEventListener('click',()=>{

    productData = {
        customerId: 'oco0217@gmail.com', //고객이메일
        productId : Number(`${productDTO.productVO.id}`), //상품고유번호
        qty : Number(document.getElementById('productQty').innerText), //주문할 수량
    };

    $.ajax({
        type : 'POST',
        url : '/basket/register',
        contentType : 'application/json',
        data : JSON.stringify(productData),
        success: function (rsp){
            console.log("응답 오느지 확인" + rsp);


            const message = rsp.replace(/\d+$/, '');
            const number = rsp.replace(/\D/g, '');

            //장바구니에 넣을 때 수량이 초과일 경우
            if(message == 'excess_quantity'){
                if (confirm(`${productDTO.productVO.name} 상품은 최대 주문 수량이 ${number} 개 입니다. \n장바구니 페이지로 이동하시겠습니까?`)) {
                    location.href = '/basket/myBasket';
                }
            }

            //장바구니를 추가했을경우
            if (rsp == 'register_success'){
                if (confirm(`${productDTO.productVO.name}` + `를 장바구니에 저장했습니다. \n 장바구니 페이지로 이동하시겠습니까?`)) {
                location.href = '/basket/myBasket';
                }
            }
            
            //더 담아넣을 경우
            if(message == 'update_success'){
                if (confirm(`한번더 담으셨네요! \n수량이 ${number} 개가 추가되었습니다. \n장바구니 페이지로 이동하시겠습니까?`)) {
                    location.href = '/basket/myBasket';
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("에러: " + textStatus + ", " + errorThrown);
        }

    });
});


