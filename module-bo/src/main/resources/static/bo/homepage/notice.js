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
    location.href=`/bo/homepage/notice/reg`;
})


//상세
$("button[data-role=detail]").click(function(){
    let noticeId = $(this).attr("data-seq");
    location.href=`/bo/homepage/notice/detail?noticeId=`+noticeId;
})

$('select[name=rowSize]').change(function(){
    $('form[name=searchForm]').submit();
})
$('#checkAll').on('change', function () {
    $('.check-item').prop('checked', this.checked);
});
$('button[data-role=delete]').on('click', function () {
    const selectedIds = $('.check-item:checked').map(function () {
        return $(this).val();
    }).get();

    if (selectedIds.length === 0) {
        alert('삭제할 공지사항을 선택해주세요.');
        return;
    }

    if (!confirm(`${selectedIds.length}건을 삭제하시겠습니까?`)) {
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/bo/homepage/notice/delete',
        contentType: 'application/json',
        data: JSON.stringify({ noticeIds: selectedIds }),
        success: function () {
            alert('삭제되었습니다.');
            location.reload();
        },
        error: function () {
            alert('삭제 중 오류가 발생했습니다.');
        }
    });
});

