// 우편번호 찾기 찾기 화면을 넣을 element
var element_wrap = document.getElementById("wrap");

function foldDaumPostcode() {
    // iframe을 넣은 element를 안보이게 한다.
    element_wrap.style.display = "none";
}

function execDaumPostcode() {
    // 현재 scroll 위치를 저장해놓는다.
    var currentScroll = Math.max(
        document.body.scrollTop,
        document.documentElement.scrollTop
    );
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ""; // 주소 변수
            var extraAddr = ""; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === "R") {
                // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else {
                // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === "R") {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== "" && data.apartment === "Y") {
                    extraAddr +=
                        extraAddr !== ""
                            ? ", " + data.buildingName
                            : data.buildingName;
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== "") {
                    extraAddr = " (" + extraAddr + ")";
                }
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("newZipCode").value = data.zonecode;
            document.getElementById("newAddress").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("hospitalImage").focus();

            // iframe을 넣은 element를 안보이게 한다.
            // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
            element_wrap.style.display = "none";

            // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
            document.body.scrollTop = currentScroll;
        },
        // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
        onresize: function (size) {
            element_wrap.style.height = size.height + "px";
        },
        width: "100%",
        height: "100%",
    }).embed(element_wrap);

    // iframe을 넣은 element를 보이게 한다.
    element_wrap.style.display = "block";
}

var fileNo = 0;
var filesArr = new Array();

/* 첨부파일 추가 */
function addFile(obj) {
    var maxFileCnt = 3;
    var attFileCnt = document.querySelectorAll(".filebox").length;
    var remainFileCnt = maxFileCnt - attFileCnt;
    var curFileCnt = obj.files.length;

    if (curFileCnt > remainFileCnt) {
        alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
    }

    // 파일 리스트를 담을 변수
    var fileList = document.querySelector(".file-list");
    // 선택된 파일 없음 메시지를 담을 변수
    var selectedFilesLabel = document.querySelector(".selected-files-label");

    for (var i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {
        const file = obj.files[i];

        if (validation(file)) {
            var reader = new FileReader();
            reader.onload = function (e) {
                // 이미지 파일인 경우에만 미리보기 추가
                if (file.type.match("image.*")) {
                    let htmlData =
                        '<div id="file' + fileNo + '" class="filebox">';
                    htmlData +=
                        '   <img src="' +
                        e.target.result +
                        '" alt="file-preview" />';
                    htmlData += '   <p class="name">' + file.name + "</p>";
                    htmlData +=
                        '   <a class="delete" onclick="deleteFile(' +
                        fileNo +
                        ');"><i class="far fa-minus-square"></i></a>';
                    htmlData += "</div>";
                    $(".file-list").append(htmlData);
                } else {
                    // 이미지가 아닌 경우 파일명만 표시
                    let htmlData =
                        '<div id="file' + fileNo + '" class="filebox">';
                    htmlData += '   <p class="name">' + file.name + "</p>";
                    htmlData +=
                        '   <a class="delete" onclick="deleteFile(' +
                        fileNo +
                        ');"><i class="far fa-minus-square"></i></a>';
                    htmlData += "</div>";
                    $(".file-list").append(htmlData);
                }

                // 파일 배열에 담기
                filesArr.push(file);
                fileNo++;
            };
            reader.readAsDataURL(file);
        } else {
            continue;
        }
    }

    // 선택된 파일 없음 메시지 표시 제어
    if (fileList.children.length > 0) {
        selectedFilesLabel.style.display = "none";
    } else {
        selectedFilesLabel.style.display = "block";
    }

    // 초기화
    document.querySelector("input[type=file]").value = "";
}

/* 첨부파일 검증 */
function validation(obj) {
    const fileTypes = ["image/jpeg", "image/png", "image/bmp", "image/jpg"];
    if (obj.name.length > 100) {
        alert("파일명이 100자 이상인 파일은 제외되었습니다.");
        return false;
    } else if (obj.size > 100 * 1024 * 1024) {
        alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
        return false;
    } else if (obj.name.lastIndexOf(".") == -1) {
        alert("확장자가 없는 파일은 제외되었습니다.");
        return false;
    } else if (!fileTypes.includes(obj.type)) {
        alert("첨부가 불가능한 파일은 제외되었습니다.");
        return false;
    } else {
        return true;
    }
}

/* 첨부파일 삭제 */
function deleteFile(num) {
    document.querySelector("#file" + num).remove();
    filesArr[num].is_delete = true;
}

/* 폼 전송 */
function submitForm() {
    // 폼데이터 담기
    var form = document.querySelector("form");
    var formData = new FormData(form);
    for (var i = 0; i < filesArr.length; i++) {
        // 삭제되지 않은 파일만 폼데이터에 담기
        if (!filesArr[i].is_delete) {
            formData.append("attach_file", filesArr[i]);
        }
    }

    $.ajax({
        method: "POST",
        url: "/register",
        dataType: "json",
        data: formData,
        async: true,
        timeout: 30000,
        cache: false,
        headers: { "cache-control": "no-cache", pragma: "no-cache" },
        success: function () {
            alert("파일업로드 성공");
        },
        error: function (xhr, desc, err) {
            alert("에러가 발생 하였습니다.");
            return;
        },
    });
}
