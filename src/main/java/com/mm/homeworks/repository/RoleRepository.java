package com.mm.homeworks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mm.homeworks.model.entity.Authority;
import com.mm.homeworks.model.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Query("SELECT r from Role r WHERE r.authority = :authority")
	List<Role> findByAuthorityName(Authority authority);
	
	List<Role> findByAuthority(String authority);
	
	
}
