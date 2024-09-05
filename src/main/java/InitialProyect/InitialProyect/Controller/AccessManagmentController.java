package InitialProyect.InitialProyect.Controller;

import InitialProyect.InitialProyect.DTOs.Invitation.Base64Request;
import InitialProyect.InitialProyect.DTOs.Invitation.GenerateInvitation;
import InitialProyect.InitialProyect.Services.IAccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessManagmentController {

    @Autowired
    private IAccessControlService _accessService;

    @PostMapping("/invitation")
    public void GenerateInvitation(@RequestBody GenerateInvitation invitation)
    {
        _accessService.GenerateInvitation(invitation);
    };

    @PostMapping("/access/check/{documentNumber}")
    public Boolean CheckAccess(@PathVariable Long documentNumber)
    {
        return _accessService.CheckAccess(documentNumber);
    };

    @PostMapping("/access/check")
    public Boolean CheckAccess(@RequestBody Base64Request image)
    {
        return _accessService.CheckAccess(image.base64);
    };
}
