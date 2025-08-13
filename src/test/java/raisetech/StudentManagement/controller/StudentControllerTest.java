package raisetech.StudentManagement.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;
import java.util.Set;

/*import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;*/
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private StudentConverter converter;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 受講生詳細の一覧検索空のリストが返ってくること() throws Exception {
        when(service.searchStudentList()).thenReturn(List.of(new StudentDetail()));

        mockMvc.perform(get("/studentList"))
                .andExpect(status().isOk());

        verify(service, times(1)).searchStudentList();
    }

    @Test
    void 受講生詳細のIDに数字以外を用いた際の例外テスト() {
        Student student = new Student();
        student.setId("テスト");
        student.setName("江並公史");
        student.setKanaName("エナミコウジ");
        student.setNickname("エナミ");
        student.setEmail("test@example.com");
        student.setArea("奈良県");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("数字のみ入力");
    }

}