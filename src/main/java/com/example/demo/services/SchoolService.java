package com.example.demo.services;

import com.example.demo.exceptions.EtBadRequestException;
import com.example.demo.model.School;

import java.util.List;

public interface SchoolService {

    List<School> getSchools();

    void addNewSchool(School school) throws EtBadRequestException;

    void deleteSchoolWithAllStudent(Integer id);

    School updateSchool(Integer schoolId, String name, String description) throws EtBadRequestException;

    School getSchoolById(Integer schoolId);
}
