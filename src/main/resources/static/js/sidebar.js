function openPopup() {
    window.open('/popup', 'PopupWindow', 'width=700,height=700,scrollbars=yes,left=700,top=230, scrollbars = yes');
}
document.getElementById('bannerBasket').addEventListener('click', ()=>{
    if(role != "role_user" || role == null){
        if(confirm("구매자만 사용가능한 서비스 입니다. 로그인 하시겠습니까? ")){
            window.location.href = "/login/form";
        }
    }else{
        window.location.href = '/basket/myBasket';
    }
});
document.getElementById('bannerSlang').addEventListener('click', ()=>{
    if(role != "role_user" || role == null){
        if(confirm("구매자만 사용가능한 서비스 입니다. 로그인 하시겠습니까? ")){
            window.location.href = "/login/form";
        }
    }else{
        window.location.href = '/customer/myPage?defaultTab=d3';
    }
});document.getElementById('bannerInquiry').addEventListener('click', ()=>{
    if((role != "role_user" &&  role != "role_seller") || role == null ){
        if(confirm("구매자만 사용가능한 서비스 입니다. 로그인 하시겠습니까? ")) {
            window.location.href = "/login/form";
        }
    }else{
        openPopup();
    }
});