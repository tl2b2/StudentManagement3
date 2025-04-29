package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 受講生情報を取り扱うサービスクラス
 * 受講生の検索、登録・更新処理を行う
 */
@Service
public class StudentService {

    private StudentRepository repository;
    private StudentDetail student;
    private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository, StudentConverter converter){
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * 受講生一覧
     *
     * @return　受講生一覧（全件）
     */
    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();
        return converter.convertStudentDetails(studentList, studentsCoursesList);
    }
    public List<StudentsCourses> searchStudentsCourseList(){
        return repository.searchStudentsCoursesList();
    }

    /**
     * 受講生検索
     * IDに紐づいた受講生の情報を取得できる。その受講生に紐づいた受講生コース情報も取得し、検索する
     * @param id　受講生ID
     * @return　受講生
     */
    public StudentDetail searchStudent(String id){
        Student student = repository.searchStudent(id);
        List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());
        return new StudentDetail(student, studentsCourses);
    }

    private List<StudentDetail> convertStudentDetails(
            List<Student> students30Pluses, List<StudentsCourses> studentsCourses) {
        return new ArrayList<>();
    }

    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());

        for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
             studentsCourse.setStudentId(studentDetail.getStudent().getId());
             studentsCourse.setCourseStartAt(LocalDateTime.now());
             studentsCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
             repository.registerStudentsCourses(studentsCourse);
        }

        return studentDetail;
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());

        for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
            studentsCourse.setStudentId(studentDetail.getStudent().getId());
            repository.updateStudentsCourses(studentsCourse);
        }
    }
}
