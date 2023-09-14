package com.example.demo.repositories;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("Select s From User s Where s.email = ?1 and s.password=?2")
    User findByEmailAndPassword(String email, String password);

    @Query("Select count(*) From User s Where s.email = ?1")
    Integer getCountByEmail(String email);
}
