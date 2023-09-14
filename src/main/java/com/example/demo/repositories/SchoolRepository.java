package com.example.demo.repositories;

import com.example.demo.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

    @Query("Select s From School s Where s.name = ?1")
    Optional<School> findSchoolByName(String name);
}
