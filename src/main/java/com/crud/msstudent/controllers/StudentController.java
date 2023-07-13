package com.crud.msstudent.controllers;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.crud.msstudent.exceptions.StudentNotFoundException;
import com.crud.msstudent.models.Student;
import com.crud.msstudent.services.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {
    StudentService studentservice;
    @Autowired
    public StudentController(StudentService studentservice) {
        this.studentservice = studentservice;
    }
    @PostMapping(value="/students")                                   //1st api postapi
    public Student addStudent(@Valid @RequestBody Student std) {
        return studentservice.save(std);
    }

    @RequestMapping(value="/allstudents",method = RequestMethod.GET)
    public List<Student> getAllStudents(){
        return studentservice.getAllStudents();
    }           
    @GetMapping(value="/students/{id}")
    public Student getStudentById(@PathVariable("id") @Min(1) int id) {
        Student std = studentservice.findById(id)
                                    .orElseThrow(()->new StudentNotFoundException("Student with "+id+" is Not Found!"));
        return std;
    }
    @PutMapping(value="/students/{id}")
    public Student updateStudent(@PathVariable("id") @Min(1) int id, @Valid @RequestBody Student newstd) {
        Student stdu = studentservice.findById(id).orElseThrow(()->new StudentNotFoundException("Student with "+id+" is Not Found!"));
        stdu.setFirstname(newstd.getFirstname());
        stdu.setLastname(newstd.getLastname());
        stdu.setEmail(newstd.getEmail());
        return studentservice.save(stdu); 
    }           
    @DeleteMapping(value="/students/{id}")
    public String deleteStudent(@PathVariable("id") @Min(1) int id) {
        Student std = studentservice.findById(id)
                                     .orElseThrow(()->new StudentNotFoundException("Student with "+id+" is Not Found!"));
        studentservice.deleteById(std.getId());
        return "Student with ID :"+id+" is deleted";            
    }

    @GetMapping(value = "/indexapi")
    public String index(){
        return "Succesfully Application is running";
    }
}
