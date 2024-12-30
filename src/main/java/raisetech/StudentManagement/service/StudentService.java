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
        return repository.search();

        //年齢30代の人のみを抽出する

    }
    public List<StudentsCourses> searchStudentsCourseList(){
        return repository.searchStudentsCourses();

    }
    //
}
