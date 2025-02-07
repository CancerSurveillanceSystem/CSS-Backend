package cancer.cssbackend.Services;

import cancer.cssbackend.Entities.*;
import cancer.cssbackend.Entities.Requests.AddHormonalRequest;
import cancer.cssbackend.Repositories.DoctorRepository;
import cancer.cssbackend.Repositories.HormonalRepository;
import cancer.cssbackend.Repositories.HospitalRepository;
import cancer.cssbackend.Repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HormonalService {
    private final HormonalRepository hormonalRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final UserService userService;

    public Hormonal addHormonal(AddHormonalRequest addHormonalRequest){
        Hormonal hormonal = addHormonalRequest.mapToHormonal(patientService, doctorService, userService);
        hormonalRepository.save(hormonal);
        return hormonal;
    }

    public Hormonal updateHormonal(AddHormonalRequest addHormonalRequest, Long hormonalId){
        Optional<Hormonal> optionalHormonal = hormonalRepository.findById(hormonalId);
        if (optionalHormonal.isPresent()) {
            Hormonal hormonal = optionalHormonal.get();
            hormonal = addHormonalRequest.mapToHormonal(doctorService, userService, hormonal);
            hormonalRepository.save(hormonal);
            return hormonal;
        }
        return null;
    }

    public List<Doctor> fetchAllHormonalDoctors(){
        List<Long> doctorIDs = doctorRepository.findAllSurgeons();
        List<Doctor> doctorList = new ArrayList<>();
        for(Long id : doctorIDs){
            Optional<Doctor> x = doctorRepository.findById(id);
            x.ifPresent(doctorList::add);
        }

        return doctorList;
    }

    public List<Hospital> fetchHormonalHospitals(){
        List<Long> facilityIDs = hormonalRepository.fetchHormonalHospitals();
        List<Hospital> hospitalList = new ArrayList<>();

        for(Long id : facilityIDs){
            Optional<Hospital> x = hospitalRepository.findById(id);
            x.ifPresent(hospitalList::add);
        }

        return hospitalList;
    }

    public List<Hormonal> findByPatientID(Long patientID){
        Optional<Patient> patient = patientRepository.findById(patientID);

        if (patient.isPresent()){
            return hormonalRepository.findByPatient(patient.get());
        } else {
            throw new RuntimeException("Hormonal records not found with patient ID " + patientID);
        }
    }

    public Hormonal fetchLatestByPatient(Long patientID){
        List<Long> treatmentIDs = hormonalRepository.fetchLatestByPatient(patientID);
        treatmentIDs.sort(Comparator.reverseOrder());

        if(treatmentIDs.isEmpty()){
            throw new RuntimeException("Hormonal records not found with patient ID " + patientID);
        }

        Optional<Hormonal> latest = hormonalRepository.findById(treatmentIDs.get(0));

        if(latest.isPresent()) {
            return latest.get();
        } else {
            throw new RuntimeException("Hormonal records not found with patient ID " + patientID);
        }
    }
}
