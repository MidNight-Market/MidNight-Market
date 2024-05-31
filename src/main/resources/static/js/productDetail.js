

let productQty = document.getElementById('productQty');
let productPrice = document.getElementById('productPrice');

// + 또는 - 를 눌렀을 경우 가격 계산 
document.addEventListener('click',(e)=>{

    console.log(productDTO.productVO.totalQty);

    if(e.target.id == '+'){

        productQty.innerText = parseInt(productQty.innerText) + 1;
        productPrice.innerText = (Number(productDTO.productVO.price) * Number(productQty.innerText)).toLocaleString('ko-KR') + ' 원';

        if(Number(productQty.innerText) == productDTO.productVO.totalQty){
            document.getElementById('+').disabled = true;
            document.getElementById('max-quantity-notice').innerText = '최대 수량입니다.'
            return;
        }

        if(parseInt(productQty.innerText) > 1){
            document.getElementById('-').disabled = false;
        }else{
            document.getElementById('-').disabled = true;
        }
        return;
    }

    if(e.target.id == '-'){
        console.log('추가');
        productQty.innerText = parseInt(productQty.innerText) - 1;
        productPrice.innerText = (Number(productDTO.productVO.price) * Number(productQty.innerText)).toLocaleString('ko-KR') + ' 원';

        if(parseInt(productQty.innerText) > 1){
            document.getElementById('-').disabled = false;
        }else{
            document.getElementById('-').disabled = true;
        }

        if(Number(productQty.innerText) != productDTO.productVO.totalQty){
            document.getElementById('+').disabled = false;
            document.getElementById('max-quantity-notice').innerText = ''
        }
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
            if(confirm(`${productDTO.productvo.name}`+ ' 를 장바구니에 저장했습니다. \n 장바구니 페이지로 이동하시겠습니까?')){
                location.href = '/product/basket';
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("에러: " + textStatus + ", " + errorThrown);
        }

    });
});
