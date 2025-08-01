$(document).ready(function () {
    $.ajax({
        url: '/bo/menu/file-info',
        method: 'GET',
        success: function (data) {
            data.forEach(function (item) {
                let previewId = '';
                let deleteBtnId = '';
                switch (item.fileOrder) {
                    case '1':
                        previewId = '#previewMonthly';
                        deleteBtnId = '#btnDeleteMonthly';
                        break;
                    case '2':
                        previewId = '#previewCatering';
                        deleteBtnId = '#btnDeleteCatering';
                        break;
                    case '3':
                        previewId = '#previewFinger';
                        deleteBtnId = '#btnDeleteFinger';
                        break;
                    case '4':
                        previewId = '#previewDeli';
                        deleteBtnId = '#btnDeleteDeli';
                        break;
                }

                if (previewId && deleteBtnId) {
                    $(previewId).text(item.fileName + ' (등록됨)');
                    $(deleteBtnId).removeClass('d-none'); // 숨김 해제
                    $(deleteBtnId).data('fileId', item.fileSeq); // 파일 ID 바인딩
                }
            });
        },
        error: function () {
            console.error("기존 메뉴 파일 정보를 불러오는 데 실패했습니다.");
        }
    });

    // 파일명 표시
    $('input[type="file"]').on('change', function () {
        const fileName = this.files.length > 0 ? this.files[0].name : '선택된 파일 없음';
        const previewId = '#preview' + this.id.replace('file', '');
        $(previewId).text(fileName);
    });

    // 등록 버튼 클릭
    $('#btnUploadMenu').on('click', function () {
        const form = $('#menuUploadForm')[0];
        const formData = new FormData(form);

        $.ajax({
            url: '/bo/menu/insert',
            type: 'POST',
            data: formData,
            processData: false, // 파일 전송 시 필수
            contentType: false, // 파일 전송 시 필수
            success: function (response) {
                if (response.message === '성공') {
                    alert('메뉴가 등록되었습니다.');
                    location.reload();
                } else {
                    alert('등록 실패');
                }
            },
            error: function () {
                alert('업로드 중 오류가 발생했습니다.');
            }
        });
    });

    $('#btnDeleteMonthly, #btnDeleteCatering, #btnDeleteFinger, #btnDeleteDeli').on('click', function () {
        const fileId = $(this).data('fileId');
        if (!fileId) return;

        if (confirm('파일을 삭제하시겠습니까?')) {
            $.ajax({
                url: '/bo/menu/file-delete/' + fileId,
                type: 'DELETE',
                success: function (res) {
                    alert('파일이 삭제되었습니다.');
                    location.reload();
                },
                error: function () {
                    alert('파일 삭제 중 오류가 발생했습니다.');
                }
            });
        }
    });
});