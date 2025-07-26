$("a[data-role='mypageOpen']").click(function () {
    $("#modal-mypage").modal('show');
});
$('button[data-role="mypageModalClose"]').click( function () {
    $("#modal-mypage").modal('hide');
})
$("li[data-role='modalOpen']").click(function () {
    let noticeSeq = $(this).data('notice-seq');
    let noticeTypeNm = $(this).data('notice-type');
    let title = $(this).data('title');
    let content = $(this).data('content');

    // 모달의 내용을 업데이트합니다.
    $('#modal-title').text(title);
    $('#modal-noticeTypeTitle').text('[' + noticeTypeNm + '] ' + title);
    $('#modal-noticeContent').html(content.replace(/\n/g, '<br>').replace(/ /g, '&nbsp;'));
    // 모달을 엽니다.
    $('#modal-notice').modal('show');
});
$("button[data-role='closeModal']").click(function () {
    $("#modal-sale-price").modal('hide');
    $("#modal-notice").modal('hide');
    $("#modal-certification-number").modal('hide');
});
$("button[data-role='adminOut']").click(function () {
    $("#alert-withdrawal").modal('show');
});
$("button[data-role='adminOutClose']").click(function () {
    $("#alert-withdrawal").modal('hide');
});
$("button[data-role='adminLogout']").click(function () {
    $("#alert-logout").modal('show');
});
$("button[data-role='adminLogoutClose']").click(function () {
    $("#alert-logout").modal('hide');
});
$("button[data-role='logout']").click(function () {
    window.location.href='/bo/logout'
});

// 재설정 버튼 클릭시
$('button[data-role="sendEmail"]').on('click', function(){
    let email = $('input[name="email"]').val();

    $('#loadingBar').show();

    $.ajax({
        type: 'POST',
        url: '/contact/send',
        contentType: 'application/json',
        data: JSON.stringify({ email: email }),
        success: function(response){
            $('#loadingBar').hide();
            $("#modal-certification-number").modal('show');
        },
        error: function(xhr, status, error){
            $('#loadingBar').hide();
            console.error(xhr.responseText);
        }
    });
});
// 인증번호 입력 후 인증 버튼 클릭시
$('button[data-role=certificate]').click(function () {

    const authCode = $('input[name=authCode]').val();
    if (!authCode) {
        $('#alert-no-num').modal('show');
        $('#noNum').on('click', function (){
            $('#alert-no-num').modal('hide');
        });
        return false;
    }

    $.ajax({
        type: 'POST',
        url: '/contact/send/check',
        contentType: 'application/json',
        data: JSON.stringify({ authCode: authCode }),
        success: function(response){
            console.log(response.status);
            if (response.status === 200) {
                $('#alert-num-success').modal('show');
                $('#success').on('click', function (){
                    $('#alert-num-success').modal('hide');
                    $("#modal-certification-number").modal('hide');
                    $("#modal-password-reset").modal('show');
                });
            }
        },
        error: function(xhr, status, error){
            $('#alert-num-fail').modal('show');
            $('#fail').on('click', function (){
                $('#alert-num-fail').modal('hide');
            });
            console.error(xhr.responseText);
        }
    });
})
// 비밀번호 재설정 버튼 클릯기
$('button[data-role=changePw]').click(function () {
    let password = $('input[name="password"]').val();
    let rePassword = $('input[name="rePassword"]').val();
    let email = $('input[name="email"]').val();

    // 비밀번호 유효성 검사 (테스트로 인해 우선 주석)
    //if (!validatePassword(password)) {
    //    $('#alert-password-not').modal('show');
    //    $('#noPassword').on('click', function (){
    //        $('#alert-password-not').modal('hide');
    //    });
    //    return false;
    //}

    if (password !== rePassword) {
        $('#alert-not-password').modal('show');
        $('#failModal').on('click', function (){
            $('#alert-not-password').modal('hide');
            $("input[name=rePassword]").focus();
        });
        return false;
    } else {
        $.ajax({
            type: "POST",
            url: "/change/password",
            contentType: 'application/json',
            data: JSON.stringify({ adminPassword: password, email: email }),
            success: function (data) {
                $('#alert-reset-password').modal('show');
                $('#okModal').on('click', function (){
                    $('#alert-reset-password').modal('hide');
                    location.href = "/bo/logout";
                });
            },
            error: function () {
                $('#alert-not-process').modal('show');
                $('button[name="processModalClose"]').on('click', function (){
                    $('#alert-not-process').modal('hide');
                });
            }
        });
    }
})