
//현재 날짜의 해당하는 요일의 정보를 처음에 보여주기
var todayTime = document.getElementById("today-time");
var todayDayOfWeek = document.getElementById("today-day");
todayTime.innerHTML = getTodayDutyHour();
todayDayOfWeek.innerHTML = getKoreanDayOfWeek();

function getTodayDutyHour() {
    const sysdate = new Date();
    var dayOfWeek = sysdate.getDay();  //일월~토 0~6
    console.log("dutyTime"+dayOfWeek);
    return document.getElementById("dutyTime"+dayOfWeek).innerHTML;
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
    center: new kakao.maps.LatLng(xCoordinate, yCoordinate),
    level: 3
};
var map = new kakao.maps.Map(container, options);

var markerPosition  = new kakao.maps.LatLng(xCoordinate, yCoordinate);

var basicImageSize = new kakao.maps.Size(30, 42);
// 마커 이미지를 생성합니다
var mainImageSrc = new kakao.maps.MarkerImage(imageSrc, basicImageSize);

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition,
    image: mainImageSrc
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);


$(".slide-1").on("click", function () {
    $(".slide-container").css("transform","translateX(0)");
});
$(".slide-2").on("click", function () {
    $(".slide-container").css("transform","translateX(-100vw)");
});
$(".slide-3").on("click", function () {
    $(".slide-container").css("transform","translateX(-200vw)");
});

