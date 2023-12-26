const showhDetails = (id) =>{
    id="detail"+id;
//    if(document.getElementById(id).style.display==='none'){
//        showDetails(id);
//    }else{
//        hideDetails(id);
//    }
    location.href=""
}

function hideDetails(id)  {
    document.getElementById(id).style = 'display: none !important';
  }

function showDetails(id)  {
    document.getElementById(id).style = 'display: "" !important';
}