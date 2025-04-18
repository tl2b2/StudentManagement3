package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/studentList")
    public String getStudentList(Model model){
            List<Student> Students = service.searchStudentList();
        List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();
                model.addAttribute("studentList", converter.convertStudentDetails(Students,studentsCourses));
        return "studentList";
    }

    /*@GetMapping("/studentCourseList")
    public List<StudentsCourses> getStudentsCourseList(){
        return service.searchStudentsCourseList();
    }*/

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }

    /*@GetMapping("/student/{id}")
    public String getStudent(@PathVariable String id, Model model) {
        StudentDetail studentDetail = service.searchStudent(id);
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";
    }*/
    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable String id, Model model) {
        StudentDetail studentDetail = service.searchStudent(id);
        if (studentDetail == null || studentDetail.getStudentsCourses() == null) {
            model.addAttribute("studentsCourses", new ArrayList<>()); // 空のリストを設定
        } else {
            model.addAttribute("studentsCourses", studentDetail.getStudentsCourses());
        }
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent"; // "updateStudent" テンプレートに遷移
    }




    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result){
       if(result.hasErrors()){
           return "registerStudent";
       }
       service.registerStudent(studentDetail);
        return "redirect:/studentList";
    }

    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result){
        if(result.hasErrors()){
            return "updateStudent";
        }
        service.updateStudent(studentDetail);
        return "redirect:/studentList";
    }

}
