

function joinCheck() {
    var id = document.getElementById("userID");
    var email = document.getElementById("userEmail");
    var pass = document.getElementById("userPassword");
    var pwdCheck = document.getElementById("passwordCheck");
    var name = document.getElementById("userName");
    var phone = document.getElementById("userPhone");


    //아이디 정규화식
    var regul1 = /^[a-zA-Z0-9]{4,12}$/;


    //이메일 정규화 공식
    var regul2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;

    //이름 정규화 공식
    var regul3 = /^[가-힝a-zA-Z]{2,}$/;

    //비밀번호 정규화식
    var regul4 = /^[a-zA-Z0-9]{8,14}$/;

    //핸드폰 정규화식
    var regul5 = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

    //아이디 입력 하지 않았을 경우
    if ((id.value) === ""){
        alert("아이디를 입력하지 않았습니다.");
        id.focus();
        return false;
    }

    //아이디 유효성 검사
    //내가 입력한 데이터를 검사하는 check()
    //만약 내가 아이디에 정규화 방식을 하나라도 지키지 않으면 if문 안으로 들어가서 alert message를 띄움
    if (!check(regul1,id,"아이디는 4~12자의 대소문자와 숫자로만 입력 가능합니다.")) {
        return false;//반환 할 곳 없이 if문 탈출
    }

    //이메일 입력 안했을 경우
    if ((email.value) === "") {
        alert("이메일을 입력해 주세요");
        email.focus();
        return false;
    }
    //이메일이 잘못된 경우
    if (!check(regul2,email,"이메일을 잘못 입력 했습니다.")) {
        return false;
    }

    //비밀번호 입력 하지 않았을 경우
    if ((pass.value) === ""){
        alert("비밀번호를 입력해 주세요");
        pass.focus();
        return false;
    }
    if ((pwdCheck.value === "")){
        alert("비밀번호를 입력해 주세요");
        pwdCheck.focus();
        return false;
    }

    //비밀번호 유효성 검사
    //만약 내가 비밀번호에 정규화 방식을 하나라도 지키지 않으면 if문 안으로 들어가서 alert message를 띄움
    if (!check(regul4,pass,"비밀번호는 8~14자의 대소문자와 숫자로만 입력 가능합니다.")) {
        return false;
    }

    //비밀번호와 비밀번호 확인이 일치 하지 않을 경우
    if ((pass.value) !== (pwdCheck.value)) {
        alert("비밀번호가 일치 하지 않습니다.");
        pass.focus();
        pwdCheck.focus();
        return false;
    }

    //이름 입력 안 한 경우
    if ((name.value) === "") {
        alert("이름을 입력해 주세요");
        name.focus();
        return false;
    }
    //이름에 특수 문자가 들어간 경우
    if (!check(regul3,name,"이름이 잘못 되었습니다.")) {
        return false;
    }


    //핸드폰 번호 입력 안 한 경우
    if ((phone.value) === "") {
        alert("이름을 입력해 주세요");
        phone.focus();
        return false;
    }

    // 핸드폰 정규식
    if (!check(regul5,phone, "올바른 핸드폰 번호가 아닙니다.")) {
        return false;
    }
    return success();
}

function check(re,what,message){//정규화데이터,아이템 id,메세지
    if (re.test(what.value)) {//what의 문자열에 re의 패턴이 있는지 나타내는 함수 test
        //만약 내가 입력한 곳의 값이 정규화 데이터를 썼다면 true를 반환해서 호출 된 곳으로 리턴됨
        return true;
    }
    alert(message);
    what.value = "";
    what.focus();
}




// 카카오 주소 api
function kakaopost() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.querySelector("#LOGIN_POST").value = data.zonecode;
            document.querySelector("#LOGIN_ADDR").value =  data.address
            document.querySelector("input[name=ADDR_DETAIL]").focus(); //상세입력 포커싱
        }
    }).open();
}


function success(){
    alert("회원 가입이 되었습니다.");
}