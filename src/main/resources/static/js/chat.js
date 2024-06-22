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
            lastRoomId = chatRoom.id;

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
        const messages = await resp.json();
        window.scrollTo(0, document.body.scrollHeight);
        displayMessages(messages);
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

    // 스크롤 맨 아래로 이동
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

function spreadMessage(message, messagesContainer) {
    const messageElement = document.createElement('div');
    messageElement.textContent = `${message.senderId}: ${message.content}`;

    // Sender type에 따라 CSS 클래스를 추가합니다.
    if (message.senderType === 'customer') {
        messageElement.classList.add('message', 'customer');
    } else if (message.senderType === 'seller') {
        messageElement.classList.add('message', 'seller');
    }

    messagesContainer.appendChild(messageElement);
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
        console.log('Message sent successfully');
        document.getElementById('messageInput').value = ''; // 입력 필드 초기화
        showMessageOutput(message);
    }, function(error) {
        console.error('Error sending message:', error);
    });
}

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', async function () {
    window.scrollTo(0, document.body.scrollHeight);
    try {
        await renderChatRoomList(currentId);
    } catch (error) {
        console.error('Error loading chat room list on page load:', error.message);
    }
});

// 채팅방 클릭 시 해당 방 로드
document.body.addEventListener('click', function (event) {
    if (event.target.classList.contains('room') || event.target.classList.contains('marketName')) {
        const roomElement = event.target.closest('.room');
        if (roomElement) {
            const roomIdElement = roomElement.querySelector('p');
            const chatRoomId = roomIdElement.textContent.trim();
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
        const roomId = document.getElementById('roomId').innerText.trim();
        sendMessage(roomId);
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

// 새로운 채팅방 생성
document.body.addEventListener('click', (e) => {
    if (e.target.id == "newChat") {
        const newDiv = document.createElement('div');
        newDiv.classList.add('seller-input-container');

        const textContainer = document.createElement('div');
        textContainer.classList.add('seller-input-text-container');

        const text = document.createElement('p');
        text.classList.add('seller-input-text');
        text.textContent = '판매자이름을 입력해주세요. ';

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

        const currentId = "(kakao)ehdwo13@kakao.com";
        button.addEventListener('click', async () => {
            const sellerId = input.value;
            const response = await fetch(`/chat/rooms`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `customerId=${currentId}&sellerId=${sellerId}`
            });
            const chatRoom = await response.json();
            renderChatRoomList(currentId); // 방 리스트 다시 렌더링
            await loadChatRoom(chatRoom.id); // 새로 생성된 방 열기
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
    console.log('Received message:', message);
    const messagesContainer = document.querySelector('.messages');
    const messageElement = document.createElement('div');

    messageElement.classList.add('message');
    messageElement.classList.add(message.senderType); // Add class based on senderType

    const messageContent = document.createElement('div');
    messageContent.classList.add('message-content');
    messageContent.textContent = `${message.senderId}: ${message.content}`;

    messageElement.appendChild(messageContent);
    messagesContainer.appendChild(messageElement);
    document.getElementById('inputVal').value = '';
}

// 페이지 로드 시 WebSocket 연결
connect();
