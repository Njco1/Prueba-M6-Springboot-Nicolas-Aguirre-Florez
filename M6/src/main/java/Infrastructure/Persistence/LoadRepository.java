package Infrastructure.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import Domain.Entities.Load;

public interface LoadRepository extends JpaRepository<Load, Long> {

}
