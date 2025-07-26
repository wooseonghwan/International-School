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
    $("select[name=searchType2]").val('');
    $("form[name=searchForm]").submit();
});
$("select[name=searchType]").change(function () {
    $("form[name=searchForm]").submit();
});
$("select[name=searchType2]").change(function () {
    $("form[name=searchForm]").submit();
});
$("input[name=searchStart], input[name=searchEnd]").change(function () {
    $("form[name=searchForm]").submit();
});
$('button[data-role=excelDownload]').click(function () {
    // 엑셀 다운로드 요청 URL
    const downloadUrl = "/bo/payment/payment-history/excel-download";

    // 폼 생성하여 GET 요청 전송
    const form = document.createElement("form");
    form.setAttribute("method", "GET");
    form.setAttribute("action", downloadUrl);

    const searchDateInput = document.getElementById("searchDateInput");
    if (searchDateInput) {
        const hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "searchDate");
        hiddenField.setAttribute("value", searchDateInput.value);
        form.appendChild(hiddenField);
    }

    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form); // 제출 후 폼 제거
});
$('button[data-role=detail]').click(function () {
    const paymentId = $(this).data('payment-id');
    // idx 값을 URL에 포함하여 페이지 이동
    window.location.href = `/bo/payment/payment-history-detail?paymentId=${paymentId}`;
});