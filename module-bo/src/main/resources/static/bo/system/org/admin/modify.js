$("select").selectAjax();

let adminSeq = $("input[name=adminSeq]").val();

/* [수정] 버튼 클릭 시 */
$("button[data-role=modify]").click(function () {
    let groupCd = $("form[name=updateAdminForm] select[name=groupCd] option:selected").val();
    let adminNm = $("form[name=updateAdminForm] input[name=adminNm]").val();
    let email = $("form[name=updateAdminForm] input[name=email]").val();
    let adminStatusCd = $("form[name=updateAdminForm] select[name=adminStatusCd] option:selected").val();
    let useStartDt = $("form[name=updateAdminForm] input[name=useStartDt]").val();
    let useEndDt = $("form[name=updateAdminForm] input[name=useEndDt]").val();

    /* 필수입력값 alert */
    if (adminNm === "") {
        alert("사용자 이름을 입력해주세요.");
        $("form[name=updateAdminForm] input[name=adminNm]").focus();
        return false;
    }
    if (email === "") {
        alert("이메일을 입력해주세요.");
        $("form[name=updateAdminForm] input[name=email]").focus();
        return false;
    }

    /* update 될 data -> params 그룹화 */
    let params = {
        adminSeq: adminSeq,
        groupCd: groupCd,
        adminNm: adminNm,
        email: email,
        adminStatusCd: adminStatusCd,
        useStartDt: useStartDt,
        useEndDt: useEndDt,
    }
    console.log(params);

    if (confirm("수정하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/org/admin/update",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (data) {
                alert("수정되었습니다.");
                window.location.href = '/bo/system/org/admin/detail?adminSeq=' + adminSeq;
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
});

$(document).ready(function() {
    let startDt = $("input[name=mUseStartDt]").val();
    let endDt = $("input[name=mUseEndDt]").val();
    $("#useStartDt").val(startDt);
    $("#useEndDt").val(endDt);
});

