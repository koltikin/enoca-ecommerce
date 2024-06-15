package com.enoca.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$") // Minimum 6 character at least one digit and one uppercase latter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passWord;

    private CartDto cart;
    private List<OrderDto> orders;
}
