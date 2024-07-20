package com.ngs.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
	private Long project_id;
	private String projectName;
	private String client;
	private String projectStatus;
	private String workedFromYear; // Add this property
    private String workedFromMonth; // Add this property
    private String projectDetails; // Add this property
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id") // Foreign key column in ProjectDetails referencing CandidateDetails
    private CandidateDetails candidateDetails;
}
