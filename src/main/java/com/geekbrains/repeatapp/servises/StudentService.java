package com.geekbrains.repeatapp.servises;

import com.geekbrains.repeatapp.model.Student;
import com.geekbrains.repeatapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    public void save(Student student){
        studentRepository.save(student);
    }

    public Student findById(Long id) {
        return studentRepository.findById(id);
    }

    public void delete(Long id) {
        studentRepository.delete(id);
    }

    public void decrementScore(Long id) {
        Student student = findById(id);
        student.setScore(student.getScore()-1);
    }

    public void incrementScore(Long id) {
        Student student = findById(id);
        student.setScore(student.getScore()+1);
    }
}
