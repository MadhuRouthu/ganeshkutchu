package com.ngs.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ngs.model.CandidateDetails;

public interface CandidateDetailsRepo extends JpaRepository<CandidateDetails, Integer> {
	Optional<CandidateDetails> findByRegister_Registrationid(Long registrationId);

}
