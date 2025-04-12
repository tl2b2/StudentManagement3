package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.Student;

import java.util.List;

/**
 * 受講生情報を扱うリポジトリ
 * 全件検索、単一条件検索、コース情報の検索が行えるクラス
 */
@Mapper
public interface StudentRepository {


    @Select("SELECT * FROM students WHERE isDeleted = false")
    List<Student> search();

    @Select("SELECT * FROM students WHERE id = #{id}")
    Student searchStudent(String id);

    @Select("SELECT * FROM student_couses")
    List<StudentsCourses> searchStudentsCoursesList();

    @Select("SELECT * FROM student_couses WHERE student_id = #{studentId}")
    List<StudentsCourses> searchStudentsCourses(String studentId);

    @Insert("INSERT INTO students(name, kana_name, nickname, email, area, age, sex, remark, isDeleted)"
            + "VALUES(#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);

    @Insert("INSERT INTO student_couses(student_id, course_name, course_start_at, course_end_at)"
            + "VALUES(#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudentsCourses(StudentsCourses studentsCourses);

    @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, "
          + "email = #{email}, area = #{area}, age = #{age}, sex = #{sex}, remark = #{remark}, isDeleted = #{isDeleted} WHERE id = #{id}")
    void updateStudent(Student student);

    @Update("UPDATE student_couses SET course_name = #{courseName} WHERE id = #{id}")
    void updateStudentsCourses(StudentsCourses studentsCourses);

}
