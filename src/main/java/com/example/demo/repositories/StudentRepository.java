package com.example.demo.repositories;

import com.example.demo.exceptions.EtResourceFoundException;
import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

    @Query("Select s From Student s Where s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Transactional
    @Modifying
    @Query("Delete From Student s Where s.school.id = ?1")
    void deleteBySchoolId(Integer schoolId);

    @Query("Select s From Student s Where s.school.id = ?1")
    List<Student> findStudentBySchoolId(Integer schoolId);

    @Query("Select s From Student s Where s.school.id = ?1 and s.id = ?2")
    Optional<Student> findBySchoolIdAndStudentId(Integer schoolId, Integer studentId);
}
