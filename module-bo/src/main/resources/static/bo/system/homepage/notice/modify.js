$("select").selectAjax();

// Ckeditor 설정
let ckeditor_config = {
	autoParagraph: false,
	filebrowserUploadUrl: "/bo/system/file/fileUpload",
	filebrowserUploadMethod: 'form'
}

/* textarea 내용 입력란 ckeditor 연결 */
CKEDITOR.replace('noticeContent', ckeditor_config);

/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function(){
	window.location.href = "/bo/system/homepage/notice/detail?noticeSeq=" + $("#noticeSeq").val();
});

/** [저장] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	let noticeTypeCd = $("form[name=updateNotice] select[name=selectType] option:selected").val();
	let noticeSeq = $("form[name=updateNotice] input[name=noticeSeq]").val();
	let title = $("form[name=updateNotice] input[name=title]").val();
	let content = CKEDITOR.instances.noticeContent.getData();

	/** 필수값 입력 alert */
	if (title === "") {
		alert("제목을 입력해주세요");
		$("form[name=updateNotice] input[name=title]").focus();
		return false;
	} else if (content === "") {
		alert("내용을 입력해주세요");
		return false;
	}

	let formData = new FormData();

	$("#notice-upload").each(function(){
		Array.from($(this)[0].files).map(e => formData.append('noticeFiles', e));
	});

	let jsonData = {
		fileSeq : $("#noticeFileSeq").val(),
		noticeTypeCd : noticeTypeCd,
		noticeSeq: noticeSeq,
		content: content,
		title: title,
	}
	formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

	if (!confirm("수정하시겠습니까?")) {

	} else {
		$.ajax({
			type: "post",
			url: "/bo/system/homepage/notice/update",
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function(data) {
				alert("수정되었습니다.");
				location.href = "/bo/system/homepage/notice/detail?noticeSeq=" + $("form[name=updateNotice] input[name=noticeSeq]").val();
			},
			error: function() {
				alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
			}
		});
	}
});

$("[data-role=fileDelete]").on("click", function () {
	if(!confirm("확인을 누르시면 파일이 수정과 상관없이 삭제됩니다.")) {
		return false;
	} else {
		let fileId = $(this).attr("name")
		$("[name=" + fileId + "]").remove()
		$(this).closest("br").remove()
		$.ajax({
			type: "post",
			url: "/bo/system/homepage/notice/deleteFile",
			data: {
				id: fileId,
			},
			success: function(data) {
				if ($(".registered-file").length === 0) {
					$(".registered-file-tr").css('display','none');
				}
			},
			error: function() {
				alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
			}
		});
	}
})