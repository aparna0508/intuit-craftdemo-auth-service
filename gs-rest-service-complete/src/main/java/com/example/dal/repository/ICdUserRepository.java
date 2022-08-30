package com.example.dal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dal.model.CdUser;

@Repository
public interface ICdUserRepository extends JpaRepository<CdUser, String> {
	CdUser findByUsername(String username);
	List<CdUser> findByName(String name);
	List<CdUser> findByEmailId(String emailId);
	List<CdUser> findByPhoneNo(Integer phoneNo);
	List<CdUser> findByNameAndEmailId(String name, String emailId);
}
