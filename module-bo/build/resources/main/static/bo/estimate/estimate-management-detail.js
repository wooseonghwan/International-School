$('button[data-role=list]').click( function () {
    location.href = '/bo/estimate/estimate-management';
});
$('button[data-role=save]').click( function () {
    const estimateId = $("form[name=updateEstimate] input[name=estimateId]").val();
    const estimateAmount = $("form[name=updateEstimate] input[name=estimateAmount]").val();
    const beforeEstimateAmount = $("form[name=updateEstimate] input[name=beforeEstimateAmount]").val();
    const status = $("form[name=updateEstimate] select[name=status] option:selected").val();
    const userId = $("#userId").val();
    const categoryId = $("#categoryId").val();
    const locationId = $("#locationId").val();
    const platform = $("form[name=updateEstimate] input[name=platform]").val();
    const platformDetail = $("form[name=updateEstimate] input[name=platformDetail]").val();
    const adType = $("#adType").val();
    const adTypeEtc = $("#adTypeEtc").val();
    const adTime = $("#adTime").val();
    const adTimeEtc = $("#adTimeEtc").val();
    const memo = $("form[name=updateEstimate] input[name=memo]").val();

    let jsonData = {
        'estimateId': estimateId,
        'estimateAmount': estimateAmount,
        'beforeEstimateAmount': beforeEstimateAmount,
        'status': status,
        'userId': userId,
        'categoryId': categoryId,
        'locationId': locationId,
        'platform': platform,
        'platformDetail': platformDetail,
        'adType': adType,
        'adTypeEtc': adTypeEtc,
        'adTime': adTime,
        'adTimeEtc': adTimeEtc,
        'memo': memo,
    };
    if( confirm("수정하시겠습니까?") ) {
        $.ajax({
            type: "post",
            url: "/bo/estimate/update-estimate",
            data: JSON.stringify(jsonData),
            contentType: "application/json",
            processData: false,
            success: function(data) {
                alert("수정되었습니다.");
                location.href = '/bo/estimate/estimate-management';
            },
            error: function() {
                alert("수정에 실패했습니다.");
            }
        });
    }
})
$('#toggleHistoryBtn').on('click', function () {
    $('#estimateHistoryBox').slideToggle();
});
$(document).on('click', '.history-item', function () {
    const estimateId = $(this).data('estimate-id');
    const createDt = $(this).data('create-dt');

    $.ajax({
        url: '/bo/estimate/estimate-history-detail',
        type: 'GET',
        data: {
            estimateId: estimateId,
            createDt: createDt
        },
        success: function (data) {
            $('#modalUserName').val(data.name);
            $('#modalPhone').val(data.phone);
            $('#modalLoginId').val(data.loginId);
            $('#modalBusinessNumber').val(data.businessNumber);
            $('#modalCategory').val(data.categoryName + ' > ' + data.subCategoryName);
            $('#modalLocation').val(data.city + ' ' + data.district + ' ' + data.town);
            $('#modalPlatform').val(data.platform);
            $('#modalAdType').val(data.platformDetail);
            $('#modalAdRunType').val(data.adTypeEtc || data.adType); // 예시로 넣은 고정값
            $('#modalAdTime').val(data.adTimeEtc || data.adTime);
            $('#modalKeyword').val(data.keywordName);
            $('#modalEstimateAmount').val(Number(data.estimateAmount).toLocaleString() + '원');
            $('#modalCreateDt').val(data.createDt);
            $('#modalMemo').val(data.memo);
            $('#modalName').val(data.updateAdminNm || data.updateUserNm);
            $('#modalStatus').val(data.statusNm);

            $('#estimateHistoryModal').modal('show');
        },
        error: function () {
            alert('수정 내역 상세 정보를 불러오지 못했습니다.');
        }
    });
});
$('.btn-close').on('click', function () {
    const $modal = $('#estimateHistoryModal');
    $modal.find('input').val('');
    $modal.modal('hide');
});