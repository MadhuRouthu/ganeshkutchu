package com.ngs.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ngs.model.Resume;


public interface ResumeRepo extends JpaRepository<Resume, Long> {
	// Method to find Resume by candidateId
    Optional<Resume> findByCandidateDetails_CandidateId(Long candidateId);
}
