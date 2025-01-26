package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private StudentService service;
    private StudentConverter converter;
    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        List<student> students = service.searchStudentList();
        List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

        List<StudentDetail> studentDetails = convertStudentDetails(students, studentsCourses);
        return studentDetails;
    }

    private static List<StudentDetail> convertStudentDetails(List<student> students, List<StudentsCourses> studentsCourses) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        students.forEach(student -> {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<StudentsCourses> convertStudentCourses = studentsCourses.stream()
                    .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
                    .collect(Collectors.toList());

            studentDetail.setStudentsCourses(convertStudentCourses);
            studentDetails.add(studentDetail);
        });
        return studentDetails;
    }

    @GetMapping("/studentCourseList")
    public List<StudentsCourses> getStudentsCourseList(){
        return service.searchStudentsCourseList();
    }

    @GetMapping("/studentList30s")
    public List<StudentDetail> getStudentList30s(){
        List<student> students = service.searchStudentList();
        List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

        List<student> students30Plus = students.stream()
                .filter(student -> student.getAge() >= 30) // 年齢が30以上の学生をフィルタリング
                .collect(Collectors.toList());
        List<StudentDetail> studentDetails = convertStudentDetails(students30Plus, studentsCourses);
        return studentDetails;
    }

    @GetMapping("/studentCourseListJava")
    public List<StudentDetail> getStudentListJava(){
        List<student> students = service.searchStudentList();
        List<StudentsCourses> studentCourses =service.searchStudentsCourseList();

        List<StudentsCourses> student_couses = studentCourses.stream()
            .filter(StudentsCourses -> "JAVA".equals(StudentsCourses.getCourseName()))
            .collect(Collectors.toList());

        List<StudentDetail> studentDetails = convertStudentDetails (students,student_couses);
        return studentDetails;
        }

    /*@GetMapping("/newStudent")
    public String newStudent (Model model){

    }

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail){
        return "";
    }*/

}
