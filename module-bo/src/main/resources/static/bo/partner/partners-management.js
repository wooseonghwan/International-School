$('a[data-toggle="modal"]').click(function () {
    const partnersId = $(this).data('partners-id');
    const adId = $(this).data('ad-id');

    $.ajax({
        type: 'GET', // GET이 더 적절
        url: '/bo/partner/status-change-history',
        data: {
            partnersId: partnersId,
            adId: adId
        },
        success: function (data) {
            const tbody = $('#status-change-tbody');
            tbody.empty(); // 기존 내용 초기화

            if (data && data.length > 0) {
                data.forEach(item => {
                    const row = `
                        <tr>
                            <td class="text-center">${item.statusChangeDt}</td>
                            <td class="text-center">${item.statusName}</td>
                        </tr>`;
                    tbody.append(row);
                });
            } else {
                tbody.append(`<tr><td colspan="2" class="text-center">이력이 없습니다.</td></tr>`);
            }

            // Bootstrap 5에서 모달 열기
            const modal = new bootstrap.Modal(document.getElementById('modal-state-change'));
            modal.show();
        },
        error: function () {
            alert("상태 변경 이력 조회에 실패했습니다.");
        }
    });
});
$('a[data-role="detail"]').click(function (e) {
    e.preventDefault(); // 기본 링크 이동 방지
    const adId = $(this).data('ad-id');

    window.location.href = '/bo/partner/partners-management-detail?adId=' + encodeURIComponent(adId);
});

$('a[data-role="report"]').click(function (e) {
    e.preventDefault(); // 기본 링크 이동 방지
    const adId = $(this).data('ad-id');
    const createId = $(this).data('partners-id');

    window.location.href = '/bo/partner/partners-report?adId=' + encodeURIComponent(adId) +'&createId=' + encodeURIComponent(createId);
});

$('a[data-role="noReport"]').click(function () {
    alert("보고서 미제출 상태입니다.");
    return false;
});

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
