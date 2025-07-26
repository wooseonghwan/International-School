$(document).ready(function () {
    tinymce.init({
        selector: 'textarea[name=content]', // name 속성이 'content'인 textarea 선택
        height: 300,
        menubar: false,
        plugins: [
            'advlist autolink lists link image charmap print preview anchor',
            'searchreplace visualblocks code fullscreen',
            'insertdatetime media table paste code help wordcount'
        ],
        toolbar: 'undo redo | formatselect | ' +
            'bold italic backcolor | alignleft aligncenter ' +
            'alignright alignjustify | bullist numlist outdent indent | ' +
            'removeformat | image | help', // 이미지 버튼 추가
        image_title: true,
        automatic_uploads: true,
        /*file_picker_types: 'image',

        // 서버로 이미지 업로드하는 핸들러
        images_upload_handler: function (blobInfo, success, failure) {
            // 서버 업로드 API URL - 실제 서버의 업로드 URL로 변경 필요
            let uploadUrl = '/bo/system/file/tinymceUpload';
            let xhr = new XMLHttpRequest();
            let formData = new FormData();

            xhr.open('POST', uploadUrl);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    try {
                        // 서버에서 JSON 응답을 받아와서 이미지 URL을 성공 콜백에 전달
                        let json = JSON.parse(xhr.responseText);
                        if (json && json.url) {
                            success(json.url); // 서버의 응답 JSON에서 이미지 URL을 추출하여 TinyMCE에 전달
                        } else {
                            failure('Invalid JSON response');
                        }
                    } catch (error) {
                        failure('Failed to parse JSON response');
                    }
                } else {
                    failure('Image upload failed with status ' + xhr.status);
                }
            };
            xhr.onerror = function () {
                failure('Image upload failed due to a network error.');
            };

            formData.append('file', blobInfo.blob(), blobInfo.filename());
            xhr.send(formData);
        },*/
        init_instance_callback: function (editor) {
            const existingContent = $('input[name=editorContent]').val();
            editor.setContent(existingContent);
        }
    });
    // 초기 로드 시 첫 번째, 두 번째, 세 번째 선택 옵션 초기화
    updateMediaDetails();
    updateMediaDetailOptions();

    // 첫 번째 옵션(media) 선택 변경 시 첫 번째와 두 번째 상세 옵션 업데이트
    $('#media').change(function () {
        updateMediaDetails();
        updateMediaDetailOptions();
    });

    // 두 번째 옵션(mediaDetail) 선택 변경 시 세 번째 옵션 업데이트
    $('#naverDetails select, #instarDetails select, #youtubeDetails select, #kakaoDetails select').change(function () {
        updateMediaDetailOptions();
    });

    // 첫 번째 옵션에 따라 두 번째 옵션(mediaDetail)을 표시하고 동적으로 name 속성을 설정
    function updateMediaDetails() {
        // 모든 첫 번째 상세 옵션 숨기기 및 name 속성 제거
        $('.mediaDetailRow').hide().find('select').removeAttr('name');

        const selectedMedia = $('#media').val();

        // 선택된 첫 번째 옵션에 따라 두 번째 옵션 보이기
        if (selectedMedia === 'NAVER') {
            $('#naverDetails').show().find('select').attr('name', 'mediaDetail');
        } else if (selectedMedia === 'INSTAR') {
            $('#instarDetails').show().find('select').attr('name', 'mediaDetail');
        } else if (selectedMedia === 'YOUTUBE') {
            $('#youtubeDetails').show().find('select').attr('name', 'mediaDetail');
        } else if (selectedMedia === 'KAKAO') {
            $('#kakaoDetails').show().find('select').attr('name', 'mediaDetail');
        }
    }

    // 두 번째 옵션에 따라 세 번째 옵션(mediaDetail2)을 표시하고 동적으로 name 속성을 설정
    function updateMediaDetailOptions() {
        // 모든 세 번째 상세 옵션 숨기고 name 속성 제거
        $('.naverDetails, .instarDetails, .youtubeDetails, .kakaoDetails').hide().find('select').removeAttr('name');

        const selectedMedia = $('#media').val();
        let selectedDetail;

        // 각 첫 번째 옵션(media)에 해당하는 두 번째 상세 옵션 값 가져오기
        if (selectedMedia === 'NAVER') {
            selectedDetail = $('#naverDetails select[name="mediaDetail"]').val();
        } else if (selectedMedia === 'INSTAR') {
            selectedDetail = $('#instarDetails select[name="mediaDetail"]').val();
        } else if (selectedMedia === 'YOUTUBE') {
            selectedDetail = $('#youtubeDetails select[name="mediaDetail"]').val();
        } else if (selectedMedia === 'KAKAO') {
            selectedDetail = $('#kakaoDetails select[name="mediaDetail"]').val();
        }

        // 선택된 두 번째 상세 옵션 값에 따라 세 번째 상세 옵션 보이기 및 name 속성 추가
        let targetElement;
        if (selectedDetail === 'BLOG') {
            targetElement = $('#naverBlogDetails');
        } else if (selectedDetail === 'PLACE') {
            targetElement = $('#naverPlaceDetails');
        } else if (selectedDetail === 'CAFE') {
            targetElement = $('#naverCafeDetails');
        } else if (selectedDetail === 'JISICK') {
            targetElement = $('#naverJisickDetails');
        } else if (selectedDetail === 'IMAGE') {
            targetElement = $('#naverImageDetails');
        } else if (selectedDetail === 'NEWS') {
            targetElement = $('#naverNewsDetails');
        } else if (selectedDetail === 'AUTO') {
            targetElement = $('#naverAutoDetails');
        } else if (selectedDetail === 'SEARCH-AD') {
            targetElement = $('#naverSearchDetails');
        } else if (selectedDetail === 'TOP-SITE') {
            targetElement = $('#naverTopDetails');
        } else if (selectedDetail === 'ACCOUNT') {
            targetElement = $('#naverAcountDetails');
        } else if (selectedDetail === 'MANAGEMENT') {
            targetElement = $('#instarManagerDetails');
        } else if (selectedDetail === 'POPULAR') {
            targetElement = $('#instarPopularDetails');
        } else if (selectedDetail === 'SPONSOR') {
            targetElement = $('#instarSponsorDetails');
        } else if (selectedDetail === 'PRODUCT') {
            targetElement = $('#youtubeProductDetails');
        } else if (selectedDetail === 'KAKAOTALK') {
            targetElement = $('#kakaoKakaoDetails');
        }

        // 선택된 옵션만 보이도록 설정하고 name 속성 추가
        if (targetElement) {
            targetElement.show().find('select').attr('name', 'mediaDetail2');
        }
    }
});

