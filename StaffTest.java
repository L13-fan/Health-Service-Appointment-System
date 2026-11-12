import java.time.LocalDateTime;

public class StaffTest {
    
    public static void main(String[] args) {

        MedicalStaff[] staffs = {
        new GeneralPractitioner("Dr. Alex Chen", 1001, "+86 13800001001",
         "General Practice", 8, "Senior Physician", 
         50.0, "Provides general medical consultations and basic health assessments."),
        
        new Surgeon("Dr. Michael Wang", 1002, "+86 13800001002", "Surgery",
        15, "Chief Physician", 120.0, "Specialises in surgical consultations and treatment.",
        "Orthopaedic Surgery"),

        new Dentist("Dr. Emily Liu", 1003, "+86 13800001003", "Dentistry", 6,
        "Attending Physician", 80.0, "Provides dental examinations and oral health care.",
        "Dental Cleaning, Filling"),

        new Nurse("Sarah Zhang", 1004, "+86 13800001004", "Nursing", 5,
        "Registered Nurse", 30.0, "Provides nursing care and patient health monitoring.", "Emergency Nursing")
    };

        Appointment patient1 = new Appointment("Tom", "13800001234", LocalDateTime.of(2026, 11, 13, 14, 30),
    staffs[1]);

        for (MedicalStaff staff : staffs){
            System.out.println(staff);
            staff.provideService();
            System.out.println(" ");
        }

        System.out.println(patient1);
    }
}
