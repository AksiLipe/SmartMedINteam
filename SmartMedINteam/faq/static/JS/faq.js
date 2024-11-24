// faq.js
document.addEventListener("DOMContentLoaded", function () {
    const answerButtons = document.querySelectorAll('.answer-btn');
    const modal = document.getElementById('answerModal');
    const closeModal = document.querySelector('.close');
    const answerForm = document.getElementById('answerForm');
    let currentQuestion = null;

    answerButtons.forEach(button => {
        button.addEventListener('click', function () {
            currentQuestion = this.parentElement;
            modal.style.display = 'block';
        });
    });

    closeModal.onclick = function () {
        modal.style.display = 'none';
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    }

    answerForm.onsubmit = function (event) {
        event.preventDefault();
        const answerText = document.getElementById('answerText').value;
        const answerDiv = currentQuestion.querySelector('.answer');
        answerDiv.textContent = `Ответ врача: ${answerText}`;
        answerDiv.style.display = 'block';
        modal.style.display = 'none';
        answerForm.reset();
    }
});