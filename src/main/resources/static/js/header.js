console.log("test");

if(document.getElementById("isReset")!= null){
    const width = 600;
    const height = 600;

    // 화면의 크기를 가져옴
    const screenWidth = window.screen.width;
    const screenHeight = window.screen.height;

    // 팝업 창의 위치를 계산
    const left = (screenWidth / 2) - (width / 2);
    const top = (screenHeight / 2) - (height / 2);
    alert("비밀번호가 초기화되었습니다. 다시 설정해주세요. ")
    openPop = window.open('/login/reset', '비밀번호변경', `width=${width},height=${height},scrollbars=yes,left=${left},top=${top}`);

    let id = document.getElementById('idValue').innerText;
    openPop.onload = function (){
        openPop.document.getElementById('id').value = id;
    }
    const popupInterval = setInterval(function() {
        if (openPop.closed) {
            clearInterval(popupInterval);
            logout().then(result=>{
                console.log(result);
                location.reload();
            })
        }
    }, 500);
}
async function logout() {
    try {
        const url = '/logout';
        const config = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        };
        const response = await fetch(url, config);

        if (response.ok) {
            window.location.href = '/login'; // 로그인 페이지로 리디렉션
        } else {
            console.error('로그아웃 실패:', response.statusText);
        }
    } catch (error) {
        console.error('로그아웃 중 오류 발생:', error);
    }
}


$('#autoComplete').autocomplete({
    source : function(request, response) { //source: 입력시 보일 목록
        $.ajax({
            url : "/ajax/autocomplete.do"
            , type : "POST"
            , dataType: "JSON"
            , data : {value: request.term}	// 검색 키워드
            , success : function(data){ 	// 성공
                response(
                    $.map(data.resultList, function(item) {
                        return {
                            label : item.name    	// 목록에 표시되는 값
                            , value : item.name		// 선택 시 input창에 표시되는 값
                        };
                    })
                );    //response
            }
            ,error : function(){ //실패
                alert("오류가 발생했습니다.");
            }
        });
    }
    ,focus : function(event, ui) { // 방향키로 자동완성단어 선택 가능하게 만들어줌
        return false;
    }
    ,minLength: 1// 최소 글자수
    ,autoFocus : true // true == 첫 번째 항목에 자동으로 초점이 맞춰짐
    ,delay: 100	//autocomplete 딜레이 시간(ms)
    ,select : function(evt, ui) {
        // 아이템 선택시 실행 ui.item 이 선택된 항목을 나타내는 객체, lavel/value/idx를 가짐
        console.log(ui.item.label);
    }
});

document.getElementById('autoComplete').addEventListener('focus', ()=>{
    document.getElementById('recentSearch').style.display = 'block';
    let searchHistory = window.localStorage.getItem("searchHistory");
    let parseData = JSON.parse(searchHistory);
    for(let i=0; i<parseData.length; i++){
       document.getElementById('recentSearch').innerHTML += '<a href="#">'+parseData[i]+'</a>'+"<br>";
    }
});
document.getElementById('autoComplete').addEventListener('blur', ()=>{
    document.getElementById('recentSearch').innerText = '';
    document.getElementById('recentSearch').style.display = 'none';
});
document.getElementById('searchButton').addEventListener('click', ()=>{
    let searchVal = document.getElementById('autoComplete').value;
    let prevData = window.localStorage.getItem("searchHistory");
    if(prevData == null){
        let saveHistoryArr = [];
        saveHistoryArr.push(searchVal);
        window.localStorage.setItem("searchHistory",JSON.stringify(saveHistoryArr));
    }else{
        let prevArr = JSON.parse(prevData);
        prevArr.push(searchVal);
        window.localStorage.setItem("searchHistory", JSON.stringify(prevArr));
    }
});