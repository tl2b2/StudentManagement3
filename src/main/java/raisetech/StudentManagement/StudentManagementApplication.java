package raisetech.StudentManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class StudentManagementApplication {

	private String name = "Enami Kouji";

	public static void main(String[] args) {

		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping("/name")
	public String getName() {
		return name;
	}

}
