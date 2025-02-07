package cancer.cssbackend.Services;

import cancer.cssbackend.Entities.Patient;
import cancer.cssbackend.Entities.Radiotherapy;
import cancer.cssbackend.Entities.Requests.AddTreatmentRequest;
import cancer.cssbackend.Entities.Treatment;
import cancer.cssbackend.Repositories.PatientRepository;
import cancer.cssbackend.Repositories.RxTypeRepository;
import cancer.cssbackend.Repositories.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final RxTypeRepository rxTypeRepository;
    private final PatientService patientService;
    private final RxTypeService rxTypeService;
    private final UserService userService;
    private final PatientRepository patientRepository;
    public Treatment findTreatment(Long treatmentID){
        Optional<Treatment> treatment = treatmentRepository.findById(treatmentID);
        return treatment.orElseThrow(() -> new RuntimeException("Treatment not found with ID " + treatmentID));
    }

    public Treatment addTreatment(AddTreatmentRequest addTreatmentRequest){
        Treatment treatment = addTreatmentRequest.mapToTreatment(patientService, rxTypeService, userService);
        if (treatment.getTreatmentPlan() != null) {
            if(treatment.getTreatmentPlan().getRxtypeId() == null){
                rxTypeRepository.save(treatment.getTreatmentPlan());
            }
        }
        treatmentRepository.save(treatment);

        return treatment;
    }

    public Treatment updateTreatment(AddTreatmentRequest addTreatmentRequest, Long treatmentId) {
        Optional<Treatment> optionalTreatment = treatmentRepository.findById(treatmentId);
        if (optionalTreatment.isPresent()) {
            Treatment treatment = optionalTreatment.get();
            treatment = addTreatmentRequest.mapToTreatment(rxTypeService, userService, treatment);
            if (treatment.getTreatmentPlan() != null) {
                if (treatment.getTreatmentPlan().getRxtypeId() == null) {
                    rxTypeRepository.save(treatment.getTreatmentPlan());
                }
            }
            treatmentRepository.save(treatment);

            return treatment;
        }
        return null;
    }

    public List<Treatment> findByPatientID(Long patientID){
        Optional<Patient> patient = patientRepository.findById(patientID);

        if (patient.isPresent()){
            return treatmentRepository.findByPatient(patient.get());
        } else {
            throw new RuntimeException("Treatment records not found with patient ID " + patientID);
        }
    }

    public Treatment fetchLatestByPatient(Long patientID){
        List<Long> treatmentIDs = treatmentRepository.fetchLatestByPatient(patientID);
        treatmentIDs.sort(Comparator.reverseOrder());

        if(treatmentIDs.isEmpty()){
            throw new RuntimeException("Treatment records not found with patient ID " + patientID);
        }

        Optional<Treatment> latestTreatment = treatmentRepository.findById(treatmentIDs.get(0));

        if(latestTreatment.isPresent()) {
            return latestTreatment.get();
        } else {
            throw new RuntimeException("Treatment records not found with patient ID " + patientID);
        }
    }
}
