Kakao.init('7e37a44de8f06e46d95434ccb4e93234');

function loginWithKakao() {
    Kakao.Auth.authorize({
        redirectUri: 'http://localhost:8090/login/oauth2/code/kakao' // property로 빼기
    });
}