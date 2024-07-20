package com.ngs.dto;

import com.ngs.model.CandidateDetails;
import com.ngs.model.Experience;
import com.ngs.model.Register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateExperienceDTO {
	private CandidateDetails candidateDetails;
    private Experience experience;
    private Register register;
}
