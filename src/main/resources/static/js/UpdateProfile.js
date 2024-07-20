/*Profile image*/

    document.getElementById('updateButton').addEventListener('click', function() {
    document.getElementById('fileInput').click();
  });
 
  document.getElementById('fileInput').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function(e) {
        document.querySelector('.profile-image').style.backgroundImage = `url(${e.target.result})`;
      }
      reader.readAsDataURL(file);
    }
  });
 
//ItSkills
function addSkill() {
      const skillsContainer = document.getElementById("skillsContainer");
      const skillEntry = document.createElement("div");
      skillEntry.classList.add("form-group");
   
      const selectedSkill = document.getElementById("skillName").value;
      const experienceYears = document.getElementById("experienceYears").value;
   
      if (selectedSkill === "" || experienceYears === "") {
          const validationMessage = document.getElementById("validationMessage");
          validationMessage.textContent = "Please select both the skill and experience before adding.";
          validationMessage.style.color = "red";
   
          // Attach event listeners to clear the validation message when fields are selected again
          document.getElementById("skillName").addEventListener("change", clearValidationMessage);
          document.getElementById("experienceYears").addEventListener("change", clearValidationMessage);
          
          return; // Exit the function early if validation fails
      }
   
      const skillLabel = document.createElement("label");
      skillLabel.textContent = selectedSkill + " - Experience: " + experienceYears + " years";
      skillEntry.appendChild(skillLabel);
   
      const skillInput = document.createElement("input");
      skillInput.setAttribute("type", "hidden");
      skillInput.setAttribute("name", "skills");
      skillInput.setAttribute("value", selectedSkill);
      skillEntry.appendChild(skillInput);
   
      const experienceInput = document.createElement("input");
      experienceInput.setAttribute("type", "hidden");
      experienceInput.setAttribute("name", "experienceYears");
      experienceInput.setAttribute("value", experienceYears);
      skillEntry.appendChild(experienceInput);
   
      skillsContainer.appendChild(skillEntry);
   
      // Clear the selected skill and experience from the dropdowns
      document.getElementById("skillName").value = "";
      document.getElementById("experienceYears").value = "";
      
      // Create a delete button symbol to remove the selected language
                  const deleteSymbol = document.createElement("span");
                  deleteSymbol.textContent = "×";
                  deleteSymbol.classList.add("delete-symbol");
                  deleteSymbol.onclick = function() {
                      skillEntry.remove(); // Remove the selected language entry when the delete symbol is clicked
                  };
                  skillEntry.appendChild(deleteSymbol);
  }
   
  // Function to clear the validation message
  function clearValidationMessage() {
      const validationMessage = document.getElementById("ItskillsValidationMessage");
      validationMessage.textContent = ""; // Clear the validation message
   
      // Remove event listeners to prevent clearing the validation message repeatedly
      document.getElementById("skillName").removeEventListener("change", clearValidationMessage);
      document.getElementById("experienceYears").removeEventListener("change", clearValidationMessage);
  }
     
     //save it skills********  
     function toggleProjectForm(radioButton) {
        var projectForm = document.getElementById('projects');
        if (radioButton.value === 'Fresher') {
            projectForm.style.display = 'none'; // Hide the project details form
        } else {
            projectForm.style.display = 'block'; // Show the project details form
        }
    }
  function submitItskills() {
    debugger;
    var skillData = []; // Array to store all skill data
    var allSkills = ""; // Variable to store all skills separated by commas
    var allExperiences = ""; // Variable to store all experiences separated by commas

    // Collect data from dynamically added skills
    var hiddenInputs = document.querySelectorAll("#skillsContainer input[name='skills']");
   
    hiddenInputs.forEach(function(input) {
        var skillName = input.value;
        var experienceYears = input.nextElementSibling.value;
        skillData.push({ skillName: skillName, experienceYears: experienceYears });
        // Concatenate skills and experiences into their respective strings
        allSkills += skillName + ", ";
        allExperiences += experienceYears + " years, ";
    });

    // Remove the trailing comma and space
    allSkills = allSkills.slice(0, -2);
    allExperiences = allExperiences.slice(0, -2);

    // Collect data from the selected working status
    var workingStatus = document.querySelector('input[name="workingStatus"]:checked');
    var statusValue = workingStatus ? workingStatus.value : null;
     var role= document.getElementById("role").value;
    // Push all variables into a list
    var dataList = {
    skillName: allSkills,
    experienceYears: allExperiences,
    workingStatus: statusValue,
    role:role
};

console.log("dataList",dataList);
    // Send the dataList as JSON
    fetch('/saveSkills', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dataList)
    })
    .then(response => {
        if (response.ok) {
            return response.text(); // Assuming the response is text
        }
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        console.log(data); // Log the response
        // Handle success or failure here
    })
    .catch(error => {
        console.error('Error:', error);
        // Handle error here
    });
}



