package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class student {
    private String id;
    private String name;
    private String kanaName;
    private String nickname;
    private String email;
    private String area;
    private int age;
    private String sex;
}