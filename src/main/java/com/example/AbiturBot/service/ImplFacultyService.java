package com.example.AbiturBot.service;

import com.example.AbiturBot.model.Faculty;
import com.example.AbiturBot.repository.FacultyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ImplFacultyService implements FacultyService{

    private final FacultyRepository facultyRepository;

    @Override
    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }
}



