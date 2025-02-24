package raisetech.StudentManagement.controller.converter;

import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;

import raisetech.StudentManagement.data.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentConverter {
    public List<StudentDetail> convertStudentDetails(List<Student> Students,
        List<StudentsCourses> studentsCourses) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        Students.forEach(student -> {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<StudentsCourses> convertStudentCourses = studentsCourses.stream()
                    .filter(studentCourses -> student.getId().equals(studentCourses.getStudentId()))
                    .collect(Collectors.toList());
            studentDetail.setStudentsCourses(convertStudentCourses);
            studentDetails.add(studentDetail);
        });
        return studentDetails;
    }
}
