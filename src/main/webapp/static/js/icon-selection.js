document.addEventListener('DOMContentLoaded', function() {
    const radioButtons = document.querySelectorAll('.icon-radio');

    radioButtons.forEach(radio => {
        radio.addEventListener('change', function() {
            document.querySelectorAll('.icon-option').forEach(opt => {
                opt.classList.remove('selected');
            });

            if (this.checked) {
                this.parentElement.classList.add('selected');
            }
        });
    });

    document.querySelectorAll('.icon-option').forEach(option => {
        option.addEventListener('click', function(e) {
            const radio = this.querySelector('.icon-radio');
            if (radio) {
                radio.checked = true;
                radio.dispatchEvent(new Event('change'));
            }
        });
    });
});