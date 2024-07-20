package com.ngs.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ngs.model.CandidateDetails;
import com.ngs.model.ProjectDetails;

public interface ProjectDetailsRepo extends JpaRepository<ProjectDetails, Integer> {

	Optional<ProjectDetails> findByCandidateDetails(CandidateDetails candidateDetails);

	Optional<ProjectDetails> findByCandidateDetails_CandidateId(Long long1);

}
