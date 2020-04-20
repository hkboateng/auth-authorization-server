package com.protecksoftware.authentication.auth.resposity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protecksoftware.authentication.business.entity.LoginUser;


public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
	LoginUser findByUsername(String username);
}
