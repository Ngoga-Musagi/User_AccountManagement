package com.userAccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.userAccount.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
   
	@Query("SELECT u FROM User u WHERE u.email=?1")
	User findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query("update User u set u.password =:pass where u.email =:email")
	void updateUserPass(String pass,String email);
	
	@Transactional
	@Modifying
	@Query("update User u set u.photo =:file , u.nid=:nid, u.status=:status where u.email =:email")
	void verifyAccount(String file,String nid,String status,String email);
}
