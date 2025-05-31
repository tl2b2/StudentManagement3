package raisetech.StudentManagement.controller.converter;

import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.StudentCourse;
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
     * @param studentList 受講生一覧
     * @param studentCourseList　受講生コース情報のリスト
     * @return　受講生詳細情報のリスト
     */
    public List<StudentDetail> convertStudentDetails(List<Student> studentList,
        List<StudentCourse> studentCourseList) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        studentList.forEach(student -> {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
                    .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
                    .collect(Collectors.toList());
            studentDetail.setStudentCourseList(convertStudentCourseList);
            studentDetails.add(studentDetail);
        });
        return studentDetails;
    }
}
