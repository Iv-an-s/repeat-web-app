package com.geekbrains.repeatapp.dtos;

import com.geekbrains.repeatapp.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserRegDto {
    private Long id;
    @NotNull
    @Length(min = 2, max = 64, message = "Длина имени пользователя должна составлять от 2 до 64 символов")
    private String username;
    @NotNull
    @Min(value = 3, message = "Длина пароля не может быть менее 3 символов")
    private String password;
    @NotNull
    private String controlPassword;
    @NotNull
    private String email;

    public UserRegDto(User user){
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        controlPassword = user.getPassword();
        email = user.getEmail();
    }

    public UserRegDto(String username, String password, String controlPassword, String email) {
        this.username = username;
        this.password = password;
        this.controlPassword = controlPassword;
        this.email = email;
    }
}