//save the project****
function saveProject() {
    // Get form data
     var projectStatus = document.querySelector('input[name="projectStatus"]:checked');
     var statusValue = projectStatus ? projectStatus.value : null;
    var formData = {
        projectName: document.getElementById('projectTitle').value,
        client: document.getElementById('client').value,
        projectStatus: statusValue,
        workedFromYear: document.getElementById('workedFromYear').value,
        workedFromMonth: document.getElementById('workedFromMonth').value,
        projectDetails: document.getElementById('projectDetails').value
        // Add other form field values as needed
    };

    // Submit form data asynchronously using AJAX
    $.ajax({
        type: 'POST',
        url: '/saveProject',
        data: JSON.stringify(formData), // Convert data to JSON string
        contentType: 'application/json', // Set content type to JSON
        success: function(response) {
            // Handle success response
            console.log(response);
            // Optionally, you can display a success message or redirect the user
        },
        error: function(xhr, status, error) {
            // Handle error response
            console.error(xhr.responseText);
            // Optionally, you can display an error message to the user
        }
    });
}

function cancel() {
    // Implement cancel action if needed
}

//save candidate details**************
function saveEducation() {
    // Collect form data
    var formData = {
        "Graduation_College_Name": document.getElementById("Graduation_College_Name").value,
        "Graduation_University": document.getElementById("Graduation_University").value,
        "Graduation_Specialization": document.getElementById("Graduation_Specialization").value,
        "Graduation_GPA": document.getElementById("Graduation_GPA").value,
        "Graduation_YOP": document.getElementById("Graduation_YOP").value,
        "Graduation_Project": document.getElementById("Graduation_Project").value,
        "Post_Graduation_College_Name": document.getElementById("Post_Graduation_College_Name").value,
        "Post_Graduation_University": document.getElementById("Post_Graduation_University").value,
        "Post_Graduation_Specialization": document.getElementById("Post_Graduation_Specialization").value,
        "Post_Graduation_GPA": document.getElementById("Post_Graduation_GPA").value,
        "Post_Graduation_YOP": document.getElementById("Post_Graduation_YOP").value,
        "Post_Graduation_Project": document.getElementById("Post_Graduation_Project").value,
        "SSC_School_Name": document.getElementById("SSC_School_Name").value,
        "SSCBoard": document.getElementById("SSCBoard").value,
        "SSCCourseStream": document.getElementById("SSCCourseStream").value,
        "SSC_Marks": document.getElementById("SSC_Marks").value,
        "SSC_YOP": document.getElementById("SSC_YOP").value,
        "Inter_College": document.getElementById("Inter_College").value,
        "InterBoard": document.getElementById("InterBoard").value,
        "Inter_Group": document.getElementById("Inter_Group").value,
        "Inter_Marks": document.getElementById("Inter_Marks").value,
        "Inter_YOP": document.getElementById("Inter_YOP").value
    };

    // Convert form data to JSON string
    var jsonData = JSON.stringify(formData);

    // Send AJAX request to the controller
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/saveEducation", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log(xhr.responseText); // Log response to console
                // You can handle the response here as needed
            } else {
                console.error("Error saving education details");
            }
        }
    };
    xhr.send(jsonData);
}

  //PersonalDetails details***
  function cancelForm() {
            document.getElementById("PersonalForm").reset();
        }
 
        function submitPersonalDetails() {
            const selectedLanguages = []; // Array to store selected languages
            $('#selectedLanguages input[type="hidden"]').each(function() {
                selectedLanguages.push($(this).val()); // Push each selected language to the array
            });
 
            // Concatenate the selected languages into a single string separated by commas
            const languagesString = selectedLanguages.join(', ');
 
            const formData = {
                "candidate_Name": document.getElementById("candidate_Name").value,
                "age": document.getElementById("age").value,
                "gender": document.querySelector('input[name="gender"]:checked').value,
                "Marital_Status": document.querySelector('input[name="Marital_Status"]:checked').value,
                "language_Known": languagesString, // Set the concatenated string as the value
                "door_No": document.getElementById("door_No").value,
                "street": document.getElementById("street").value,
                "landMark": document.getElementById("landMark").value,
                "mandal": document.getElementById("mandal").value,
                "distric": document.getElementById("distric").value,
                "state": document.getElementById("state").value,
                "pincode": document.getElementById("pincode").value,
                "current_City": document.getElementById("location").value
                
            };
  // Convert form data to JSON string
        var jsonData = JSON.stringify(formData);

        // Send AJAX request to the controller
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/savePD", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    console.log(xhr.responseText); // Log response to console
                    // You can handle the response here as needed
                } else {
                    console.error("Error saving education details");
                }
            }
        };
        xhr.send(jsonData);
        }
 
 
        function addLanguage() {
            var selectedLanguagesDiv = $('#selectedLanguages');
            const languageKnown = document.getElementById("language_Known");
            const selectedLanguage = languageKnown.options[languageKnown.selectedIndex].text;
 
            // Check if the selected language is not already added
            if (!selectedLanguagesDiv.find(`input[value="${selectedLanguage}"]`).length) {
                // Create a new span element to hold the selected language
                const languageEntry = document.createElement("span");
                languageEntry.classList.add("selected-language");
 
                // Create a label element to display the selected language
                const languageLabel = document.createElement("label");
                languageLabel.textContent = selectedLanguage;
                languageEntry.appendChild(languageLabel);
 
                // Create a hidden input element to store the selected language value
                const languageInput = document.createElement("input");
                languageInput.setAttribute("type", "hidden");
                languageInput.setAttribute("name", "languages");
                languageInput.setAttribute("value", selectedLanguage);
                languageEntry.appendChild(languageInput);
 
                // Create a delete button symbol to remove the selected language
                const deleteSymbol = document.createElement("span");
                deleteSymbol.textContent = "×";
                deleteSymbol.classList.add("delete-symbol");
                deleteSymbol.onclick = function() {
                    languageEntry.remove(); // Remove the selected language entry when the delete symbol is clicked
                };
                languageEntry.appendChild(deleteSymbol);
 
                // Append the new language entry to the selectedLanguagesDiv
                selectedLanguagesDiv.append(languageEntry);
            }
        }
 
        function deleteSelectedLanguage() {
            var selectedLanguages = [];
            $('#language_Known option:selected').each(function() {
                var language = $(this).val();
                if(language !== "") {
                    selectedLanguages.push(language);
                }
            });
            var selectedLanguagesDiv = $('#selectedLanguages');
            selectedLanguagesDiv.empty(); // Clear previous selections
            selectedLanguages.forEach(function(language) {
                selectedLanguagesDiv.append('<div class="form-group"><input type="text" class="form-control" value="' + language + '" readonly><button type="button" class="btn btn-danger" onclick="removeLanguage(this)">Delete</button></div>');
            });
        }
 
        function removeLanguage(button) {
            $(button).parent().remove();
        }
