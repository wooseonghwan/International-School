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
        automatic_uploads: true
        /*file_picker_types: 'image',

        // 서버로 이미지 업로드하는 핸들러
        images_upload_handler: function (blobInfo, success, failure) {
            // 서버 업로드 API URL - 실제 서버의 업로드 URL로 변경 필요
            let uploadUrl = '/bo/system/file/tinymceUpload';
            let xhr = new XMLHttpRequest();
            let formData = new FormData();

            xhr.open('POST', uploadUrl);
            xhr.onload = function() {
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
            xhr.onerror = function() {
                failure('Image upload failed due to a network error.');
            };

            formData.append('file', blobInfo.blob(), blobInfo.filename());
            xhr.send(formData);
        }*/
    });
    // 페이지 로드 시 첫 번째, 두 번째, 세 번째 선택 옵션 초기화
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

    const fileInput = document.getElementById("banner-upload");
    const fileChosen = document.getElementById("file-chosen");

    // 파일 선택 시 선택된 파일 이름을 표시
    fileInput.addEventListener("change", function() {
        if (this.files.length > 0) {
            // 여러 파일 이름을 ,로 구분하여 표시
            const fileNames = Array.from(this.files).map(file => file.name).join(", ");
            fileChosen.textContent = fileNames;
        } else {
            fileChosen.textContent = "선택된 파일 없음";
        }
    });
});

$("button[data-role=save]").click(function(){

    let media = $("form[name=insertPackage] select[name=media] option:selected").val();
    let mediaDetail = $("form[name=insertPackage] select[name=mediaDetail] option:selected").val();
    let mediaDetail2 = $("form[name=insertPackage] select[name=mediaDetail2] option:selected").val();
    let packageName = $("form[name=insertPackage] input[name=packageName]").val();
    let content = tinymce.get('edit-mode-textarea').getContent();
    let price = $("form[name=insertPackage] input[name=price]").val();

    // 필수값 입력 alert
    if (media === "") {
        alert("메체를 선택해주세요");
        $("form[name=insertPackage] select[name=media]").focus();
        return false;
    } else if (mediaDetail === "") {
        alert("메체 상세를 선택해주세요");
        $("form[name=insertPackage] select[name=mediaDetail]").focus();
        return false;
    } else if (mediaDetail2 === "") {
        alert("메체 상세 옵션을 선택해주세요");
        $("form[name=insertPackage] select[name=mediaDetail2]").focus();
        return false;
    } else if (price === "") {
        alert("상품 가격을 입력해주세요");
        $("form[name=insertPackage] input[name=price]").focus();
        return false;
    } else if (packageName === "") {
        alert("패키지명을 입력해주세요");
        $("form[name=insertProduct] input[name=packageName]").focus();
        return false;
    }


    let jsonData = {
        'media': media,
        'mediaDetail': mediaDetail,
        'mediaDetail2': mediaDetail2,
        'packageName': packageName,
        'content': content,
        'price': price
    };

    let formData = new FormData();
    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], { type: 'application/json' }));
    Array.from($("#banner-upload")[0].files).forEach(file => {
        formData.append('productFiles', file);
    });

    if ( confirm("등록하시겠습니까?") ) {
        $.ajax({
            url: "/bo/content/insert-package",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            success: function(data) {
                alert("등록되었습니다.");
                location.href = "/bo/content/package";
            },
            error: function() {
                alert("등록 중 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
});