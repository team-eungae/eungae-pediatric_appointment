//날짜 입력창 기본 값으로 현재 날짜 설정
const sysdate = new Date();
document.getElementById('date').value = sysdate.toISOString().substring(0, 10);
//선택가능한 최소날짜 설정
document.getElementById('date').min = sysdate.toISOString().substring(0, 10);

//선택가능한 최대날짜 설정 다음달 말일까지
var year = sysdate.getFullYear() + 1;
var month = sysdate.getMonth() % 11 + 1;

if (month < 10) {
    month = "0" + month;
}

const nextmonthdate = new Date(year, month, 1);
document.getElementById('date').max = nextmonthdate.toISOString().substring(0, 10);


var setdate = function () {
    document.getElementById('appointment-date-view').innerHTML = document.getElementById('date').value.substring(5, 7) + '월' + document.getElementById('date').value.substring(8) + '일';
};

//날짜 클릭 때마다 병원 정보의 날짜도 변경되도록
document.getElementById('date').addEventListener("change", setdate)

//진료가능시간 버튼 만들기
var list;
$(document).ready(function () {
    setdate()
    // todo: ajax로 휴무일만 받아와서 해당 달력 라이브러리로
    //  휴무일을 달력에서 비활성화할 수 있는 기능이 있는지 확인할 것.
});


let value = document.getElementById('date').value;
let date = sysdate.toISOString().substring(0, 10);
let dayOfWeek = sysdate.getDay();

var hospitalSeq = document.getElementById("hospitalSeq").value;
console.log(hospitalSeq);

$('input[name=doctorSeq], input[name=childrenSeq], input[name=appointmentDate]').change(function (e) {
    let childrenSeq = $('input[name=childrenSeq]:checked').val();
    let doctorSeq = $('input[name=doctorSeq]:checked').val();

    let value = document.getElementById('date').value;
    let dateObject = new Date(value)

    dayOfWeek = dateObject.getDay()

    if (childrenSeq && date && doctorSeq) {
        $.ajax({
            type: 'GET',
            url: '/api/appointment/time',
            dataType: "json",
            data: {
                appointmentDate: date,
                appointmentDayOfWeek: dayOfWeek,
                doctorSeq: doctorSeq,
                hospitalSeq: hospitalSeq
            },
            contentType: 'application/json',
            success: function (data) {
                console.log(data)
                let timeList = $('#time-list');
                timeList.empty();
                for (let i = 0; i < data.length; i++) {
                    timeList.append('<label class="appointment-time"><input type=\"radio\" name=\"appointmentHHMM\" value=\"' + data[i].substring(0, 5) + '"\"><span>' + data[i].substring(0, 5) + '</span></label>');
                }
                console.log("요청 성공")
            }, error: function () {
                alert("경고");
            }
        })
    } else {
        return
    }
});

$('input[name=date]').change(function (e) {
    let timeList = $('#time-list');
    timeList.empty();
});
