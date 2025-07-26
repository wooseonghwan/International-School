let defaultUrl = '/bo/system/org/admin';

$("a[data-role=btnGoPage]").click(function () {
    let page = $(this).attr('data-page');
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

$("tr[data-role=goDetail]").click(function () {
    window.location.href = defaultUrl + '/detail?adminSeq=' + $(this).attr('data-seq');
});

$("select[name=rowSize]").change(function () {
    $("input[name=rowSize]").val($(this).val());
    $("input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

$("button[data-role=btn-search-form]").click(function() {
    let searchStart = $("input[name=searchStart]").val();
    let searchEnd = $("input[name=searchEnd]").val();

    if (searchEnd < searchStart) {
        alert("날짜를 확인해주세요");
        $("input[name=searchEnd]").val("");
    } else {
        $("input[name=page]").val("1");
        $("form[name=searchForm]").submit();
    }
});

/****기간 검색****/
let start = $("#searchStart").val();
let end = $("#searchEnd").val();

/** 시작일자 선택 */
$('#searchStart').change(function() {
    start = $(this).val();
})

/** 종료일자 선택 */
$('#searchEnd').change(function() {
    end = $(this).val();
})

$(document).ready(function() {
    let startDt = $("#startDt").val();
    let endDt = $("#endDt").val();
    $("#searchStart").val(startDt);
    $("#searchEnd").val(endDt);
    start = startDt;
    end = endDt;
});