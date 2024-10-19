package ru.pavelbev.springSecurityAppByPavelBev.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotEmpty
    private String name;

    @Size(min = 2, message = "password min 2")
    private String password;
    private String confirmPassword;

    private String roles;
    private String age;
}
