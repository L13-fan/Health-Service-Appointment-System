import java.time.LocalDateTime;
import java.util.Scanner;

public class AssignmentOne {
    public static void main(String[] args) {
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
            staff.provideService();
            System.out.println(" ");
        }

        AppointmentManager manager = new AppointmentManager();

        Scanner input = new Scanner(System.in);

        // 调用bookAppointment方法
        while (true){
            bookAppointment(staffs, manager, input);

            // 消耗换行符
            input.nextLine();

            // 让患者输入Y/N决定要不要结束预约
            System.out.println("\nHas the reservation been completed? ");
            System.out.println("Yes, please enter \"Y\" to end the reservation. No, please enter \"N\" to continue with the reservation.");
            String end = input.nextLine();
            if (end.equalsIgnoreCase("Y")){
                break;
            }
        }
    }
        

    public static void bookAppointment(MedicalStaff[] staffs, AppointmentManager manager, Scanner input){
            // 让患者自己输入信息
            System.out.print("\nEnter patient name: ");
            String patientName = input.nextLine();
            System.out.print("\nEnter patient phone: ");
            String patientPhone = input.nextLine();

            // 让患者选择工作人员
            System.out.println("\nSelect a medical staff by entering their ID: ");
            for (MedicalStaff staff : staffs){
                System.out.println(staff.getStaffId() + " - " + staff.getName());
            }
            System.out.print("\nEnter medical staff ID: ");
            int staffId = input.nextInt();
            MedicalStaff selectedStaff = null;
            for (MedicalStaff staff : staffs){
                if (staff.getStaffId() == staffId){
                    selectedStaff = staff;
                    break;
                }
            }
            if (selectedStaff == null){
                System.out.println("Invalid medical staff ID.");
                return;
            }
            // 输入预约时间
            System.out.println("Appointment booking rules:\n" + //
                                "- Appointments are available for the current day only.\n" + //
                                "- Available time slots are from 08:00 to 16:00, at 30-minute intervals.\n" + //
                                "- You can only book on the selected medical staff's working days.\n" + //
                                "- The selected time must be in the future.");
            System.out.print("Enter appointment time (yyyy-MM-ddTHH:mm): ");
            String timeInput = input.next();
            LocalDateTime appointmentTime = LocalDateTime.parse(timeInput);
            
            // 添加预约，并判断能能成功预约
            Appointment patient = new Appointment(patientName,patientPhone,appointmentTime,selectedStaff);
            if (manager.addAppointment(patient)){
                System.out.println("Appointment booked successfully");
            } else {
                System.out.println("Booking failed. Please check the appointment time and staff availability.");
            }
            System.out.println("\nCurrent appointments: ");
            manager.displayAppointments();

    
    }
}