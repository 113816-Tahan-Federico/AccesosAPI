package InitialProyect.InitialProyect.DTOs.Invitation;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class GenerateInvitation {
    private LocalDate StartDate;

    private LocalDate EndDate;

    private LocalTime StartHour;

    private LocalTime  EndHour;

    private Long DocumentNumber;
}
