window.onload = function() {
    const roleModal = document.getElementById('roleModal');
    const doctorForm = document.getElementById('doctorForm');
    const patientForm = document.getElementById('patientForm');
    const selectDoctorBtn = document.getElementById('selectDoctorBtn');
    const selectPatientBtn = document.getElementById('selectPatientBtn');

    // 弹窗默认显示
    roleModal.style.display = 'block';
    doctorForm.style.display = 'none';
    patientForm.style.display = 'none';

    selectDoctorBtn.onclick = function() {
        roleModal.style.display = 'none';
        doctorForm.style.display = 'block';
        patientForm.style.display = 'none';
    };

    selectPatientBtn.onclick = function() {
        roleModal.style.display = 'none';
        patientForm.style.display = 'block';
        doctorForm.style.display = 'none';
    };
};

document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get("success") === "true") {
        const successModal = new bootstrap.Modal(document.getElementById('successModal'));
        successModal.show();

        setTimeout(() => {
            window.location.href = "/login";
        }, 2500);
    }
});
