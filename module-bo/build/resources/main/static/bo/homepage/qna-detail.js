// 목록
$('[data-role="btnList"]').click(function(){
    location.href='/bo/homepage/qna';
});

// 답변하기 이동
$('[data-role="btnReply"]').click(function(){
    location.href='/bo/homepage/qna/reply?qnaId='+$('#qnaId').val();
});