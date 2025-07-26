$('button[data-role=save]').click( function () {
    let paymentId = $("#paymentId").val();
    let status = $("form[name=updatePayment] select[name=status] option:selected").val();
    let jsonData = {
        'paymentId': paymentId,
        'status': status,
    };
    if( confirm("수정하시겠습니까?") ) {
        $.ajax({
            type: "post",
            url: "/bo/payment/update-payment",
            data: JSON.stringify(jsonData),
            contentType: "application/json",
            processData: false,
            success: function(data) {
                alert("수정되었습니다.");
                /** update 후 리스트 페이지로 이동 */
                location.href = "/bo/payment/payment-history"
            },
            error: function() {
                alert("수정에 실패했습니다.");
            }
        });
    }
})
function openBillPopup() {
    // 팝업 창 열기
    const popup = window.open("", "billPopup", "width=800,height=600");

    // 팝업 창의 내용 설정
    popup.document.write(`
        <html>
        <head>
            <title>계산서</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 20px; }
                .print-button { margin-top: 20px; }
            </style>
        </head>
        <body>
            <h2 class="text-center">계산서</h2>
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
function openContractPopup() {
    // 팝업 창 열기
    const popup = window.open("", "contractPopup", "width=800,height=600");

    // 팝업 창의 내용 설정
    popup.document.write(`
        <html>
        <head>
            <title>계약서</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 20px; }
                .print-button { margin-top: 20px; }
            </style>
        </head>
        <body>
            <h2 class="text-center">계약서</h2>
            <iframe src="/static/images/logo.svg" width="100%" height="400px" id="contractFrame"></iframe>
            <button onclick="printContract()" class="print-button">인쇄</button>
            <script>
                function printContract() {
                    const iframe = document.getElementById("contractFrame");
                    iframe.contentWindow.focus();
                    iframe.contentWindow.print();
                }
            </script>
        </body>
        </html>
    `);
    popup.document.close();
}
function openEstimatePopup() {
    // 팝업 창 열기
    const popup = window.open("", "estimatePopup", "width=800,height=600");

    // 팝업 창의 내용 설정
    popup.document.write(`
        <html>
        <head>
            <title>견적서</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 20px; }
                .print-button { margin-top: 20px; }
            </style>
        </head>
        <body>
            <h2 class="text-center">견적서</h2>
            <iframe src="/static/images/logo.svg" width="100%" height="400px" id="estimateFrame"></iframe>
            <button onclick="printEstimate()" class="print-button">인쇄</button>
            <script>
                function printEstimate() {
                    const iframe = document.getElementById("estimateFrame");
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
    window.location.href = "/bo/payment/payment-history";
});