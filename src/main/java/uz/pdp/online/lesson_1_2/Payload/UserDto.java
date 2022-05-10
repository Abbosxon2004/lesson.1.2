package uz.pdp.online.lesson_1_2.Payload;

import lombok.Data;


import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    @NotNull(message = "Email  bo`sh bo`lmasligi kerak")
    private String email;

    @NotNull(message = "Password bo`sh bo`lmasligi kerak")
    private String password;
}
