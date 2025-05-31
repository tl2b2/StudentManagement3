package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
        return converter.convertStudentDetails(studentList, studentCourseList);
    }
    public List<StudentCourse> searchStudentsCourseList(){
        return repository.searchStudentCourseList();
    }

    /**
     * 受講生検索
     * IDに紐づいた受講生の情報を取得できる。その受講生に紐づいた受講生コース情報も取得し、検索する
     * @param id　受講生ID
     * @return　受講生
     */
    public StudentDetail searchStudent(String id){
        Student student = repository.searchStudent(id);
        List<StudentCourse> studentCourse = repository.searchStudentsCourses(student.getId());
        return new StudentDetail(student, studentCourse);
    }

    private List<StudentDetail> convertStudentDetails(
            List<Student> students30Pluses, List<StudentCourse> studentsCourses) {
        return new ArrayList<>();
    }

    /**
     * 受講生詳細の登録、受講生とコース情報は個別に登録される
     * コース情報と受講生情報を紐づけるコースID
     * @param studentDetail　受講生詳細
     * @return　登録情報を付与した受講生詳細
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        repository.registerStudent(student);
        studentDetail.getStudentCourseList().forEach(studentCourse -> {
            initStudentsCourse(studentCourse, student);
            repository.registerStudentCourse(studentCourse);
        });

        return studentDetail;
    }

    /**
     * 受講生コース情報登録の際の初期情報
     * @param studentCourse　受講生コース情報
     * @param student　受講生
     */
    private void initStudentsCourse(StudentCourse studentCourse, Student student) {
        LocalDateTime now = LocalDateTime.now();

        studentCourse.setStudentId(student.getId());
        studentCourse.setCourseStartAt(now);
        studentCourse.setCourseEndAt(now.plusYears(1));
    }

    /**
     * 受講生詳細の更新
     * 受講生情報、コース情報の更新
     * @param studentDetail　受講生詳細
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());

        for (StudentCourse studentCourse : studentDetail.getStudentCourseList()) {
            studentCourse.setStudentId(studentDetail.getStudent().getId());
            repository.updateStudentCourse(studentCourse);
        }
    }
}
