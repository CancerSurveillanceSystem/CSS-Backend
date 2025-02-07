package cancer.cssbackend.Entities.Requests;

import cancer.cssbackend.Entities.*;
import cancer.cssbackend.Services.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddChemotherapyRequest {
    @JsonProperty("PATIENT_ID")
    private Long patientId;

    @JsonProperty("CHEMO_TYPE")
    private String chemoType; // Choices: neoadjuvant, adjuvant, palliative

    @JsonProperty("CHEMO_PROTOCOL")
    private Long chemoProtocolId;

    @JsonProperty("CHEMO_INITIALDATE")
    private String chemoInitialDate;

    @JsonProperty("CHEMO_ENDDATE")
    private String chemoEndDate;

    @JsonProperty("CHEMO_CYCLENUMBERGIVEN")
    private int chemoCycleNumberGiven;

    @JsonProperty("CHEMO_STATUS")
    private String chemoStatus; // Choices: ongoing, completed, not completed

    @JsonProperty("CHEMO_NOTES")
    private String chemoNotes;

    @JsonProperty("CHEMO_ISCOMPLETED")
    private char chemoIsCompleted;

    @JsonProperty("CHEMO_FACILITY")
    private Long chemoFacilityId;

    @JsonProperty("CHEMO_DOCTOR")
    private Long chemoDoctorId;

    @JsonProperty("CHEMO_ENCODER")
    private Long chemoEncoderId;

    // Method to map AddChemotherapyRequest to Chemotherapy entity
    public Chemotherapy mapToChemotherapy(PatientService patientService, ChemoprotocolService chemoprotocolService, HospitalService hospitalService, DoctorService doctorService, UserService userService) {
        Chemotherapy chemotherapy = new Chemotherapy();

        Patient patient = patientService.findPatient(patientId);
        if(patient != null){
            chemotherapy.setPatient(patient);
        }

        chemotherapy.setChemoType(this.chemoType);

        Chemoprotocol chemoProtocol = chemoprotocolService.findChemoprotocol(chemoProtocolId);
        if(chemoProtocol != null){
            chemotherapy.setChemoProtocol(chemoProtocol);
        }

        chemotherapy.setChemoInitialDate(Date.valueOf(this.chemoInitialDate));
        chemotherapy.setChemoEndDate(Date.valueOf(this.chemoEndDate));
        chemotherapy.setChemoCycleNumberGiven(this.chemoCycleNumberGiven);
        chemotherapy.setChemoStatus(this.chemoStatus);
        chemotherapy.setChemoNotes(this.chemoNotes);
        chemotherapy.setChemoIsCompleted(this.chemoIsCompleted);

        Hospital facility = hospitalService.findHospital(chemoFacilityId);
        if(facility != null){
            chemotherapy.setChemoFacility(facility);
        }

        Doctor doctor = doctorService.findDoctor(chemoDoctorId);
        if(doctor != null){
            chemotherapy.setChemoDoctor(doctor);
        }

        User encoder = userService.getUser(chemoEncoderId);
        if(encoder != null){
            chemotherapy.setEncoder(encoder);
        }

        chemotherapy.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        chemotherapy.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

        return chemotherapy;
    }

    public Chemotherapy mapToChemotherapy(ChemoprotocolService chemoprotocolService, HospitalService hospitalService, DoctorService doctorService, UserService userService, Chemotherapy chemotherapy) {
        chemotherapy.setChemoType(this.chemoType);

        Chemoprotocol chemoProtocol = chemoprotocolService.findChemoprotocol(chemoProtocolId);
        if(chemoProtocol != null){
            chemotherapy.setChemoProtocol(chemoProtocol);
        }

        chemotherapy.setChemoInitialDate(Date.valueOf(this.chemoInitialDate));
        chemotherapy.setChemoEndDate(Date.valueOf(this.chemoEndDate));
        chemotherapy.setChemoCycleNumberGiven(this.chemoCycleNumberGiven);
        chemotherapy.setChemoStatus(this.chemoStatus);
        chemotherapy.setChemoNotes(this.chemoNotes);
        chemotherapy.setChemoIsCompleted(this.chemoIsCompleted);

        Hospital facility = hospitalService.findHospital(chemoFacilityId);
        if(facility != null){
            chemotherapy.setChemoFacility(facility);
        }

        Doctor doctor = doctorService.findDoctor(chemoDoctorId);
        if(doctor != null){
            chemotherapy.setChemoDoctor(doctor);
        }

        User encoder = userService.getUser(chemoEncoderId);
        if(encoder != null){
            chemotherapy.setEncoder(encoder);
        }

        chemotherapy.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

        return chemotherapy;
    }
}
