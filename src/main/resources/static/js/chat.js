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

// 아이디 주고 생성된 방리스트 가져오기
async function getChatRoomList(currentId) {
    try {
        const url = '/chat/rooms/' + currentId;
        const config = {
            method: 'GET'
        };
        const resp = await fetch(url, config);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.error(error);
    }
}
//사이드바에 방리스트렌더링
async function renderChatRoomList(currentId) {
    try {
        const result = await getChatRoomList(currentId);
        const sidebar = document.getElementById('sidebar');
        sidebar.innerHTML = '';
        const button = document.createElement('button');
        button.id = 'newChat';
        button.innerText = '+';
        sidebar.appendChild(button);
        let lastRoomId = null;
        result.forEach(chatRoom => {
            const roomElement = document.createElement('div');
            roomElement.classList.add('room');
            roomElement.dataset.roomId = chatRoom.id;
            const roomId = document.createElement('p');
            roomId.textContent = `${chatRoom.id}`;
            roomId.style.display = 'none';
            const marketName = document.createElement('p');
            marketName.classList.add("marketName")
            getMarketName(chatRoom.sellerId).then(result =>
                marketName.textContent = result
            );
            roomElement.append(roomId,marketName);
            sidebar.appendChild(roomElement);
            if (lastRoomId === null || chatRoom.id > lastRoomId) {
                lastRoomId = chatRoom.id;
            }
            currentRoomId = lastRoomId;
        });
        if (lastRoomId) {
            await loadChatRoom(lastRoomId);
        }
    } catch (error) {
        console.error(error);
    }
}

// 채팅방 로드 및 메시지 자동 채우기
async function loadChatRoom(chatRoomId) {
    try {
        const url = '/chat/messages/' + chatRoomId;
        const config = {
            method: 'GET'
        };
        const resp = await fetch(url, config);
        if (!resp.ok) {
            throw new Error('Network response was not ok');
        }
        let messages = [];
        try {
            messages = await resp.json();
            displayMessages(messages);
        } catch (e) {
            console.warn('No JSON response or empty response:', e.message);
        }
    } catch (error) {
        console.error('Error loading chat room messages:', error.message);
    }
}


function displayMessages(messages) {
    const messagesContainer = document.querySelector('.messages');
    messagesContainer.innerHTML = ''; // 기존 메시지 초기화
    let roomId = document.createElement('p');
    roomId.textContent = messages[0].chatRoomId;
    roomId.style.display = 'none';
    roomId.id = 'roomId'
    messagesContainer.appendChild(roomId);
    messages.forEach(message => {
        spreadMessage(message, messagesContainer);
    });
    scrollToBottom(messagesContainer);
}
function scrollToBottom(element) {
    element.scrollTop = element.scrollHeight;
}
function splitDate(time) {
    const parts = time.split('T');
    return {
        datePart: parts[0],
        timePart: parts[1]
    };
}

function spreadMessage(message, messagesContainer) {
    const messageElement = document.createElement('div');
    const date = document.createElement('p');
    const time = document.createElement('p');
    messageElement.textContent = `${message.content}`;
    const timeSplit = splitDate(message.sendTime);
    date.textContent = timeSplit.datePart;
    time.textContent = timeSplit.timePart;
    if (message.senderType === 'customer') {
        messageElement.classList.add('message', 'customer');
        date.classList.add('date','customer')
        time.classList.add('time','customer')
    } else if (message.senderType === 'seller') {
        messageElement.classList.add('message', 'seller');
        date.classList.add('date','seller');
        time.classList.add('time','seller')
    }
    messagesContainer.appendChild(messageElement);
    messagesContainer.appendChild(time)
}

// 메시지 보내기
function sendMessage(roomId) {
    const inputVal = document.getElementById('inputVal').value;
    let senderType;
    if(role == "role_user"){
        senderType = "customer";
    }else if(role == "role_seller"){
        senderType = "seller";
    }
    const message = {
        chatRoomId: roomId,
        senderId: currentId,
        senderType: senderType,
        content: inputVal
    };
    // 웹소켓을 통해 메시지 전송
    stompClient.send(`/app/chat.sendMessage`, {}, JSON.stringify(message), function() {
        document.getElementById('messageInput').value = ''; // 입력 필드 초기화
        showMessageOutput(message);
    }, function(error) {
        console.error('Error sending message:', error);
    });
}

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', async function () {

});

