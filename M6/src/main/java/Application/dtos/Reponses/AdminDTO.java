package Application.dtos.Reponses;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AdminDTO {
    
    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Last name is mandatory")
    String lastName;
}