/* Upload resume */
function uploadResume() {
    var fileInput = document.getElementById('resumeFile');
    var file = fileInput.files[0];
    //var errorMessageElement = document.getElementById('errorMessage');
    const validationMessage = document.getElementById("errorMessage");
          
    // Check if a file is selected
    if (!file) {
		 validationMessage.textContent = "Please select a file.";
          validationMessage.style.color = "red";
        return;
    }
    // Check if the selected file type is valid (DOC or PDF)
    var validTypes = ['application/msword', 'application/pdf'];
    if (!validTypes.includes(file.type)) {
		validationMessage.textContent = "Please select a valid file type (DOC or PDF).";
          validationMessage.style.color = "red";
        return;
    }
    // Clear error message if no error
    validationMessage.textContent = '';
    var formData = new FormData();
    formData.append('resume', file);
    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();
    // Configure the request
    xhr.open('POST', '/uploadresumeFile', true);
    // Set up a handler for when the request finishes
    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log('Resume uploaded successfully:', xhr.responseText);
        } else {
            console.error('Failed to upload resume:', xhr.responseText);
        }
    };
    // Set up a handler for when the request fails
    xhr.onerror = function() {
        console.error('Error occurred during resume upload.');
    };
    // Send the request
    xhr.send(formData);
}
  
    /*upload image*/
     document.getElementById('fileInput').addEventListener('change', function() {
    var fileInput = this;
    var file = fileInput.files[0];
    if (file) {
        var formData = new FormData();
        formData.append('profileImage', file);
 
        // Make an HTTP request to send the file to the backend
        fetch('/uploadProfileImage', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (response.ok) {
                console.log('Image uploaded successfully.');
                // Optionally, you can update the profile image on the frontend
                document.querySelector('.profile-image').style.backgroundImage = `url('${URL.createObjectURL(file)}')`;
            } else {
                console.error('Error uploading image.');
            }
        })
        .catch(error => {
            console.error('Error uploading image:', error);
        });
    } else {
        console.error('No file selected.');
    }
});


/*Active Session*/


	
		function setActive(section) {
			// Remove active class from all sections
			document.querySelectorAll('.component').forEach(function(el) {
				el.classList.remove('active');
			});
			// Add active class to the clicked section
			document.getElementById(section).classList.add('active');
			// Store the clicked section in sessionStorage
			sessionStorage.setItem('activeSection', section);
		}

		// Function to scroll to component
		function scrollToComponent(id) {
			document.getElementById(id).scrollIntoView({
				behavior : 'smooth'
			});
		}

		// Check sessionStorage for the last active section and apply styles
		document.addEventListener('DOMContentLoaded', function() {
			var activeSection = sessionStorage.getItem('activeSection');
			if (activeSection) {
				document.getElementById(activeSection).classList.add('active');
			}
		});

 
