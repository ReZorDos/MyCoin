document.addEventListener('DOMContentLoaded', function() {
    const preselectedCategoryId = document.querySelector('input[type="hidden"][name$="Id"]')?.value || '';

    if (!preselectedCategoryId) {
        console.warn('No category selected for transaction');
    }
});