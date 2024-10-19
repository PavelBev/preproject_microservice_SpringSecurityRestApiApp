package ru.pavelbev.springSecurityAppByPavelBev.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The first line in your passport. Come on, I believe in you!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @NotEmpty(message = "The password field cannot be empty")
    @Size(min = 4, message = "The password must contain at least 4 characters")
    private String password;

    @Min(value = 0,message = "Age cannot be less than zero. Dalbaeb!")
    @Max(value = 135, message = "Age cannot exceed 135 years. Ti sho syka, mumia ?")
    private Integer age;
    private String roles;
    private Date createdAt;
}
