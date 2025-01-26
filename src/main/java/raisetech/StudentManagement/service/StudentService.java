package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.student;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }

    public List<student> searchStudentList() {
        //検索処理
        return repository.search();
    }
    public List<StudentsCourses> searchStudentsCourseList(){
        return repository.searchStudentsCourses();

    }
    /*public List<student> searchStudentList30s(){
    return repository.searchStudentList30s();
     */
}
