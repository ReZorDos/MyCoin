let activeCard = null;

document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('.category-icon');
    images.forEach(img => {
        img.addEventListener('error', function() {
            this.style.display = 'none';
            const noIcon = this.nextElementSibling;
            if (noIcon && noIcon.classList.contains('no-icon')) {
                noIcon.style.display = 'flex';
            }
        });
    });

    document.querySelectorAll('.category-card').forEach(card => {
        card.addEventListener('click', function(e) {
            if (e.target.closest('.actions-menu')) {
                return;
            }

            if (activeCard && activeCard !== this) {
                activeCard.classList.remove('active');
            }

            this.classList.toggle('active');

            if (this.classList.contains('active')) {
                activeCard = this;
            } else {
                activeCard = null;
            }
        });
    });

    document.addEventListener('click', function(e) {
        if (activeCard && !activeCard.contains(e.target)) {
            activeCard.classList.remove('active');
            activeCard = null;
        }
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