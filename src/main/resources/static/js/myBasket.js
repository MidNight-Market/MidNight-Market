let myBasket;

// 비동기로 불러오기 전에 체크된 체크박스 저장
let checkedCheckboxes = [];

// 전체 선택 및 해제
$('#basketCheckboxAll').click(function () {
    const selectAllCheckbox = $('#basketCheckboxAll');
    $('.basketCheckbox').prop('checked', selectAllCheckbox.prop('checked'));

    // 전체 선택 체크된 경우 모두 저장
    if (selectAllCheckbox.prop('checked')) {
        checkedCheckboxes = $('.basketCheckbox').map(function () {
            return this.value;
        }).get();
    } else {
        checkedCheckboxes = [];
    }
});

// 다른 버튼 선택 해제 시 전체 선택도 선택 해제
$(document).on('click', '.basketCheckbox', function () {
    const allChecked = $('.basketCheckbox:checked').length === $('.basketCheckbox').length;
    $('#basketCheckboxAll').prop('checked', allChecked);

    // 체크박스 체크 상태 저장
    if ($(this).prop('checked')) {
        checkedCheckboxes.push($(this).val());
    } else {
        checkedCheckboxes = checkedCheckboxes.filter(id => id !== $(this).val());
    }
});

// 선택 삭제
$('#select-delete').click(function (event) {

    event.preventDefault();

    if(checkedCheckboxes.length === 0){ //체크가 모두 안되어 있을경우
        return;
    }

    if(!confirm('정말로 삭제하시겠습니까?')){
    event.preventDefault(); // 기본 동작을 막음
        return;
    }

    let checkedValues = [];
    $('.basketCheckbox:checked').each(function () {
        checkedValues.push({customerId: customerId, productId: $(this).val()});
    });

    $.ajax({
        type: 'DELETE',
        url: '/basket/delete',
        contentType: 'application/json',
        data: JSON.stringify(checkedValues),
        success: function (response) {
            console.log(response);
            // 리스트 뿌리기
            const checkedLength = checkedValues.length;
            let basketBadge = document.getElementById('basketBadge');
            basketBadge.innerText = String(parseInt(basketBadge.innerText) - checkedLength);

            //1개만 남을경우 높이가 위에가 너무 떠서 설정

            spreadMyBasketList(customerId);

        }
    });
});

// 상품 계산 및 리스트 뿌리기
$(document).on('click', '.box1 button', function () {
    const productId = $(this).data('productid');
    const qty = $(this).data('qty');

    const data = {
        customerId: customerId,
        productId: productId,
        qty: qty
    };

    $.ajax({
        type: 'PUT',
        url: '/basket/update',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            console.log(response);

            // 리스트 뿌리기
            spreadMyBasketList(customerId);
        }
    });
});

// 리스트 뿌리기
function spreadMyBasketList(customerId) {

    $.ajax({
        type: 'GET',
        url: '/basket/myBasketList',
        contentType: 'application/json',
        data: {
            customerId: customerId
        },
        success: function (response) {

            myBasket = response;
            console.log(myBasket);

            myBasketList(response);
            // 체크되어 있는 상품들을 다시 체크 처리
            $('.basketCheckbox').each(function () {
                if (checkedCheckboxes.includes($(this).val())) {
                    $(this).prop('checked', true);
                }
            });

            // 전체 선택 체크 확인
            const allChecked = $('.basketCheckbox:checked').length === $('.basketCheckbox').length;
            $('#basketCheckboxAll').prop('checked', allChecked);
        }
    });
}

