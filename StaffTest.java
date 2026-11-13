import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class StaffTest {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        MedicalStaff[] staffs = {
        new GeneralPractitioner("Dr. Alex Chen", 1001, "+86 13800001001",
         "General Practice", 8, "Senior Physician", 
         50.0, "Provides general medical consultations and basic health assessments.",
         new String[]{"Monday","Wednesday","Friday"}),
        
        new Surgeon("Dr. Michael Wang", 1002, "+86 13800001002", "Surgery",
        15, "Chief Physician", 120.0, "Specialises in surgical consultations and treatment.",
        new String[]{"Tuesday","Thursday","Saturday"},"Orthopaedic Surgery" ),

        new Dentist("Dr. Emily Liu", 1003, "+86 13800001003", "Dentistry", 6,
        "Attending Physician", 80.0, "Provides dental examinations and oral health care.",
        new String[] {"Monday","Tuesday","Friday"}, "Dental Cleaning, Filling"),

        new Nurse("Sarah Zhang", 1004, "+86 13800001004", "Nursing", 5,
        "Registered Nurse", 30.0, "Provides nursing care and patient health monitoring.", 
        new String[]{"Wednesday", "Thursday","Friday","Saturday"}, "Emergency Nursing")
        };

        for (MedicalStaff staff: staffs){
            System.out.println(staff);
            System.out.println(" ");
        }

        // Create two appointments with the same staff and time    
        Appointment patient1 = new Appointment("Tom", "13800001234", LocalDateTime.of(2026, 11, 13, 14, 30),
    staffs[0]);

        Appointment patient2 = new Appointment(
        "Lucy",
        "13800005678",
        LocalDateTime.of(2026, 11, 13, 14, 45),
        staffs[0]
        );

        Appointment patient3 = new Appointment("Fiona", "13800012345", LocalDateTime.of(2026,11,13,16,30),
        staffs[2]);

        Appointment patient4 = new Appointment("David", "138000123456", LocalDateTime.of(2026,11,13,15,30),
        staffs[2]);

        Appointment patient5 = new Appointment(
            "Lukas",
            "13900001223",
            LocalDateTime.of(2026, 11, 13, 9, 00),
            staffs[1]
        );
        // Create appointment manager
         AppointmentManager manager = new AppointmentManager();

        // Test first appointment
        if (manager.addAppointment(patient1)) {
            System.out.println("Patient 1: Appointment added successfully!");
        } else {
        System.out.println("Patient 1: Appointment conflict!");
        }

        // Test second appointment
        if (manager.addAppointment(patient2)) {
            System.out.println("Patient 2: Appointment added successfully!");
        } else {
        System.out.println("Patient 2: Appointment conflict!");
        }

        if (manager.addAppointment(patient3)) {
            System.out.println("Patient 3: Appointment added successfully!");
        } else {
        System.out.println("Patient 3: Appointment conflict!");
        }
        if (manager.addAppointment(patient4)) {
            System.out.println("Patient 4: Appointment added successfully!");
        } else {
        System.out.println("Patient 4: Appointment conflict!");
        }
        if (manager.addAppointment(patient5)) {
            System.out.println("Patient 5: Appointment added successfully!");
        } else {
        System.out.println("Patient 5: Appointment conflict!");
        }
        // Check total appointment count

        System.out.println("Total appointments: " + manager.getAppointmentCount());

        System.out.println(" ");
        System.out.println("按照预约时间排序：");
        manager.sortAppointmentsByTime();
        manager.displayAppointments();

        System.out.println(" ");
        System.out.println("用患者手机号查询预约详情：");
        System.out.println("Please enter the mobile phone number you wish to inquire about: ");
        String phone = input.nextLine();
        ArrayList<Appointment> results = manager.findAppointmentsByPatientPhone(phone);
        for (Appointment appointment : results){
            System.out.println(appointment);
        }

        System.out.println(" ");
        System.out.println("用预约医生的ID查询预约详情：");
        System.out.println("Please enter the number of the doctor you wish to inquire about:");
        int staffId = input.nextInt();
        input.nextLine();
        ArrayList<Appointment> staffResults = manager.findAppointmentsByStaffId(staffId);
        for (Appointment appointment : staffResults){
            System.out.println(appointment);
        }

        System.out.println(" ");
        System.out.println("取消预约：");
        System.out.println("Please enter the patient's phone :");
        String patientphone = input.nextLine();
        
        System.out.println("Please enter appointment time (yyyy-MM-dd HH:mm):");
        String timeInput = input.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime targetTime = LocalDateTime.parse(timeInput, formatter);
        //System.out.println(manager.cancelAppointment(patientphone, targetTime));
        if (manager.cancelAppointment(patientphone, targetTime)) {
            System.out.println("Appointment cancelled successfully!");
            System.out.println("Total appointments: " + manager.getAppointmentCount());
        } else {
            System.out.println("Appointment not found!");
        }
    }
}
