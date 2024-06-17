package com.enoca.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password should not be blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$"
    ,message = "Password must be at least 6 characters long and contain at least one digit and one uppercase letter")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passWord;
}
