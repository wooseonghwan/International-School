// 페이징
$("a[data-role=btnGoPage]").click(function () {
    let page = $(this).attr('data-page');
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

//검색
$('button[data-role=btn-search-form]').click(function(){
    $('form[name=searchForm]').submit();
})

//등록
$('button[data-role=btnRegister]').click(function(){
    location.href=`/bo/homepage/faq/reg`;
})

//상세
$("tr").click(function(){
    let faqId = $(this).attr("data-seq");
    location.href=`/bo/homepage/faq/detail?faqId=`+faqId;
})

$('select[name=rowSize]').change(function(){
    $('form[name=searchForm]').submit();
})