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


let modal = document.getElementById("myModal");

// 모달 열기 (타입, 상품코드)
function openModal(type, productId) {
    let modalContent = document.getElementById("modalContent");
    console.log(productId);
    // 초기화
    modalContent.innerHTML = "";

    // 타입에 따라 모달 내용 설정
    if (type === 'quantity') {
        modalContent.innerHTML = `
                    <div class="form-group">
                        <label for="quantity">수량:</label>
                        <input type="number" id="quantity" name="quantity" value="1" min="1">
                    </div>
                    <div class="form-group">
                        <button onclick="updateQuantity()">수량 추가</button>
                    </div>
                `;
    } else if (type === 'price') {
        modalContent.innerHTML = `
                    <div class="form-group">
                        <label for="price">새로운 가격:</label>
                        <input type="number" id="price" name="price" step="0.01" min="0.01">
                    </div>
                    <div class="form-group">
                        <button onclick="updatePrice()">가격 변경</button>
                    </div>
                `;
    } else if (type === 'discount') {
        modalContent.innerHTML = `
                    <div class="form-group">
                        <label for="discount">할인율 (%):</label>
                        <input type="number" id="discount" name="discount" value="0" min="0" max="100">
                    </div>
                    <div class="form-group">
                        <button onclick="updateDiscount()">할인율 변경</button>
                    </div>
                `;
    }

    modal.style.display = "block";
}

// 모달 닫기
function closeModal() {
    modal.style.display = "none";
}

// 수량 추가 함수
function updateQuantity() {
    let quantity = document.getElementById("quantity").value;
    alert("수량이 " + quantity + "개 추가되었습니다.");
    closeModal();
}

// 가격 변경 함수
function updatePrice() {
    let price = document.getElementById("price").value;
    alert("가격이 $" + price + "로 변경되었습니다.");
    closeModal();
}

// 할인율 변경 함수
function updateDiscount() {
    let discount = document.getElementById("discount").value;
    alert("할인율이 " + discount + "%로 변경되었습니다.");
    closeModal();
}

// 모달 외부 클릭 시 닫기 기능 제거
modal.onclick = function(event) {
    event.stopPropagation();
};

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};


