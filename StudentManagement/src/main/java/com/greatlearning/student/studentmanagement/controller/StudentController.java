package com.greatlearning.student.studentmanagement.controller;


import java.security.Principal;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.greatlearning.student.studentmanagement.model.Student;
import com.greatlearning.student.studentmanagement.services.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@RequestMapping("/list")
	public String getAllStudents(Model model) {
		List<Student> students = studentService.findAll();
		model.addAttribute("Students", students);
		return "list-Students";
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormforAdd(Model model) {
		Student student = new Student();
		model.addAttribute("Student", student);
		return "Student-form";
	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormforUpdate(@RequestParam("studentId")int id,Model model) {
		Student student = studentService.findById(id);
		model.addAttribute("Student", student);
		return "Student-form";
	}
	
	/*
	 * @RequestMapping("/save") public String save(@Valid @ModelAttribute("Student")
	 * Student student, BindingResult bindingResult) {
	 * System.out.println(bindingResult.getErrorCount()); student =
	 * studentService.save(student); return "redirect:/students/list"; }
	 */

	@RequestMapping("/save")
	public String save(@RequestParam("id")int id,@RequestParam("firstName")String firstName,@RequestParam("lastName")String 
			lastName,@RequestParam("course")String course,@RequestParam("country")String country) {
		Student student;
		if (id!=0) {
			student=studentService.findById(id);
			student.setFirstName(firstName);
			student.setLastName(lastName);
			student.setCourse(course);
			student.setCountry(country);
		}
		else {
			student=new Student();
			student.setFirstName(firstName);
			student.setLastName(lastName);
			student.setCourse(course);
			student.setCountry(country);
		}
		student = studentService.save(student);
		return "redirect:/students/list";
	}
	
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("studentId") int id) {
		studentService.deleteById(id);
		return "redirect:/students/list";
	}
	
	@RequestMapping("/403")
	public ModelAndView handleAccessDenied(Principal user) {
		ModelAndView model = new ModelAndView();
		if(user != null)
			model.addObject("msg", "Hi "+user.getName()+", you don't have access to perform this operation" );
		else
			model.addObject("msg", "Hi, you don't have access to perform this operation" );
		model.setViewName("403");
		return model;
	}

}
