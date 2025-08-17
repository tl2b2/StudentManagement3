package raisetech.StudentManagement.controller.converter;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentConverterTest {

    private StudentConverter sut;

    @BeforeEach
    void before(){
        sut = new StudentConverter();
    }

    @Test
    void 受講生のリストと受講生コースのリストを渡して受講生の詳細リストを作成できること(){
        Student student = new Student();
        student.setId("1");
        student.setName("江並公史");
        student.setKanaName("エナミコウジ");
        student.setNickname("エナミ");
        student.setEmail("test@example.com");
        student.setArea("奈良県");
        student.setAge(36);
        student.setSex("男性");
        student.setRemark("");
        student.setDeleted(false);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId("1");
        studentCourse.setStudentId("1");
        studentCourse.setCourseName("Java");
        studentCourse.setCourseStartAt(LocalDateTime.now());
        studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

        List<Student> studentList = List.of(student);
        List<StudentCourse> studentCourseList = List.of(studentCourse);
        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
    }

    @Test
    void 受講生のリストと受講生コースのリストを渡し紐づかない受講生コース情報を除外できること() {
        Student student = new Student();
        student.setId("1");
        student.setName("江並公史");
        student.setKanaName("エナミコウジ");
        student.setNickname("エナミ");
        student.setEmail("test@example.com");
        student.setArea("奈良県");
        student.setAge(36);
        student.setSex("男性");
        student.setRemark("");
        student.setDeleted(false);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId("1");
        studentCourse.setStudentId("2");
        studentCourse.setCourseName("Java");
        studentCourse.setCourseStartAt(LocalDateTime.now());
        studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

        List<Student> studentList = List.of(student);
        List<StudentCourse> studentCourseList = List.of(studentCourse);
        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentCourseList()).isEmpty();
    }

}