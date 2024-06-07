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
        purchasedData = result;
        let div = document.getElementById('purchasedPage');
        div.innerHTML = '';
        let str = `<!--<p style="font-weight: bold; margin-bottom: 10px">전체 ${result.length}개</p>--> `;
        str += `<table class="table" style="text-align: center; font-size: 15px;">`;
        str += `<thead>`;
        str += `<th scope="col">주문날짜</th>`;
        str += `<th scope="col">상품</th>`;
        str += `<th scope="col">가격</th>`;
        str += `<th scope="col">수량</th>`;
        str += `<th scope="col">주문상태</th>`;
        str += `<th scope="col">리뷰작성</th>`;
        str += `</tr>`;
        str += `</thead>`;
        str += `<tbody>`;
        if (result.length > 0) {
            result.forEach((ordersVO, index) => {

                const price = ordersVO.payPrice.toLocaleString('ko-KR') + '원';
                const date = ordersVO.ordersDate.split(' ')[0];
                console.log(ordersVO.reviewComment);

                str += `<tr>`;
                str += `<th scope="row"><p>${date}</p></th>`;
                str += `<th scope="row">${ordersVO.productVO.name}</th>`;
                str += `<th scope="col">${price}</th>`;
                str += `<th scope="col">${ordersVO.qty}</th>`;
                str += `<th> <p>${ordersVO.status}</p> <button>환불하기</button> </th>`;
                if (ordersVO.reviewComment) {
                    str += `<th><span style="color: #0f5132; font-weight: 650" >작성완료</span></th>`;
                } else {
                    str += `<th><button class="review-button" data-index="${index}" data-bs-toggle="modal" data-bs-target="#staticBackdrop">작성하기</button> </th>`;
                }
                str += `</tr>`;
            });
            str += `</tbody>`;
            str += `</table>`;
            div.innerHTML += str;
        } else {
            div.innerText = '주문하신 상품이 없습니다.';
        }

    })
}

