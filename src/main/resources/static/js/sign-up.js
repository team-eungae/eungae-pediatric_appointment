// 우편번호 찾기 찾기 화면을 넣을 element
var element_wrap = document.getElementById('wrap');

function foldDaumPostcode() {
    // iframe을 넣은 element를 안보이게 한다.
    element_wrap.style.display = 'none';
}

function execDaumPostcode() {
    // 현재 scroll 위치를 저장해놓는다.
    var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
    new daum.Postcode({
        oncomplete: function (data) {
            // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }

            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipCode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("addressDetail").focus();

            // iframe을 넣은 element를 안보이게 한다.
            // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
            element_wrap.style.display = 'none';

            // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
            document.body.scrollTop = currentScroll;
        },
        // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
        onresize: function (size) {
            element_wrap.style.height = size.height + 'px';
        },
        width: '100%',
        height: '100%'
    }).embed(element_wrap);

    // iframe을 넣은 element를 보이게 한다.
    element_wrap.style.display = 'block';
}



// 비밀번호 유효성 검사
var myInput = document.getElementById("password");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");

// 비밀번호 입력 칸 클릭 시 조건문 div 보여줌
myInput.onfocus = function() {
  document.getElementById("message").style.display = "block";
}

// 비밀번호 입력 칸이 아닌 곳을 클릭 시 조건문 사라지게 함
myInput.onblur = function() {
  document.getElementById("message").style.display = "none";
}

// 비밀번호 입력을 시작하면 유효성 검사 시작
myInput.onkeyup = function() {
  // 영문 소문자 검사
  var lowerCaseLetters = /[a-z]/g;
  if(myInput.value.match(lowerCaseLetters)) {
    letter.classList.remove("invalid");
    letter.classList.add("valid");
  } else {
    letter.classList.remove("valid");
    letter.classList.add("invalid");
}

  // 영문 대문자 검사
  var upperCaseLetters = /[A-Z]/g;
  if(myInput.value.match(upperCaseLetters)) {
    capital.classList.remove("invalid");
    capital.classList.add("valid");
  } else {
    capital.classList.remove("valid");
    capital.classList.add("invalid");
  }

  // 숫자 검사
  var numbers = /[0-9]/g;
  if(myInput.value.match(numbers)) {
    number.classList.remove("invalid");
    number.classList.add("valid");
  } else {
    number.classList.remove("valid");
    number.classList.add("invalid");
  }

  // 비밀번호 길이 검사
  if(myInput.value.length >= 8) {
    length.classList.remove("invalid");
    length.classList.add("valid");
  } else {
    length.classList.remove("valid");
    length.classList.add("invalid");
  }
}

// 비밀번호 정보 가져오기
// 1. 비밀번호 입력창 정보 가져오기
let elInputPassword = document.querySelector('#password'); // input#password
// 2. 비밀번호 확인 입력창 정보 가져오기
let elInputPasswordCheck = document.querySelector('#password-check'); // input#password-retype
// 3. 실패 메시지 정보 가져오기 (비밀번호 불일치)
let elMismatchMessage = document.querySelector('.mismatch-message'); // div.mismatch-message.hide

//비밀번호와 비밀번호 확인이 일치할 경우 true, 아니면 false를 리턴한다.
function isMatch (password1, password2) {
  return password1 === password2;
}

elInputPasswordCheck.onkeyup = function () {

  // console.log(elInputPasswordRetype.value);
  if (elInputPasswordCheck.value.length !== 0) {
    if(isMatch(elInputPassword.value, elInputPasswordCheck.value)) {
      elMismatchMessage.classList.add('hide'); // 실패 메시지가 가려져야 함
    }
    else {
      elMismatchMessage.classList.remove('hide'); // 실패 메시지가 보여야 함
    }
  }
  else {
    elMismatchMessage.classList.add('hide'); // 실패 메시지가 가려져야 함
  }
};
//조건이 부적합할 경우 : .classList.remove('hide')로 hide 클래스를 삭제하여 화면에 표시한다.
//조건이 적합할 경우 : .classList.add('hide')로 hide 클래스를 추가하여 화면에서 가린다.

// $(document).ready(function () {
//     var errorMessage = [[${errorMessage}]];
//     if (errorMessage != null) {
//         alert(errorMessage);
//     }
// })