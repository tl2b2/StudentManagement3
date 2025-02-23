package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.student;

import java.util.List;

/**
 * 受講生情報を扱うリポジトリ
 * 全件検索、単一条件検索、コース情報の検索が行えるクラス
 */
@Mapper
public interface StudentRepository {

    /**
     * 全件検索
     * @return　全件検索した、受講生一覧
     */
    @Select("SELECT * FROM students")
    List<student> search();

    @Select("SELECT * FROM student_couses")
    List<StudentsCourses> searchStudentsCourses();

    @Insert("INSERT INTO students(name, kana_name, nickname, email, area, age, sex, remark, isDeleted)"
            + "VALUES(#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(student student);
}
