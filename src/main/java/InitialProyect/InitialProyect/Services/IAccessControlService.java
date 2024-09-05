package InitialProyect.InitialProyect.Services;


import InitialProyect.InitialProyect.DTOs.Invitation.GenerateInvitation;

public interface IAccessControlService {

    void GenerateInvitation(GenerateInvitation invitation);
    Boolean CheckAccess(Long documentNumber);
    Boolean SaveAccess(Long documentNumber);
    Boolean CheckAccess(String image);


}
