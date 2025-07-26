$("select").selectAjax();

let idCheckFlag = false;

/* 사용자 아이디 중복체크 */
$("button[data-role=idDupCheck]").click(function () {
    let id = $("input[name=adminId]").val().trim();

    if (id === "") {
        alert("아이디를 입력해주세요.");
        $("input[name=adminId]").focus();
        return false;
    }

    $.ajax({
        type: "post",
        url: "/bo/system/org/admin/idCheck",
        data: {adminId: id},
        success: function (result) {
            if (result === 0) { // result=0 사용가능한 아이디일 경우
                alert("사용 가능한 아이디입니다.");
                idCheckFlag = true;
                $("button[data-role=idDupCheck]").hide();
            } else { // result != 0 이미 사용중인 아이디일 경우
                alert("이미 사용중인 아이디입니다.");
                idCheckFlag = false;
                $("input[name=adminId]").focus();
                return false;
            }
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
        }
    });
});

$("input[name=adminId]").change(function () {
    idCheckFlag = false;
    $("button[data-role=idDupCheck]").show();
});

/* 비밀번호 일치/불일치 여부 판단 */
$("input[name=adminPasswordChk]").focusout(function () {

    let pw = $("input[name=adminPassword]").val();
    let pwCheck = $("input[name=adminPasswordChk]").val();

    if (pwCheck === "") {
        $("div[name=pwChkMsgN]").css("display", "none");
        $("div[name=pwChkMsgY]").css("display", "none");
    }

    /* 비밀번호 입력란 공란 */
    if (pw === "") {
        /* 하단 메세지 전부 숨김 */
        $("div[name=pwChkMsgN]").css("display", "none");
        $("div[name=pwChkMsgY]").css("display", "none");
        alert("비밀번호를 먼저 입력해주세요.");
        $("input[name=adminPassword]").focus();
        /* 재확인 비밀번호 초기화 */
        $("input[name=adminPasswordChk]").val("");
        return false;
    }

    if (pw !== "" && pwCheck !== "") {

        if (pw === pwCheck) {
            $("div[name=pwChkMsgN]").css("display", "none");
            $("div[name=pwChkMsgY]").css("display", "inline-block");
        } else {
            $("div[name=pwChkMsgN]").css("display", "inline-block");
            $("div[name=pwChkMsgY]").css("display", "none");
        }
    }
});

/* [등록] 버튼 클릭시 */
$("button[data-role=register]").click(function () {
    let groupCd = $("form[name=insertForm] select[name=groupCd] option:selected").val();
    let adminId = $("form[name=insertForm] input[name=adminId]").val();
    let adminNm = $("form[name=insertForm] input[name=adminNm]").val();
    let adminStatusCd = $("form[name=insertForm] select[name=adminStatusCd] option:selected").val();
    let adminPassword = $("form[name=insertForm] input[name=adminPassword]").val();
    let useStartDt = $("form[name=insertForm] input[name=useStartDt]").val();
    let useEndDt = $("form[name=insertForm] input[name=useEndDt]").val();
    let email = $("form[name=insertForm] input[name=email]").val();

    /* 비밀번호 재확인 */
    let adminPasswordChk = $("form[name=insertForm] input[name=adminPasswordChk]").val();

    if (adminNm === "") {
        alert("이름을 입력해주세요.");
        $("form[name=insertForm] input[name=adminNm]").focus();
        return false;
    }

    if (email === "") {
        alert("이메일을 입력해주세요.");
        $("form[name=insertForm] input[name=emailId]").focus();
        return false;
    }

    if (adminId === "") {
        alert("사용자ID를 입력해주세요.");
        $("form[name=insertForm] input[name=adminId]").focus();
        return false;
    }

    if (!idCheckFlag) {
        alert("아이디 중복검사를 진행해주세요.");
        return false;
    }

    if (adminNm === "") {
        alert("사용자 이름을 입력해주세요.");
        $("form[name=insertForm] input[name=adminNm]").focus();
        return false;
    }

    if (adminStatusCd === "") {
        alert("관리자 상태를 지정해주세요.");
        $("form[name=insertForm] input[name=adminStatusCd]").focus();
        return false;
    }

    if (adminPassword === "") {
        alert("비밀번호를 입력해주세요.");
        $("form[name=insertForm] input[name=adminPassword]").focus();
        return false;
    }

    if (adminPasswordChk === "") {
        alert("비밀번호 재확인을 진행해주세요.");
        $("form[name=insertForm] input[name=adminPasswordChk]").focus();
        return false;
    }

    if (adminPassword !== adminPasswordChk && adminPasswordChk !== "") {
        alert("비밀번호 일치여부를 확인해주세요.");
        $("form[name=insertForm] input[name=adminPasswordChk]").focus();
        return false;
    }

    let params = {
        groupCd: groupCd,
        adminId: adminId,
        adminNm: adminNm,
        email: email,
        adminStatusCd: adminStatusCd,
        adminPassword: adminPassword,
        useStartDt: useStartDt,
        useEndDt: useEndDt
    }
    console.log({params});

    if (confirm("등록하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/org/admin/insert",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (data) {
                alert("등록되었습니다.");
                window.location.href = "/bo/system/org/admin";
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
});