package com.mm.homeworks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.homeworks.model.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>{

}
