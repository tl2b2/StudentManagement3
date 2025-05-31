package raisetech.StudentManagement.controller;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.Arrays;
import java.util.List;

@RestController
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentCourseList(Arrays.asList(new StudentCourse()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }

    /**
     * 受講生一覧
     *
     * @return　受講生一覧（全件）
     */
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList(){
        return service.searchStudentList();
    }

    /**
     * 受講生検索
     * IDに紐づいた受講生の情報を取得できる
     *
     * @param id　受講生ID
     * @return　受講生情報
     */
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable @Size(min=1,max=4) String id) {
        return service.searchStudent(id);
    }

    /**
     * 受講生詳細の登録
     * @param studentDetail　受講生詳細
     * @return　実行結果
     */
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail){
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
       }

    /**
     * 受講生詳細の更新とキャンセルフラグの更新
     * @param studentDetail　受講生詳細
     * @return　実行結果
     */
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail){
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が完了しました");
    }

}
