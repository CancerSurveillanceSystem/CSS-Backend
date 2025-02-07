package cancer.cssbackend.Services;

import cancer.cssbackend.Entities.*;
import cancer.cssbackend.Entities.Requests.AddChemotherapyRequest;
import cancer.cssbackend.Repositories.ChemotherapyRepository;
import cancer.cssbackend.Repositories.DoctorRepository;
import cancer.cssbackend.Repositories.HospitalRepository;
import cancer.cssbackend.Repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChemotherapyService {
    private final ChemotherapyRepository chemotherapyRepository;
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientService patientService;
    private final ChemoprotocolService chemoprotocolService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final UserService userService;
    private final DoctorRepository doctorRepository;

    public Chemotherapy addChemotherapy (AddChemotherapyRequest addChemotherapyRequest){
        Chemotherapy chemotherapy = addChemotherapyRequest.mapToChemotherapy(patientService, chemoprotocolService, hospitalService, doctorService, userService);
        chemotherapyRepository.save(chemotherapy);
        return chemotherapy;
    }

    public Chemotherapy updateChemotherapy(AddChemotherapyRequest addChemotherapyRequest, Long chemotherapyId){
        Optional<Chemotherapy> optionalChemotherapy = chemotherapyRepository.findById(chemotherapyId);
        if (optionalChemotherapy.isPresent()) {
            Chemotherapy chemotherapy = optionalChemotherapy.get();
            chemotherapy = addChemotherapyRequest.mapToChemotherapy(chemoprotocolService, hospitalService, doctorService, userService, chemotherapy);
            chemotherapyRepository.save(chemotherapy);
            return chemotherapy;
        }
        return null;
    }

    public List<Doctor> fetchAllDoctors(){
        List<Long> doctorIDs = chemotherapyRepository.fetchAllDoctors();
        List<Doctor> doctorList = new ArrayList<>();

        for(Long id : doctorIDs){
            Optional<Doctor> x = doctorRepository.findById(id);
            x.ifPresent(doctorList::add);
        }

        return doctorList;
    }

    public List<Doctor> fetchDoctorsByFacility(Long facilityID){
        List<Long> doctorIDs = chemotherapyRepository.fetchDoctorsByFacility(facilityID);
        List<Doctor> doctorList = new ArrayList<>();

        for(Long id : doctorIDs){
            Optional<Doctor> x = doctorRepository.findById(id);
            x.ifPresent(doctorList::add);
        }

        return doctorList;
    }

    public List<Hospital> fetchChemotherapyFacilities(){
        List<Long> facilityIDs = chemotherapyRepository.fetchChemotherapyFacilities();
        List<Hospital> hospitalList = new ArrayList<>();

        for(Long id : facilityIDs){
            Optional<Hospital> x = hospitalRepository.findById(id);
            x.ifPresent(hospitalList::add);
        }

        return hospitalList;
    }

    public List<Chemotherapy> findByPatientID(Long patientID){
        Optional<Patient> patient = patientRepository.findById(patientID);

        if (patient.isPresent()){
            return chemotherapyRepository.findByPatient(patient.get());
        } else {
            throw new RuntimeException("Chemotherapy records not found with patient ID " + patientID);
        }
    }

    public Chemotherapy fetchLatestByPatient(Long patientID){
        List<Long> treatmentIDs = chemotherapyRepository.fetchLatestByPatient(patientID);
        treatmentIDs.sort(Comparator.reverseOrder());

        if(treatmentIDs.isEmpty()){
            throw new RuntimeException("Chemotherapy records not found with patient ID " + patientID);
        }

        Optional<Chemotherapy> latest = chemotherapyRepository.findById(treatmentIDs.get(0));

        if(latest.isPresent()) {
            return latest.get();
        } else {
            throw new RuntimeException("Surgery records not found with patient ID " + patientID);
        }
    }
}
