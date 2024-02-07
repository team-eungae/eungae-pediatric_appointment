//현재 날짜의 해당하는 요일의 정보를 처음에 보여주기
var todayTime = document.getElementById("today-time");
var todayDayOfWeek = document.getElementById("today-day");
todayTime.innerHTML = getTodayDutyHour();
todayDayOfWeek.innerHTML = getKoreanDayOfWeek();

function getTodayDutyHour() {
    const sysdate = new Date();
    var dayOfWeek = sysdate.getDay();  //일월~토 0~6
    console.log("dutyTime" + dayOfWeek);
    return document.getElementById("dutyTime" + dayOfWeek).innerHTML;
}

function getKoreanDayOfWeek() {
    var daysOfWeek = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
    const sysdate = new Date();
    var dayOfWeek = sysdate.getDay();
    return daysOfWeek[dayOfWeek];
}

let shownum = -1;
const onShowBtn = () => {
    shownum = -shownum;
    if (shownum === 1) {
        showClinic();
        document.getElementsByClassName("clinic-hours-button")[0].style.transform = "rotate(180deg)";
    } else if (shownum === -1) {
        hideClinic();
        document.getElementsByClassName("clinic-hours-button")[0].style.transform = "rotate(0deg)";
    }
}

function hideClinic() {
    const row = document.getElementById('schedule');
    row.style.display = 'none';
}

function showClinic() {
    const row = document.getElementById('schedule');
    row.style.display = '';
}

var imageSrc = "/img/eungaemarker.png";

var xCoordinate = document.getElementById("hospital_x").getAttribute("value");
var yCoordinate = document.getElementById("hospital_y").getAttribute("value");

var container = document.getElementById('map');
var options = {
    // center: new kakao.maps.LatLng(33.450701, 126.570667),
    center: new kakao.maps.LatLng(yCoordinate, xCoordinate),
    level: 3
};
var map = new kakao.maps.Map(container, options);

var markerPosition = new kakao.maps.LatLng(yCoordinate, xCoordinate);

var basicImageSize = new kakao.maps.Size(30, 42);
// 마커 이미지를 생성합니다
var mainImageSrc = new kakao.maps.MarkerImage(imageSrc, basicImageSize);

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition,
    image: mainImageSrc
});

let liked;

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);

//병원 이미지 슬라이드
$(document).ready(function () {
    var slideCount = parseInt($('.hospital-images').attr('data-count'));
    var currentIndex = 0;

    function showSlide(index) {
        $('.slide').removeClass('active').hide();
        $('.slide').eq(index).addClass('active').show();
    }

    function nextSlide() {
        currentIndex = (currentIndex + 1) % slideCount;
        showSlide(currentIndex);
    }

    showSlide(currentIndex);

    // 3초마다 자동 슬라이드
    setInterval(function () {
        nextSlide();
    }, 3000);
});

// 찜하기 버튼의 클릭 이벤트 처리
$(document).ready(function () {
    const hospitalSeq = $("#hospitalSeq").val()

    $.ajax({
        type: 'GET',
        url: '/api/my/hospital?hospitalSeq=' + hospitalSeq,
        contentType: 'application/json',
        success: function (data) {
            liked = data;
            let likeButtonClassName = data ? "fa-solid" : "fa-regular";
            $("#likeButton").html(`<i class="${likeButtonClassName} fa-star"></i>`);
        }
    })
});

$("#likeButton").click(function () {
    const hospitalSeq = $("#hospitalSeq").val()

    $.ajax({
        type: 'PATCH',
        url: '/api/my/hospital?hospitalSeq=' + hospitalSeq,
        contentType: 'application/json',
        success: function (data) {
            liked = !liked; // 상태 토글 (찜하기 누를 때마다 상태 변경)
            if (liked) {
                // 찜하기 상태일 때 아이콘과 텍스트 변경
                $("#likeButton").html('<i class="fa-solid fa-star"></i>');
            } else {
                // 찜하기 해제 상태일 때 아이콘과 텍스트 변경
                $("#likeButton").html('<i class="fa-regular fa-star"></i>');
            }
        },
        error: function () {
            alert("즐겨찾기 추가에 실패했습니다.");
        }
    })
});

$("#paste-btn").on("click", function(){
    var textVal = $("#hospital-num").text().replace(/\-/g, "").trim();
    var textarea = document.createElement('textarea');
    textarea.value = textVal;
    document.body.appendChild(textarea);
    textarea.select();
    textarea.setSelectionRange(0, 9999);
    document.execCommand('copy');
    document.body.removeChild(textarea);
    alert("병원 번호가 복사되었습니다.");
});