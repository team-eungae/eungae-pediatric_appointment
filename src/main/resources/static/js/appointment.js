//날짜 입력창 기본 값으로 현재 날짜 설정
const sysdate = new  Date();
document.getElementById('date').value = sysdate.toISOString().substring(0, 10);
//선택가능한 최소날짜 설정
document.getElementById('date').min = sysdate.toISOString().substring(0, 10);

//선택가능한 최대날짜 설정 다음달 말일까지
var year = sysdate.getFullYear()+1;
var month = sysdate.getMonth() % 11 + 1;

if (month < 10) {
    month = "0" + month;
}

const nextmonthdate = new Date(year,month,1);
document.getElementById('date').max =  nextmonthdate.toISOString().substring(0, 10);


var setdate = function (){
    document.getElementById('appointment-date-view').innerHTML=document.getElementById('date').value.substring(5,7)+'월'+document.getElementById('date').value.substring(8)+'일';
};

//날짜 클릭 때마다 병원 정보의 날짜도 변경되도록
document.getElementById('date').addEventListener("change", setdate)

//진료가능시간 버튼 만들기
var starttime = "8:00";
var endtime = "18:00";
var list = ["8:00","8:30","9:00","9:30","10:00","10.30","11:00","11:30","12:00","12:30","2:00","2:30","3:00","3:30","4:00","4:30","5:00","5:30","6:00"];


$( document ).ready(function() {
    for(var i=0;i<list.length;i++){
        document.getElementById('time-list').innerHTML = document.getElementById('time-list').innerHTML+'<label class="appointment-time"><input type=\"radio\" name=\"appointment-time\" value=\"'+list[i]+'"\"><span>'+list[i]+'</span></label>';
    }
    setdate()
});

const buttons = document.querySelectorAll('.dropdown-button');

buttons.forEach(function(button, index) {
    button.addEventListener('click', function(e) {
        e.preventDefault();

        this.parentNode.classList.toggle('on');

        // buttons.forEach(function(button2, index2) {
        //     if ( index !== index2 ) {
        //         button2.parentNode.classList.remove('on');
        //     }
        // });
    });
});
