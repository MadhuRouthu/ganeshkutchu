package com.ngs.dto;

import com.ngs.model.CandidateDetails;
import com.ngs.model.Experience;
import com.ngs.model.ProjectDetails;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LoginResponse {
private CandidateDetails candidateDetails;
private ProjectDetails projectDetails;
private Experience experience;
}
