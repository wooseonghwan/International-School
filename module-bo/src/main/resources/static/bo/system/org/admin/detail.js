let adminSeq = $("input[name=adminSeq]").val();

/* [수정] 버튼 클릭시 */
$("button[data-role=modify]").click(function () {
    window.location.href = '/bo/system/org/admin/modify?adminSeq=' + adminSeq;
});

/* [삭제] 버튼 클릭시 */
$("button[data-role=delete]").click(function () {
    if (confirm("삭제하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/org/admin/delete",
            data: {adminSeq: adminSeq},
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = "/bo/system/org/admin";
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
});