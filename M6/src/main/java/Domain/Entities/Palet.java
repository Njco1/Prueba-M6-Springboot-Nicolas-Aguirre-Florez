package Domain.Entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.*;

import Domain.Enums.StatePaletEnum;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@Table(name = "Palet")
public class Palet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Max(500)
    @Column(name = "Max weight", nullable = false)
    int maxWeight;
    
    @Column(name = "state", length = 50, nullable = false)
    StatePaletEnum state;

}
