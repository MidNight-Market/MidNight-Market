//상품 정보 저장
let purchasedData;

async function getMyPurchasedProductListFromServer(customerId) {

    try {
        const response = await fetch('/orders/getMyPurchasedProductList/' + customerId);
        const result = await response.json();
        return result;
    } catch (e) {
        console.log(e);
    }
}

function spreadMyPurchasedProductList(customerId) {
    getMyPurchasedProductListFromServer(customerId).then(result => {
        console.log(result);
        let div = document.getElementById('purchasedPage');
        div.innerHTML = '';
        let str = '';
        if(result.length > 0){
            result.forEach((value) => {

                const [year, month, day,hour, minute] = value.ordersDate.match(/\d+/g);

                str += `<div class="purchased-date-box">`;
                str += `<span>${year}.${month}.${day} (${hour}시 ${minute}분)</span>`;
                str += `</div>`;
                str += `<div class="purchased-box">`;
                str += `<div class="purchased-image-box">`;
                str += `<img src="${value.productVO.mainImage}" alt="사진없음">`;
                str += `</div>`;
                str += `<div class="purchased-product-info-box">`;
                str += `<p>${value.productVO.name}</p>`;
                str += `<span class="purchased-price">${value.payPrice}원</span>`;
                str += `<span class="purchased-quantity">${value.qty}개 구매</span>`;
                str += `</div>`;
                str += `<div class="purchased-status">`;
                str += `<span>${value.status}</span>`;
                str += `</div>`;
                str += `<div class="purchases-select-button-box">`;
                str += `<button>주문확정</button>`;
                str += `<button>환불하기</button>`;
                str += `</div>`;
                str += `</div>`;
            });
            div.innerHTML = str;
        }else{
            div.innerHTML = '<h1 style="font-size: 32px; font-weight: 700">주문 내역이 존재하지 않습니다.</h1>';
        }
    });
}

