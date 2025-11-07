document.addEventListener('DOMContentLoaded', function() {
    const preselectedCategoryId = document.querySelector('input[type="hidden"][name$="Id"]')?.value || '';

    if (!preselectedCategoryId) {
        console.warn('Для транзакции не выбрана категория');
    }
});