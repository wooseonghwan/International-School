document.addEventListener("DOMContentLoaded", function() {
    toggleMediaDetails();
    // 매체 선택 변경 시마다 상세 옵션 표시 변경
    $('#media').change(function () {
        toggleMediaDetails();
    });
    // 선택된 매체에 따라 관련 상세 옵션만 표시
    function toggleMediaDetails() {
        // 모든 상세 옵션 행 숨기기 + select 비활성화
        $('.mediaDetailRow').hide().find('select[name="mediaDetail"]').prop('disabled', true);

        // 선택된 매체 값
        const selectedMedia = $('#media').val();

        // 해당 매체에 맞는 행만 표시하고 select 활성화
        if (selectedMedia === 'NAVER') {
            $('#naverDetails').show().find('select[name="mediaDetail"]').prop('disabled', false);
        } else if (selectedMedia === 'INSTAR') {
            $('#instarDetails').show().find('select[name="mediaDetail"]').prop('disabled', false);
        } else if (selectedMedia === 'YOUTUBE') {
            $('#youtubeDetails').show().find('select[name="mediaDetail"]').prop('disabled', false);
        } else if (selectedMedia === 'KAKAO') {
            $('#kakaoDetails').show().find('select[name="mediaDetail"]').prop('disabled', false);
        }
    }
});
// + , - 버튼 클릭시
$(document).ready(function() {
    // + 버튼 클릭 시
    $(document).on("click", "button[data-role=plus]", function() {
        let newRow = `
            <div class="input-group" style="margin-top: 3px;">
                <input type="text" class="form-control" name="productSubContent" placeholder=""/>
                <div class="input-group-append" style="margin-left: 3px;">
                    <button type="button" class="btn btn-outline-dark" data-role="plus">
                        <i class="icon-plus"></i>
                    </button>
                    <button type="button" class="btn btn-outline-danger" data-role="minus">
                        <i class="icon-minus"></i>
                </button>
                </div>
            </div>`;
        $("#productSubContent").append(newRow);
    });

    // - 버튼 클릭 시
    $(document).on("click", "button[data-role=minus]", function() {
        $(this).closest(".input-group").remove();
    });
});
const fileInput = document.getElementById('fileId');
const previewArea = document.getElementById('filePreview1');

fileInput.addEventListener('change', function () {
    const file = this.files[0];
    previewArea.innerHTML = ''; // 초기화

    if (!file || !file.type.startsWith('image/')) return;

    const reader = new FileReader();
    reader.onload = function (e) {
        const img = document.createElement('img');
        img.src = e.target.result;
        img.style.maxWidth = '100%';
        img.style.maxHeight = '210px';
        img.alt = '선택된 이미지 미리보기';
        previewArea.appendChild(img);
    };
    reader.readAsDataURL(file);
});

function removePreview() {
    fileInput.value = '';       // 파일 input 초기화
    previewArea.innerHTML = ''; // 미리보기 제거
}
// 저장 버튼 클릭시
$("button[data-role=save]").click(function () {
    const form = $("form[name=insertProduct]")[0];
    const formData = new FormData(form);

    const media = formData.get("media");
    const mediaDetail = formData.get("mediaDetail");
    const title = formData.get("title");
    const amount = formData.get("amount");
    const workDt = formData.get("workDt");
    const productSubContents = [];
    $("input[name='adProductSubContent'], input[name='productSubContent']").each(function () {
        const val = $(this).val().trim();
        if (val) productSubContents.push(val);
    });
    formData.append("productSubContents", JSON.stringify(productSubContents));

    // 유효성 검사
    if (!media) return alert("매체를 선택해주세요.");
    if (!mediaDetail) return alert("매체 상세를 선택해주세요.");
    if (!title) return alert("상품이름을 입력해주세요.");
    if (!amount) return alert("상품 가격을 입력해주세요.");
    if (!workDt) return alert("작업일을 입력해주세요.");

    const file = $('#fileId')[0].files[0];
    if (file) {
        formData.append('thumbnailImage', file);
    }

    if (confirm("등록하시겠습니까?")) {
        $.ajax({
            url: "/bo/content/insert-ad-product",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function () {
                alert("등록되었습니다.");
                location.href = "/bo/content/ad-product";
            },
            error: function () {
                alert("등록 중 오류가 발생했습니다.");
            }
        });
    }
});
$('#thumbnailImage').on('change', function () {
    const file = this.files[0];
    if (!file) return;

    // 파일명 표시
    $('#thumbnailFileName').text(file.name);

    // 이미지 미리보기
    const reader = new FileReader();
    reader.onload = function (e) {
        $('#thumbnailPreview').html(`<img src="${e.target.result}" alt="미리보기" style="max-width: 200px; max-height: 200px;">`);
    };
    reader.readAsDataURL(file);
});