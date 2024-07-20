package com.ngs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ngs.dto.CandidateExperienceDTO;
import com.ngs.dto.ForgotPwdDto;
import com.ngs.dto.LoginDto;
import com.ngs.dto.LoginResponse;
import com.ngs.dto.RegisterDto;
import com.ngs.model.CandidateDetails;
import com.ngs.model.Experience;
import com.ngs.model.ProjectDetails;
import com.ngs.model.Register;
import com.ngs.model.Resume;
import com.ngs.model.TALogin;
import com.ngs.service.NgsService;

@Controller
@SessionAttributes("email")
public class NgsController {
	@Autowired
	private NgsService ngsService;

	@GetMapping("/")
	public String showForm(Model model) {
		return "home";
	}

	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("login", new LoginDto());
		return "login";
	}

	@GetMapping("/profile")
	public String getProfile(Model model) {
		// Add loginForm object to the model
		model.addAttribute("project", new ProjectDetails());
		return "UpdateProfile";
	}

	@GetMapping("/login-profile")
	public String getLoginProfile(Model model, @ModelAttribute("loginResponseJson") JSONObject loginResponseJson) {
		return "UpdateProfile2";
	}

	@PostMapping("/login-verify")
	public String login(@ModelAttribute LoginDto loginDto, Model model, RedirectAttributes redirectAttributes) {
		boolean b = ngsService.login(loginDto);
		if (b) {
			// Convert the loginResponse object to JSON
			LoginResponse loginResponse = ngsService.loginResponse(loginDto);
			JSONObject loginResponseJson = new JSONObject(loginResponse);

			// Add the loginResponse JSON string to the flash attributes
			redirectAttributes.addAttribute("loginResponseJson", loginResponseJson.toString());
			model.addAttribute("email", loginDto.getEmail());
			// Redirect to the profile page
			return "redirect:/login-profile";
		} else {
			// If loginResponse is null, redirect back to the login page
			model.addAttribute("errorMessage", "Invalid email or password. Please try again.");
			model.addAttribute("login", new LoginDto());
			return "login"; // Assuming "login" is your login page
		}
	}

	@PostMapping("/view-profile")
	public String loginProfile(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
		// Here you can use the email parameter as needed
		System.out.println("Received email: " + email);
		// Assuming ngsService.viewProfile() returns LoginResponse object
		LoginResponse viewProfile = ngsService.viewProfile(email);
		JSONObject loginResponseJson = new JSONObject(viewProfile);

		// Add the loginResponse JSON string to the flash attributes
		redirectAttributes.addAttribute("loginResponseJson", loginResponseJson.toString());
		// Redirect to the UpdateProfile2 page after processing the email
		return "redirect:/ViewPage";
	}

	@GetMapping("/ViewPage")
	public String viewProfile(Model model, @ModelAttribute("loginResponseJson") JSONObject loginResponseJson) {
		// Here you can use the loginResponseJson as needed
		System.out.println("loginResponseJson: " + loginResponseJson);
		// Redirect to a suitable page after processing the email
		return "UpdateProfile2";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {
		model.addAttribute("register", new Register()); // Add loginForm object to the model
		return "register";
	}

	@GetMapping("/UpdateProfile2")
	public String getTATeamView(Model model) {
		// model.addAttribute("register", new Register()); // Add loginForm object to
		// the model
		return "UpdateProfile2";
	}

	@PostMapping("/regData")
	public String register(@ModelAttribute RegisterDto registerDto, Model model) {

		boolean register = ngsService.register(registerDto);
		if (register) {
			model.addAttribute("emailID", registerDto.getEmailID());
			model.addAttribute("otp", "");
			return "verify";
		} else {
			model.addAttribute("emailID", new RegisterDto());
			model.addAttribute("errorMessage", "Email is Registered. Please enter a another Email.");
			return "register";
		}
	}

	@PostMapping("/verify")
	public String verifyAccount(@RequestParam(name = "emailID") String emailID, @RequestParam(name = "otp") String otp,
			Model model) {
		// Call your service method to verify the account using emailID and otp
		boolean verifyAccount = ngsService.verifyAccount(emailID, otp);
		if (verifyAccount) {
			System.out.println(verifyAccount);
			model.addAttribute("email", emailID);
			return "UpdateProfile";
		} else {
			model.addAttribute("emailID", emailID);
			model.addAttribute("otp", "");
			return "verify";
		}
	}

	@PostMapping("/regenerate-otp")
	public String regenerateOtp(@RequestParam(name = "emailID") String emailID, Model model) {
		// model.addAttribute("emailID",);
		model.addAttribute("emailID", emailID);
		System.out.println(emailID);
		ngsService.regenerateOtp(emailID);
		return "verify";
	}

	@GetMapping("/forgotPassword")
	public String getForgotPassword(Model model) {
		model.addAttribute("forgotDto", new ForgotPwdDto());
		return "forgot-email";
	}

	@PostMapping("/send-otp")
	public String resetSendotp(@ModelAttribute(name = "forgotDto") ForgotPwdDto dto, Model model) {
		System.out.println("ForgotPwdDto" + dto.getEmail());
		boolean otpforResetPwd = ngsService.generateOtpforResetPwd(dto.getEmail());
		if (otpforResetPwd) {
			model.addAttribute("forgotDto", new ForgotPwdDto());
			model.addAttribute("email", dto.getEmail());
			return "forgot_otp";
		} else {
			model.addAttribute("errorMessage", "Your Email is not Registered. Please enter a correct Email.");
			model.addAttribute("forgotDto", new ForgotPwdDto());
			return "forgot-email";
		}

	}

	@PostMapping("/verify-otp")
	public String resetVerifyotp(@ModelAttribute(name = "forgotDto") ForgotPwdDto dto,
			@ModelAttribute(name = "email") String email, Model model, BindingResult bindingResult) {
		System.out.println("ForgotPwdDto" + dto.getOtp());
		boolean resetOtpValidate = ngsService.resetOtpValidate(dto.getOtp(), email);
		if (resetOtpValidate) {
			model.addAttribute("forgotDto", new ForgotPwdDto());
			return "forgot_reset";
		} else {
			model.addAttribute("errorMessage", "Invalid OTP. Please enter a correct OTP.");
			model.addAttribute("forgotDto", new ForgotPwdDto());
			return "forgot_otp";
		}
	}

	@PostMapping("/reset-pwd")
	public String resetPassword(@ModelAttribute(name = "forgotDto") ForgotPwdDto dto,
			@ModelAttribute(name = "email") String email, Model model) {
		boolean resetPassword = ngsService.resetPassword(dto.getPassword(), email);
		if (resetPassword) {
			model.addAttribute("login", new LoginDto());
			return "login";
		} else {
			model.addAttribute("forgotDto", new ForgotPwdDto());
			return "forgot_reset";
		}

	}

	@PostMapping("/saveSkills")
	public ResponseEntity<String> saveSkills(@RequestBody String skills, @ModelAttribute(name = "email") String email) {
		// Send the concatenated skills string to the service for processing
		try {
			ngsService.saveITSkills(skills, email);
			return ResponseEntity.ok("Skills saved successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving skills");
		}
	}

	@PostMapping("/saveProject")
	public ResponseEntity<String> saveProjectDetails(@RequestBody String details,
			@ModelAttribute(name = "email") String email) {

		boolean saveProjectDetails = ngsService.saveProjectDetails(details, email);
		if (saveProjectDetails) {
			return ResponseEntity.ok("Project details saved successfully!");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving project details");
		}
	}

	@PostMapping("/saveEducation")
	public ResponseEntity<String> saveEducationDetails(@RequestBody String jsonData,
			@ModelAttribute(name = "email") String email) {
		Register idbyEmail = ngsService.getIdbyEmail(email);
		// Set other fields similarly
		boolean saveEducationDetails = ngsService.saveEducationDetails(jsonData, idbyEmail);
		// For demonstration, let's just return a success message
		if (saveEducationDetails) {
			return ResponseEntity.ok("Education details saved successfully!");
		} else {
			return ResponseEntity.ok("Education details Not saved successfully!");
		}
	}

	@PostMapping("/savePD")
	public ResponseEntity<String> savePersonalDetails(@RequestBody String jsonData,
			@ModelAttribute(name = "email") String email) {
		Register idbyEmail = ngsService.getIdbyEmail(email);

		boolean savePersonalDetails = ngsService.savePersonalDetails(jsonData, idbyEmail);
		if (savePersonalDetails) {
			// For demonstration, let's just return a success message
			return ResponseEntity.ok("Education details saved successfully!");
		} else {
			return ResponseEntity.ok("Education details Not saved successfully!");

		}
	}

	@PostMapping("/uploadProfileImage")
	public ResponseEntity<String> saveProfilePhoto(@RequestParam("profileImage") MultipartFile file,
			@ModelAttribute(name = "email") String email) {
		Register idbyEmail = ngsService.getIdbyEmail(email);

		boolean savePersonalDetails = ngsService.saveProfile(idbyEmail, file);
		if (savePersonalDetails) {
			// For demonstration, let's just return a success message
			return ResponseEntity.ok("Education details saved successfully!");
		} else {
			return ResponseEntity.ok("Education details Not saved successfully!");

		}
	}

	@PostMapping("/uploadresumeFile")
	public ResponseEntity<String> uploadResume(@RequestParam("resume") MultipartFile file,
			@ModelAttribute(name = "email") String email) {
		// Save the resume to the database
		Register idbyEmail = ngsService.getIdbyEmail(email);
		ngsService.saveResume(file, idbyEmail);
		return ResponseEntity.status(HttpStatus.OK).body("Resume uploaded successfully");
	}

	@GetMapping("/download")
	public ResponseEntity downloadFromDB(@ModelAttribute(name = "email") String email) {
		Optional<Resume> optionalResume = ngsService.getResume(email);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(optionalResume.get().getResumecontentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + optionalResume.get().getResumefileName() + "\"")
				.body(optionalResume.get().getResume());
	}

	@GetMapping("/resume")
	public String getResume() {
		return "resume";

	}

	@GetMapping("/ta")
	public String getLoginTATeam(Model model) {
		model.addAttribute("login", new TALogin());
		return "TATeamLogin";
	}

	@PostMapping("/TATeam")
	public String getCandidateList(Model model, @ModelAttribute TALogin taLogin) {
		boolean loginTATeam = ngsService.loginTATeam(taLogin.getLoginid());
		if (loginTATeam) {
			List<Object[]> candidateList = ngsService.getCandidateList();
			List<CandidateExperienceDTO> candidateExperienceDTOs = new ArrayList<>();

			for (Object[] objects : candidateList) {
				CandidateDetails candidateDetails = (CandidateDetails) objects[0];
				Experience experience = (Experience) objects[1];
				CandidateExperienceDTO dto = new CandidateExperienceDTO();
				dto.setCandidateDetails(candidateDetails);
				dto.setExperience(experience);
				candidateExperienceDTOs.add(dto);
			}

			model.addAttribute("details", candidateExperienceDTOs);
			return "TATeamSearch";
		} else {
			model.addAttribute("login", new TALogin());
			model.addAttribute("errorMessage", "Invalid email or password. Please try again.");
			return "TATeamLogin";
		}
	}

}
