package InitialProyect.InitialProyect.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = true)
    private LocalDate StartDate;

    @Column(nullable = true)
    private LocalDate EndDate;

    @Column(nullable = true)
    private LocalTime StartHour;

    @Column(nullable = true)
    private LocalTime  EndHour;

    private Long DocumentNumber;

    @Column(nullable = true)
    private String CreatedByUserId;

}
