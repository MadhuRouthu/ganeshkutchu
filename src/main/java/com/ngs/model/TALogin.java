package com.ngs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TALogin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loginid;
    private String email;
    private String password;
}
