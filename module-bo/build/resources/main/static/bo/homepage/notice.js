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

