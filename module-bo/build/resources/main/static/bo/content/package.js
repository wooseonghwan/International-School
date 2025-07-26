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
    $("form[name=searchForm]").submit();
});
$('button[data-role=insert-page]').click( function () {
    window.location.href = '/bo/content/package-form';
})
$('button[data-role=detail]').click(function () {
    const packageId = $(this).data('id');
    window.location.href = `/bo/content/package-detail?packageId=${packageId}`;
});
function changeDate(monthOffset) {
    // 현재 표시된 날짜 가져오기
    const dateElement = document.getElementById("displayDate");
    let currentDate = new Date(dateElement.textContent + "-01");

    // 월을 변경하고 새로운 날짜로 설정
    currentDate.setMonth(currentDate.getMonth() + monthOffset);

    // 변경된 날짜를 업데이트
    updateDateDisplay(currentDate);

    document.querySelector("form[name=searchForm]").submit();
}
// 변경된 날짜 화면에 표시
function updateDateDisplay(date) {
    const formattedDate = date.toISOString().slice(0, 7);
    document.getElementById("displayDate").textContent = formattedDate;
    document.getElementById("searchDateInput").value = formattedDate;
}