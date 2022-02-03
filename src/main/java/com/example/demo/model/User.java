package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "id_generator")
    @SequenceGenerator(
            name = "id_generator",
            sequenceName = "id_user_sequence",
            initialValue = 1
    )
    private Long userId;

    @NotBlank(message = "Name mustn't be blank")
    @Size(min = 2, max = 100, message = "Name length should be between 2 and 100")
    private String name;

    @NotBlank(message = "Surname mustn't be blank")
    @Size(min = 2, max = 100, message = "Surname length should be between 2 and 100")
    private String surname;

    @Size(max = 250, message = "Address length should be max 250")
    private String address;

    @Size(max = 100, message = "Email length should be max 250")
    @Email(message = "Email should be valid")
    private String email;
}
