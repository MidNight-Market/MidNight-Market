let productQty = document.getElementById('productQty');
let productPrice = document.getElementById('productPrice');

document.addEventListener('click',(e)=>{

    console.log(productDTO.productVO.totalQty);

    if(e.target.id == '+'){

        productQty.innerText = parseInt(productQty.innerText) + 1;
        productPrice.innerText = Number(productDTO.productVO.price * productQty.innerText)+'원';

        if(Number(productQty.innerText) == productDTO.productVO.totalQty){
            document.getElementById('+').disabled = true;
            document.getElementById('+').innerHTML = '<span style="color: red">최대 수량입니다.</span>'
            return;
        }

        if(parseInt(productQty.innerText) > 0){
            document.getElementById('-').disabled = false;
        }else{
            document.getElementById('-').disabled = true;
        }

        return;
    }

    if(e.target.id == '-'){
        console.log('추가');
        productQty.innerText = parseInt(productQty.innerText) - 1;
        productPrice.innerText = Number(productDTO.productVO.price * productQty.innerText)+'원';

        if(parseInt(productQty.innerText) > 0){
            document.getElementById('-').disabled = false;
        }else{
            document.getElementById('-').disabled = true;
        }

        return;
    }



})