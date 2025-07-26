/****수정****/
$("button[data-role=update]").click(function () {
	window.location.href = '/bo/system/screen/banner/modify?bannerSeq=' + $("#bannerSeq").val();
});

/****삭제****/
$("button[data-role=delete]").click(function () {
	if( confirm("삭제하시겠습니까?") ) {
		$.ajax({
			type: "post",
			url: "/bo/system/screen/banner/delete",
			data: {
				'bannerSeq': $("#bannerSeq").val()
				, 'fileSeq': $("#fileSeq").val()
			},
			success: function (data) {
				alert("삭제되었습니다.");
				window.location.href = "/bo/system/screen/banner";
			},
			error: function () {
				alert("*****ajax fail*****");
			}
		});
	}
});

/****목록****/
$("button[data-role=list]").click(function () {
	window.location.href = '/bo/system/screen/banner';
});