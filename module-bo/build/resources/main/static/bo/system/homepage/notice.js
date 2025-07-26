$("select").selectAjax();

$("a[data-role=btnGoPage]").click(function () {
	let page = $(this).attr('data-page');
	$("form[name=searchForm] input[name=page]").val(page);
	$("form[name=searchForm]").submit();
});

/****기간 검색****/
let start = $("#searchStart").val();
let end = $("#searchEnd").val();

/** date format */
function toStringByFormatting(source, delimiter = '-') {
	var year = source.getFullYear();
	var month = source.getMonth() + 1 ;
	var day = source.getDate() ;
	return [year, month >= 10  ? month : '0' + month, day >= 10 ? day : '0' + day].join(delimiter);
}

/** 시작일자 선택 */
$('#searchStart').change(function() {
	start = $(this).val();
})

/** 종료일자 선택 */
$('#searchEnd').change(function() {
	end = $(this).val();
})

/****검색****/
$("button[data-role=btn-search-form]").click(function() {
	let searchStart = $("input[name=searchStart]").val();
	let searchEnd = $("input[name=searchEnd]").val();

	if (searchEnd < searchStart) {
		alert("날짜를 확인해주세요");
		$("input[name=searchEnd]").val("");
	} else {
		$("input[name=page]").val("1");
		$("form[name=searchForm]").submit();
	}
});

/* [데이터 개수] 버튼 클릭시 */
$("select[data-role=select-row]").on('change', function(){
	let row = $(this).val();
	$("input[name=rowSize]").val(row);
	$("input[name=page]").val("1");
	$("form[name=searchForm]").submit();
});

/** [등록] 버튼 클릭시 */
$("button[data-role=form]").click(function() {
	window.location.href = "/bo/system/homepage/notice/form";
});

$(document).ready(function() {
	let startDt = $("#startDt").val();
	let endDt = $("#endDt").val();
	$("#searchStart").val(startDt);
	$("#searchEnd").val(endDt);
	start = startDt;
	end = endDt;
});