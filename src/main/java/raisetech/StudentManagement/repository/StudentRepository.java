package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.student;

import java.util.List;

@Mapper
public interface StudentRepository {
    @Select("SELECT * FROM students")
    List<student> search();

    @Select("SELECT * FROM student_couses")
    List<StudentsCourses> searchStudentsCourses();
}
