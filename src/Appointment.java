/**
 * @author Yifan Liu
 * number: 25259081
 * This is the appointment record class.
 * It stores four pieces of information: the patient's name, the patient's mobile phone number, the appointment time, and the medical staff.
 */
import java.time.LocalDateTime;

public class Appointment {
    private String patientName;
    private String patientPhone;
    private LocalDateTime appointmentTime;
    private MedicalStaff medicalStaff;   

    /**
     * Create an appointment record and initialize the patient's name, patient's mobile phone number, appointment time, and the medical staff object.
     * @param patientName This is the patient's name.
     * @param patientPhone The patient's mobile phone number.
     * @param appointmentTime The time of the patient's appointment.
     * @param medicalStaff Scheduled medical staff's information.
     */
    public Appointment(String patientName, String patientPhone, LocalDateTime appointmentTime,
        MedicalStaff medicalStaff){
        this.patientName = patientName;
        this.patientPhone = patientPhone;
        this.appointmentTime = appointmentTime;
        this.medicalStaff = medicalStaff;
    }

    // Return the patient's name
    public String  getPatientName(){
        return patientName;
    }
    // Return the patient's phone
    public String getPatientPhone(){
        return patientPhone;
    }

    // Return the patient's appointment time
    public LocalDateTime getAppointmentTime(){
        return appointmentTime;
    }

    // Retrieve the medical staff object of the patient's appointment.
    public MedicalStaff getMedicalStaff(){
        return medicalStaff;
    }

    // Return the string representation of the appointment record
    @Override
    public String toString(){
        return String.format("Patient Name: %s\nPatient Phone: %s\nAppointment Time: %s\nMedical Staff: %s\nDepartment: %s", 
        patientName, patientPhone, appointmentTime, medicalStaff.getName(), medicalStaff.getDepartment());
    }

    
}
