function clearDate(id) {
    document.getElementById(id).value = '';
}

$('.search-form').keyup(function (event) {
    if (event.keyCode === 13) {
        $('form[name=searchForm]').submit();
    }
});
$('button[data-role=btn-search-form]').click(function () {
    $('input[name="page"]').val(1);
    $('form[name=searchForm]').submit();
});
$('a[data-role=btnGoPage]').click(function () {
    let page = $(this).attr('data-page');
    $('form[name=searchForm] input[name=page]').val(page);
    $('form[name=searchForm]').submit();
});