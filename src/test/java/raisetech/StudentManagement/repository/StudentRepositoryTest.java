package raisetech.StudentManagement.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository sut;


    @Test
    void 受講生の全件検索が行えること(){
        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(5);
    }

    @Test
     void IDから受講生情報の個別検索が行えること(){
        Student actual = sut.searchStudent("1001");
        assertThat(actual.getName()).isEqualTo("山田太郎");
    }

    @Test
    void 受講生コースの全件検索が行えること(){
        List<StudentCourse> actual = sut.searchStudentCourseList();
        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(8);
    }

    @Test
    void IDから受講生コース情報の個別検索が行えること(){
        List<StudentCourse> actual = sut.searchStudentsCourses("1001") ;
        assertThat(actual).isNotEmpty();
        assertThat(actual.get(0).getCourseName()).isEqualTo("簿記検定");
    }

    @Test
    void 受講生の新規登録が行えること(){
        Student student = new Student();

        student.setName("江並公史");
        student.setKanaName("エナミコウジ");
        student.setNickname("エナミ");
        student.setEmail("test@example.com");
        student.setArea("奈良県");
        student.setAge(36);
        student.setSex("男性");
        student.setRemark("");
        student.setDeleted(false);

        sut.registerStudent(student);

        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(6);
    }

    @Test
    void 受講生コース情報の新規登録が行えること(){
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId("1");
        studentCourse.setStudentId("2");
        studentCourse.setCourseName("簿記検定");
        studentCourse.setCourseStartAt(LocalDateTime.now());
        studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentsCourses("1001");

        assertThat(actual).extracting(StudentCourse::getCourseName)
                          .contains("簿記検定");
    }

    @Test
    void 受講生情報の更新が行えること(){
        Student student = sut.findById("1001");
        Assertions.assertNotNull(student);

        String newNickName = "エナコウ";
        student.setNickname(newNickName);
        student.setRemark("Remark更新");

        sut.updateStudent(student);

        Student updated = sut.findById("1001");
        assertThat(updated.getNickname()).isEqualTo("エナコウ");
        assertThat(updated.getRemark()).isEqualTo("Remark更新");
    }
}