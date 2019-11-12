package by.pankov.simple.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRq {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Positive
    @NotNull
    private Integer age;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
