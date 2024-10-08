package Application.dtos.Requests;

import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    
    @NotBlank(message = "Email is mandatory¿")
    String email;

    @NotBlank(message = "Password is mandatory")
    String password;
}
