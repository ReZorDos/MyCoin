document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('.category-icon');
    images.forEach(img => {
        img.addEventListener('error', function() {
            this.style.display = 'none';
            const fallback = this.nextElementSibling;
            if (fallback && fallback.classList.contains('icon-fallback')) {
                fallback.style.display = 'flex';
            }
        });

        img.addEventListener('load', function() {
            const fallback = this.nextElementSibling;
            if (fallback && fallback.classList.contains('icon-fallback')) {
                fallback.style.display = 'none';
            }
        });
    });

});

function deleteCategory(uuid, type) {
    if (confirm('Вы уверены, что хотите удалить эту категорию?')) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = type === 'expense'
            ? contextPath + '/expense-category/delete'
            : contextPath + '/income-category/delete';

        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'uuid';
        input.value = uuid;

        form.appendChild(input);
        document.body.appendChild(form);
        form.submit();
    }
}

function editCategory(uuid, type) {
    window.location.href = type === 'expense'
        ? contextPath + '/expense-category/update?uuid=' + uuid
        : contextPath + '/income-category/update?uuid=' + uuid;
}

function createTransaction(categoryId, type) {
    if (type === 'expense') {
        window.location.href = contextPath + '/create-transaction/expense?categoryId=' + categoryId;
    } else {
        window.location.href = contextPath + '/create-transaction/income?categoryId=' + categoryId;
    }
}