function myBasketList(response) {
    let total_price = 0;
    let payment_price = 0;

    const ul = $('#basketList');
    ul.empty();

    if (response.length > 0) {
        for (let i = 0; i < response.length; i++) {
            total_price += response[i].qty * response[i].productVO.price;
            payment_price += response[i].qty * response[i].productVO.discountPrice;
            let li = $('<li class="basketList-li"></li>');

            const price = response[i].qty > 0 ? (response[i].qty * response[i].productVO.discountPrice).toLocaleString('ko-KR') + ' 원' : '품절된 상품';

            li.html(`
                    <label class="checkbox_label">
                        <input type="checkbox" class="basketCheckbox" value="${response[i].productId}" />
                        <span class="checkbox_icon"></span>
                    <div class="product-image-box">
                        <img src="${response[i].productVO.mainImage}" alt="이미지">
                    </div>
                    </label>
                    <div class="product-desc-box">
                        <p>${response[i].productVO.name}</p>
                        <p>${response[i].productVO.description}</p>
                    </div>
                    <div class="calc-box">
                        <div class="box1">
                                <button type="button" class="-" data-productId="${response[i].productId}" data-qty="${response[i].qty - 1}" ${response[i].qty <= 1 ? 'disabled' : ''}>-</button>
                            <div class="box2">
                                <span>${response[i].qty}</span>
                            </div>
                    <button type="button" class="+" data-productId="${response[i].productId}" data-qty="${response[i].qty + 1}" ${response[i].qty >= response[i].productVO.totalQty ? 'disabled' : ''}>+</button>
                        </div>
                    </div>
                    <div class="product-price-box">
                        <p style="font-weight: 500">${(response[i].productVO.discountPrice * response[i].qty).toLocaleString('ko-KR')} 원</p>
                    ${response[i].productVO.discountRate != 0 ? `<s style="font-size: 13px; font-weight: 400;">${(response[i].productVO.price * response[i].qty).toLocaleString('ko-KR')} 원</s>` : ''}
                    </div>
            `);

            ul.append(li);

        }

        discount_price = payment_price - total_price;
        $('#product-price-text').text(total_price.toLocaleString('ko-KR') + ' 원');
        $('#product-discount-price-text').text(discount_price.toLocaleString('ko-KR') + ' 원');
        $('#product-total-price-text').text(payment_price.toLocaleString('ko-KR') + ' 원');
        $('#basketBadge').text(String(response.length));
    }else{
        let li = $('<li><div style="width: 100%; height: 100%; display: flex; justify-content: center; align-items: center"> <p style="color: rgb(181, 181, 181); font-weight: 400">장바구니에 등록한 상품이 존재하지 않습니다.</p> </div></li>');
        ul.append(li);
        $('#product-price-text').text( ' 0원');
        $('#product-discount-price-text').text(' 0원');
        $('#product-total-price-text').text(' 0원');
    }
}

//주문하기를 누를 경우
document.getElementById('orders-button').addEventListener('click', (e) => {


    //글자누르면 인식 안될 수 있기 때문에 상위버튼 찾기
    const button = e.target.closest('#orders-button');

    if (button) {

        if(!confirm('장바구니에 담긴 상품들을 주문하시겠습니까?')){
            e.preventDefault();
            return;
        }

        let soldOutQuantity = myBasket.filter(item => item.qty === 0);

        //주문할 상품이 하나인데 품절 상품일 경우 취소
        if (myBasket.length === 1 && myBasket[0].qty === 0) {
            alert('품절된 상품입니다.')
            return;
        }

        if (soldOutQuantity.length > 0) {

            // 품절된 상품이 존재하는 경우
            if (!confirm(`장바구니 상품 중 품절된 상품이 존재합니다.\n품절된 상품을 제외하고 주문하시겠습니까?`)) {
                // "아니요"를 선택한 경우 아무런 작업도 하지 않음
                return;
            }
        }

        const merchant_uid = 'merchent_uid' + new Date().getTime();

        const payData = {
            merchantUid: merchant_uid,
            customerId: customerId
        };

        $.ajax({
            type: 'POST',
            url: '/payment/basketPost',
            contentType: 'application/json',
            data: JSON.stringify(payData),
            success: function (rsp) {

                if (rsp === 'excess_quantity') {
                    alert('수량이 맞지 않습니다.\n다시 시도해주세요.');
                    location.reload();
                }

                if(rsp === 'quantity_exhaustion'){
                    alert('현재 장바구니에 등록된 상품이 존재하지 않습니다.\n상품을 하나이상 담고 진행해주세요.');
                    return;
                }

                if (rsp === 'post_success') {
                    alert('주문서 페이지로 이동합니다.');
                    //form데이터 merchantUid를 order페이지에 보낸다
                    document.getElementById('merchantUid').value = merchant_uid;
                    document.getElementById('orderMoveForm').submit();
                }

            }
        });

    }

});
