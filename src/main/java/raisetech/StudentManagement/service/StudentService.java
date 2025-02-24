package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
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

    public List<Student> searchStudentList() {
        return repository.search();
    }
    public List<StudentsCourses> searchStudentsCourseList(){
        return repository.searchStudentsCourses();
    }

    private List<StudentDetail> convertStudentDetails(
            List<Student> students30Pluses, List<StudentsCourses> studentsCourses) {
        return new ArrayList<>();
    }

    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());

        for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
             studentsCourse.setStudentId(studentDetail.getStudent().getId());
             studentsCourse.setCourseStartAt(LocalDateTime.now());
             studentsCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
             repository.registerStudentsCourses(studentsCourse);
        }

    }

    public List<StudentDetail> getStudentList30s(){
        List<Student> Students = searchStudentList();
        List<StudentsCourses> studentsCourses = searchStudentsCourseList();

        List<Student> students30Pluses = Students.stream()
                .filter(student -> student.getAge() >= 30)
                .collect(Collectors.toList());
        return convertStudentDetails(students30Pluses, studentsCourses);
    }

      public List<StudentDetail> getStudentListJava(){
        List<Student> Students = searchStudentList();
        List<StudentsCourses> studentCourses =searchStudentsCourseList();

        List<StudentsCourses> student_couses = studentCourses.stream()
            .filter(StudentsCourses -> "JAVA".equals(StudentsCourses.getCourseName()))
            .collect(Collectors.toList());

        List<StudentDetail> studentDetails = convertStudentDetails (Students,student_couses);
        return studentDetails;
        }
}
