package com.example.AbiturBot.service;

import com.example.AbiturBot.model.University;
import com.example.AbiturBot.repository.UniversityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ImplUniversityService implements UniversityService{

    private final UniversityRepository universityRepository;

    @Override
    public List<University> getAll() {
        return universityRepository.findAll();
    }

    @Override
    public University universityById(long id) {
        return universityRepository.findById(id).get();
    }

    @Override
    public University universityByName(String nameUniversity) {
        return universityRepository.findByName(nameUniversity);
    }
}
