package InitialProyect.InitialProyect.Repository;

import InitialProyect.InitialProyect.Models.AccessControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface JPAAccessControl extends JpaRepository<AccessControl, Long> {
    @Query("SELECT CASE WHEN COUNT(ac) > 0 THEN true ELSE false END " +
            "FROM AccessControl ac " +
            "WHERE (:startDate IS NULL OR (ac.StartDate IS NULL OR ac.StartDate <= :startDate) AND (ac.EndDate IS NULL OR ac.EndDate >= :startDate)) " +
            "AND (:startHour IS NULL OR (ac.StartHour IS NULL OR ac.StartHour <= :startHour) AND (ac.EndHour IS NULL OR ac.EndHour >= :startHour)) " +
            "AND ac.DocumentNumber = :documentNumber")
    boolean hasInvitation(@Param("startDate") LocalDate startDate,
                          @Param("startHour") LocalTime startHour,
                          @Param("documentNumber") Long documentNumber);

}