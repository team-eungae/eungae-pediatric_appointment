let shownum = -1;
const onShowBtn = () =>{
    shownum = -shownum;
    console.log(shownum);
    if(shownum===1){
        showClinic();
        document.getElementsByClassName("clinic-hours-button")[0].style.transform = "rotate(180deg)";
    }else if(shownum===-1){
        hideClinic();
        document.getElementsByClassName("clinic-hours-button")[0].style.transform = "rotate(0deg)";
    }
}

function hideClinic()  {
    const row = document.getElementById('schedule');
    row.style.display = 'none';
  }
  
function showClinic()  {
    const row = document.getElementById('schedule');
    row.style.display = '';
}

var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
};
var map = new kakao.maps.Map(container, options);
