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
        str += `<table class="table">`;
        str += `<thead class="thead-border">`;
        str += `<tr>`;
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
                str += `<td><p>${date}</p></td>`;
                str += `<td>${ordersVO.productVO.name}</td>`;
                str += `<td>${price}</td>`;
                str += `<td>${ordersVO.qty}</td>`;
                str += `<td> <p>${ordersVO.status}</p> <button class="refundButton" data-id="${ordersVO.id}">환불하기</button> </td>`;
                if (ordersVO.reviewComment) {
                    str += `<td><span style="color: #0f5132; font-weight: 650" >작성완료</span></td>`;
                } else {
                    str += `<td><button class="review-button" data-index="${index}" data-bs-toggle="modal" data-bs-target="#staticBackdrop">작성하기</button> </td>`;
                }
                str += `</tr>`;
            });
            str += `</tbody>`;
            str += `</table>`;
            div.innerHTML += str;

            //환불버튼
            const refundButtons = document.querySelectorAll('.refundButton');
            refundButtons.forEach(button => {
                button.addEventListener('click', (e) => {
                    const id = e.target.dataset.id;
                    console.log(id);

                    // POST 요청 보내기
                    fetch('/payment/refund', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ id: id })
                    })
                        .then(response => response.text())
                        .then(data => {
                            console.log('Success:', data);
                            // 서버 응답 처리
                            alert(data);
                        })
                        .catch((error) => {
                            console.error('Error:', error);
                        });
                });
            });



        } else {
            div.innerText = '주문하신 상품이 없습니다.';
        }

    })
}

