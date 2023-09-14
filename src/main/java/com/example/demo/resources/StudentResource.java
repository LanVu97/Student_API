package com.example.demo.resources;

import com.example.demo.exceptions.EtBadRequestException;
import com.example.demo.model.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/schools/{schoolId}/students")
public class StudentResource {

    @Autowired
    StudentService studentService;

    @GetMapping("")
    public ResponseEntity<List<Student>> getStudents(@PathVariable("schoolId") Integer schoolId){
        List<Student> students = studentService.getStudents(schoolId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("schoolId") Integer schoolId,
            @PathVariable("studentId") Integer studentId){
        Student student = studentService.getStudentById(schoolId,studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map< String, Boolean>> registerStudent(@PathVariable("schoolId") Integer schoolId,@RequestBody Student student) throws EtBadRequestException {
        studentService.addNewStudent(schoolId,student);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable("schoolId") Integer schoolId,@PathVariable("studentId") Integer id){
        studentService.deleteStudent(schoolId, id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<Student>  updateStudent(@PathVariable("schoolId") Integer schoolId,@PathVariable("studentId") Integer studentId, @RequestBody Student student) throws EtBadRequestException {
        String name = student.getName();
        String email =  student.getEmail();
        Student studentUpdate = studentService.updateStudent(schoolId, studentId, name, email);

        return new ResponseEntity<>(studentUpdate, HttpStatus.OK);
    }


}
