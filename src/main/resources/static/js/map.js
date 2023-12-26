// 마커를 담을 배열입니다
var markers = [];
var overlays= [];


var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.4659942, 126.8895083), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 마커를 표시할 위치와 title 객체 배열입니다
//응애 서비스를 이용하는 병원 마커
var positions = [
    {
        title: '독산역',
        latlng: new kakao.maps.LatLng(37.4659942, 126.8895083)
    },
    {
        title: '플레이데이터',
        latlng: new kakao.maps.LatLng(37.468656, 126.886638)
    },
    {
        title: '매일365의원',
        latlng: new kakao.maps.LatLng(37.4662108, 126.8870784)
    }
];

//응애 서비스를 이용하지 않는 병원 마커
var basicPositions = [
    {
        title: '스타벅스',
        latlng: new kakao.maps.LatLng(37.4676446, 126.8876698)
    }
];


// 마커 이미지의 이미지 주소입니다
var imageSrc = "/img/eungaemarker.png";
var basicImageSrc = "/img/basic4.png";


//응애 서비스를 이용하지 않는 병원 마커 생성 - 먼저 생성해야 뒤로 가려짐
for (var i = 0; i < basicPositions.length; i ++) {

    // 마커 이미지의 이미지 크기 입니다
    var basicImageSize = new kakao.maps.Size(30, 42);
    // 마커 이미지를 생성합니다
    var basicMarkerImage = new kakao.maps.MarkerImage(basicImageSrc, basicImageSize);

    // // 마커를 생성합니다
    // var marker = new kakao.maps.Marker({
    // 	map: map, // 마커를 표시할 지도
    // 	position: basicPositions[i].latlng, // 마커를 표시할 위치
    // 	title : basicPositions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
    // 	image : basicMarkerImage // 마커 이미지
    // });

    addBasicMarker(basicPositions[i], basicMarkerImage);
}

//응애 서비스를 이용하는 병원 마커 생성
for (var i = 0; i < positions.length; i ++) {

    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(36, 52.5);
    // 마커 이미지를 생성합니다
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    addMarker(positions[i], markerImage);

}
// 오버레이 정의
var overlay = new kakao.maps.CustomOverlay({
    map: map
});
// 오버레이 인덱스
var index = 0;

// 마커를 생성하고 지도위에 표시하는 함수입니다
function addMarker(position, markerImage) {

    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: position.latlng, // 마커를 표시할 위치
        title : position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        var content = '<div id="wrap" class="wrap">' +
            '    <div class="info">' +
            '        <div class="title">' +
            position.title +
            '            <div class="close" onclick="closeOverlay('+index+')" title="닫기"></div>' +
            '        </div>' +
            '        <div class="body">' +
            '            <div class="img">' +
            '                <img src="/img/logo1.png" width="73" height="70">' +
            '           </div>' +
            '            <div class="desc">' +
            '                <div class="ellipsis">'+'서울특별시 구로구 독산동'+'</div>' +
            '                <div class="jibun ellipsis">진료 가능 시간</div>' +
            '                <div><a href="/hospital" target="_blank" class="link">예약</a></div>' +
            '            </div>' +
            '        </div>' +
            '    </div>' +
            '</div>';


        // 마커 위에 커스텀오버레이를 표시합니다
        // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
        overlay = new kakao.maps.CustomOverlay({
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

    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: position.latlng, // 마커를 표시할 위치
        title : position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        var content = '<div id="wrap" class="wrap">' +
            '    <div class="info">' +
            '        <div class="title">' +
            position.title +
            '            <div class="close" onclick="closeOverlay('+index+')" title="닫기"></div>' +
            '        </div>' +
            '        <div class="body">' +
            '            <div class="img">' +
            '                <img src="/img/logo1.png" width="73" height="70">' +
            '           </div>' +
            '            <div class="desc">' +
            '                <div class="ellipsis">'+'서울특별시 구로구 독산동'+'</div>' +
            '                <div class="jibun ellipsis">진료 가능 시간</div>' +
            '            </div>' +
            '        </div>' +
            '    </div>' +
            '</div>';


        // 마커 위에 커스텀오버레이를 표시합니다
        // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
        overlay = new kakao.maps.CustomOverlay({
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


function closeOverlay(index) {
    overlays[index].setMap(null);
}





kakao.maps.event.addListener(map, 'dragend', function() {
    // markers.forEach(
    // 		marker => marker.setMap(null)
    // )
    // 지도 중심좌표를 얻어옵니다
    var latlng = map.getCenter();

    var message = '변경된 지도 중심좌표는 ' + latlng.getLat() + ' 이고, ';
    message += '경도는 ' + latlng.getLng() + ' 입니다';
    console.log(message);
    // var resultDiv = document.getElementById('result');
    // resultDiv.innerHTML = message;
})