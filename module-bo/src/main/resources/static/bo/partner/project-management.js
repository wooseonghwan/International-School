$('button[data-role=form]').click( function () {
    window.location.href = '/bo/partner/project-form';
})
$("a[data-role=btnGoPage]").click(function () {
    let page = $(this).attr('data-page');
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});
$('div[data-role="detail"]').click(function (e) {
    e.preventDefault(); // 기본 링크 이동 방지
    const projectId = $(this).data('project-id');

    window.location.href = '/bo/partner/project-detail?projectId=' + encodeURIComponent(projectId);
});