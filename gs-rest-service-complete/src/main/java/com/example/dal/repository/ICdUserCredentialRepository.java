package com.example.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dal.model.CdUser;
import com.example.dal.model.CdUserCredential;

@Repository
public interface ICdUserCredentialRepository extends JpaRepository<CdUserCredential, Long> {
	CdUserCredential findByUsernameAndPassword(CdUser user, String password);
}
