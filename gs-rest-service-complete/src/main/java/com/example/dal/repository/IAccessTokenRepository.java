package com.example.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dal.model.AccessToken;
import com.example.dal.model.CdUser;

@Repository
public interface IAccessTokenRepository extends JpaRepository<AccessToken, Long> {
	AccessToken findByUsername(CdUser user);
	AccessToken findByUsernameAndToken(CdUser user, String token);
	long deleteByUsername(CdUser user);
}
