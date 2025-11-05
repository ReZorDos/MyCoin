function updateDistribution() {
    const inputs = document.querySelectorAll('.distribution-input');
    let total = 0;
    let distributionFields = '';

    inputs.forEach(input => {
        const amount = parseFloat(input.value) || 0;
        if (amount > 0) {
            total += amount;
            distributionFields +=
                `<input type="hidden" name="saveGoalIds" value="${input.dataset.goalId}">` +
                `<input type="hidden" name="amounts" value="${amount}">`;
        }
    });

    document.getElementById('distributionFields').innerHTML = distributionFields;
    return total;
}

function setupTransactionForm() {
    const transactionForm = document.getElementById('transactionForm');
    if (transactionForm) {
        transactionForm.addEventListener('submit', function(event) {
            const totalDistribution = updateDistribution();
            const transactionSum = parseFloat(document.getElementById('transactionSum').value) || 0;

            if (totalDistribution > transactionSum) {
                alert('Сумма распределения по целям превышает общую сумму транзакции!');
                event.preventDefault();
            }
        });
    }

    const distributionInputs = document.querySelectorAll('.distribution-input');
    distributionInputs.forEach(input => {
        input.addEventListener('change', updateDistribution);
        input.addEventListener('input', updateDistribution);
    });
}

document.addEventListener('DOMContentLoaded', function() {
    setupTransactionForm();
});