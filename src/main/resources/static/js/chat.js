currentId = '(kakao)ehdwo13@kakao.com';
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
});