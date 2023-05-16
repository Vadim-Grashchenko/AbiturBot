package com.example.AbiturBot.repository;

import com.example.AbiturBot.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    University findByName(String nameUniversity);
}
