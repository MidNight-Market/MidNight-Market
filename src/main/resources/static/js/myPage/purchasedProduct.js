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
        if (result.length > 0) {
            result.forEach((value) => {

                const [year, month, day, hour, minute] = value.ordersDate.match(/\d+/g);

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
                str += `<span style="color: ${displayColorByStatus(value.status)};">${value.status}</span>`;
                str += `</div>`;
                str += `<div class="purchases-select-button-box">`;
                if (value.status === '주문완료') {
                    str += `<button>주문확정</button>`;
                    str += `<button class="refundButton" data-id="${value.id}">환불하기</button>`;
                }
                str += `</div>`;
                str += `</div>`;
            });
            div.innerHTML = str;

            //환불버튼
            const refundButtons = document.querySelectorAll('.refundButton');
            refundButtons.forEach(button => {
                button.addEventListener('click', (e) => {

                    if (!confirm(`정말로 환불을 진행하시겠습니까?`)) {
                        e.preventDefault();
                        return;
                    }

                    const id = e.target.dataset.id;
                    console.log(id);

                    // POST 요청 보내기
                    fetch('/payment/refund', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({id: id})
                    })
                        .then(response => response.text())
                        .then(data => {
                            console.log('Success:', data);
                            // 서버 응답 처리
                            alert(data);
                            spreadMyPurchasedProductList(customerId);
                        })
                        .catch((error) => {
                            console.error('Error:', error);
                        });
                });
            });

        } else {
            div.innerHTML = '<h1 style="font-size: 32px; font-weight: 700">주문 내역이 존재하지 않습니다.</h1>';
        }
    });
}

function displayColorByStatus(status) {

    let color;

    switch (status) {
        case '배송완료' :
            color = 'forestgreen';
            break;

        case '환불처리' :
            color = 'red';
            break;

        default :
            color = 'black';
            break;
    }
    return color;
}


// function displayButtonByStatus(status){
//
//     let result;
//     switch (status){
//         case '주문완료' :
//             result = `<button>주문확정</button>
// <button class="refundButton" data-id="${value.id}">환불하기</button>`;
//             return;
//         case '배송완료' :
//             result =
//     }
//
// }

