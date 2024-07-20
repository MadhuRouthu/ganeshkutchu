package com.ngs.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngs.model.Register;
@Repository
public interface RegisterRepo extends JpaRepository<Register, Integer> {
	Optional<Register> findByEmailID(String emailID);
}
