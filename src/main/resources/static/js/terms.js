function joinAgree() {
    const form = document.querySelector(".agreeForm");
    const agreeAllCheckbox = document.querySelector(".agreeAll");
    const checkBoxes = document.querySelectorAll(".checkBoxes");
    const submitBtn = document.querySelector(".submitBtn");
    const otherCheckboxes = document.querySelectorAll('input[name="agree"]');

    agreeAllCheckbox.addEventListener('change', function() {
        otherCheckboxes.forEach(checkbox => {
            checkbox.checked = agreeAllCheckbox.checked;
        });
        submitBtn.disabled = !agreeAllCheckbox.checked;
    });


    submitBtn.addEventListener('click', function() {
        if (agreeAllCheckbox.checked) {
            window.location.href = 'member/register.html';
        } else {
            alert('약관에 동의해주세요.');
        }
    });
}