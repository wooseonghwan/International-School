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
        automatic_uploads: true,

        init_instance_callback: function (editor) {
            const existingContent = $('input[name=content]').val();
            editor.setContent(existingContent);
        }
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
        location.href = `/bo/homepage/faq`;
    })

    $('button[data-role=btnSave]').click(function () {
        let title = $('#title').val();
        let category = $('#category').val();

        if (title.length === 0) {
            alert("제목을 입력해주세요");
            return;
        } else if (category === '') {
            alert("카테고리를 선택해주세요");
            return;
        } else {
            if (!confirm("수정하시겠습니까?")) {
                return;
            }
        }

        let saveForm = {
            faqId: $('#faqId').val(),
            category: category,
            title: title,
            content: tinymce.get('content').getContent(),
            useYn: $('input[name=useYn]:checked').val()
        }

        $.ajax({
            type: "POST",
            url: `/bo/homepage/faq/update`,
            contentType: "application/json",
            data: JSON.stringify(saveForm),
            success: function () {
                location.href = `/bo/homepage/faq`;
            },
            error: function () {
                alert("ajax failed on updating faq");
            }
        });
    })
})

