$(document).ready(function () {
    // 초기 로드 시 선택된 매체에 따라 상세 옵션 보이기
    toggleMediaDetails();
    // 매체 선택 변경 시마다 실행
    $('#media').change(function () {
        toggleMediaDetails();
    });
    // 선택된 매체에 따라 관련 상세 옵션만 표시 및 name 속성 설정
    function toggleMediaDetails() {
        // 모든 상세 옵션 행 숨기기 및 name 속성 제거
        $('.mediaDetailRow').hide();
        $('.mediaDetailRow select').removeAttr('name');

        // 선택된 매체 값에 따라 해당 상세 옵션 행만 표시하고 name 속성 설정
        const selectedMedia = $('#media').val();
        if (selectedMedia === 'NAVER') {
            $('#naverDetails').show();
            $('#naverDetails select').attr('name', 'mediaDetail');
        } else if (selectedMedia === 'INSTAR') {
            $('#instarDetails').show();
            $('#instarDetails select').attr('name', 'mediaDetail');
        } else if (selectedMedia === 'YOUTUBE') {
            $('#youtubeDetails').show();
            $('#youtubeDetails select').attr('name', 'mediaDetail');
        } else if (selectedMedia === 'KAKAO') {
            $('#kakaoDetails').show();
            $('#kakaoDetails select').attr('name', 'mediaDetail');
        }
    }
});
const fileInput = document.getElementById('fileId');
const previewContainer = document.getElementById('filePreview1');

fileInput.addEventListener('change', function () {
    const file = this.files[0];
    if (!file || !file.type.startsWith('image/')) return;

    const reader = new FileReader();
    reader.onload = function (e) {
        if (previewContainer.tagName.toLowerCase() === 'img') {
            // 기존 이미지가 <img id="filePreview1">인 경우, 해당 img의 src만 변경
            previewContainer.src = e.target.result;
        } else {
            // 최초 이미지 없는 경우 (img 태그 없고 div만 존재할 경우)
            const img = document.createElement('img');
            img.src = e.target.result;
            img.id = 'filePreview1';
            img.style.maxWidth = '100%';
            img.style.maxHeight = '210px';
            img.style.cursor = 'pointer';
            img.alt = '대표 이미지';
            previewContainer.appendChild(img);
        }
    };
    reader.readAsDataURL(file);
});
// 삭제 버튼 클릭 시 미리보기 제거
function removePreview() {
    fileInput.value = '';
    const previewImage = document.getElementById('filePreview1');
    if (previewImage) {
        previewImage.remove();
    }
}
// 저장 버튼 클릭시
$("button[data-role=save]").click(function () {
    const formData = new FormData();

    const productId = $("#productId").val();
    const file = $('#fileId')[0].files[0];
    const originFileId = $("#originFileId").val();

    formData.append("productId", productId);
    formData.append("media", $("#media").val());
    formData.append("mediaDetail", $("select[name=mediaDetail]:enabled").val());
    formData.append("title", $("input[name=title]").val());
    formData.append("content", $("input[name=content]").val());
    formData.append("mainContent", $("input[name=mainContent]").val());
    formData.append("amountContent", $("input[name=amountContent]").val());
    formData.append("etcContent", $("input[name=etcContent]").val());
    formData.append("amount", $("input[name=amount]").val());
    formData.append("workDt", $("input[name=workDt]").val());

    // 파일 처리
    if (file) {
        formData.append("thumbnailImage", file); // 새 파일
    } else {
        formData.append("originFileId", originFileId); // 기존 파일 유지
    }

    // 부가 설명
    const productSubContents = [];
    $("input[name='productSubContent']").each(function () {
        const value = $(this).val().trim();
        if (value) productSubContents.push(value);
    });
    formData.append("productSubContents", JSON.stringify(productSubContents));

    if (confirm("수정하시겠습니까?")) {
        $.ajax({
            url: "/bo/content/update-ad-product",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function () {
                alert("수정되었습니다.");
                location.href = "/bo/content/ad-product";
            },
            error: function () {
                alert("수정 중 오류가 발생했습니다.");
            }
        });
    }
});
$('button[data-role=list]').click(function () {
    window.location.href = '/bo/content/ad-product';
})
$("button[data-role=delete]").click(function () {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/content/delete-ad-product",
            data: {
                productId: $("#productId").val()
            },
            success: function () {
                alert("삭제되었습니다.");
                window.location.href = "/bo/content/ad-product";
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
})
$(document).ready(function() {
    // + 버튼 클릭 시 새로운 부가 설명 추가
    $(document).on("click", "button[data-role=plus]", function() {
        let newRow = `
            <div class="input-group mb-2">
                <input type="text" class="form-control" name="productSubContent" placeholder=""/>
                <div class="input-group-append" style="margin-left: 3px;">
                    <button type="button" class="btn btn-outline-danger" data-role="plus">
                        <i class="icon-plus"></i>
                    </button>
                    <button type="button" class="btn btn-outline-danger" data-role="minus">
                        <i class="icon-minus"></i>
                    </button>
                </div>
            </div>`;
        $("#productSubContent").append(newRow);
    });

    // - 버튼 클릭 시 해당 부가 설명 삭제
    $(document).on("click", "button[data-role=minus]", function() {
        $(this).closest(".input-group").remove();
    });
});
