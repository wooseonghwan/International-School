$(document).on('click', 'button[data-role]', function () {
    const role = $(this).data('role');

    if (role === 'register') {
        const userId = $('input[name=newUserId]').val().trim();
        const adminPassword = $('input[name=newPassword]').val().trim();
        const description = $('input[name=newDescription]').val().trim();
        const useYn = $('select[name=newUseYn]').val();

        if (!userId || !adminPassword) {
            alert('아이디와 비밀번호를 입력해주세요.');
            return;
        }

        if (!confirm("등록하시겠습니까?")) {
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/bo/admin-insert',
            contentType: 'application/json',
            data: JSON.stringify({ adminId: userId, adminPassword, description, useYn }),
            success: function () {
                alert('등록되었습니다.');
                location.reload();
            },
            error: function () {
                alert('등록 중 오류가 발생했습니다.');
            }
        });
    }

    if (role === 'update') {
        const $row = $(this).closest('tr');
        const adminSeq = $(this).data('seq');
        const adminId = $row.find('input[name=adminId]').val();
        const adminPassword = $row.find('input[name=password]').val();
        const description = $row.find('input[name=description]').val();
        const useYn = $row.find('select[name=useYn]').val();

        if (!confirm("수정 하시겠습니까?")) {
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/bo/admin-update',
            contentType: 'application/json',
            data: JSON.stringify({ adminId, adminSeq, adminPassword, description, useYn }),
            success: function () {
                alert('수정되었습니다.');
                location.reload();
            },
            error: function () {
                alert('수정 중 오류가 발생했습니다.');
            }
        });
    }

    if (role === 'delete') {
        const adminSeq = $(this).data('seq');
        if (!confirm(`계정을 삭제하시겠습니까?`)) return;

        $.ajax({
            type: 'POST',
            url: '/bo/admin-delete',
            contentType: 'application/json',
            data: JSON.stringify({ adminSeq }),
            success: function () {
                alert('삭제되었습니다.');
                location.reload();
            },
            error: function () {
                alert('삭제 중 오류가 발생했습니다.');
            }
        });
    }
});
