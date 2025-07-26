document.addEventListener("DOMContentLoaded", function () {
    const adDateSelect = document.getElementById("adDate");
    const adDateEtcWrap = document.getElementById("adDateEtcWrap");

    if (!adDateSelect || !adDateEtcWrap) return; // 안정성 체크

    function toggleEtcInput() {
        if (adDateSelect.value === "ETC") {
            adDateEtcWrap.style.display = "block";
        } else {
            adDateEtcWrap.style.display = "none";
        }
    }

    toggleEtcInput(); // 초기 로딩 시 처리
    adDateSelect.addEventListener("change", toggleEtcInput);
});
$('button[data-role=cancel]').click( function () {
    window.location.href = '/bo/partner/project-management';
});
function unformatNumber(value) {
    return value ? value.replace(/,/g, "") : "";
}
$('button[data-role=update]').click(function () {
    const formData = new FormData();

    const file = $('#fileInput1')[0].files[0];
    if (file) {
        formData.append('file', file);
    }

    formData.append('projectId', $('#projectId').val());
    formData.append('status', $('#status').val());
    formData.append('title', $('#title').val());
    formData.append('summary', $('#summary').val());
    formData.append('budget', unformatNumber($('#budget').val()));
    formData.append('adDate', $('#adDate').val());
    formData.append('adDateEtc', $('#adDateEtc').val());
    formData.append('closeDt', $('#closeDt').val());
    formData.append('viewCount', $('#viewCount').val());
    formData.append('userIntro', $('#userIntro').val());
    formData.append('content', $('#content').val());
    formData.append('serviceRequest', $('#serviceRequest').val());
    formData.append('taskRequest', $('#taskRequest').val());
    formData.append('fileId', $('#fileId').val()); // 기존 이미지가 있다면 유지

    $.ajax({
        url: '/bo/partner/update-project',
        type: 'POST',
        processData: false,
        contentType: false,
        data: formData,
        success: function (res) {
            alert('프로젝트 상세가 수정되었습니다.');
            window.location.href = '/bo/partner/project-management';
        },
        error: function () {
            alert('프로젝트 상세 수정중 오류가 발생했습니다.');
        }
    });
});
function formatNumber(value) {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 콤마 제거
function unformatNumber(value) {
    return value.replace(/,/g, "");
}

document.addEventListener("DOMContentLoaded", function () {
    const budgetInput = document.getElementById("budget");

    if (!budgetInput) return;

    // 입력창에서 포커스 해제 시: 콤마 붙이기
    budgetInput.addEventListener("blur", function () {
        const raw = unformatNumber(budgetInput.value);
        if (!isNaN(raw) && raw !== "") {
            budgetInput.value = formatNumber(raw);
        }
    });

    // 폼 전송 전: 콤마 제거
    const form = budgetInput.closest("form");
    if (form) {
        form.addEventListener("submit", function () {
            budgetInput.value = unformatNumber(budgetInput.value);
        });
    }
});
$('button[data-role=delete]').click(function () {
    const projectId = $('#projectId').val();
    $.ajax({
        type: 'POST',
        url: '/bo/partner/delete-project',
        contentType: 'application/json',
        data: JSON.stringify({
            projectId: projectId
        }),
        success: function (res) {
            alert('프로젝트가 삭제되었습니다.');
            window.location.href = '/bo/partner/project-management';
        },
        error: function () {
            alert('프로젝트 삭제중 오류가 발생했습니다.');
        }
    });
})
const fileInput = document.getElementById('fileInput1');
const previewImage = document.getElementById('previewImage');
const previewContainer = document.getElementById('filePreview1');

// 이미지 클릭 시 input 열기
function triggerFileInput() {
    fileInput.click();
}

// 새 파일 선택 시 미리보기 교체
fileInput.addEventListener('change', function () {
    const file = this.files[0];
    if (!file || !file.type.startsWith('image/')) return;

    const reader = new FileReader();
    reader.onload = function (e) {
        if (previewImage) {
            previewImage.src = e.target.result;
        } else {
            // 처음 등록일 경우 <img> 동적 추가
            const img = document.createElement('img');
            img.src = e.target.result;
            img.id = 'previewImage';
            img.style.maxWidth = '100%';
            img.style.maxHeight = '210px';
            img.style.cursor = 'pointer';
            img.alt = '대표 이미지';
            img.onclick = triggerFileInput;

            previewContainer.appendChild(img);
        }
    };
    reader.readAsDataURL(file);
});

// 삭제 버튼 클릭 시 미리보기 제거
function removePreview() {
    fileInput.value = '';
    if (previewImage) {
        previewImage.remove();
    }
}