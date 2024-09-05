package InitialProyect.InitialProyect.Repository;

import InitialProyect.InitialProyect.Models.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAAccess extends JpaRepository<Access, Long> {
}
