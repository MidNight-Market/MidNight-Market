if (document.getElementById("isReset") != null) {
    const width = 600;
    const height = 600;

    // 화면의 크기를 가져옴
    const screenWidth = window.screen.width;
    const screenHeight = window.screen.height;

    // 팝업 창의 위치를 계산
    const left = (screenWidth / 2) - (width / 2);
    const top = (screenHeight / 2) - (height / 2);
    alert("비밀번호가 초기화되었습니다.\n다시 설정해주세요.");
    openPop = window.open('/login/reset', '비밀번호변경', `width=${width},height=${height},scrollbars=yes,left=${left},top=${top}`);

    let id = document.getElementById('idValue').innerText;
    openPop.onload = function () {
        openPop.document.getElementById('id').value = id;
    }
    const popupInterval = setInterval(function () {
        if (openPop.closed) {
            clearInterval(popupInterval);
            logout().then(result => {
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
    source: function (request, response) {
        $.ajax({
            url: "/ajax/autocomplete.do",
            type: "POST",
            dataType: "JSON",
            data: {value: request.term},
            success: function (data) {
                response(
                    $.map(data.resultList, function (item) {
                        return {
                            label: item.name,
                            value: item.name
                        };
                    })
                );
            },
            error: function () {
                alert("오류가 발생했습니다.");
            }
        });
    },
    focus: function (event, ui) {
        return false;
    },
    minLength: 1,
    autoFocus: true,
    delay: 100,
    select: function (evt, ui) {
        console.log(ui.item.label);
    },
    open: function () {
        $('#recentSearch').hide();
    }
});

document.getElementById('autoComplete').addEventListener('focus', () => {
    document.getElementById('recentSearch').style.display = 'block';
    let searchHistory = window.localStorage.getItem("searchHistory");
    let parseData = JSON.parse(searchHistory);
    let count = 1;
    document.getElementById('recentSearch').innerHTML = ''; // Clear previous entries
    for (let i = 0; i < parseData.length; i++) {
        document.getElementById('recentSearch').innerHTML += `<a href="/product/list?type=product&search=${parseData[i]}" style="color: black">${parseData[i]}</a><button type="button" id="delBtn${count}" style="border: none; background-color: white">X</button><br>`;
        count++;
    }
});


document.getElementById('searchButton').addEventListener('click', () => {
    let searchVal = document.getElementById('autoComplete').value;
    let prevData = window.localStorage.getItem("searchHistory");
    if (prevData == null) {
        let saveHistoryArr = [];
        saveHistoryArr.push(searchVal);
        window.localStorage.setItem("searchHistory", JSON.stringify(saveHistoryArr));
    } else {
        let preletr = JSON.parse(prevData);
        preletr.push(searchVal);
        window.localStorage.setItem("searchHistory", JSON.stringify(preletr));
    }

    //요기 수정
    let search = encodeURI(searchVal);
    window.location.href = `/product/list?type=product&search=${search}`;
});

document.getElementById('recentSearch').addEventListener('click', (e) => {
    let str = e.target.id;
    if (str.includes('delBtn')) {
        console.log("test");
        let id = e.target.id;
        let num = parseInt(id.replace('delBtn', '')); // Parse button number
        let storage = window.localStorage.getItem("searchHistory");
        let parseData = JSON.parse(storage);

        // num-1 위치의 요소를 제거
        parseData.splice(num - 1, 1);

        // 수정된 배열을 다시 JSON 문자열로 변환하여 저장
        let stringifyArr = JSON.stringify(parseData);
        window.localStorage.setItem("searchHistory", stringifyArr);

        // 최근 검색어 목록 업데이트
        document.getElementById('recentSearch').innerHTML = '';
        let count = 1;
        for (let i = 0; i < parseData.length; i++) {
            document.getElementById('recentSearch').innerHTML += `<a href="/product/list?type=product&search=${parseData[i]}" style="color: black">${parseData[i]}</a><button type="button" id="delBtn${count}" style="border: none; background-color: white">X</button><br>`;
            count++;
        }
    }
});

let notificationDiv = null;

function toggleNotificationContent(event) {
    const content = event.target;
    if (content.style.whiteSpace === 'nowrap') {
        content.style.whiteSpace = 'normal';
    } else {
        content.style.whiteSpace = 'nowrap';
    }
}

async function fetchNotifications() {
    try {
        let response = await fetch('/notification/notifications');
        if (response.ok) {
            return await response.json();
        } else {
            console.error('Failed to fetch notifications');
            return [];
        }
    } catch (error) {
        console.error('Error:', error);
        return [];
    }
}

async function deleteNotification(content) {
    try {
        let response = await fetch(`/notifications/${content}`, {
            method: 'DELETE',
        });
        if (response.ok) {
            console.log('Notification deleted');
            showNotification();
        } else {
            console.error('Failed to delete notification');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function showNotification(event) {
    if (loginId == null) {
        alert("로그인이 필요한 서비스입니다.");
        event.preventDefault();
        window.location.href = "http://localhost:8090/login/form";
    } else {
        if (notificationDiv) {
            document.body.removeChild(notificationDiv);
            notificationDiv = null;
            return;
        }

        let button = event.target;
        let rect = button.getBoundingClientRect();

        notificationDiv = document.createElement('div');
        notificationDiv.className = 'notification';

        let notifications = await fetchNotifications();
        notifications.forEach(notification => {
            let notificationItem = document.createElement('div');
            notificationItem.className = 'notification-item';

            let notificationText = document.createElement('div');
            notificationText.className = 'notification-content';
            notificationText.textContent = notification.notifyContent;
            notificationText.onclick = toggleNotificationContent;

            let notificationTime = document.createElement('div');
            notificationTime.className = 'notification-time';
            let timeVal = getTimeDiff(notification.notifyDate);
            notificationTime.textContent = timeVal;

            let deleteBtn = document.createElement('button');
            deleteBtn.className = 'notification-button';
            deleteBtn.textContent = "X";
            deleteBtn.onclick = function () {
                deleteNotification(notification.content);
            };

            notificationItem.appendChild(notificationText);
            notificationItem.appendChild(notificationTime);
            notificationItem.appendChild(deleteBtn);
            notificationDiv.appendChild(notificationItem);
        });

        document.body.appendChild(notificationDiv);
        notificationDiv.style.top = (rect.bottom + window.scrollY) + 15 + 'px';
        notificationDiv.style.left = (rect.right + window.scrollX) + 20 - notificationDiv.offsetWidth + 'px';
    }

}

document.getElementById('notificationButton').onclick = showNotification;

function getTimeDiff(notificationDate) {
    let currentDate = new Date();
    let notifyDate = new Date(notificationDate);
    let diffMs = currentDate - notifyDate;

    let diffMinutes = Math.round(diffMs / 60000); // 60000 밀리초(ms) = 1분

    if (diffMinutes < 1) {
        return "방금 전";
    } else if (diffMinutes < 60) {
        return `${diffMinutes}분 전`;
    } else if (diffMinutes < 1440) {
        let hours = Math.floor(diffMinutes / 60);
        return `${hours}시간 전`;
    } else {
        let days = Math.floor(diffMinutes / 1440);
        return `${days}일 전`;
    }
}

document.addEventListener('DOMContentLoaded', () => {

    const navigationButton = document.querySelectorAll('.bar');

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    const type = urlParams.get('type'); // 'sale'
    const category = urlParams.get('category'); // ''
    const categoryDetail = urlParams.get('categoryDetail'); // ''
    // const subType = urlParams.get('subType'); // ''
    const search = urlParams.get('search'); // ''

    const url = new URL(window.location.href);
    const path = url.pathname;



    if (search !== null && search !== '') {// 검색을 한 경우
        return;
    }

    if (type === 'sale') { //카테고리 또는 카테고리 디테일 클릭경우
        navigationButton[1].style.color = 'black';
        navigationButton[1].style.fontSize = '22px';
        return;
    }

    if (type === 'best') { //베스트상품일 경우
        navigationButton[2].style.color = 'black';
        navigationButton[2].style.fontSize = '22px';
        return;
    }

    if (type === 'new') { //신상품일 경우
        navigationButton[3].style.color = 'black';
        navigationButton[3].style.fontSize = '22px';
        return;
    }

    if (path.includes('help') || path.includes('notice')) {//고객센터를 누르거나 고객센터 서브메뉴를 눌렀을 경우
        navigationButton[4].style.color = 'black';
        navigationButton[4].style.fontSize = '22px';
        return;
    }

    if (category !== null || categoryDetail !== null || search === '') {//카테고리 또는 카테고리 디테일 클릭경우
        navigationButton[0].style.color = 'black';
        navigationButton[0].style.fontSize = '22px';
    }

});


//장바구니 카운트 가져오기
async function GetBasketQuantity(customerId) {

    if (customerId == null || customerId === '') {
        console.log('고객이 아님');
        return;
    }

    try {
        const response = await fetch('/basket/getBasketQuantity/' + customerId);
        const result = await response.text();

        if (result !== null || result !== '') {
            console.log(result);
            document.getElementById('basketBadge').innerText = result; //값 집어넣음
        }

    } catch (e) {

    }

}
