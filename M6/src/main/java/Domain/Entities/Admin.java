package Domain.Entities;
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
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", length = 100, nullable = false)
    String name;

    @Column(name = "last_name", length = 100, nullable = false)
    String lastName;
 
    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "role")
    String role;
}
