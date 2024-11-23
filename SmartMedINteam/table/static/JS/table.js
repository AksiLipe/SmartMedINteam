// table.js
document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("registerModal");
    const btn = document.getElementById("registerBtn");
    const span = document.getElementsByClassName("close")[0];
    let currentEditRow = null;

    btn.onclick = function () {
        modal.style.display = "block";
        document.getElementById("registerForm").reset();
        currentEditRow = null;
    }

    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    document.getElementById("registerForm").onsubmit = function (event) {
        event.preventDefault();

        const name = document.getElementById("patientName").value;
        const age = document.getElementById("patientAge").value;
        const gender = document.getElementById("patientGender").value;
        const diagnosis = document.getElementById("patientDiagnosis").value;
        const phone = document.getElementById("patientPhone").value;
        const login = document.getElementById("patientLogin").value;
        const password = document.getElementById("patientPassword").value;
        const photoInput = document.getElementById("patientPhoto");
        const photoURL = photoInput.files.length > 0 ? URL.createObjectURL(photoInput.files[0]) : '../static/images/default-avatar.png';

        if (currentEditRow) {
            currentEditRow.cells[1].innerText = name;
            currentEditRow.cells[2].innerText = age;
            currentEditRow.cells[3].innerText = gender;
            currentEditRow.cells[4].innerText = diagnosis;
            currentEditRow.dataset.phone = phone;
            currentEditRow.dataset.login = login;
            currentEditRow.dataset.password = password;
            currentEditRow.cells[0].querySelector('img').src = photoURL;
        } else {
            const tableBody = document.getElementById("patientTableBody");
            const newRow = tableBody.insertRow();

            newRow.innerHTML = `
                <td><img src="${photoURL}" alt="Фото пациента" class="patient-photo"></td>
                <td>${tableBody.rows.length}</td>
                <td>${name}</td>
                <td>${age}</td>
                <td>${gender}</td>
                <td>${diagnosis}</td>
                <td><button class="editBtn">Редактировать</button></td>`;

            newRow.dataset.phone = phone;
            newRow.dataset.login = login;
            newRow.dataset.password = password;
        }

        modal.style.display = "none";
        document.getElementById("registerForm").reset();
        currentEditRow = null;
    }

    document.getElementById("patientTableBody").addEventListener("click", function (event) {
        if (event.target.classList.contains("editBtn")) {
            currentEditRow = event.target.parentElement.parentElement;
            document.getElementById("patientName").value = currentEditRow.cells[2].innerText;
            document.getElementById("patientAge").value = currentEditRow.cells[3].innerText;
            document.getElementById("patientGender").value = currentEditRow.cells[4].innerText;
            document.getElementById("patientDiagnosis").value = currentEditRow.cells[5].innerText;
            document.getElementById("patientPhone").value = currentEditRow.dataset.phone;
            document.getElementById("patientLogin").value = currentEditRow.dataset.login;
            document.getElementById("patientPassword").value = currentEditRow.dataset.password;

            modal.style.display = "block";
        }
    });

    // Фильтрация по возрасту
    document.getElementById("filterAgeBtn").onclick = function () {
        const ageFrom = parseInt(document.getElementById("ageFrom").value) || 0;
        const ageTo = parseInt(document.getElementById("ageTo").value) || 100;
        const rows = document.querySelectorAll("#patientTableBody tr");

        rows.forEach(row => {
            const age = parseInt(row.cells[3].innerText);
            if (age >= ageFrom && age <= ageTo) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    };

    // Сортировка по алфавиту
    document.getElementById("sortAlphaBtn").onclick = function () {
        const rows = Array.from(document.querySelectorAll("#patientTableBody tr"));
        rows.sort((a, b) => a.cells[2].innerText.localeCompare(b.cells[2].innerText));
        const tableBody = document.getElementById("patientTableBody");
        tableBody.innerHTML = "";
        rows.forEach(row => tableBody.appendChild(row));
    };

    // Сортировка по новизне
    document.getElementById("sortNewestBtn").onclick = function () {
        const rows = Array.from(document.querySelectorAll("#patientTableBody tr"));
        rows.reverse();
        const tableBody = document.getElementById("patientTableBody");
        tableBody.innerHTML = "";
        rows.forEach(row => tableBody.appendChild(row));
    };

    // Поиск по фамилии
    document.getElementById("searchBtn").onclick = function () {
        const searchValue = document.getElementById("searchSurname").value.toLowerCase();
        const rows = document.querySelectorAll("#patientTableBody tr");

        rows.forEach(row => {
            const surname = row.cells[2].innerText.split(' ')[0].toLowerCase();
            if (surname.includes(searchValue)) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    };
});