package InitialProyect.InitialProyect.Services.Imp;

import InitialProyect.InitialProyect.DTOs.Invitation.GenerateInvitation;
import InitialProyect.InitialProyect.Models.Access;
import InitialProyect.InitialProyect.Models.AccessControl;
import InitialProyect.InitialProyect.Repository.JPAAccess;
import InitialProyect.InitialProyect.Services.IAccessControlService;
import InitialProyect.InitialProyect.Repository.JPAAccessControl;
import InitialProyect.InitialProyect.Services.IIdScannerService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.*;
import java.util.Date;

@Service
public class AccessControlService implements IAccessControlService {

    @Value("${app.timezone}")
    private String timezone;

    @Autowired
    private JPAAccessControl _accessControlRepository;

    @Autowired
    private JPAAccess _accessRepository;

    @Autowired
    private IIdScannerService _IDscannerService;

    @Override
    public void GenerateInvitation(GenerateInvitation invitation) {

        AccessControl accessControl = new AccessControl();
        accessControl.setStartHour(invitation.getStartHour());
        accessControl.setEndHour(invitation.getEndHour());
        accessControl.setStartDate(invitation.getStartDate());
        accessControl.setEndDate(invitation.getEndDate());
        accessControl.setDocumentNumber(invitation.getDocumentNumber());
        _accessControlRepository.save(accessControl);
    }

    @Override
    public Boolean CheckAccess(Long documentNumber) {
        LocalTime localTime = LocalTime.now(ZoneId.of(timezone));
        LocalDate localDate = LocalDate.now(ZoneId.of(timezone));
       return _accessControlRepository.hasInvitation(localDate, localTime ,documentNumber);
    }

    @Override
    public Boolean SaveAccess(Long documentNumber) {
        LocalDateTime accessDate = LocalDateTime.now(ZoneId.of(timezone).normalized());

        Boolean canAccess = this.CheckAccess(documentNumber);

        if(!canAccess)
        {
            return canAccess;
        }

        Access access = new Access();
        access.setAccessDate(accessDate);
        access.setDocumentNumber(documentNumber);
        _accessRepository.save(access);
        return Boolean.TRUE;
    }

    @Override
    public Boolean CheckAccess(String image) {
        try {
            Long documentNumber = _IDscannerService.ScanDocumentNumber(image);
            return this.SaveAccess(documentNumber);

        } catch (TesseractException e) {
            e.printStackTrace();
            System.err.println("Error al procesar el documento: " + e.getMessage());
        }
        return false;
    }
}
