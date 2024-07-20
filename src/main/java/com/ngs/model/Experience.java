package com.ngs.model;

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
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This generates the id automatically
    private Long id;  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id") // Foreign key column in Experience referencing CandidateDetails
    private CandidateDetails candidateDetails;
    private String skillName;
    private String experienceYears;
    private String workingStatus;
    private String role;
    
}
