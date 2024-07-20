function addSkill() {
    const selectedSkill = document.getElementById("skillName").value;
    const experienceYears = document.getElementById("experienceYears").value;

    if (!selectedSkill || !experienceYears) {
        document.getElementById("validationMessage").textContent = "Please select both skill and experience before adding.";
        return;
    }

    // Clear previous validation message
    document.getElementById("validationMessage").textContent = "";

    const skillsContainer = document.getElementById("skillsContainer");
    const skillListItem = document.createElement("li");
    skillListItem.textContent = `${selectedSkill} - Experience: ${experienceYears} years`;
    skillsContainer.appendChild(skillListItem);

    // Clear the selected skill and experience from the dropdowns
    document.getElementById("skillName").value = "";
    document.getElementById("experienceYears").value = "";

    // Show the selected skills heading
    document.getElementById("selectedSkillsHeading").style.display = "block";
}

function cancelForm() {
    document.getElementById("itSkillsForm").reset();
    document.getElementById("skillsContainer").innerHTML = ''; // Clear selected skills
    document.getElementById("selectedSkillsHeading").style.display = "none"; // Hide selected skills heading
    document.getElementById("validationMessage").textContent = ""; // Clear validation message
}

function validateForm() {
    const selectedSkills = document.querySelectorAll("#skillsContainer li");
    if (selectedSkills.length === 0) {
        document.getElementById("validationMessage").textContent = "Please add at least one skill before submitting.";
        return false;
    }
    return true;
}

document.getElementById("itSkillsForm").addEventListener("submit", function(event) {
    if (!validateForm()) {
        event.preventDefault(); // Prevent form submission if validation fails
    }
});
