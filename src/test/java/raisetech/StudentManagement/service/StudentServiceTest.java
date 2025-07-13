package raisetech.StudentManagement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository repository;
    @Mock
    private StudentConverter converter;

    private StudentService sut;

    @BeforeEach
    void before(){
        sut = new StudentService(repository,converter);
    }

    @Test
    void 受講生一覧検索が機能すること(){
        StudentService sut = new StudentService(repository, converter);
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        when(repository.search()).thenReturn(studentList);
        when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

        List<StudentDetail> actual = sut.searchStudentList();

        verify(repository,times(1)).search();
        verify(repository,times(1)).searchStudentCourseList();
        verify(converter,times(1)).convertStudentDetails(studentList,studentCourseList);

    }

    @Test
    void 受講生詳細検索が機能すること() {

        String id = "1";
        Student student = new Student();
        student.setId(id);
        when(repository.searchStudent(id)).thenReturn(student);
        when(repository.searchStudentsCourses(id)).thenReturn(new ArrayList<>());

        StudentDetail expected = new StudentDetail(student, new ArrayList<>());


        StudentDetail actual = sut.searchStudent(id);

        verify(repository, times(1)).searchStudent(id);
        verify(repository, times(1)).searchStudentsCourses(id);
        Assertions.assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
    }

    @Test
    void 受講生登録が機能すること() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        List<StudentCourse> studentCourseList = List.of(studentCourse);
        StudentDetail studentDetail = new StudentDetail(student,studentCourseList);

        sut.registerStudent(studentDetail);

        verify(repository, times(1)).registerStudent(student);
        verify(repository, times(1)).registerStudentCourse(studentCourse);
    }

    @Test
    void 受講生詳細の更新が機能すること() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        List<StudentCourse> studentCourseList = List.of(studentCourse);
        StudentDetail studentDetail = new StudentDetail(student,studentCourseList);

        sut.updateStudent(studentDetail);

        verify(repository, times(1)).updateStudent(student);
        verify(repository, times(1)).updateStudentCourse(studentCourse);
    }

}