package com.mm.homeworks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.homeworks.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	List<User> findByUsername(String username);

}
