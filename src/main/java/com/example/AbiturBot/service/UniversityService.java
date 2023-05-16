package com.example.AbiturBot.service;

import com.example.AbiturBot.model.University;

import java.util.List;

public interface UniversityService {
    List<University> getAll();
    University universityById(long id);
    University universityByName(String nameUniversity);
}
