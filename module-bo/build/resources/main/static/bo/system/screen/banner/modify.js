$("select").selectAjax();

/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function(){
	window.location.href = "/bo/system/screen/banner/detail?bannerSeq=" + $("#bannerSeq").val();
});

/** [저장] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	let bannerSeq = $("#bannerSeq").val();
	let fileSeq = $("#fileSeq").val();
	let bannerTypeCd = $("form[name=insertBanner] select[name=bannerTypeCd] option:selected").val();
	let bannerNm = $("form[name=insertBanner] input[name=bannerNm]").val();
	let bannerOrder = $("form[name=insertBanner] input[name=bannerOrder]").val();
	let bannerStartDt = $("form[name=insertBanner] input[name=bannerStartDt]").val();
	let bannerEndDt = $("form[name=insertBanner] input[name=bannerEndDt]").val();
	let linkUrl = $("form[name=insertBanner] input[name=linkUrl]").val();
	let useFlag = $("form[name=insertBanner] input[name=useFlag]:checked").val();

	/** 필수값 입력 alert */
	if (bannerTypeCd === "") {
		alert("배너 종류를 입력해주세요");
		$("form[name=insertBanner] select[name=bannerTypeCd]").focus();
		return false;
	} else if (bannerNm === "") {
		alert("배너 이름을 입력해주세요");
		$("form[name=insertBanner] input[name=bannerNm]").focus();
		return false;
	} else if (bannerOrder === "") {
		alert("배너순서를 입력해주세요");
		$("form[name=insertBanner] input[name=bannerOrder]").focus();
		return false;
	} else if (bannerStartDt === "") {
		alert("배너시작일을 입력해주세요");
		$("form[name=insertBanner] input[name=bannerStartDt]").focus();
		return false;
	} else if (bannerStartDt > bannerEndDt) {
		alert("날짜를 확인해주세요.");
		$("form[name=insertBanner] input[name=bannerEndDt]").focus();
		return false;
	} else if( useFlag === "" ) {
		alert("사용여부를 선택해주세요.");
		return false;
	}


	let jsonData = {
		'bannerSeq': bannerSeq,
		'fileSeq' : fileSeq,
		'bannerTypeCd': bannerTypeCd,
		'bannerNm': bannerNm,
		'bannerOrder': bannerOrder,
		'bannerStartDt': bannerStartDt,
		'bannerEndDt': bannerEndDt,
		'linkUrl': linkUrl,
		'useFlag': useFlag,
		'fileChangeFlag': $("input[name=fileChangeFlag]").val()
	};

	let formData = new FormData();
	formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
	$("#banner-upload").each(function(){
		Array.from($(this)[0].files).map(e => formData.append('bannerFiles', e));
	});

	if( confirm("수정하시겠습니까?") ) {
		$.ajax({
			type: "post",
			url: "/bo/system/screen/banner/update",
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function(data) {
				alert("수정되었습니다.");
				/** update 후 리스트 페이지로 이동 */
				location.href = "/bo/system/screen/banner"
			},
			error: function() {
				alert("수정에 실패했습니다.");
			}
		});
	}
});

$("[data-role=fileDelete]").on("click", function () {
	if(!confirm("확인을 누르시면 파일이 수정과 상관없이 삭제됩니다.")) {
		return false;
	} else {
		let fileId = $(this).attr("name");
		$("[name=" + fileId + "]").remove();
		$(this).closest("br").remove();
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
				alert("삭제에 실패했습니다.");
			}
		});
	}
})


/****준비완료****/
$(document).ready(function() {
	let startDt = $("#startDt").val();
	let endDt = $("#endDt").val();
	$("#bannerStartDt").val(startDt);
	$("#bannerEndDt").val(endDt);
	start = startDt;
	end = endDt;
});

$("input[name=file]").change(function () {
	$("input[name=fileChangeFlag]").val("Y");
})