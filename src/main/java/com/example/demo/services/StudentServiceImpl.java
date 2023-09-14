package com.example.demo.services;

import com.example.demo.exceptions.EtBadRequestException;
import com.example.demo.exceptions.EtResourceFoundException;
import com.example.demo.model.Student;
import com.example.demo.repositories.SchoolRepository;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Override
    public List<Student> getStudents(Integer schoolId) {

        return studentRepository.findStudentBySchoolId(schoolId);
    }

    @Override
    public void addNewStudent(Integer schoolId, Student student) throws EtBadRequestException {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if(studentByEmail.isPresent()){
            throw new EtBadRequestException("email taken");
        }
        Student _student = schoolRepository.findById(schoolId).map(
                school ->{
                    student.setSchool(school);
                    return studentRepository.save(student);
                }
        ).orElseThrow(() -> new EtResourceFoundException("Not found Tutorial with id = " + schoolId));


    }

    @Override
    public void deleteStudent(Integer schoolId, Integer studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new EtResourceFoundException("student with id" + studentId +
                    "does not exists");
        };

        studentRepository.deleteById(studentId);
    }

    @Override
    @Transactional
    public Student updateStudent(Integer schoolId, Integer studentId, String name, String email) throws EtBadRequestException {

        Student student = studentRepository.findBySchoolIdAndStudentId(schoolId, studentId).orElseThrow(() -> new EtResourceFoundException("student with id" + studentId + "does not exist"));

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOption = studentRepository.findStudentByEmail(email);
            if(studentOption.isPresent()){
                throw new EtBadRequestException("email taken");
            }
            student.setEmail(email);
        }
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Integer schoolId, Integer studentId) {
        return studentRepository.findBySchoolIdAndStudentId(schoolId, studentId).orElseThrow(() -> new EtResourceFoundException("student with id" + studentId +
                "does not exists"));
    }
}
