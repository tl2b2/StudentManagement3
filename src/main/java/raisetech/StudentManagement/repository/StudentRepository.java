package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.Student;

import java.util.List;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepository
 *
 */
@Mapper
public interface StudentRepository {


    /**
     * 受講生の全件検索
     *
     * @return　受講生一覧（全件）
     */
    @Select("SELECT * FROM students WHERE isDeleted = false")
    List<Student> search();

    /**
     * 受講生情報の検索
     *
     * @param id　受講生ID
     * @return　受講生
     */
    @Select("SELECT * FROM students WHERE id = #{id}")
    Student searchStudent(String id);

    /**
     * 受講生コース情報の全件検索
     * @return　受講生コース情報（全件）
     */
    @Select("SELECT * FROM student_couses")
    List<StudentsCourses> searchStudentsCoursesList();

    /**
     * 受講生IDに紐づく受講生コース情報の検索
     *
     * @param studentId　受講生ID
     * @return　受講生IDに紐づく受講生コース情報
     */
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
