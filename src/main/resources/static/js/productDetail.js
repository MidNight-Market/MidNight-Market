

let productQty = document.getElementById('productQty');
let productPrice = document.getElementById('productPrice');

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
})