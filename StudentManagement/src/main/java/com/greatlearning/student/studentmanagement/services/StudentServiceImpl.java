package com.greatlearning.student.studentmanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.student.studentmanagement.model.Student;
import com.greatlearning.student.studentmanagement.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student save(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student findById(int id) {
		return studentRepository.findById(id).get();
	}

	@Override
	public Student deleteById(int id) {
		Student student = findById(id);
		studentRepository.delete(student);
		return student;
	}

}
