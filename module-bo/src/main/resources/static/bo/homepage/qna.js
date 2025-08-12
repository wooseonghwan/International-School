let searchDirty = false;

$('select[name="searchType"], input[name="searchValue"]').on('input change', function () {
    searchDirty = true;
});

// 검색 버튼: 무조건 1페이지부터
$('button[data-role="btn-search-form"]').on('click', function () {
    $('input[name="page"]').val(1);
    searchDirty = false; // 제출했으니 초기화
    $('form[name="searchForm"]').submit();
});

// 페이지 번호 클릭
$(document).on('click', 'a[data-role="btnGoPage"]', function () {
    const $form = $('form[name="searchForm"]');
    const page = $(this).attr('data-page');

    // 검색어/조건이 바뀐 상태라면 강제로 1페이지부터
    $form.find('input[name="page"]').val(searchDirty ? 1 : page);
    searchDirty = false; // 제출했으니 초기화
    $form.submit();
});

$("tr").click(function(){
    let qnaId = $(this).attr("data-seq");
    location.href=`/bo/homepage/qna/detail?qnaId=`+qnaId;
})

$('select[name=rowSize]').change(function(){
    $('form[name=searchForm]').submit();
})