document.addEventListener('DOMContentLoaded',async () => {
    let urlParams = new URLSearchParams(window.location.search);
    let paramValue = urlParams.get('param');
    if (paramValue != null) {
        getSellerId(paramValue).then(async result => {
            getChatRoomList(result).then(async roomList => {
                if (roomList == null || roomList.length == 0) {
                    let sellerId = result;
                    const response = await fetch(`/chat/rooms`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: `customerId=${currentId}&sellerId=${sellerId}`
                    });
                    const chatRoom = await response.json();
                    // 방 리스트 다시 렌더링
                    renderChatRoomList(currentId);
                    // 새로 생성된 방 열기
                    await loadChatRoom(chatRoom.id);
                }else{
                    renderChatRoomList(currentId);
                    getRoomId(currentId,result).then(async id => {
                        if (id != null) {
                            console.log(id);
                            await loadChatRoom(id);
                        }else{
                            let sellerId = result;
                            const response = await fetch(`/chat/rooms`, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: `customerId=${currentId}&sellerId=${sellerId}`
                            });
                            const chatRoom = await response.json();
                            // 방 리스트 다시 렌더링
                            renderChatRoomList(currentId);
                            // 새로 생성된 방 열기
                            await loadChatRoom(chatRoom.id);
                        }
                    })
                }
            })
        })
    } else {
        try {
            await renderChatRoomList(currentId);
        } catch (error) {
            console.error('Error loading chat room list on page load:', error.message);
        }
    }
})
async function getRoomId(customerId, sellerId){
    const url = "/chat/getRoomId/"+customerId+"/"+sellerId;
    const config = {
        method: 'GET'
    };
    const resp = await fetch(url, config);
    const result = await resp.text();
    return result;
}

// 채팅방 클릭 시 해당 방 로드
document.body.addEventListener('click', function (event) {
    if (event.target.classList.contains('room') || event.target.classList.contains('marketName')) {
        const roomElement = event.target.closest('.room');
        if (roomElement) {
            const roomIdElement = roomElement.querySelector('p');
            const chatRoomId = roomIdElement.innerText.trim();
            currentRoomId = chatRoomId;
            if (chatRoomId) {
                loadChatRoom(chatRoomId)
                    .then(() => {
                        console.log('Chat room loaded successfully.');
                    })
                    .catch(error => {
                        console.error('Error loading chat room:', error.message);
                    });
            }
        }
    }
    if (event.target.id === 'send') {
        sendMessage(currentRoomId);
    }
});
async function getMarketName(sellerId){
    try{
        const url = '/seller/getShopName/'+sellerId;
        const config = {
            method: 'GET'
        };
        const resp = await fetch(url, config);
        return await resp.text();
    }catch (e){
        console.log(e);
    }
}
async function getSellerId(marketName){
    try{
        const url = '/seller/getSellerId/'+marketName;
        const config = {
            method: 'GET'
        };
        const resp = await fetch(url, config);
        return await resp.text();
    }catch (e){
        console.log(e);
    }
}

// 새로운 채팅방 생성
document.body.addEventListener('click', (e) => {
    if (e.target.id == "newChat") {
        const newDiv = document.createElement('div');
        newDiv.classList.add('seller-input-container');

        const textContainer = document.createElement('div');
        textContainer.classList.add('seller-input-text-container');

        const text = document.createElement('p');
        text.classList.add('seller-input-text');
        text.textContent = '쇼핑몰 이름을 입력해주세요. ';

        // X 닫기 버튼 추가
        const closeButton = document.createElement('button');
        closeButton.classList.add('close-button');
        closeButton.textContent = 'X';
        closeButton.addEventListener('click', () => {
            document.body.removeChild(newDiv);
        });

        textContainer.appendChild(text);
        textContainer.appendChild(closeButton);

        const inputContainer = document.createElement('div');
        inputContainer.classList.add('seller-input-input-container');

        const input = document.createElement('input');
        input.classList.add('seller-input-input');
        input.setAttribute('type', 'text');

        const button = document.createElement('button');
        button.classList.add('seller-input-button');
        button.textContent = '검색';

        inputContainer.appendChild(input);
        inputContainer.appendChild(button);

        newDiv.appendChild(textContainer);
        newDiv.appendChild(inputContainer);
        document.body.appendChild(newDiv);

        button.addEventListener('click', async () => {
            const sellerId = await getSellerId(input.value);
            const response = await fetch(`/chat/rooms`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `customerId=${currentId}&sellerId=${sellerId}`
            });
            const chatRoom = await response.json();
            // 방 리스트 다시 렌더링
            renderChatRoomList(currentId);
            // 새로 생성된 방 열기
            await loadChatRoom(chatRoom.id);
            // newDiv 요소 제거
            document.body.removeChild(newDiv);
        });
    }
});
// WebSocket 연결 설정
let stompClient;

function connect() {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/{chatRoomId}', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function showMessageOutput(message) {
    const messagesContainer = document.querySelector('.messages');
    const messageElement = document.createElement('div');

    messageElement.classList.add('message');
    messageElement.classList.add(message.senderType); // Add class based on senderType

    const messageContent = document.createElement('div');
    messageContent.classList.add('message-content');
    messageContent.textContent = `${message.content}`;

    const time = document.createElement('p');
    time.textContent = getCurrentTime();
    time.classList.add('time');
    time.classList.add(message.senderType);

    messageElement.appendChild(messageContent);
    messagesContainer.appendChild(messageElement);
    messagesContainer.appendChild(time)

    document.getElementById('inputVal').value = '';
}
let currentRoomId = null;

function getCurrentTime() {
    const now = new Date();

    const hours = now.getHours();       // 시
    const minutes = now.getMinutes();   // 분
    const seconds = now.getSeconds();   // 초

    return `${hours}:${minutes}:${seconds}`;
}

// 페이지 로드 시 WebSocket 연결
connect();

