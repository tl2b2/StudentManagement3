package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.data.StudentCourse;
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
    List<Student> search();

    /**
     * 受講生情報の検索
     *
     * @param id　受講生ID
     * @return　受講生
     */
    Student searchStudent(String id);

    /**
     * 受講生コース情報の全件検索
     * @return　受講生コース情報（全件）
     */

    List<StudentCourse> searchStudentCourseList();

    /**
     * 受講生IDに紐づく受講生コース情報の検索
     *
     * @param studentId　受講生ID
     * @return　受講生IDに紐づく受講生コース情報
     */

    List<StudentCourse> searchStudentsCourses(String studentId);

    /**
     * 受講生情報の新規登録
     * IDは自動採番
     * @param student　受講生情報
     */

    void registerStudent(Student student);

    /**
     * 受講生コース情報の新規登録
     * IDは自動採番
     * @param studentsCourse　受講生コース情報
     */

    void registerStudentCourse(StudentCourse studentsCourse);

    /**
     * 受講生情報の更新
     * @param student　受講生
     */

    void updateStudent(Student student);

    /**
     * 受講生コース情報の更新
     * @param studentCourse　受講生コース情報
     */

    void updateStudentCourse(StudentCourse studentCourse);

}
