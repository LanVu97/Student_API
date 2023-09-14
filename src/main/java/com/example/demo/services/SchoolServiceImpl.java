package com.example.demo.services;

import com.example.demo.exceptions.EtBadRequestException;
import com.example.demo.exceptions.EtResourceFoundException;
import com.example.demo.model.School;
import com.example.demo.repositories.SchoolRepository;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService{

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<School> getSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public void addNewSchool(School school) throws EtBadRequestException {
        Optional<School> schoolByName = schoolRepository.findSchoolByName(school.getName());
        if(schoolByName.isPresent()){
            throw new EtBadRequestException("school taken");
        }
        schoolRepository.save(school);
    }

    @Override
    public void deleteSchoolWithAllStudent(Integer id) {

        boolean exists = schoolRepository.existsById(id);
        if(!exists){
            throw new EtResourceFoundException("school with id" + id +
                    "does not exists");
        };

        studentRepository.deleteBySchoolId(id);
        schoolRepository.deleteById(id);
    }

    @Override
    public School updateSchool(Integer schoolId, String name, String description) throws EtBadRequestException {
        School school = schoolRepository.findById(schoolId).orElseThrow(() -> new EtResourceFoundException("school with id" + schoolId + "does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(school.getName(), name)) {
            school.setName(name);
        }

        school.setDescription(description);

        return schoolRepository.save(school);
    }

    @Override
    public School getSchoolById(Integer schoolId) {
        return schoolRepository.findById(schoolId).orElseThrow(() -> new EtResourceFoundException("school with id" + schoolId +
                "does not exists"));
    }
}
