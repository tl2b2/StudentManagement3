package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository repository;
    private StudentDetail student;

    @Autowired
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }

    public List<student> searchStudentList() {
        return repository.search();
    }
    public List<StudentsCourses> searchStudentsCourseList(){
        return repository.searchStudentsCourses();
    }

    private List<StudentDetail> convertStudentDetails(
            List<raisetech.StudentManagement.data.student> students30Plus, List<StudentsCourses> studentsCourses) {
        return new ArrayList<>();
    }

    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());
    }

    public List<StudentDetail> getStudentList30s(){
        List<student> students = searchStudentList();
        List<StudentsCourses> studentsCourses = searchStudentsCourseList();

        List<student> students30Plus = students.stream()
                .filter(student -> student.getAge() >= 30)
                .collect(Collectors.toList());
        return convertStudentDetails(students30Plus, studentsCourses);
    }

      public List<StudentDetail> getStudentListJava(){
        List<student> students = searchStudentList();
        List<StudentsCourses> studentCourses =searchStudentsCourseList();

        List<StudentsCourses> student_couses = studentCourses.stream()
            .filter(StudentsCourses -> "JAVA".equals(StudentsCourses.getCourseName()))
            .collect(Collectors.toList());

        List<StudentDetail> studentDetails = convertStudentDetails (students,student_couses);
        return studentDetails;
        }
}
