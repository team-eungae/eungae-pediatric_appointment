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

    value = document.getElementById('date').value;
    let dateObject = new Date(value)

    dayOfWeek = dateObject.getDay()

    if (childrenSeq && value && doctorSeq) {
        $.ajax({
            type: 'GET',
            url: '/api/appointment/time',
            dataType: "json",
            data: {
                appointmentDate: value,
                appointmentDayOfWeek: dayOfWeek,
                doctorSeq: doctorSeq,
                hospitalSeq: hospitalSeq,
                childrenSeq: childrenSeq
            },
            contentType: 'application/json',
            success: function (data) {
                let timeTable = data[0]
                console.log(timeTable)
                if (data[1] === true) {
                    alert("해당 날짜에 이미 예약이 존재합니다.")
                    let timeList = $('#time-list');
                    timeList.empty();
                } else {
                    let timeList = $('#time-list');
                    timeList.empty();
                    for (let i = 0; i < timeTable.length; i++) {
                        timeList.append('<label class="appointment-time"><input type=\"radio\" name=\"appointmentHHMM\" value=\"' + timeTable[i].substring(0, 5) + '"\"><span>' + timeTable[i].substring(0, 5) + '</span></label>');
                    }
                }
            }, error: function () {
                alert("서버 통신에 실패했습니다.");
            }
        })
    }
});

const summitBtn = () => {
    let childrenSeq = $('input[name=childrenSeq]:checked').val();
    let doctorSeq = $('input[name=doctorSeq]:checked').val();
    let appointmentHHMM = $('input[name=appointmentHHMM]:checked').val();
    let note = $('textarea[name=note]').val();
    let value = document.getElementById('date').value;

    if (childrenSeq && doctorSeq && appointmentHHMM && note && value) {
        $.ajax({
            type: 'POST',
            url: `/api/${hospitalSeq}/appointments`,
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({
                appointmentDate: value,
                appointmentHHMM: appointmentHHMM,
                hospitalSeq: hospitalSeq,
                childrenSeq: childrenSeq,
                doctorSeq: doctorSeq,
                note: note
            }),
            success: function (data) {
                alert("예약이 완료되었습니다.")
                window.location.href = `/hospital/${hospitalSeq}`;
            }, error: function (e) {
                alert("서버 통신에 실패했습니다.");
            }
        })
    } else {
        alert("입력하신 정보를 다시한번 확인해주십시오.")
    }

}

$('input[name=date]').change(function (e) {
    let timeList = $('#time-list');
    timeList.empty();
});
