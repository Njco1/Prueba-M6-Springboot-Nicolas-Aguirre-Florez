package Infrastructure.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import Domain.Entities.Admin;

public interface AdminRepository extends JpaRepository <Admin, Long>{
    
}
