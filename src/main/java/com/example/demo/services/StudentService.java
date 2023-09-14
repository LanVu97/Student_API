package com.example.demo.services;

import com.example.demo.exceptions.EtBadRequestException;
import com.example.demo.model.Student;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface StudentService {

    List<Student> getStudents(Integer schoolId);

    void addNewStudent(Integer schoolId, Student student) throws EtBadRequestException;

    void deleteStudent(Integer schoolId, Integer id);

    Student updateStudent(Integer schoolId, Integer studentId, String name, String email) throws EtBadRequestException;

    Student getStudentById(Integer schoolId, Integer studentId);
}
