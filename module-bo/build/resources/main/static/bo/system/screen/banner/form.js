$("select").selectAjax();

/****배너순서 유효성체크****/
let replaceNotInt = /[^0-9]/gi;
$("form[name=insertBanner] input[name=bannerOrder]").on("focusout", function() {
	let x = $(this).val();
	if (x.length > 0) {
		if (x.match(replaceNotInt)) {
			x = x.replace(replaceNotInt, "");
		}
		$(this).val(x);
	}
}).on("keyup", function() {
	$(this).val($(this).val().replace(replaceNotInt, ""));
});

/****목록****/
$("button[data-role=list]").click(function(){
	window.location.href = "/bo/system/screen/banner";
});

/****저장****/
$("button[data-role=save]").click(function(){
	let bannerTypeCd = $("form[name=insertBanner] select[name=bannerTypeCd] option:selected").val();
	let bannerNm = $("form[name=insertBanner] input[name=bannerNm]").val();
	let bannerOrder = $("form[name=insertBanner] input[name=bannerOrder]").val();
	let bannerStartDt = $("form[name=insertBanner] input[name=bannerStartDt]").val();
	let bannerEndDt = $("form[name=insertBanner] input[name=bannerEndDt]").val();
	let linkUrl = $("form[name=insertBanner] input[name=linkUrl]").val();

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
	}

	let jsonData = {
		'bannerTypeCd': bannerTypeCd,
		'bannerNm': bannerNm,
		'bannerOrder': bannerOrder,
		'bannerStartDt': bannerStartDt,
		'bannerEndDt': bannerEndDt,
		'linkUrl': linkUrl
	};

	let formData = new FormData();
	formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
	$("#banner-upload").each(function(){
		Array.from($(this)[0].files).map(e => formData.append('bannerFiles', e));
	});

	if ( confirm("등록하시겠습니까?") ) {
		$.ajax({
			url: "/bo/system/screen/banner/insert",
			type: "POST",
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function(data) {
				let result = data.data;
				if( result!=undefined && result!=null && result=="DUPLICATE" ) {
					alert("배너순서가 중복됩니다.");
					$("form[name=insertBanner] input[name=bannerOrder]").focus();
					return false;
				} else {
					alert("등록되었습니다.");
					/** insert 후 리스트 페이지로 이동 */
					location.href = "/bo/system/screen/banner";
				}
			},
			error: function() {
				alert("등록 중 오류가 발생했습니다. 관리자에게 문의하세요.");
			}
		});
	}
});