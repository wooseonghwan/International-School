function openBillPopup() {
    // 팝업 창 열기
    const popup = window.open("", "billPopup", "width=800,height=600");

    // 팝업 창의 내용 설정
    popup.document.write(`
        <html>
        <head>
            <title>보고서</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 20px; }
                .print-button { margin-top: 20px; }
            </style>
        </head>
        <body>
            <h2 class="text-center">보고서</h2>
            <iframe src="/static/images/logo.svg" width="100%" height="400px" id="billFrame"></iframe>
            <button onclick="printBill()" class="print-button">인쇄</button>
            <script>
                function printBill() {
                    const iframe = document.getElementById("billFrame");
                    iframe.contentWindow.focus();
                    iframe.contentWindow.print();
                }
            </script>
        </body>
        </html>
    `);
    popup.document.close();
}
$("button[data-role=list]").click(function(){
    window.location.href = "/bo/advertisement/ad-history";
});
$('button[data-role=save]').click( function () {
    const productRequestId = $("form[name=updateAdProductHist] input[name=productRequestId]").val();
    const productId = $("form[name=updateAdProductHist] input[name=productId]").val();
    const userId = $("form[name=updateAdProductHist] input[name=userId]").val();
    const amount = $("form[name=updateAdProductHist] input[name=amount]").val();
    const status = $("form[name=updateAdProductHist] select[name=status] option:selected").val();

    let jsonData = {
        'productRequestId': productRequestId,
        'status': status,
        'productId': productId,
        'userId': userId,
        'amount': amount,
    };
    if( confirm("수정하시겠습니까?") ) {
        $.ajax({
            type: "post",
            url: "/bo/advertisement/update-ad-history",
            data: JSON.stringify(jsonData),
            contentType: "application/json",
            processData: false,
            success: function(data) {
                alert("수정되었습니다.");
                location.href = '/bo/advertisement/ad-history';
            },
            error: function() {
                alert("수정에 실패했습니다.");
            }
        });
    }
})