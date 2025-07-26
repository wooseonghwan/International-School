$("select").selectAjax();

// Ckeditor 설정
let ckeditor_config = {
	autoParagraph: false,
	filebrowserUploadUrl: "/bo/system/file/fileUpload",
	filebrowserUploadMethod: 'form'
}

/* textarea 내용 입력란 ckeditor 연결 */
CKEDITOR.replace('noticeContent', ckeditor_config);

/* [목록] 버튼 클릭시 */
$("button[data-role=list]").click(function(){
	window.location.href = "/bo/system/homepage/notice";
});

/* [저장] 버튼 클릭시 */
$("button[data-role=save]").click(function(){
	let noticeTypeCd = $("form[name=insertNotice] select[name=selectType] option:selected").val();
	let noticeContent = CKEDITOR.instances.noticeContent.getData();
	let noticeTitle = $("form[name=insertNotice] input[name=noticeTitle]").val();

	/** 필수값 입력 alert */
	if (noticeTypeCd === "") {
        alert("공지 종류 선택해주세요.");
        $("form[name=insertNotice] select[name=selectType]").focus();
        return false;
    }if (noticeContent === "") {
		alert("내용 입력해주세요");
		$("#noticeContent").focus();
		return false;
	}if (noticeTitle === "") {
		alert("제목 입력해주세요");
		$("#noticeTitle").focus();
		return false;
	}

	let formData = new FormData();
	$("#notice-upload").each(function(){
		Array.from($(this)[0].files).map(e => formData.append('noticeFiles', e));
	});

	let jsonData = {
		'noticeTypeCd': noticeTypeCd,
		'title': noticeTitle,
		'content': noticeContent,
	}
	formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
	if (!confirm("등록하시겠습니까?")) {
	} else {
		$.ajax({
			url: "/bo/system/homepage/notice/insert",
			type: "POST",
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function(data) {
				alert("등록되었습니다.");
				/** insert 후 리스트 페이지로 이동 */
				location.href = "/bo/system/homepage/notice";
			},
			error: function() {
				alert("등록에 실패하였습니다.");
			}
		});
	}
});