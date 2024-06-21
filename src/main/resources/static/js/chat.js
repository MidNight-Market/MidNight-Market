console.log(currentId);
window.addEventListener('scroll', function() {
    const scrollButton = document.getElementById('newChat');
    const topBtn = document.getElementById('topBtn');
    if (window.scrollY) {
        scrollButton.style.top = window.scrollY+690 + 'px';
        topBtn.style.top = window.scrollY+630 + 'px';
    }
    if(window.scrollY < 700) {
        topBtn.style.display = 'none';
    }else{
        topBtn.style.display = '';
    }
});
document.getElementById('topBtn').addEventListener('click',()=>{
    window.scrollTo(0,0);
})

//소켓채팅부분
document.getElementById('newChat').addEventListener('click',()=>{
    const newDiv = document.createElement('div');
    newDiv.classList.add('seller-input-container');
    const text = document.createElement('p');
    text.classList.add('seller-input-text');
    text.textContent = '판매자 아이디를 입력해주세요';
    const input = document.createElement('input');
    input.classList.add('seller-input-input');
    input.setAttribute('type', 'text');
    const button = document.createElement('button');
    button.classList.add('seller-input-button');
    button.textContent = '검색';
    button.addEventListener('click', () => {

    });
    newDiv.appendChild(text);
    newDiv.appendChild(input);
    newDiv.appendChild(button);
    document.body.appendChild(newDiv);
})