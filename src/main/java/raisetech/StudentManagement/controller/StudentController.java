package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

import java.util.Arrays;
import java.util.List;

@Validated
@RestController
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    /**
     * 受講生一覧
     *
     * @return　受講生一覧検索（全件）
     */
    @Operation(summary = "一覧検索", description = "受講生一覧検索（全件）")
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
    public StudentDetail getStudent(
            @PathVariable @NotBlank@Pattern(regexp = "\\d+$") String id) {
        return service.searchStudent(id);
    }

    /**
     * 受講生詳細の登録
     * @param studentDetail　受講生詳細
     * @return　実行結果
     */
    @Operation(summary = "受講生登録", description = "受講生登録")
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(
            @RequestBody StudentDetail studentDetail){
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

    @ExceptionHandler(TestException.class)
    public ResponseEntity<String> handleTestException(TestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
