package com.greatlearning.student.studentmanagement.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greatlearning.student.studentmanagement.model.Student;

@Service
public interface StudentService {
	List<Student> findAll();
	Student save(Student student);
	Student findById(int id);
	Student deleteById(int id);
}
