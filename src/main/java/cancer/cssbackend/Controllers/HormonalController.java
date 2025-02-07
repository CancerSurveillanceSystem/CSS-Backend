package cancer.cssbackend.Controllers;

import cancer.cssbackend.Entities.*;
import cancer.cssbackend.Entities.Requests.AddChemotherapyRequest;
import cancer.cssbackend.Entities.Requests.AddHormonalRequest;
import cancer.cssbackend.Services.HormonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/hormonal")
public class HormonalController {
    private final HormonalService hormonalService;

    @PostMapping("/add")
    public Hormonal addHormonal(@RequestBody AddHormonalRequest addHormonalRequest) {
        return hormonalService.addHormonal(addHormonalRequest);
    }

    @PostMapping("/update/{hormonalId}")
    public Hormonal updateHormonal(@RequestBody AddHormonalRequest addHormonalRequest, @PathVariable Long  hormonalId) {
        return hormonalService.updateHormonal(addHormonalRequest, hormonalId);
    }

    @GetMapping("/findalldoctors")
    public List<Doctor> fetchAllHormonalDoctors(){
        return hormonalService.fetchAllHormonalDoctors();
    }

    @GetMapping("/hospitals")
    public List<Hospital> fetchHormonalHospitals(){
        return hormonalService.fetchHormonalHospitals();
    }

    @GetMapping("/findbypatient")
    public List<Hormonal> findByPatient(@RequestParam(value="patientID") Long patientID){
        return hormonalService.findByPatientID(patientID);
    }

    @GetMapping("/findbypatient/latest")
    public Hormonal findLatestByPatient(@RequestParam(value="patientID") Long patientID){
        return hormonalService.fetchLatestByPatient(patientID);
    }
}
