
import java.time.LocalDateTime;

public class Appointment {
    private String patientName;
    private String patientPhone;
    private LocalDateTime appointmentTime;
    private MedicalStaff medicalStaff;   

    public Appointment(String patientName, String patientPhone, LocalDateTime appointmentTime,
        MedicalStaff medicalStaff){
        this.patientName = patientName;
        this.patientPhone = patientPhone;
        this.appointmentTime = appointmentTime;
        this.medicalStaff = medicalStaff;
    }

    public String  getPatientName(){
        return patientName;
    }

    public String getPatientPhone(){
        return patientPhone;
    }

    public LocalDateTime getAppointmentTime(){
        return appointmentTime;
    }

    public MedicalStaff getMedicalStaff(){
        return medicalStaff;
    }

    @Override
    public String toString(){
        return String.format("Patient Name: %s\nPatient Phone: %s\nAppointment Time: %s\nMedical Staff: %s\nDepartment: %s", 
        patientName, patientPhone, appointmentTime, medicalStaff.getName(), medicalStaff.getDepartment());
    }
}
