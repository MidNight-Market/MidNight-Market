const email = paymentDTO.customerId.replace(/\([^)]*\)/g, '');

$(document).ready(function () {
    $.ajax({
        url: "/payment/prepare",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            merchantUid: paymentDTO.merchantUid, // 가맹점 주문번호
            payPrice: paymentDTO.payPrice // 결제 예정금액
        })
    });
});

document.getElementById('purchaseButton').addEventListener('click', (e)=> {

    e.preventDefault(); //이벤트 취소

    const data = {
     merchantUid : paymentDTO.merchantUid,
     amount : paymentDTO.payPrice,
     tel : '010-1234-5678',
     address : '경기도 인천시 제주도구'
    }

    let IMP = window.IMP; // Iamport 객체 초기화
    IMP.init('imp53054186'); // 가맹점 식별코드

    IMP.request_pay({
        pg: 'html5_inicis',
        pay_method: 'card',
        merchant_uid: paymentDTO.merchantUid,
        name: paymentDTO.payDescription,
        amount: paymentDTO.payPrice,
        buyer_email: email,
        buyer_name: nickName,
        buyer_addr: '인천시 제주도 광역시',
        buyer_postcode: '123-456'
    }, function(rsp) {
        console.log(rsp);
        if (rsp.success) {
            $.ajax({
                url: '/payment/validate',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    impUid: rsp.imp_uid,
                    merchantUid: rsp.merchant_uid,
                }),
            }).done(function (data){
                //사후 검증 완료 후 
                //성공하면 DB데이터 세부사항 저장해야 한다 : 주소,전화번호, 결제방식 등등 업데이트
                $.ajax({
                    url: '/payment/successUpdate',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        merchantUid: rsp.merchant_uid,
                        payMethod: rsp.pay_method
                        // 주소와 전화번호와 같은 정보 기입
                    }),
                    success: function(response) {
                        alert('결제가 성공적으로 완료되었습니다');
                        location.href = '/payment/success'
                    },
                    error: function(xhr, status, error) {
                        // 요청이 실패했을 때 실행되는 코드
                        console.error("걸제가 실패하였습니다. :", status, error);
                    },
                    complete: function(xhr, status) {
                        // 요청이 완료되었을 때 (성공 또는 실패 모두 포함) 실행되는 코드
                        console.log("요청 완료:", status);
                    }
                });

            })
        } else {
            alert('결제에 실패하였습니다.\n에러 내용: ' + rsp.error_msg);
            // 결제 실패 후 처리
        }
    });
});



// 배송지 등록
function daumPost() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분

            // 각 주소의 노출 규칙에 따라 주소를 조합
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가짐
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져옴
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가 (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝남
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고 공동주택일 경우 추가
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 값 넣기
            document.getElementById('sample6_postcode').value = data.zonecode; // 우편번호
            document.getElementById("sample6_address").value = addr; // 주소

            // 커서 이동
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
}

// 추가된 배송지 db저장
document.querySelector('.basket6').addEventListener('click',()=>{
    let code = document.getElementById('sample6_postcode').value;
    let add1 = document.getElementById('sample6_address').value;
    let add2 = document.getElementById('sample6_detailAddress').value;
    document.getElementById('address').value = code+"/"+add1+"/"+add2;
});

// 배송지 추가 모달
document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('.addrAddBtn').addEventListener('click', function() {
        var addrModal = document.getElementById('addrModal');
        addrModal.style.display = 'block';
    });

    // 모달 닫기 버튼
    document.querySelector('.addrClose').addEventListener('click', function() {
        var addrModal = document.getElementById('addrModal');
        addrModal.style.display = 'none';
    });

    // 모달 영역 외 다른 구간 클릭 시 모달 닫음
    window.addEventListener('click', function(e) {
        var addrModal = document.getElementById('addrModal');
        if (e.target == addrModal) {
            addrModal.style.display = 'none';
        }
    });
});
