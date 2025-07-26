document.addEventListener("DOMContentLoaded", function () {
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
    });

    $('button[data-role=btnFile]').click(function () {
        $('#fileInput').click();
    });

    $('#fileInput').change(function () {
        let fileName = $(this).val().split('\\').pop();
        alert(fileName);
        if (fileName) {
            $('#fileName').val(fileName);
        }
    })


    $('button[data-role=btnList]').click(function () {
        location.href = `/bo/homepage/notice`;
    })

    $('button[data-role=btnSave]').click(function () {
        const title = $('#title').val().trim();

        if (title.length === 0) {
            alert("제목을 입력해주세요");
            return;
        }

        if (!confirm("등록하시겠습니까?")) {
            return;
        }

        // 폼 데이터 수집
        const saveForm = {
            lang: $('select[name=searchType]').val(),
            title: title,
            content: tinymce.get('content').getContent(),
            popupType: $('input[name=popup]:checked').val() || '',
            optionYn: $('#flexCheckDefault').is(':checked') ? 'Y' : 'N',
            width: $('input[name=width]').val().trim(),
            height: $('input[name=height]').val().trim(),
            left: $('input[name=left]').val().trim(),
            top: $('input[name=top]').val().trim(),
            endDt: $('input[name=endDt]').val().trim(),
            endNoLimitYn: $('input[name=endNoLimitYn]').is(':checked') ? 'Y' : 'N',
            popupYn: $('select[name=popupYn]').val()
        };

        // 전송
        $.ajax({
            type: "POST",
            url: `/bo/homepage/notice/insert`,
            contentType: "application/json",
            data: JSON.stringify(saveForm),
            success: function () {
                alert("등록되었습니다.");
                location.href = `/bo/homepage/notice`;
            },
            error: function () {
                alert("등록 중 오류가 발생했습니다.");
            }
        });
    });

    $('input[data-picker="date"]').datepicker({
        dateFormat: 'yy-mm-dd', // 2025-07-21 형식
        showAnim: 'slideDown'
    });

    // 종료일 버튼 클릭 시 연결된 input을 찾아 datepicker 표시
    $('.btn.btn-outline-dark').on('click', function () {
        const $input = $(this).siblings('input[data-picker="date"]');
        if ($input.length) {
            $input.datepicker('show');
        }
    });

    // 제한없음 체크 시 비활성화
    $('input[name="noLimit"]').on('change', function () {
        const isChecked = $(this).is(':checked');
        const $input = $(this).closest('td').find('input[data-picker="date"]');
        const $button = $(this).closest('td').find('button');

        $input.prop('disabled', isChecked);
        $button.prop('disabled', isChecked);
    });
});