package com.guestbook.task.repository;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.guestbook.task.entity.UserEntity;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Serializable> {
	UserEntity findByEmail(String email);

	UserEntity findByGsm(String gsm);

	UserEntity findById(long id);

	@Query(value = "select * from gb_user where email=:email and is_admin=1", nativeQuery = true)
	UserEntity getAdminUserByEmail(@Param("email") String email);
}
