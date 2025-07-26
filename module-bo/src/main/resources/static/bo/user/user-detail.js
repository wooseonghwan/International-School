document.addEventListener('DOMContentLoaded', function () {
    const phoneInput = document.querySelector('input[name="phone"]');
    if (phoneInput) {
        const raw = phoneInput.value.replace(/[^0-9]/g, '');
        let formatted = raw;

        if (raw.startsWith('02')) {
            if (raw.length === 9) {
                formatted = raw.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
            } else if (raw.length === 10) {
                formatted = raw.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
            }
        } else {
            if (raw.length === 10) {
                formatted = raw.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
            } else if (raw.length === 11) {
                formatted = raw.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
            }
        }

        phoneInput.value = formatted;
    }
});
$('#togglePassword').on('click', function () {
    const userId = $('#userId').val();

    if (!confirm('비밀번호를 "1234"로 초기화하시겠습니까?')) return;

    $.ajax({
        url: '/bo/reset-password',
        type: 'POST',
        data: {userId: userId},
        success: function () {
            alert('비밀번호가 "1234"로 초기화되었습니다.');
            $('#passwordInput').val('********'); // 마스킹
        },
        error: function () {
            alert('비밀번호 초기화에 실패했습니다.');
        }
    });
});
$('button[data-role=btnList]').click(function () {
    location.href = `/bo/user`;
})

// 블랙리스트 등록
$('button[data-role=blacklist]').click(function () {
    const seq = $(this).attr('data-seq');
    $('#modalSeq').val(seq);
    $('#modal-blacklist').modal('show');
});

// 회원 이용 내역
$('button[data-role=history]').click(function () {
    const seq = $(this).attr('data-seq');
    $('#historySeq').val(seq);
    $('#modal-history').modal('show');
});

// 닫기
$('button[data-role=close]').click(function () {
    $('#modal-history').modal('hide');
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
$('button[data-role=cancel]').click(function () {
    $('#modal-blacklist').modal('hide');
});

// 수정
$("button[data-role=update]").click(function () {

    let userId = $("#userId").val();
    let name = $("form[name=userDetail] input[name=name]").val();
    let companyName = $("form[name=userDetail] input[name=companyName]").val();
    let addr = $("form[name=userDetail] input[name=addr]").val();
    let phone = $("form[name=userDetail] input[name=phone]").val();
    let bankAccountNumber = $("form[name=userDetail] input[name=bankAccountNumber]").val();
    let loginId = $("form[name=userDetail] input[name=loginId]").val();
    let businessNumber = $("form[name=userDetail] input[name=businessNumber]").val();
    let mainMedia = $("form[name=userDetail] input[name=mainMedia]").val();
    let memo = $("form[name=userDetail] input[name=memo]").val();
    let approvalStatus = $("form[name=userDetail] input[name=approvalStatus]").val();

    if (confirm("수정하시겠습니까?")) {

        let jsonData = {
            'userId': userId,
            'name': name,
            'companyName': companyName,
            'addr': addr,
            'phone': phone,
            'bankAccountNumber': bankAccountNumber,
            'loginId': loginId,
            'businessNumber': businessNumber,
            'mainMedia': mainMedia,
            'memo': memo,
            'approvalStatus': approvalStatus
        };

        $.ajax({
            type: "POST",
            url: "/bo/user/update",
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            success: function () {
                alert("수정되었습니다.");
                window.location.href = "/bo/user";
            },
            error: function () {
                alert("수정에 실패하였습니다.");
            }
        });
    }
});
// 통장사본 다운로드
$('#bankUrl').on('click', function () {
    const fileUrl = $(this).data('url');         // 실제 파일 URL
    const userName = $('#userName').val()?.trim() || '사용자'; // 이름이 없으면 기본값

    if (!fileUrl) {
        alert("등록된 통장사본 파일이 없습니다.");
        return;
    }
    // 파일 확장자 추출 (.pdf, .jpg 등)
    const extension = fileUrl.split('.').pop().split(/\#|\?/)[0];
    const cleanExt = extension.length <= 5 ? extension : 'download';
    const downloadFileName = `${userName}_통장사본.${cleanExt}`;
    // a 태그 생성하여 다운로드 트리거
    const a = document.createElement('a');
    a.href = fileUrl;
    a.download = downloadFileName;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
});
// 사업자 등록증 다운로드
$('#businessFileUrl').on('click', function () {
    const fileUrl = $(this).data('url');
    const userName = $('#userName').val()?.trim() || '사용자';

    if (!fileUrl) {
        alert("등록된 사업자등록증 파일이 없습니다.");
        return;
    }
    // 파일 확장자 추출 (.pdf, .jpg 등)
    const extension = fileUrl.split('.').pop().split(/\#|\?/)[0];
    const cleanExt = extension.length <= 5 ? extension : 'download';
    const downloadFileName = `${userName}_사업자등록증.${cleanExt}`;
    // a 태그 생성하여 다운로드 트리거
    const a = document.createElement('a');
    a.href = fileUrl;
    a.download = downloadFileName;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
});


