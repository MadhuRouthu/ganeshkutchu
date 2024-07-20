package com.ngs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resume {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
	private Long resumeId;
	@Lob
    @Column(length = 16777215)
    private byte[] Resume;
	private String resumefileName;
    private String resumecontentType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id") // Foreign key column in Experience referencing CandidateDetails
    private CandidateDetails candidateDetails;
}
