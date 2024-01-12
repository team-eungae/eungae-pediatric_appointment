var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
    coll[i].addEventListener("click", function () {
        this.classList.toggle("active");
        var content = this.nextElementSibling;
        if (content.style.maxHeight) {
            content.style.maxHeight = null;
        } else {
            content.style.maxHeight = content.scrollHeight + "px";
        }
    });
}

function selectImage() {
    document.getElementById('file-input').click();
}

function previewImage(event) {
    var img = document.getElementById('preview');
    img.src = URL.createObjectURL(event.target.files[0]);
}
// // 폼 제출 이벤트 리스너 추가
// document.querySelector('.children-add-form').addEventListener('submit', function(e) {
//     e.preventDefault(); // 기본 폼 제출 동작 중단
//     submitForm();
// });
//
// function submitForm() {
//     var formData = new FormData(document.querySelector('.children-add-form'));
//     var fileInput = document.getElementById('file-input');
//     if(fileInput.files[0]) {
//         formData.append('profileImage', fileInput.files[0]);
//     }
//
//     // 생년월일 데이터 추가 확인
//     console.log("생년월일 데이터: ", formData.get('birthday'));
//
//     $.ajax({
//         type: 'POST',
//         url: '/children/add', // 컨트롤러의 엔드포인트 URL
//         data: formData,
//         processData: false, // FormData 사용 시 필요
//         contentType: false, // FormData 사용 시 필요
//         success: function(response) {
//             // 성공적으로 데이터를 전송한 후의 동작
//             alert("자녀 정보가 성공적으로 등록되었습니다!");
//             window.location.href = '/children/list'; // 예: 자녀 목록 페이지로 리디렉션
//         },
//         error: function(xhr, status, error) {
//             // 오류 처리
//             alert('자녀 정보 등록에 실패하였습니다. 오류: ' + error);
//         }
//     });
// }

