// 마커 이미지의 이미지 주소입니다
const imageSrc = "/img/eungaemarker.png";
const basicImageSrc = "/img/basic4.png";
const searchForm = document.querySelector(".search-form");

// 마커 이미지의 이미지 크기 입니다
const imageSize = new kakao.maps.Size(36, 52.5);
const basicImageSize = new kakao.maps.Size(30, 42);

// 마커 이미지 입니다
const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
const basicMarkerImage = new kakao.maps.MarkerImage(basicImageSrc, basicImageSize);

const blankCheck = /\s/g;

// 마커를 표시할 위치와 title 객체 배열입니다
let positions = [];
let position = {};

// 마커를 담을 배열입니다
let markers = [];

// 오버레이들을 담을 배열입니다
let overlays = [];

// 오버레이 인덱스
let index = 0;

// 경도(x)와 위도(y) 입니다
let longitude, latitude;

// 커스텀 오버레이 표시할때 사용됩니다
let content;
let keyword;

// HTML5의 geolocation으로 사용할 수 있는지 확인합니다
if (navigator.geolocation) {
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function (position) {
        /* var lat = position.coords.latitude, // 위도
             lon = position.coords.longitude; // 경도
         현재 ssl 인증이 안돼서 ajax요청을 이 함수 밖에서 사용하고
         lat과 lon 은 플레이데이터의 주소로 사용헀습니다.
         $.ajax({
             url:"api/hospital/nearbyHospital",
             type: "GET",
             data: {
                 "longitude": longitude,
                 "latitude": latitude
             },success:function(result){
                 console.log(result);
             }
         }) */
    });
    longitude = 126.88656925; // 경도
    latitude = 37.46758697;  // 위도37.46758697;
    $.ajax({
        url: "api/hospital/around",
        type: "GET",
        data: {
            "longitude": longitude,
            "latitude": latitude
        }, success: function (hospitalList) {
            if(hospitalList.length == 0){
                alert("주변 병원을 찾을 수 없습니다.");
            }else {
                hospitalList.forEach((hospital, index) => {
                    if (hospital.hospitalId == null) {
                        position = {
                            title: hospital.name,
                            latlng: new kakao.maps.LatLng(hospital.latitude, hospital.longitude),
                            address: hospital.address,
                            contact: hospital.contact
                        }
                        positions[index] = position;
                        addBasicMarker(positions[index], basicMarkerImage);

                    } else {
                        position = {
                            title: hospital.name,
                            latlng: new kakao.maps.LatLng(hospital.latitude, hospital.longitude),
                            address: hospital.address,
                            hospitalSeq: hospital.hospitalSeq,
                            contact: hospital.contact,
                            currWait: hospital.currentWaitingCount,
                            hospitalThumbnail: hospital.hospitalThumbnail
                        }
                        positions[index] = position;
                        addMarker(positions[index], markerImage);
                    }
                });
            }
        }, error: function () {
            alert("주변 병원 검색 중 오류가 발생하였습니다.");
        }
    })
} else {
    longitude = 126.88656925; // 경도
    latitude = 37.46758697;  // 위도37.46758697;
    $.ajax({
        url: "api/hospital/around",
        type: "GET",
        data: {
            "longitude": longitude,
            "latitude": latitude
        }, success: function (result) {
            console.log(result);
        }
    })
}

