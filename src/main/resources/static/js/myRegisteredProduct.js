document.addEventListener('click', (e) => {
    if (e.target.classList.contains('updateQty')) {
        // 버튼의 부모 요소(li)에서 .qty 클래스를 가진 input 요소 찾기
        const productId = e.target.dataset.id;
        const qtyInput = e.target.closest('li').querySelector('.qty').value;
        console.log('ID:', productId);
        console.log('수량:', qtyInput);

        if(qtyInput.length == 0 || qtyInput.val == ''){
            alert('변경하실 상품의 수량을 입력해주세요.');
            return;
        }

        const sendData = {
            id : productId,
            totalQty : qtyInput
        }

        sendUpdateQtyToSeller(sendData).then(result=>{
            if(result == 'true'){
                alert('수량변경에 성공하였습니다.');
                e.target.closest('ul').querySelector('.productQty').innerText = qtyInput;
                e.target.closest('li').querySelector('.qty').value = '';

            }
        })

    }
});

async function sendUpdateQtyToSeller(sendData){

    try {
        const url = '/seller/productQtyUpdate';
        const config = {
            method : 'put',
            headers : {
                'content-type' : 'application/json; charset = utf-8'
            },
            body : JSON.stringify(sendData)
        }

        const resp = await  fetch(url,config);
        const result = await resp.text();
        return result;

    }catch(error){
        console.log(error);
    }

}