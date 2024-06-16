currentId = '(kakao)ehdwo13@kakao.com';
document.body.addEventListener('click',(e)=>{
    let content = document.getElementById('firstContent');
    if(e.target.id == "first"){
        content.innerText = '';
        let str = "";
        getOrderList(currentId).then(result =>{
            console.log(result);
            str += '<table>';
            str += '<thead>';
            str += '<tr>';
            str += '<th>상품번호</th>';
            str += '<th>수량</th>';
            str += '<th>가격</th>';
            str += '<th>주문날짜</th>';
            str += '<th>주문상태</th>';
            str += '</tr>';
            str += '</thead>';
            str += '<tbody>';
            result.forEach(orders => {
                str += '<tr>';
                str += `<td>${orders.productId}</a></td>`;
                str += `<td>${orders.qty}</td>`;
                str += `<td>${orders.payPrice}</td>`;
                str += `<td>${orders.ordersDate}</td>`;
                str += `<td>${orders.status}</td>`;
                str += '</tr>';
            });
            str += '</tbody>';
            str += '</table>';
            content.innerHTML = str;
        })
    }
})
async function getOrderList(currentId){
    try {
        const resp = await fetch(`/orders/getList?currentId=${currentId}`); // GET 요청
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}
document.getElementById('option2Div').style.display = 'none';
document.body.addEventListener('click',(e)=>{
    console.log(e.target.id);
    if(e.target.id === "option1"){
        document.getElementById('option2Div').style.display = 'none';
        document.getElementById('option1Div').style.display = '';
    }else if(e.target.id === "option2"){
        document.getElementById('option2Div').style.display = '';
        document.getElementById('option1Div').style.display = 'none';
    }
})