// 키워드 검색 함수 입니다
const onSearch = (event) => {
    event.preventDefault();
    keyword = document.getElementById("keyword").value;
    if (keyword.length < 2) {
        alert("두글자 이상 입력해주세요");
        return;
    }
    if (keyword.match(blankCheck)) {
        alert("공백을 재거해주세요");
        return;
    }
    $.ajax({
        url: "api/hospital/search",
        type: "GET",
        data: {
            "keyword": keyword,
            "longitude": longitude,
            "latitude": latitude
        },
        dataType: "json",
        contentType: 'application/json',
        success: function (hospitalList) {
            // ajax 요청 성공시 마커와 오버레이 배열을 초기화 합니다
            markers.forEach((marker) => {
                marker.setMap(null);
            });

            markers.length = 0;
          
            overlays.forEach((overlay) => {
                overlay.setMap(null);
            })
            overlays.length = 0;
            index = 0;
            if(hospitalList.length == 0){
                alert("병원 검색 결과를 찾을 수 없습니다.");
            }else{
                moveLatLon = new kakao.maps.LatLng(hospitalList[0].latitude, hospitalList[0].longitude);
                map.setCenter(moveLatLon);
                
                hospitalList.forEach((hospital, index) => {
                    if(hospital.hospitalId == null){
                        position = {
                            title: hospital.name,
                            latlng: new kakao.maps.LatLng(hospital.latitude, hospital.longitude),
                            address: hospital.address,
                            contact: hospital.contact
                        }
                        positions[index] = position;
                        addBasicMarker(positions[index], basicMarkerImage);

                    }else{
                        position = {
                            title: hospital.name,
                            latlng: new kakao.maps.LatLng(hospital.latitude, hospital.longitude),
                            address: hospital.address,
                            hospitalSeq: hospital.hospitalSeq,
                            contact: hospital.contact,
                            currWait: hospital.currentWaitingCount,
                            hospitalThumbnail: hospital.hospitalThumbnail
                        }
                        positions[index] = position;
                        addMarker(positions[index], markerImage);
                    }
                });
            }
        }, error: function () {
            alert("키워드 검색 중 오류가 발생하였습니다. 다른 키워드로 검색해주세요");
        }
    })
}

searchForm.addEventListener("submit", onSearch);

let mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(latitude, longitude), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 오버레이 정의
var overlay = new kakao.maps.CustomOverlay({
    map: map
});

// 마커를 생성하고 지도위에 표시하는 함수입니다
function addMarker(position, markerImage) {

    let marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: position.latlng, // 마커를 표시할 위치
        title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage // 마커 이미지
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    kakao.maps.event.addListener(marker, 'click', function () {
        overlays.forEach((overlay) => {
            overlay.setMap(null);
        })
        content = `<div id="wrap" class="wrap">
                        <div class="info">
                            <div class="title">
                                ${position.title}
                                <div class="close" onclick="closeOverlay(${index})" title="닫기"></div>
                            </div>
                        <div class="body">
                        <div class="img">
                            <img src="${position.hospitalThumbnail == null ? '/img/logo1.png' : `/images/${position.hospitalThumbnail}`}" width="90" height="80">
                        </div>
                        <div class="desc">
                            <div class="ellipsis">${position.address}</div>
                            <div class="jibun ellipsis">${position.contact}</div>                       
                            <div><a href="/hospital/${position.hospitalSeq}" class="link">예약하러 가기</a></div>  
                            <div class="current-waiting">현재 대기 인원: ${position.currWait}</div>
                            <br>                        
                        </div>
                    </div>
                </div>
            </div>`;
        // 마커 위에 커스텀오버레이를 표시합니다
        // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
        let overlay = new kakao.maps.CustomOverlay({
            content: content,
            map: map,
            position: marker.getPosition()
        });

        overlay.setMap(map);
        overlays.push(overlay);
        index = index + 1;
    });

    // 생성된 마커를 배열에 추가합니다
    markers.push(marker);
}

function addBasicMarker(position, markerImage) {
    let marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: position.latlng, // 마커를 표시할 위치
        title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage // 마커 이미지
    });
    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    kakao.maps.event.addListener(marker, 'click', function () {
        overlays.forEach((overlay) => {
            overlay.setMap(null);
        })
        content = `<div id="wrap" class="wrap">
                <div class="info">
                    <div class="title">
                        ${position.title}
                        <div class="close" onclick="closeOverlay(${index})" title="닫기"></div>
                    </div>
                    <div class="body">
                        <div class="img">
                            <img src="/img/logo1.png" width="90" height="80">
                        </div>
                        <div class="desc">
                            <div class="ellipsis">${position.address}</div>
                            <div class="jibun ellipsis">${position.contact}</div>
                            <br>
                            <br>
                        </div>
                    </div>
                </div>
            </div>`;
        // 마커 위에 커스텀오버레이를 표시합니다
        // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
        let overlay = new kakao.maps.CustomOverlay({
            content: content,
            map: map,
            position: marker.getPosition()
        });
        overlay.setMap(map);
        overlays.push(overlay);
        index = index + 1;
    });

    // 생성된 마커를 배열에 추가합니다
    markers.push(marker);
}

const closeOverlay = (index) => {
    overlays[index].setMap(null);
}
