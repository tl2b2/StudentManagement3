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
    /**
     * 受講生に紐づく受講生コース情報のマッピング
     * 受講生コース情報が複数存在するため、ループさせて組み上げる
     * @param Students 受講生一覧
     * @param studentsCourses　受講生コース情報のリスト
     * @return　受講生詳細情報のリスト
     */
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
