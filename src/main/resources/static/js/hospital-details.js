   //현재 날짜의 해당하는 요일의 정보를 처음에 보여주기
    var todayTime = document.getElementById("today-time");
    var todayDayOfWeek = document.getElementById("today-day");
    todayTime.innerHTML = getTodayDutyHour();
    todayDayOfWeek.innerHTML = getKoreanDayOfWeek();
    function getTodayDutyHour() {
        const sysdate = new Date();
        var dayOfWeek = sysdate.getDay();
        return document.getElementById("dutyTime"+dayOfWeek).innerHTML;
    }
    function getKoreanDayOfWeek() {
        var daysOfWeek = ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일','일요일'];
        const sysdate = new Date();
        var dayOfWeek = sysdate.getDay();
        return daysOfWeek[dayOfWeek-1];
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

    var xCoordinate = document.getElementById("hospital_x").getAttribute("value");
    var yCoordinate = document.getElementById("hospital_y").getAttribute("value");

    var container = document.getElementById('map');
    var options = {
        // center: new kakao.maps.LatLng(33.450701, 126.570667),
        center: new kakao.maps.LatLng(xCoordinate, yCoordinate),
        level: 3
    };
    var map = new kakao.maps.Map(container, options);
