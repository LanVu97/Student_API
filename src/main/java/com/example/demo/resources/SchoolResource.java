package com.example.demo.resources;

import com.example.demo.exceptions.EtBadRequestException;
import com.example.demo.model.School;
import com.example.demo.model.Student;
import com.example.demo.services.SchoolService;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/schools")
public class SchoolResource {
    @Autowired
    SchoolService schoolService;

    @Autowired
    StudentService studentService;

    @GetMapping("")
    public ResponseEntity<List<School>> getSchools(){
        List<School> schools = schoolService.getSchools();
        return new ResponseEntity<>(schools, HttpStatus.OK);
    }

    @GetMapping("{schoolId}")
    public ResponseEntity<School> getSchoolById(@PathVariable("schoolId") Integer schoolId){
        School school = schoolService.getSchoolById(schoolId);
        return new ResponseEntity<>(school, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map< String, Boolean>> registerSchool(@RequestBody School school) throws EtBadRequestException {
        schoolService.addNewSchool(school);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping(path = "{schoolId}")
    public ResponseEntity<School> updateSchool(@PathVariable("schoolId") Integer schoolId, @RequestBody School school) throws EtBadRequestException {
        String name = school.getName();
        String description =  school.getDescription();
        School schoolUpdate = schoolService.updateSchool(schoolId, name, description);
        return new ResponseEntity<>(school, HttpStatus.OK);
    }

    @DeleteMapping(path = "{schoolId}")
    public ResponseEntity<Map<String, Boolean>> deleteSchool(@PathVariable("schoolId") Integer id){
        schoolService.deleteSchoolWithAllStudent(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }



}
