// 비동기로 불러오기 전에 체크된 체크박스 저장
let checkedCheckboxes = [];

// 전체 선택 및 해제
$('#basketCheckboxAll').click(function() {
    const selectAllCheckbox = $('#basketCheckboxAll');
    $('.basketCheckbox').prop('checked', selectAllCheckbox.prop('checked'));
});

// 다른 버튼 선택 해제 시 전체 선택도 선택 해제
$(document).on('click', '.basketCheckbox', function() {
    const allChecked = $('.basketCheckbox:checked').length === $('.basketCheckbox').length;
    $('#basketCheckboxAll').prop('checked', allChecked);
});

// 선택 삭제
$('#select-delete').click(function(event) {
    event.preventDefault(); // 기본 동작을 막음

    let checkedValues = [];
    $('.basketCheckbox:checked').each(function() {
        checkedValues.push({customerId: `${myBasket[0].customerId}`, productId: $(this).val()});
    });

    $.ajax({
        type: 'DELETE',
        url: '/basket/delete',
        contentType: 'application/json',
        data: JSON.stringify(checkedValues),
        success: function (response) {
            console.log(response);
            // 리스트 뿌리기
            spreadMyBasketList(`${myBasket[0].customerId}`);
        }
    });
});

// 상품 계산 및 리스트 뿌리기
$(document).on('click', '.box1 button', function() {
    const productId = $(this).data('productid');
    const qty = $(this).data('qty');

    const data = {
        customerId: `${myBasket[0].customerId}`,
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
            spreadMyBasketList(`${myBasket[0].customerId}`);
        }
    });
});

// 리스트 뿌리기
function spreadMyBasketList(email) {

    checkedCheckboxes = []; // 비동기 호출 전에 checkedCheckboxes 배열 초기화

    $.ajax({
        type: 'GET',
        url: '/basket/myBasketList',
        contentType: 'application/json',
        data: {
            customerId: email
        },
        success: function (response) {
            myBasketList(response);

            // 비동기로 불러온 후, 체크되어 있는 상품들을 다시 체크 처리 및 checkedCheckboxes 배열에 추가
            $('.basketCheckbox').each(function() {
                const productId = $(this).val();
                const isChecked = $(this).prop('checked'); // 현재 체크 상태 확인

                console.log('Checkbox value:', productId, 'Checked:', isChecked);

                if (isChecked) {
                    checkedCheckboxes.push(productId);
                }
            });

            // 모든 체크박스를 선택
            $('.basketCheckbox').prop('checked', true);

            // 전체 선택 체크 확인
            const allChecked = $('.basketCheckbox:checked').length == $('.basketCheckbox').length;
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

            let li = $('<li></li>');

            const price = (response[i].qty * response[i].productVO.discountPrice).toLocaleString('ko-KR') + ' 원';

            li.html(`
                <a href="">
                    <label class="checkbox_label">
                        <input type="checkbox" class="basketCheckbox" value="${response[i].productId}" />
                        <span class="checkbox_icon"></span>
                    </label>
                </a>
                <a href="/product/detail?id=${response[i].productId}">
                    <div class="image1">
                        <img src="${response[i].productVO.mainImage}" style="width: 100%; height: 100%"/>
                    </div>
                </a>
                <a href="/product/detail?id=${response[i].productId}">
                    <div class="name1">${response[i].productVO.name}</div>
                </a>
                <div class="box1">
                    <button type="button" class="-" data-productId="${response[i].productId}" data-qty="${response[i].qty - 1}" ${response[i].qty <= 1 ? 'disabled' : ''}>-</button>
                    <div class="box2">
                        <span>${response[i].qty}</span>
                    </div>
                    <button type="button" class="+" data-productId="${response[i].productId}" data-qty="${response[i].qty + 1}" ${response[i].qty >= response[i].productVO.totalQty ? 'disabled' : ''}>+</button>
                </div>
                <div class="price">
                    <span>${price}</span>
                </div>
            `);

            ul.append(li);

        }
        discount_price = payment_price - total_price;
        $('#product-price-text').text(total_price.toLocaleString('ko-KR') + ' 원');
        $('#product-discount-price-text').text(discount_price.toLocaleString('ko-KR') + ' 원');
        $('#product-total-price-text').text(payment_price.toLocaleString('ko-KR') + ' 원');
    } else {
        let li = $('<li>장바구니에 등록한 상품이 없습니다.</li>');
        ul.append(li);
    }
}
