$('button[data-role=save]').click(function () {
    const status = $('#status').val();
    const reportStatus = $('#reportStatus').val();
    const adId = $('#adId').val();
    const partnersId = $('#partnersId').val();
    const projectId = $('#projectId').val();
    $.ajax({
        type: 'POST',
        url: '/bo/partner/update-partners-status',
        contentType: 'application/json',
        data: JSON.stringify({
            adId: adId,
            status: status,
            reportStatus: reportStatus,
            partnersId: partnersId,
            projectId: projectId
        }),
        success: function (res) {
            alert('상태가 변경되었습니다.');
            window.location.href = '/bo/partner/partners-management';
        },
        error: function () {
            alert('상태 저장 중 오류가 발생했습니다.');
        }
    });
});
$('button[data-role=list]').click(function () {
    window.location.href = '/bo/partner/partners-management';
})
