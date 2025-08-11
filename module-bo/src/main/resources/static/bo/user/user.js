$('.search-form').keyup(function(event) {
    if (event.keyCode === 13) {
        $('form[name=searchForm]').submit();
    }
});
$('button[data-role=btn-search-form]').click(function (){
    $('input[name="page"]').val(1);
    $('form[name=searchForm]').submit();
});
$('a[data-role=btnGoPage]').click(function () {
    let page = $(this).attr('data-page');
    $('form[name=searchForm] input[name=page]').val(page);
    $('form[name=searchForm]').submit();
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