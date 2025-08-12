$('.search-form').keyup(function(event) {
    if (event.keyCode === 13) {
        $('form[name=searchForm]').submit();
    }
});
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

// 수정/상세
$("button[data-role=detail]").click(function () {
    const userId = $(this).data('seq');
    window.location.href = `/bo/user/user-detail?userId=${userId}`;
});

// 등록
$('button[data-role=blacklist]').click(function () {
    const seq = $(this).attr('data-seq');
    $('#modalSeq').val(seq);
    $('#modal-blacklist').modal('show');
});

// 예
$('button[data-role=add-blacklist]').click(function () {
    const data = {
        userId: $('#modalSeq').val(),
        blacklistReason: $('#blacklistReason').val()
    }
    $.ajax({
        url: '/bo/user/blacklist',
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function () {
            alert("등록 되었습니다.");
            location.reload();
        }
    });
});

// 아니오
$('button[data-role=close]').click(function () {
    $('#modal-blacklist').modal('hide');
});

// 엑셀 다운로드
$('button[data-role=excelDownload]').click(function () {
    const searchValue = $('input[name=searchValue]').val();
    const searchStart = $('input[name=searchStart]').val();
    const searchEnd = $('input[name=searchEnd]').val();
    window.location.href = `/bo/user/excel?searchValue=${searchValue}&searchStart=${searchStart}&searchEnd=${searchEnd}`;
});