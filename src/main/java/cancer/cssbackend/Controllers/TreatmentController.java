package cancer.cssbackend.Controllers;

import cancer.cssbackend.Entities.Requests.AddTreatmentRequest;
import cancer.cssbackend.Entities.Treatment;
import cancer.cssbackend.Services.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/treatment")
public class TreatmentController {
    private final TreatmentService treatmentService;

    @PostMapping("/add")
    public Treatment addTreatment(@RequestBody AddTreatmentRequest addTreatmentRequest) {
        return treatmentService.addTreatment(addTreatmentRequest);
    }

    @PutMapping("/update/{treatmentId}")
    public Treatment updateTreatment(@RequestBody AddTreatmentRequest addTreatmentRequest, @PathVariable Long treatmentId) {
        return treatmentService.updateTreatment(addTreatmentRequest, treatmentId);
    }

    @GetMapping("/findbypatient")
    public List<Treatment> findByPatient(@RequestParam(value="patientID") Long patientID){
        return treatmentService.findByPatientID(patientID);
    }

    @GetMapping("/findbypatient/latest")
    public Treatment findLatestByPatient(@RequestParam(value="patientID") Long patientID){
        return treatmentService.fetchLatestByPatient(patientID);
    }
}
