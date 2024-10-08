package Infrastructure.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import Domain.Entities.Palet;

public interface PaletRepository extends JpaRepository <Palet, Long> {
    
}
