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

// 채팅방 로드 및 메시지 자동 채우기
async function loadChatRoom(chatRoomId) {
    try {
        const url = '/chat/messages/' + chatRoomId;
        const config = {
            method: 'GET'
        };
        const resp = await fetch(url, config);
        const messages = await resp.json();

        const messagesContainer = document.querySelector('.messages');
        messagesContainer.innerHTML = ''; // 기존 메시지 초기화

        messages.forEach(message => {
            const messageElement = document.createElement('div');
            messageElement.classList.add('message');
            messageElement.textContent = `${message.senderId}: ${message.content}`;
            messagesContainer.appendChild(messageElement);
        });

        // 스크롤 맨 아래로 이동
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    } catch (error) {
        console.error('Error loading chat room messages:', error.message);
    }
}

// 메시지 보내기
function sendMessage() {
    const inputVal = document.getElementById('inputVal').value;
    // 현재 활성화된 채팅방 ID 가져오는 로직 필요
    const chatRoomId = 1;
    const message = {
        chatRoomId: chatRoomId,
        senderId: "(kakao)ehdwo13@kakao.com",
        senderType: 'customer', // 혹은 'seller', 상황에 맞게 설정
        content: inputVal
    };
    // 웹소켓을 통해 메시지 전송
    stompClient.send(`/app/chat.sendMessage`, {}, JSON.stringify(message));
    // 메시지를 서버로 보내는 fetch 호출
    fetch(`/chat/messages`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(message)
    });
}

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', function () {
    const currentId = "(kakao)ehdwo13@kakao.com"; // 예시: 현재 사용자 ID
    getChatRoomList(currentId)
        .then(result => {
            const sidebar = document.getElementById('sidebar');
            sidebar.innerHTML = ''; // 기존 목록 초기화
            const firstRoomId = result.length > 0 ? result[0].id : null;

            result.forEach(chatRoom => {
                const roomElement = document.createElement('div');
                roomElement.classList.add('room');
                roomElement.textContent = `Chat Room ID: ${chatRoom.id}`;
                const roomId = document.createElement('p');
                roomId.textContent = `${chatRoom.id}`;
                roomId.style.display = 'none';
                roomElement.appendChild(roomId);
                sidebar.appendChild(roomElement);
            });

            // 첫 번째 방 채팅 내용 자동으로 로드
            if (firstRoomId) {
                loadChatRoom(firstRoomId);
            }
        })
        .catch(error => {
            console.error('Error fetching chat rooms:', error.message);
        });
});

// 채팅방 클릭 시 해당 방 로드
document.body.addEventListener('click', function (event) {
    // sidebar의 room 클래스를 가진 요소가 클릭된 경우
    if (event.target.classList.contains('room')) {
        const roomIdElement = event.target.querySelector('p'); // 클릭된 room 안의 p 태그
        const chatRoomId = roomIdElement.textContent.trim(); // p 태그의 내용을 가져와서 공백 제거

        if (chatRoomId) {
            loadChatRoom(chatRoomId); // 선택된 채팅방의 메시지 로드
        }
    }

    // send 클릭된 경우
    if (event.target.id === 'send') {
        // const chatRoomId = document.getElementById('chatRoomId').value.trim(); // chatRoomId 입력 필드의 값 가져오기
        const senderId =  "(kakao)ehdwo13@kakao.com";
        const chatRoomId = 1;
        const content = document.getElementById('inputVal').value.trim();

        if (chatRoomId && senderId && content) {
            sendMessage(chatRoomId, senderId, content)
                .then(() => {
                    console.log('Message sent successfully');
                    document.getElementById('messageInput').value = ''; // 입력 필드 초기화
                })
                .catch(error => {
                    console.error('Error sending message:', error.message);
                });
        } else {
            console.log('모든 필드를 입력하세요');
        }
    }
});


// 새로운 채팅방 생성
document.body.addEventListener('click', (e) => {
    if (e.target.id == "newChat") {
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
        button.addEventListener('click', async () => {
            const sellerId = input.value.trim();
            if (sellerId) {
                try {
                    const url = '/chat/rooms';
                    const config = {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: `customerId=${currentId}&sellerId=${sellerId}`
                    };
                    const resp = await fetch(url, config);
                    const chatRoom = await resp.json();
                    loadChatRoom(chatRoom.id);
                } catch (error) {
                    console.error('Error creating new chat room:', error.message);
                }
            } else {
                console.log('판매자 아이디를 입력해주세요');
            }
        });
        newDiv.appendChild(text);
        newDiv.appendChild(input);
        newDiv.appendChild(button);
        document.body.appendChild(newDiv);
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
    // 원하는 동작을 수행 (예: 채팅 메시지를 UI에 표시)
}

// 페이지 로드 시 WebSocket 연결
connect();
