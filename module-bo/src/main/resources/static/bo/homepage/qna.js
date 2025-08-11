// 페이징
$("a[data-role=btnGoPage]").click(function () {
    let page = $(this).attr('data-page');
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

// 검색
$('button[data-role=btn-search-form]').click(function(){
    $('input[name="page"]').val(1);
    $('form[name=searchForm]').submit();
})

$("tr").click(function(){
    let qnaId = $(this).attr("data-seq");
    location.href=`/bo/homepage/qna/detail?qnaId=`+qnaId;
})

$('select[name=rowSize]').change(function(){
    $('form[name=searchForm]').submit();
})