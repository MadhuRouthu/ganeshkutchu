package com.ngs.service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngs.dto.LoginDto;
import com.ngs.dto.LoginResponse;
import com.ngs.dto.RegisterDto;
import com.ngs.model.CandidateDetails;
import com.ngs.model.Experience;
import com.ngs.model.ProjectDetails;
import com.ngs.model.Register;
import com.ngs.model.Resume;
import com.ngs.model.TALogin;
import com.ngs.repo.CandidateDetailsRepo;
import com.ngs.repo.ExperienceRepo;
import com.ngs.repo.ProjectDetailsRepo;
import com.ngs.repo.RegisterRepo;
import com.ngs.repo.ResumeRepo;
import com.ngs.repo.TALoginRepo;
import com.ngs.util.EmailUtil;
import com.ngs.util.OtpUtil;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class NgsServiceImpl implements NgsService {

	@Autowired
	private ExperienceRepo itSkillsRepo;
	@Autowired
	private CandidateDetailsRepo candidatedetailsrepo;
	@Autowired
	private RegisterRepo registerRepo;
	@Autowired
	private OtpUtil otpUtil;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private ProjectDetailsRepo detailsRepo;
	@Autowired
	private ResumeRepo resumeRepo;
	@Autowired
	private TALoginRepo taloginrepo;

	@Override
	public Register getIdbyEmail(String email) {
		// TODO Auto-generated method stub
		Optional<Register> byEmailID = registerRepo.findByEmailID(email);
		Register register = null;
		if (byEmailID.isPresent()) {
			register = byEmailID.get();
		}
		return register;
	}

	@Override
	public boolean saveITSkills(String itSkills, String email) {
		boolean saved = false;

		// Find the Register entity by email
		Optional<Register> byEmailID = registerRepo.findByEmailID(email);
		if (byEmailID.isPresent()) {
			Register register = byEmailID.get();

			// Find the CandidateDetails associated with the Register
			Optional<CandidateDetails> byRegister_Registrationid = candidatedetailsrepo
					.findByRegister_Registrationid(register.getRegistrationid());
			if (byRegister_Registrationid.isPresent()) {
				CandidateDetails candidateDetails = byRegister_Registrationid.get();

				ObjectMapper objectMapper = new ObjectMapper();
				try {
					// Parse the JSON string into a Map
					Map<String, String> skillData = objectMapper.readValue(itSkills,
							new TypeReference<Map<String, String>>() {
							});

					// Check if candidateId is present in Experience table
					Long candidateId = candidateDetails.getCandidateId();
					Optional<Experience> existingExperience = itSkillsRepo
							.findByCandidateDetails_CandidateId(candidateId);

					Experience experience;
					if (existingExperience.isPresent()) {
						// If candidateId is present, update the existing Experience object
						experience = existingExperience.get();
					} else {
						// If candidateId is not present, create a new Experience object
						experience = new Experience();
					}

					// Set the properties of the Experience object
					experience.setSkillName(skillData.get("skillName"));
					experience.setExperienceYears(skillData.get("experienceYears"));
					experience.setWorkingStatus(skillData.get("workingStatus"));
					experience.setRole(skillData.get("role"));
					experience.setCandidateDetails(candidateDetails);

					// Save the Experience object
					Experience savedExperience = itSkillsRepo.save(experience);
					if (savedExperience != null) {
						saved = true;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return saved;
	}

	@Override
	public boolean registerCandidateDetails(Register candidateRegister) {
		// TODO Auto-generated method stub
		Register save = registerRepo.save(candidateRegister);
		boolean saved;
		if (save == null) {
			saved = false;
		} else {
			saved = true;
		}
		return saved;
	}

	@Override
	public boolean saveEducationDetails(String jsonData, Register register) {
		boolean saved = false;
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> formData = null;

		try {
			formData = objectMapper.readValue(jsonData, new TypeReference<Map<String, String>>() {
			});

			// Check if CandidateDetails already exists for the given register
			Optional<CandidateDetails> existingCandidateDetails = candidatedetailsrepo
					.findByRegister_Registrationid(register.getRegistrationid());
			CandidateDetails candidateDetails;
			if (existingCandidateDetails.isPresent()) {
				// Update existing CandidateDetails
				candidateDetails = existingCandidateDetails.get();
			} else {
				candidateDetails = new CandidateDetails();
			}
			candidateDetails.setSSC_School_Name(formData.get("SSC_School_Name"));
			candidateDetails.setSSC_Marks(formData.get("SSC_Marks"));
			candidateDetails.setSSC_YOP(formData.get("SSC_YOP"));
			candidateDetails.setSSCBoard(formData.get("SSCBoard"));
			candidateDetails.setSSCCourseStream(formData.get("SSCCourseStream"));
			candidateDetails.setInterBoard(formData.get("InterBoard"));
			candidateDetails.setInter_College(formData.get("Inter_College"));
			candidateDetails.setInter_Marks(formData.get("Inter_Marks"));
			candidateDetails.setInter_Group(formData.get("Inter_Group"));
			candidateDetails.setInter_YOP(formData.get("Inter_YOP"));
			candidateDetails.setGraduation_College_Name(formData.get("Graduation_College_Name"));
			candidateDetails.setGraduation_GPA(formData.get("Graduation_GPA"));
			candidateDetails.setGraduation_Specialization(formData.get("Graduation_Specialization"));
			candidateDetails.setGraduation_YOP(formData.get("Graduation_YOP"));
			candidateDetails.setGraduation_University(formData.get("Graduation_University"));
			candidateDetails.setGraduation_Project(formData.get("Graduation_Project"));
			candidateDetails.setPost_Graduation_University(formData.get("Post_Graduation_University"));
			candidateDetails.setPost_Graduation_College_Name(formData.get("Post_Graduation_College_Name"));
			candidateDetails.setPost_Graduation_YOP(formData.get("Post_Graduation_YOP"));
			candidateDetails.setPost_Graduation_Specialization(formData.get("Post_Graduation_Specialization"));
			candidateDetails.setPost_Graduation_GPA(formData.get("Post_Graduation_GPA"));
			candidateDetails.setPost_Graduation_Project(formData.get("Post_Graduation_Project"));
			candidateDetails.setRegister(register);
			// Save the new CandidateDetails
			CandidateDetails savedCandidateDetails = candidatedetailsrepo.save(candidateDetails);
			if (savedCandidateDetails != null) {
				saved = true;
			} else {
				saved = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return saved;
	}

	@Override
	public boolean candidateRegister(Register register) {
		// TODO Auto-generated method stub
		Register save = registerRepo.save(register);
		boolean saved;
		if (save == null) {
			saved = false;
		} else {
			saved = true;
		}
		return saved;
	}

	@Override
	public boolean register(RegisterDto registerDto) {
		String otp = otpUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(registerDto.getEmailID(), otp);
		} catch (MessagingException e) {
			// Log the exception or handle it accordingly
			e.printStackTrace();
			return false; // Failed to send OTP
		}

		Optional<Register> userOptional = registerRepo.findByEmailID(registerDto.getEmailID());
		if (userOptional.isPresent()) {
			return false; // User with the email already exists
		}

		// User doesn't exist, proceed with registration
		Register user = new Register();
		user.setFullName(registerDto.getFullName());
		user.setEmailID(registerDto.getEmailID());
		user.setPassword(registerDto.getPassword());
		user.setOtp(otp);
		user.setOtpGeneratedTime(LocalDateTime.now());
		user.setMobileNumber(registerDto.getMobileNumber());

		registerRepo.save(user);
		return true;
	}

	@Override
	public boolean verifyAccount(String emailID, String otp) {
		Register user = registerRepo.findByEmailID(emailID)
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + emailID));
		if (user.getOtp().equals(otp)
				&& Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
			user.setActive(true);
			registerRepo.save(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean regenerateOtp(String email) {
		Register user = registerRepo.findByEmailID(email)
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
		String otp = otpUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(email, otp);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp please try again");
		}
		user.setOtp(otp);
		user.setOtpGeneratedTime(LocalDateTime.now());
		Register save = registerRepo.save(user);
		if (save != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean login(LoginDto loginDto) {
		Register user = registerRepo.findByEmailID(loginDto.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));

		if (!loginDto.getPassword().equals(user.getPassword())) {
			return false;
		} else if (!user.isActive()) {
			return false;
		}
		return true;
	}
	@Override
	public LoginResponse loginResponse(LoginDto loginDto) {
		Register user = registerRepo.findByEmailID(loginDto.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));
		Long registrationId = user.getRegistrationid();
		// Initialize response object
	    LoginResponse response = new LoginResponse();

	    // Fetch candidate details
	    CandidateDetails candidateDetails = candidatedetailsrepo.findByRegister_Registrationid(registrationId)
	            .orElse(null);
	    response.setCandidateDetails(candidateDetails);

	    // Fetch experiences
	    Experience experiences = itSkillsRepo.findByCandidateDetails_CandidateId(candidateDetails != null ? candidateDetails.getCandidateId() : null).orElse(null);
	    response.setExperience(experiences);

	    // Fetch project details
	    ProjectDetails projectDetails = detailsRepo.findByCandidateDetails_CandidateId(candidateDetails != null ? candidateDetails.getCandidateId() : null).orElse(null);
	    response.setProjectDetails(projectDetails);
	    return response;
	}
	@Override
	public LoginResponse viewProfile(String email) {
		Register user = registerRepo.findByEmailID(email)
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
		Long registrationId = user.getRegistrationid();
		// Initialize response object
	    LoginResponse response = new LoginResponse();

	    // Fetch candidate details
	    CandidateDetails candidateDetails = candidatedetailsrepo.findByRegister_Registrationid(registrationId)
	            .orElse(null);
	    response.setCandidateDetails(candidateDetails);

	    // Fetch experiences
	    Experience experiences = itSkillsRepo.findByCandidateDetails_CandidateId(candidateDetails != null ? candidateDetails.getCandidateId() : null).orElse(null);
	    response.setExperience(experiences);

	    // Fetch project details
	    ProjectDetails projectDetails = detailsRepo.findByCandidateDetails_CandidateId(candidateDetails != null ? candidateDetails.getCandidateId() : null).orElse(null);
	    response.setProjectDetails(projectDetails);

		
		return response;
	}

	@Override
	public boolean generateOtpforResetPwd(String email) {
		String otp = otpUtil.generateOtp();
		Optional<Register> userOptional = registerRepo.findByEmailID(email);
		if (userOptional.isPresent()) {
			Register user = userOptional.get();
			try {
				emailUtil.sendOtpEmail(email, otp);
				user.setOtp(otp);
				user.setOtpGeneratedTime(LocalDateTime.now());
				registerRepo.save(user);
				
				return true;
			} catch (MessagingException e) {
				// Log the exception or handle it accordingly
				return false; // Failed to send OTP
			}
		} else {
			return false; // User not found
		}

	}

	@Override
	public boolean resetOtpValidate(String otp, String email) {
		Register user = registerRepo.findByEmailID(email)
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
		if (user.getOtp().equals(otp)
				&& Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
			user.setActive(true);
			registerRepo.save(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean resetPassword(String password, String email) {
		// TODO Auto-generated method stub
		Register register = registerRepo.findByEmailID(email)
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
		register.setPassword(password);
		Register save = registerRepo.save(register);
		if (save != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean saveProjectDetails(String details, String email) {
		boolean saved = false;
		try {
			// Find the Register entity by email
			Optional<Register> byEmailID = registerRepo.findByEmailID(email);

			if (byEmailID.isPresent()) {
				Register register = byEmailID.get();

				// Find the CandidateDetails associated with the Register
				Optional<CandidateDetails> byRegisterRegistrationId = candidatedetailsrepo
						.findByRegister_Registrationid(register.getRegistrationid());

				if (byRegisterRegistrationId.isPresent()) {
					CandidateDetails candidateDetails = byRegisterRegistrationId.get();

					// Parse JSON string into ProjectDetails object
					ObjectMapper objectMapper = new ObjectMapper();
					ProjectDetails projectDetails = objectMapper.readValue(details, ProjectDetails.class);

					// Check if a ProjectDetails entry already exists for the CandidateDetails
					Optional<ProjectDetails> existingProjectDetails = detailsRepo
							.findByCandidateDetails(candidateDetails);

					if (existingProjectDetails.isPresent()) {
						// Update the existing ProjectDetails entry
						ProjectDetails existingDetails = existingProjectDetails.get();
						existingDetails.setProjectName(projectDetails.getProjectName());
						existingDetails.setClient(projectDetails.getClient());
						existingDetails.setProjectStatus(projectDetails.getProjectStatus());
						existingDetails.setWorkedFromYear(projectDetails.getWorkedFromYear());
						existingDetails.setWorkedFromMonth(projectDetails.getWorkedFromMonth());
						existingDetails.setProjectDetails(projectDetails.getProjectDetails());

						// Save the updated ProjectDetails
						ProjectDetails savedProjectDetails = detailsRepo.save(existingDetails);

						if (savedProjectDetails != null) {
							saved = true;
						}
					} else {
						// Set CandidateDetails for the project
						projectDetails.setCandidateDetails(candidateDetails);

						// Save the new ProjectDetails entry
						ProjectDetails savedProjectDetails = detailsRepo.save(projectDetails);

						if (savedProjectDetails != null) {
							saved = true;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return saved;
	}

	@Override
	public boolean savePersonalDetails(String jsonData, Register register) {
		// TODO Auto-generated method stub
		CandidateDetails CandidateDetails = candidatedetailsrepo
				.findByRegister_Registrationid(register.getRegistrationid())
				.orElseThrow(() -> new RuntimeException("Data Not Saved"));
		boolean saved = false;
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> formData;
		try {
			formData = objectMapper.readValue(jsonData, new TypeReference<Map<String, String>>() {
			});
			CandidateDetails.setCandidateName(formData.get("candidate_Name"));
			CandidateDetails.setAge(formData.get("age"));
			CandidateDetails.setGender(formData.get("gender"));
			CandidateDetails.setMarital_Status(formData.get("Marital_Status"));
			CandidateDetails.setLanguages_Known(formData.get("language_Known"));
			CandidateDetails.setD_No(formData.get("door_No"));
			CandidateDetails.setStreet(formData.get("street"));
			CandidateDetails.setLandMark(formData.get("landMark"));
			CandidateDetails.setMandal(formData.get("mandal"));
			CandidateDetails.setDistrict(formData.get("distric"));
			CandidateDetails.setState(formData.get("state"));
			CandidateDetails.setPincode(formData.get("pincode"));
			CandidateDetails.setCurrentCity(formData.get("current_City"));

			CandidateDetails save = candidatedetailsrepo.save(CandidateDetails);
			if (save != null) {
				saved = true;
			} else {
				saved = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saved;
	}

	@Override
	public boolean saveProfile(Register idbyEmail,MultipartFile file) {
		// TODO Auto-generated method stub
		boolean saved=false;
	    try {
            // Generate a unique filename
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
 
            // Save the file to the database 
            Long registrationid = idbyEmail.getRegistrationid();
            Optional<CandidateDetails> byRegister_Registrationid = candidatedetailsrepo.findByRegister_Registrationid(registrationid);
            CandidateDetails candidateDetails ;
            if(byRegister_Registrationid.isPresent()) {
            	 candidateDetails = byRegister_Registrationid.get();
            	
            }else {
            	 candidateDetails=new CandidateDetails();
            }
            candidateDetails.setFileName(fileName);
        	candidateDetails.setContentType(file.getContentType());
        	candidateDetails.setData(file.getBytes());
        	candidateDetails.setRegister(idbyEmail);
            CandidateDetails save = candidatedetailsrepo.save(candidateDetails);
            if(save != null) {
            	saved=true;
            }else {
            	saved=false;
            }
        } catch (IOException e) {
            e.printStackTrace();
           
        }
		return saved;
	}

	@Override
	public boolean saveResume(MultipartFile file, Register idbyEmail) {
		boolean saved=false;
		// Save the file to the database 
        Long registrationid = idbyEmail.getRegistrationid();
        Optional<CandidateDetails> byRegister_Registrationid = candidatedetailsrepo.findByRegister_Registrationid(registrationid);
        Resume candidateDetails ;
        Optional<Resume> byCandidateDetailsId = resumeRepo.findByCandidateDetails_CandidateId(byRegister_Registrationid.get().getCandidateId()); 
        if(byCandidateDetailsId.isPresent()) {
        	 candidateDetails = byCandidateDetailsId.get();
        }else {
        	 candidateDetails=new Resume();
        }
        candidateDetails.setResumefileName(file.getOriginalFilename());
        candidateDetails.setResumecontentType(file.getContentType());
        candidateDetails.setCandidateDetails(byRegister_Registrationid.get());
        try {
			candidateDetails.setResume(file.getBytes());
			Resume save = resumeRepo.save(candidateDetails);
			if(save!=null) {
				saved=true;
			}else {
				saved=false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saved;
	}

	@Override
	public Optional<Resume> getResume(String email) {
		Optional<Register> byEmailID = registerRepo.findByEmailID(email);
		Optional<CandidateDetails> byRegister_Registrationid = candidatedetailsrepo.findByRegister_Registrationid(byEmailID.get().getRegistrationid());
		Optional<Resume> byCandidateDetails_CandidateId = resumeRepo.findByCandidateDetails_CandidateId(byRegister_Registrationid.get().getCandidateId());
		return byCandidateDetails_CandidateId;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Object[]> getCandidateList() {
		// TODO Auto-generated method stub	
		List<Object[]> allExperiencesWithCandidateAndRegistrationDetails = itSkillsRepo.getCandidateList();
		return allExperiencesWithCandidateAndRegistrationDetails;
	}

	@Override
	public List<ProjectDetails> getExperienceList() {
		// TODO Auto-generated method stub
		List<ProjectDetails> all = detailsRepo.findAll();
		return all;
	}

	@Override
	public boolean loginTATeam(Long id) {
		// TODO Auto-generated method stub
		Optional<TALogin> byId = taloginrepo.findById((long)(1));
		if(byId!=null) {
			return true;
		}else {
		return false;
		}
	}
	
}
