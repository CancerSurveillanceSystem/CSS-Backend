package cancer.cssbackend.Controllers;

import cancer.cssbackend.Services.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/notif/type")
public class NotificationTypeController {
    private final NotificationTypeService notificationTypeService;
}
