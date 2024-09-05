package InitialProyect.InitialProyect.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    public Long DocumentNumber;
    public LocalDateTime AccessDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    public AccessType AccessType;
}
