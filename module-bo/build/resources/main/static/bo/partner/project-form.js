$('button[data-role=cancel]').click(function () {
    window.location.href = '/bo/partner/project-management';
})

function unformatNumber(value) {
    return value ? value.replace(/,/g, "") : "";
}

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
// 프로젝트 등록 + 파일 업로드 처리
$('button[data-role=insert]').click(function () {
    const title = $('#title').val().trim();
    const summary = $('#summary').val().trim();
    const budgetRaw = $('#budget').val();
    const budget = unformatNumber(budgetRaw);
    const adDate = $('#adDate').val();
    const adDateEtc = $('#adDateEtc').val().trim();
    const closeDt = $('#closeDt').val();
    const viewCount = $('#viewCount').val();
    const userId = $('#userId').val();
    const estimateId = $('#estimateId').val();
    const userIntro = $('#userIntro').val().trim();
    const content = $('#content').val().trim();
    const serviceRequest = $('#serviceRequest').val().trim();
    const taskRequest = $('#taskRequest').val().trim();
    // 유효성 체크
    if (!title) return alert('제목을 입력해주세요.');
    if (!summary) return alert('개요를 입력해주세요.');
    if (!budget || isNaN(budget)) return alert('예산을 숫자로 입력해주세요.');
    if (!adDate) return alert('광고 기간을 선택해주세요.');
    if (adDate === 'ETC' && !adDateEtc) return alert('광고 기간 기타 항목을 입력해주세요.');
    if (!closeDt) return alert('마감일을 선택해주세요.');
    if (!viewCount || isNaN(viewCount)) return alert('조회수를 숫자로 입력해주세요.');
    if (!userId) return alert('클라이언트를 선택해주세요.');
    if (!content) return alert('설명을 입력해주세요.');
    if (!serviceRequest) return alert('요청 서비스를 입력해주세요.');
    if (!taskRequest) return alert('업무 요청 사항을 입력해주세요.');

    const formData = new FormData();
    const file = $('#fileId')[0].files[0];
    if (file) {
        formData.append('file', file);
    }

    formData.append('title', title);
    formData.append('summary', summary);
    formData.append('budget', budget);
    formData.append('adDate', adDate);
    formData.append('adDateEtc', adDateEtc);
    formData.append('closeDt', closeDt);
    formData.append('viewCount', viewCount);
    formData.append('userId', userId);
    formData.append('estimateId', estimateId);
    formData.append('userIntro', userIntro);
    formData.append('content', content);
    formData.append('serviceRequest', serviceRequest);
    formData.append('taskRequest', taskRequest);

    $.ajax({
        url: '/bo/partner/insert-project',
        type: 'POST',
        processData: false,
        contentType: false,
        data: formData,
        success: function (res) {
            alert('프로젝트가 등록되었습니다.');
            window.location.href = '/bo/partner/project-management';
        },
        error: function () {
            alert('프로젝트 등록 중 오류가 발생했습니다.');
        }
    });
});
document.addEventListener("DOMContentLoaded", function () {
    const select = document.getElementById("adDate");
    const etcInputWrap = document.getElementById("adDateEtcWrap");

    function toggleEtcInput() {
        if (select.value === "ETC") {
            etcInputWrap.style.display = "block";
        } else {
            etcInputWrap.style.display = "none";
        }
    }

    // 초기 로딩 시 적용
    toggleEtcInput();
    // 값이 변경될 때 실행
    select.addEventListener("change", toggleEtcInput);
});
$('#userId').on('change', function () {
    const userId = $(this).val();

    // estimateId 셀렉트 초기화
    const $estimateSelect = $('#estimateId');
    $estimateSelect.empty().append('<option value="">선택하세요</option>');

    if (!userId) return;

    // AJAX 요청
    $.ajax({
        url: '/api/estimate/list',
        type: 'GET',
        data: {userId: userId},
        dataType: 'json',
        success: function (response) {
            const list = response.data || [];

            if (list.length === 0) {
                $estimateSelect.append('<option value="">완료된 견적이 없습니다</option>');
                return;
            }

            list.forEach(est => {
                const optionText = `${est.name} (${est.platform} > ${est.platformDetail} > ${est.adType})`;
                $estimateSelect.append(`<option value="${est.estimateId}">${optionText}</option>`);
            });
        },
        error: function () {
            alert('견적 목록 조회에 실패했습니다.');
        }
    });
});