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