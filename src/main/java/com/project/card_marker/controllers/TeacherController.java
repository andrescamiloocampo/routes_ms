package com.project.card_marker.controllers;

import com.project.card_marker.dto.AddScheduleRequest;
import com.project.card_marker.models.Schedule;
import com.project.card_marker.models.Teacher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.card_marker.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        super();
        this.teacherRepository = teacherRepository;
    }

    public String index() {
        return "Welcome to Teacher Controller";
    }

    @GetMapping(value="/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping(value="/teachers/{id}")
    public Teacher getTeacherById(@PathVariable String id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.orElse(null);
    }

    @GetMapping(value="/teachers/{id}/schedules")
    public ResponseEntity<List<Schedule>> getTeacherSchedule(@PathVariable String id) {

        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        System.out.println(teacherOptional.isPresent());
        if(teacherOptional.isPresent()){
            Teacher teacher = teacherOptional.get();
            return ResponseEntity.ok().body(teacher.getSchedules());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="/createTeacher")
    Teacher newTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @PutMapping(value="/teachers/{id}/addSchedule")
    public ResponseEntity<String> addSchedule(@PathVariable String id, @RequestBody AddScheduleRequest scheduleRequest) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);

        if(optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            Schedule newSchedule = new Schedule(scheduleRequest.getIn(),scheduleRequest.getOut());
            teacher.getSchedules().add(newSchedule);
            teacherRepository.save(teacher);
            return ResponseEntity.ok("Added schedule for the teacher with the id:"+id);
        }else{
            return ResponseEntity.status(404).body("Id not found");
        }
    }
}
