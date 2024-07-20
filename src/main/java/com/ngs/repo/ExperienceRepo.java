package com.ngs.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ngs.model.CandidateDetails;
import com.ngs.model.Experience;
@Repository
public interface ExperienceRepo extends JpaRepository<Experience, Long> {

	List<Experience> findByCandidateDetails(CandidateDetails candidateDetails);
	Optional<Experience> findByCandidateDetails_CandidateId(Long candidateId);
	@Query("SELECT cd, exp, reg FROM CandidateDetails cd JOIN Experience exp ON cd.candidateId = exp.candidateDetails.candidateId JOIN Register reg ON cd.register.registrationid = reg.registrationid")
    List<Object[]> getCandidateList();
}
