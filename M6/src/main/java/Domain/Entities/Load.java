package Domain.Entities;
import Domain.Enums.StateLoadEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@Table(name = "Load")
public class Load {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "weight", nullable = false)
    int weight;

    @Column(name = "dimensions", length = 100, nullable = false)
    String dimensions;

    @Column(name = "state", length = 50, nullable = false)
    StateLoadEnum state;

}
