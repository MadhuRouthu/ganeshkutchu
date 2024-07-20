package com.ngs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
	private Long candidateId;
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id") // Foreign key column in CandidateDetails referencing Register
    private Register register;
	private String CandidateName;
    private String Age;
    private String Gender;
    private String Languages_Known;
    private String D_No;
    private String Street;
    private String LandMark;
    private String Mandal;
    private String District;
    private String State;
    private String Pincode;
    private String currentCity;
    private String SSC_School_Name;
    private String SSC_Marks;
    private String SSC_YOP;
    private String SSCBoard;
    private String SSCCourseStream;
    private String InterBoard;
    private String Inter_College;
    private String Inter_Marks;
    private String Inter_Group;
    private String Inter_YOP;
    private String Graduation_College_Name;
    private String Graduation_GPA;
    private String Graduation_Specialization;
    private String Graduation_YOP;
    private String Graduation_University;
    private String Graduation_Project;
    private String Post_Graduation_University;
    private String Post_Graduation_College_Name;
    private String Post_Graduation_YOP;
    private String Post_Graduation_Specialization;
    private String Post_Graduation_GPA;
    private String Post_Graduation_Project;
    private String Marital_Status;
    
    private String fileName;
    private String contentType;
    @Lob
    @Column(length = 16777215)
    private byte[] data;

}