$("button[data-role=save]").click(function(){
    let packageSeq = $("#packageSeq").val();
    let fileSeq = $("#fileSeq").val();
    let media = $("form[name=updatePackage] select[name=media] option:selected").val();
    let mediaDetail = $("form[name=updatePackage] select[name=mediaDetail] option:selected").val();
    let mediaDetail2 = $("form[name=updatePackage] select[name=mediaDetail2] option:selected").val();
    let packageName = $("form[name=updatePackage] input[name=packageName]").val();
    let content = tinymce.get('edit-mode-textarea').getContent();
    let price = $("form[name=updatePackage] input[name=price]").val();

    /** 필수값 입력 alert */
    if (media === "") {
        alert("매체를 선택해주세요");
        $("form[name=updatePackage] select[name=media]").focus();
        return false;
    } else if (mediaDetail === "") {
        alert("매체 상세를 선택해주세요");
        $("form[name=updatePackage] select[name=mediaDetail]").focus();
        return false;
    } else if (mediaDetail2 === "") {
        alert("매체 상세 옵션을 선택해주세요");
        $("form[name=updatePackage] select[name=mediaDetail2]").focus();
        return false;
    } else if (packageName === "") {
        alert("상품 이름을 입력해주세요");
        $("form[name=updatePackage] input[name=packageName]").focus();
        return false;
    } else if (price === "") {
        alert("상품가격을 입력해주세요");
        $("form[name=updatePackage] input[name=price]").focus();
        return false;
    }


    let jsonData = {
        'packageSeq': packageSeq,
        'fileSeq' : fileSeq,
        'media': media,
        'mediaDetail': mediaDetail,
        'mediaDetail2': mediaDetail2,
        'packageName': packageName,
        'content': content,
        'price': price,
        'fileChangeFlag': $("input[name=fileChangeFlag]").val(),
    };

    let formData = new FormData();
    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
    $("#fileUpload").each(function(){
        Array.from($(this)[0].files).map(e => formData.append('productFiles', e));
    });

    if( confirm("수정하시겠습니까?") ) {
        $.ajax({
            type: "post",
            url: "/bo/content/update-package",
            data: formData,
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            success: function(data) {
                alert("수정되었습니다.");
                /** update 후 리스트 페이지로 이동 */
                location.href = "/bo/content/package"
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
            url: "/bo/content/package-deleteFile",
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
$("input[name=file]").change(function () {
    $("input[name=fileChangeFlag]").val("Y");
})
let fileMaxCount = 1;   //첨부파일 최대 개수
let fileNo = 0;
let filesArr = new Array();
// 파일첨부 클릭
$("button[data-role='fileUpload']").click(function(){
    $("input[id='fileUpload']").click();
});
// 첨부파일 업로드
$("input[id='fileUpload']").change(function(){
    let remnantCount = fileMaxCount-($("div[class='filebox']").length+$(".registered-file").length);
    let selectedFileLength = $(this)[0].files.length;

    if ( selectedFileLength>remnantCount ) {
        alert("파일 업로드는 최대 " + fileMaxCount + "개 까지 가능합니다.");
        return false;
    }

    for (let i=0; i<selectedFileLength; i++) {
        let file = $(this)[0].files[i];

        if( validateFile(file) ) { //이미지 파일 체크
            let reader = new FileReader();
            reader.onload = function() {
                filesArr.push(file);
            };
            reader.readAsDataURL(file);

            let htmlData = '';
            htmlData += '<div id="new-file'+fileNo+'" class="filebox" style="float: left; margin-right: 5px;">';
            htmlData += '   <span class="name" style="margin-right: 5px;">' + file.name + '</span>';
            htmlData += '   <a href="javascript:void(0);" class="delete-file" onclick="deleteFile('+fileNo+');" style="cursor: pointer;">';
            htmlData += '       <i class="far fa-minus-square"></i>';
            htmlData += '   </a>&nbsp;&nbsp;';
            htmlData += '</div>';
            $("div[id='fileUpload-div']").append(htmlData);
            fileNo++;
        }
        else {
            return false;
        }
    }
    $("#fileUpload").value = "";
});
// 첨부파일 체크
function validateFile(file) {
    let types = ['image/gif', 'image/jpeg', 'image/png', 'image/bmp', 'image/tif'];

    if (!types.includes(file.type)) {
        alert("이미지 파일만 첨부 가능합니다.");
        return false;
    }
//    else if (file.size > (10 * 1024 * 1024)) {
//        alert("업로드 최대용량은 10MB입니다.");
//        return false;
//    }
//    else if (file.name.lastIndexOf('.') == -1) {
//        alert("확장자가 없는 파일입니다.");
//        return false;
//    }
    else {
        return true;
    }
}
$('button[data-role=list]').click( function () {
    window.location.href = '/bo/content/package';
})
$("button[data-role=delete]").click(function () {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/content/delete-package",
            data: {
                packageSeq: $("#packageSeq").val()
            },
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = "/bo/content/package";
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
})