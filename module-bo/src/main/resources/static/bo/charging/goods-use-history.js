document.addEventListener('DOMContentLoaded', () => {
    const rows = document.querySelectorAll('.balance-cell');
    let total = /*[[${totalChargeAmount}]]*/ 0;
    let balance = total;

    rows.forEach(cell => {
        const gbn = cell.dataset.gbn;
        const amnt = parseInt(cell.dataset.amnt || 0, 10);

        if (gbn !== 'CHARGE') {
            balance -= amnt;
        }

        cell.textContent = balance.toLocaleString();
    });
});
$('.search-form').keyup(function (event) {
    if (event.keyCode === 13) {
        $('form[name=searchForm]').submit();
    }
});
$('button[data-role=btn-search-form]').click(function () {
    $('form[name=searchForm]').submit();
});
$('a[data-role=btnGoPage]').click(function () {
    let page = $(this).attr('data-page');
    $('form[name=searchForm] input[name=page]').val(page);
    $('form[name=searchForm]').submit();
});