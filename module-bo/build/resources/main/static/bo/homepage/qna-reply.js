// 목록
$('[data-role="btnList"]').click(function(){
    location.href='/bo/homepage/qna';
});

// 답변 저장
$('[data-role="btnUpdate"]').click(function(){
    let qnaId = $('#qnaId').val();
    let replyId = $('#replyId').val();
    let reply = $('#reply').val();

    if (reply.length === 0) {
        alert("답변을 입력해주세요");
        return;
    } else if (replyId.length === 0) {
        alert("답변 작성자를 입력해주세요");
        return;
    }

    if (!confirm("저장하시겠습니까?")) {
        return;
    }

    let replyForm = {
        qnaId : qnaId,
        replyId : replyId,
        reply : reply
    }

    $.ajax({
        type : "POST",
        url : `/bo/homepage/qna/reply-update`,
        contentType : "application/json",
        data : JSON.stringify(replyForm),
        success : function(){
            location.href=`/bo/homepage/qna/detail?qnaId=` + qnaId;
        },
        error : function(){
            alert("ajax failed on saving reply");
        }

    })
});