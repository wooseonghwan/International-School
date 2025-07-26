// 엔터키 검색
$(".search-form").keyup(function(event) {
    if (event.keyCode === 13) {
        $("form[name=searchForm]").submit();
    }
});

// 검색
$("button[data-role=btn-search-form]").click(function (){
    $("form[name=searchForm]").submit();
});

// 페이징
$("a[data-role=btnGoPage]").click(function () {
    let page = $(this).attr('data-page');
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

// 초기화 버튼 클릭시
$("button[data-role=reset]").click(function () {
    $("input[name=searchText]").val('');
    $("form[name=searchForm]").submit();
});

// const fileInput = document.getElementById("banner-upload");
// const fileChosen = document.getElementById("file-chosen");
// // 파일 선택 시 선택된 파일 이름을 표시
// fileInput.addEventListener("change", function() {
//     if (this.files.length > 0) {
//         // 여러 파일 이름을 ,로 구분하여 표시
//         const fileNames = Array.from(this.files).map(file => file.name).join(", ");
//         fileChosen.textContent = fileNames;
//     } else {
//         fileChosen.textContent = "선택된 파일 없음";
//     }
// });

// 등록 버튼 클릭시
$("button[data-role=insert-modal-open]").click(function () {
    $('#modal-category').find('input').val('');
    $('#modal-category').modal('show');
});

// 등록 모달에서 저장 버튼 클릭시
$('button[data-role="insert"]').on('click', function() {

    let useYn = $('#useYn').val();
    let categoryName = $('#categoryName').val();
    let subCategoryName = $('#subCategoryName').val();

    let jsonData = {
        'useYn': useYn,
        'categoryName': categoryName,
        'subCategoryName': subCategoryName,
    };

    // let formData = new FormData();
    // formData.append("jsonData", new Blob([JSON.stringify(jsonData)], { type: 'application/json' }));
    // Array.from($("#banner-upload")[0].files).forEach(file => {
    //     formData.append('productFiles', file);
    // });

    // $.ajax({
    //     url: '/bo/content/insert-category',
    //     type: "POST",
    //     data: formData,
    //     contentType: false,
    //     processData: false,
    //     enctype: 'multipart/form-data',
    //     success: function(response) {
    //         alert('카테고리가 성공적으로 등록되었습니다.');
    //         window.location.href='/bo/content/category';
    //     },
    //     error: function(xhr, status, error) {
    //         alert('등록 중 오류가 발생했습니다. 다시 시도해주세요.');
    //         console.error('Error:', error);
    //     }
    // });
    $.ajax({
        type: "POST",
        url: "/bo/content/insert-category",
        data: JSON.stringify(jsonData),
        contentType: "application/json",
        success: function () {
            alert("등록되었습니다.");
            location.href = "/bo/content/category";
        },
        error: function () {
            alert("등록 중 오류가 발생했습니다.");
        }
    });
});

//
$('button[data-role="close"]').on('click', function() {
    $('#modal-category').modal('hide');
})

// 상세/수정 버튼 클릭시
$(document).on("click", "button[data-role=detail-modal-open]", function() {
        const categoryId = $(this).data("seq");
        $.ajax({
            type: "GET",
            url: "/bo/content/category-detail",
            data: { categoryId: categoryId },
            success: function(data) {
                console.log(data); // 전체 데이터 구조 확인
                const detail = data.data.detail;
                // const file = data.data.file;

                // 모달의 각 요소에 데이터를 설정합니다.
                $("input[name=categoryId]").val(detail.categoryId);
                $("select[name=useYn]").val(detail.useYn || '');
                $("input[name=categoryName]").val(detail.categoryName || '');
                $("input[name=subCategoryName]").val(detail.subCategoryName || '');

                // 파일 목록 처리
                const fileContainer = $("#fileUpload-div");
                fileContainer.empty();
                // if (file && file.length > 0) {
                //     file.forEach(function(item) {
                //         const fileLink = `<a name="${item.id}" href="${item.path}" target="_blank" class="mr-1 registered-file">${item.originName}</a>`;
                //         const deleteButton = `<button type="button" class="btn btn-sm btn-danger" data-role="fileDelete" name="${item.id}">파일 삭제</button>`;
                //         fileContainer.append(fileLink + deleteButton);
                //     });
                // }
                $("#detail-modal-category").modal("show");
            },
            error: function() {
                alert("카테고리 정보를 불러오는데 실패했습니다.");
            }
        });
        $('button[data-role=update]').click(function(){
            if(!confirm(`카테고리를 수정하시겠습니까?`)){
                return false;
            }

            let categoryId = $('input[name=categoryId]').val();
            let useYn = $('select[name=useYn]').val();
            let categoryName = $('input[name=categoryName]').val();
            let subCategoryName = $('input[name=subCategoryName]').val();
            // let fileSeq = $("#fileSeq").val();

            let jsonData = {
                'categoryId' : categoryId,
                'useYn' :useYn,
                'categoryName' :categoryName,
                'subCategoryName' :subCategoryName,
                // 'fileSeq' : fileSeq
            };

            // let formData = new FormData();
            // formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
            // $("#fileUpload").each(function(){
            //     Array.from($(this)[0].files).map(e => formData.append('productFiles', e));
            // });

            // $.ajax({
            //     type: "POST",
            //     url:`/bo/content/update-category`,
            //     data: formData,
            //     contentType: false,
            //     processData: false,
            //     enctype: 'multipart/form-data',
            //     success: function(){
            //         alert("수정 되었습니다.");
            //         $('#detail-modal-category').css('display', 'none');
            //         window.location.href='/bo/content/category';
            //     },
            //     error: function(){
            //         alert("ajax failed on update status");
            //     }
            // })

            $.ajax({
                type: "POST",
                url: "/bo/content/update-category",
                data: JSON.stringify(jsonData),
                contentType: "application/json",
                success: function () {
                    alert("등록되었습니다.");
                    location.href = "/bo/content/category";
                },
                error: function () {
                    alert("등록 중 오류가 발생했습니다.");
                }
            });
        })
    $('#close-btn-info').click(function(){
        $('#detail-modal-category').modal('hide');
    });
})
// $(document).on("click", "[data-role=fileDelete]", function () {
//     if (!confirm("확인을 누르시면 파일이 수정과 상관없이 삭제됩니다.")) {
//         return false;
//     } else {
//         let fileId = $(this).attr("name");
//
//         // 해당 파일 링크와 삭제 버튼을 제거
//         $("[name=" + fileId + "]").remove();
//         // 만약 삭제 버튼이 <br> 태그로 구분되어 있다면 그에 맞게 제거
//         $(this).closest("br").remove();
//         // AJAX 요청으로 서버에 파일 삭제 요청
//         $.ajax({
//             type: "POST",
//             url: "/bo/content/package-deleteFile",
//             data: {
//                 id: fileId,
//             },
//             success: function(data) {
//                 // 모든 파일이 삭제된 경우 UI 요소를 숨김 처리
//                 if ($(".registered-file").length === 0) {
//                     $(".registered-file-tr").css('display', 'none');
//                 }
//             },
//             error: function() {
//                 alert("삭제에 실패했습니다.");
//             }
//         });
//     }
// });
// $("input[name=file]").change(function () {
//     $("input[name=fileChangeFlag]").val("Y");
// })
// let fileMaxCount = 1;   //첨부파일 최대 개수
// let fileNo = 0;
// let filesArr = new Array();
// // 파일첨부 클릭
// $("button[data-role='fileUpload']").click(function(){
//     $("input[id='fileUpload']").click();
// });
// // 첨부파일 업로드
// $("input[id='fileUpload']").change(function(){
//     let remnantCount = fileMaxCount-($("div[class='filebox']").length+$(".registered-file").length);
//     let selectedFileLength = $(this)[0].files.length;
//
//     if ( selectedFileLength>remnantCount ) {
//         alert("파일 업로드는 최대 " + fileMaxCount + "개 까지 가능합니다.");
//         return false;
//     }
//
//     for (let i=0; i<selectedFileLength; i++) {
//         let file = $(this)[0].files[i];
//
//         if( validateFile(file) ) { //이미지 파일 체크
//             let reader = new FileReader();
//             reader.onload = function() {
//                 filesArr.push(file);
//             };
//             reader.readAsDataURL(file);
//
//             let htmlData = '';
//             htmlData += '<div id="new-file'+fileNo+'" class="filebox" style="float: left; margin-right: 5px;">';
//             htmlData += '   <span class="name" style="margin-right: 5px;">' + file.name + '</span>';
//             htmlData += '   <a href="javascript:void(0);" class="delete-file" onclick="deleteFile('+fileNo+');" style="cursor: pointer;">';
//             htmlData += '       <i class="far fa-minus-square"></i>';
//             htmlData += '   </a>&nbsp;&nbsp;';
//             htmlData += '</div>';
//             $("div[id='fileUpload-div']").append(htmlData);
//             fileNo++;
//         }
//         else {
//             return false;
//         }
//     }
//     $("#fileUpload").value = "";
// });
// // 첨부파일 체크
// function validateFile(file) {
//     let types = ['image/gif', 'image/jpeg', 'image/png', 'image/bmp', 'image/tif'];
//
//     if (!types.includes(file.type)) {
//         alert("이미지 파일만 첨부 가능합니다.");
//         return false;
//     }
//    else if (file.size > (10 * 1024 * 1024)) {
//        alert("업로드 최대용량은 10MB입니다.");
//        return false;
//    }
//    else if (file.name.lastIndexOf('.') == -1) {
//        alert("확장자가 없는 파일입니다.");
//        return false;
//    }
//     else {
//         return true;
//     }
// }
function changeDate(monthOffset) {
    // 현재 표시된 날짜 가져오기
    const dateElement = document.getElementById("displayDate");
    let currentDate = new Date(dateElement.textContent + "-01");

    // 월을 변경하고 새로운 날짜로 설정
    currentDate.setMonth(currentDate.getMonth() + monthOffset);

    // 변경된 날짜를 업데이트
    updateDateDisplay(currentDate);

    document.querySelector("form[name=searchForm]").submit();
}
// 변경된 날짜 화면에 표시
function updateDateDisplay(date) {
    const formattedDate = date.toISOString().slice(0, 7);
    document.getElementById("displayDate").textContent = formattedDate;
    document.getElementById("searchDateInput").value = formattedDate;
}
$('button[data-role=excelDownload]').click(function () {
    // 엑셀 다운로드 요청 URL
    const downloadUrl = "/bo/content/category/excel-download";

    // 폼 생성하여 GET 요청 전송
    const form = document.createElement("form");
    form.setAttribute("method", "GET");
    form.setAttribute("action", downloadUrl);

    const searchDateInput = document.getElementById("searchDateInput");
    if (searchDateInput) {
        const hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "searchDate");
        hiddenField.setAttribute("value", searchDateInput.value);
        form.appendChild(hiddenField);
    }

    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form); // 제출 후 폼 제거
});
$('button[data-role="delete"]').on('click', function() {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/content/delete-category",
            data: {
                cateSeq: $("#cateSeq").val()
            },
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = "/bo/content/category";
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
})