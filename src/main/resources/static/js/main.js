var slideImgInner = document.querySelector('.slideImgInner'),
slideImg = document.querySelectorAll('.slideImg'),
currentIdx = 0,
slideCount = slideImg.length,
slideHeight = 260,
leftBtn = document.querySelector('.leftBtn'),
rightBtn = document.querySelector('.rightBtn');

makeClone();

function makeClone(){
    for(var i=0; i<slideCount; i++){
        var cloneSlide = slideImg[i].cloneNode(true);
        cloneSlide.classList.add('clone');
        slideImgInner.appendChild(cloneSlide); //요소의 뒤에 추가
    }
    for(var i=slideCount-1; i>=0; i--){
        var cloneSlide = slideImg[i].cloneNode(true);
        cloneSlide.classList.add('clone');
        slideImgInner.prepend(cloneSlide); //요소의 앞에 추가
    }
    updateHeight();

    //초기 위치 잡기
    setInitialPos();
    setTimeout(function (){
        slideImgInner.classList.add('animated');
    },500);
}

function updateHeight(){
    var currentSlides = document.querySelectorAll('.slideImg img');
    var newSlideCount = currentSlides.length;

    var newHeight = (slideHeight * newSlideCount)+'px';
    slideImgInner.style.height = newHeight;
}

function setInitialPos(){
    var initialTranslateValue = (-slideHeight) * slideCount;
    slideImgInner.style.transform = 'translateY(' + initialTranslateValue + 'px)';
}

leftBtn.addEventListener('click', function (){
    moveSlide(currentIdx-1);
});
rightBtn.addEventListener('click', function (){
    moveSlide(currentIdx+1);
});

function moveSlide(num){
    slideImgInner.style.transform = "translateY(-"+num*slideHeight+"px)";
    currentIdx = num;
    console.log(currentIdx, slideCount);
    if(currentIdx == slideCount || currentIdx == -slideCount){
        setTimeout(function (){
            slideImgInner.classList.remove("animated");
            slideImgInner.style.transform = "translateY(0px)";
            currentIdx = 0;
        },500);
        setTimeout(function (){
            slideImgInner.classList.add("animated");
        },600);
    }
}

// const sectionBox = document.querySelector(".sectionBox");
// const slide = sectionBox.querySelector(".slide"); //보여지는 영역
// const slideImgInner = sectionBox.querySelector(".slideImgInner"); //움직이는 영역
// const slideImg = sectionBox.querySelector(".slideImg"); //개별 이미지
// let sliderLength = document.querySelectorAll('.slideImg').length;
// let currentIndex = 0; //현재 보이는 이미지
// let sliderCount = slideImg.length; //이미지 개수
// let sliderInterval = 3000; //이미지 변경 시간(3초)
// // let slideDotBtn = document.querySelector('.slideDotBtn');
// // let dotIndex = "";
// let sliderTimer = "";
//
// function init(){
//     // createDot();
//     imgClone();
// }
// init();
//
// window.addEventListener("load",()=>{
//     autoPlay();
// });

// function createDot(){
//     for(let i=1; i<=sliderLength; i++){
//         dotIndex += `<a href='#' class='dot DotBtn${i}'></a>`;
//     }
//     dotIndex += `<a href='#' class='play'></a>`; //auto play
//     dotIndex += `<a href='#' class='stop'></a>`; //auto stop
//     slideDotBtn.innerHTML += dotIndex; //만든 구문 태그 형식으로 넣기
//     slideDotBtn.firstElementChild.classList.add("active"); //첫번째 이미지에 클래스 부여
// }

// function imgClone(){
// let imageFirst = document.querySelectorAll('.slideImg')[0]; //첫번째 이미지
// let imageLast = document.querySelectorAll('.slideImg')[sliderLength-1]; //마지막 이미지
// let sliderCloneFirst = imageFirst.cloneNode(true); //첫번째 이미지 복사
// let sliderCloneLast = imageLast.cloneNode(true); //마지막 이미지 복사
//
// slideImgInner.appendChild(sliderCloneFirst); //복사한 첫번째 이미지 마지막에 붙여넣기
// slideImgInner.insertBefore(sliderCloneLast, imageFirst); //마지막 이미지를 첫번째 이미지 앞에 넣기
// sliderLength = document.querySelectorAll('.slideImg').length; //변경된 전체길이 값 다시 대입
// }
//
// // 이미지 총 길이 넣기
// const slider = document.querySelectorAll('.slider');	// 슬라이드 컨트롤 때문에 이미지 갯수를 함수 실행 이후로 미룸
//
// function autoPlay(){
//     sliderTimer = setInterval(()=>{	 //셋인터벌 실행
//         let intervalNum = currentIndex+1; // 복사된 값때문에 시작값은 1
//         if(intervalNum == sliderLength-1)intervalNum = 0; // 마지막 이미지에서 다음이미지 넘어가는 과정에 0으로 초기화
//         showSlide(intervalNum); // 슬라이더 이동함수에 변경된 인덱스 부여
//     }, sliderInterval);
// }
//
// function stopPlay(){
//     clearInterval(sliderTimer); // 셋인터벌 중지
// }
//
// function showSlide(){
//     currentIndex++;
//
//     slideImgInner.style.transition = "all 0.5s";
//     slideImgInner.style.transform = "translateY(-"+260*currentIndex+"px)";
//
//     //마지막 이미지에 위치했을 때
//     if(currentIndex == sliderCount){
//         setTimeout(()=>{
//             slideImgInner.style.transition = "0s";
//             slideImgInner.style.transform = "translateY(0px)";
//         }, 700);
//         currentIndex = 0;
//     }
// }
//
// setInterval(showSlide, sliderInterval);
//
// function autoSlide(){
//     document.querySelector('.slideImgInner').style.transform = 'translateY(-260px)';
// }
