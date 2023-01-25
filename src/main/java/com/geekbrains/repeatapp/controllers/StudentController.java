package com.geekbrains.repeatapp.controllers;

import com.geekbrains.repeatapp.model.Student;
import com.geekbrains.repeatapp.servises.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController {
    // root = http://localhost:8189/app
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/show_all")
    public String showStudentsPage(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students";
    }

    @GetMapping("/show/{id}")
    public String showStudentPage(Model model, @PathVariable Long id) {
        model.addAttribute("student", studentService.findById(id));
        return "student_info";
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "create_student";
    }

//    @PostMapping("/create")
//    public String saveStudent(@RequestParam Long id, @RequestParam String name, @RequestParam int score) {
//        Student student = new Student(id, name, score);
//        studentService.save(student);
//        return "redirect:/show_all";
//    }

    @PostMapping("/create")
    public String saveStudent(@ModelAttribute Student student) {
        // Если в RequestParam'ах будут поля Student'а - id, name и score, то Spring сам вытащит эти RequestParam'ы,
        // создаст пустой объект Student, затем возьмет каждый из RequestParam'ов, и через сеттеры наполнит объект Student.
        studentService.save(student);
        return "redirect:/students/show_all";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/students/show_all";
    }

    @GetMapping("/decrement_score/{id}")
    public String decrementScore(@PathVariable Long id){
        studentService.decrementScore(id);
        return "redirect:/students/show_all";
    }

    @GetMapping("/increment_score/{id}")
    public String incrementScore(@PathVariable Long id){
        studentService.incrementScore(id);
        return "redirect:/students/show_all";
    }

    @GetMapping("/json/{id}")
    @ResponseBody
    public Student findById(@PathVariable Long id){
        return studentService.findById(id);
    }

    @PostMapping("/json")
    @ResponseBody
    public void saveJsonStudent(@RequestBody Student student){
        studentService.save(student);
    }

    @GetMapping("/text")
    public String getText(Model model) throws IOException {
//        String text = Files.lines(Paths.get("C:\\Users\\Ivan\\Desktop\\text.txt")).collect(Collectors.joining("\n"));
        List<String> text = Files.lines(Paths.get("C:\\Users\\Ivan\\Desktop\\text.txt")).collect(Collectors.toList());
        model.addAttribute("text", text);
        return "text";
    }
}
