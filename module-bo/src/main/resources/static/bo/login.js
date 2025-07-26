$(document).ready(function(){

    let errorMessage = $("input[name=errorMessage]").val();
    if (errorMessage !== "") {
        alert(errorMessage);
    }

    let key = getCookie("idChk");
    let selectAdminId = $("#adminId");
    let selectIdSave = $("#idSave");

    if (key !== "") {
        selectAdminId.val(key);
    }

    if(selectAdminId.val() !== ""){
        selectIdSave.attr("checked", true);
    }

    selectIdSave.change(function(){
        if(selectIdSave.is(":checked")){
            setCookie("idChk", selectAdminId.val(), 7);
        }else{
            deleteCookie("idChk");
        }
    });

    selectAdminId.keyup(function(){
        if(selectIdSave.is(":checked")){
            setCookie("idChk", selectAdminId.val(), 7);
        }
    });
});
function validateField(field) {
    if (field.val().trim() === "") {
        field.focus();
        return false;
    }
    return true;
}
// 로그인 버튼 클릭시
$("button[name=login]").click(function (e) {

    if (!validateField($("input[name=adminId]"))) {
        alert("아이디를 입력해주세요");
        return false;
    }
    if (!validateField($("input[name=adminPassword]"))) {
        alert("비밀번호를 입력해주세요");
        return false;
    }

    $.ajax({
        url: '/fo/login/process',
        type: 'POST',
        data: $("form[name=loginForm]").serialize(),
        dataType: 'json',
        success: function (d) {
            // FIXME :: 로그인 성공 후 처리
            console.log(d);
            window.location.href = "/";
        }
        ,
        error: function (xhr) {
            // FIXME :: 로그인 실패 후 처리
            let response = JSON.parse(xhr.responseText);
        }
    });
});

// ID/비밀번호 입력란 엔터키 로그인 이벤트
$(document).on("keyup","input[name=adminId],input[name=adminPassword]",function (e) {
    if (e.keyCode === 13) {
        $("form[name=loginForm]").submit();
    }
})
function setCookie(cookieName, value, exdays) {
    let exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    let cookieValue = encodeURIComponent(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
    document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName) {
    let expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toUTCString();
}

function getCookie(cookieName) {
    cookieName = cookieName + '=';
    let cookieData = document.cookie;
    let start = cookieData.indexOf(cookieName);
    let cookieValue = '';
    if (start !== -1) {
        start += cookieName.length;
        let end = cookieData.indexOf(';', start);
        if (end === -1) end = cookieData.length;
        cookieValue = decodeURIComponent(cookieData.substring(start, end));
    }
    return cookieValue;
}
// 회원가입 버튼 클릭시
$("button[data-role='signUp']").click(function () {
    $("#modal-signup").modal('show');
});
// 비밀번호 찾기 클릭시
$("button[data-role='findPw']").click(function () {
    $("#modal-password-find").modal('show');
});
// 각 모달 x 버튼 클릭시
$("button[data-role='closeModal']").click(function () {
    $("#modal-signup").modal('hide');
    $("#modal-password-find").modal('hide');
    $("#modal-certification-number").modal('hide');
});
// 이메일 정규식 체크
function isValidEmail(email) {
    // 이메일 형식에 맞는 정규 표현식
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
}
// 인증번호 전송 버튼 클릭시
$('button[data-role="sendEmail"]').on('click', function(){

    let email = $('input[type="email"]').val();

    if (!isValidEmail(email)) {
        $('#alert-not-email').modal('show');
        $('#emailModalClose1').on('click', function (){
            $('#alert-not-email').modal('hide');
        });
        return false;
    }
    // 이메일 존재 여부 확인
    $.ajax({
        type: "POST",
        url: "/email/check",
        data: {email: email},
        success: function (result) {
            console.log(result);
            if (result === 0) {
                $('#alert-email-inconsistency').modal('show');
                $('#emailModalClose').on('click', function (){
                    $('#alert-email-inconsistency').modal('hide');
                    $("input[name=email]").focus();
                });
                return false;
            } else {
                // 이메일 존재 시 메일 전송
                $.ajax({
                    type: 'POST',
                    url: '/contact/send',
                    contentType: 'application/json',
                    data: JSON.stringify({ email: email }),
                    success: function(response){
                        $("#modal-password-find").modal('hide');
                        $("#modal-certification-number").modal('show');
                    },
                    error: function(xhr, status, error){
                        console.error(xhr.responseText);
                    }
                });
            }
        },
        error: function () {
            $('#alert-not-process').modal('show');
            $('button[name="processModalClose"]').on('click', function (){
                $('#alert-not-process').modal('hide');
            });
        }
    });
});
// 인증번호 입력 후 인증 버튼 클릭시
$('button[data-role=certificate]').click(function () {

    const authCode = $('input[name=authCode]').val();
    if (!authCode) {
        $('#alert-no-num').modal('show');
        $('#noNum').on('click', function (){
            $('#alert-no-num').modal('hide');
        });
        return false;
    }

    $.ajax({
        type: 'POST',
        url: '/contact/send/check',
        contentType: 'application/json',
        data: JSON.stringify({ authCode: authCode }),
        success: function(response){
            console.log(response.status);
            if (response.status === 200) {
                $('#alert-num-success').modal('show');
                $('#success').on('click', function (){
                    $('#alert-num-success').modal('hide');
                    $("#modal-certification-number").modal('hide');
                    $("#modal-password-reset").modal('show');
                });
            }
        },
        error: function(xhr, status, error){
            $('#alert-num-fail').modal('show');
            $('#fail').on('click', function (){
                $('#alert-num-fail').modal('hide');
            });
            console.error(xhr.responseText);
        }
    });
})
// 비밀번호 재설정 버튼 클릭시
$('button[data-role=changePw]').click(function () {
    let password = $('input[name="password"]').val();
    let rePassword = $('input[name="rePassword"]').val();
    let email = $('input[type="email"]').val();

    // 비밀번호 유효성 검사
    //if (!validatePassword(password)) {
    //    $('#alert-password-not').modal('show');
    //    $('#noPassword').on('click', function (){
    //        $('#alert-password-not').modal('hide');
    //    });
    //    return false;
    //}

    if (password !== rePassword) {
        $('#alert-not-password').modal('show');
        $('#failModal').on('click', function (){
            $('#alert-not-password').modal('hide');
            $("input[name=rePassword]").focus();
        });
        return false;
    } else {
        $.ajax({
            type: "POST",
            url: "/change/password",
            contentType: 'application/json',
            data: JSON.stringify({ adminPassword: password, email: email }),
            success: function (data) {
                $('#alert-reset-password').modal('show');
                $('#okModal').on('click', function (){
                    $('#alert-reset-password').modal('hide');
                    location.href = "/bo/login";
                });
            },
            error: function () {
                $('#alert-not-process').modal('show');
                $('button[name="processModalClose"]').on('click', function (){
                    $('#alert-not-process').modal('hide');
                });
            }
        });
    }
})

// 비밀번호 유효성 검사
function validatePassword(password) {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
    return regex.test(password);
}