// 엔터키 검색
$(".search-form").keyup(function(event) {
    if (event.keyCode === 13) {
        $("form[name=searchForm]").submit();
    }
});

// 검색
$("button[data-role=btn-search-form]").click(function (){
    $("form[name=searchForm]").submit();
});

// 페이징
$("a[data-role=btnGoPage]").click(function () {
    let page = $(this).attr('data-page');
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

// 초기화 버튼 클릭시
$("button[data-role=reset]").click(function () {
    $("input[name=searchText]").val('');
    $("input[name=searchStart]").val('');
    $("input[name=searchEnd]").val('');
    $("select[name=searchType]").val('');
    // $("select[name=searchType2]").val('');
    $("form[name=searchForm]").submit();
});

$("select[name=searchType]").change(function () {
    $("form[name=searchForm]").submit();
});

// $("select[name=searchType2]").change(function () {
//     $("form[name=searchForm]").submit();
// });

$("input[name=searchStart], input[name=searchEnd]").change(function () {
    $("form[name=searchForm]").submit();
});

$('button[data-role=detail]').click(function () {
    const productRequestId = $(this).data('seq');
    window.location.href = `/bo/advertisement/ad-history-detail?productRequestId=${productRequestId}`;
});