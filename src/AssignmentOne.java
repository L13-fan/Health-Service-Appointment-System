import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

        while (true){
            System.out.print("\n===== Health Service Appointment System =====\n" + //
                                "\n" + //
                                "1. Book an appointment\n" + //
                                "2. View all appointments\n" + //
                                "3. Search appointments\n" + //
                                "4. Sort appointments\n" + //
                                "5. Cancel an appointment\n" + //
                                "6. Check available time slots\n" + //
                                "7. Exit\n" + //
                                "\n" + //
                                "Please enter your choice:");
            int choice;
            if (input.hasNextInt()){
                choice = input.nextInt();
            } else {
                input.nextLine();
                System.out.println("请输入数字：");
                continue;
            }
            input.nextLine();
            if (choice == 1){
                 // 调用bookAppointment方法
                bookAppointment(staffs, manager, input);
                
            } else if (choice == 2) {
                    manager.displayAppointments();
            } else if (choice == 3){
                System.out.println("请选择怎样查询预约信息：");
                System.out.print("用手机号查询请输入1，用工作人员ID查询请输入2:");
                int searchChoice;
                if (input.hasNextInt()){
                    searchChoice = input.nextInt();
                } else {
                    input.nextLine();
                    System.out.println("请输入数字：");
                    continue;
                }
                input.nextLine();
                ArrayList<Appointment> results;
                if (searchChoice == 1){
                    String phone = readPatientPhone(input);
                    results = manager.findAppointmentsByPatientPhone(phone);
                    if (results.isEmpty()){
                        System.out.println("No appointment found.");
                    } else {
                        manager.sortAppointmentsByTime(results);
                        for (Appointment result : results){
                            System.out.println(result);
                        }
                    }

                } else if (searchChoice == 2){
                    System.out.print("Enter medical staff ID: ");
                    int staffId;
                    if (input.hasNextInt()){
                        staffId = input.nextInt();
                    } else {
                        input.nextLine();
                        System.out.println("请输入数字：");
                        continue;
                    }
                    input.nextLine();
                    results = manager.findAppointmentsByStaffId(staffId);
                    if (results.isEmpty()){
                        System.out.println("No appointment found.");
                    } else {
                        manager.sortAppointmentsByTime(results);
                        for (Appointment result : results){
                            System.out.println(result);
                        }
                    }
                } else {
                    System.out.println("Invalid search option.");
                }
                
            } else if (choice == 4) {
                manager.sortAppointmentsByTime();
                System.out.println("排序已完成");
                manager.displayAppointments();
            } else if (choice == 5){
                System.out.println("Please select cancellation method:\n" + //
                                        "1. Cancel by patient phone and appointment time\n" + //
                                        "2. Cancel by medical staff ID and appointment time");
                System.out.println("Pealse enter your choice: ");
                int cancelChoice;
                if (input.hasNextInt()){
                    cancelChoice = input.nextInt();
                } else {
                    input.nextLine();
                    System.out.println("请输入数字：");
                    continue;
                }
                input.nextLine();
                if (cancelChoice == 1){
                    String cancelPhone = readPatientPhone(input);
                    LocalDateTime appointmentTime = readAppointmentTime(input);
                    boolean cancelResult = manager.cancelAppointment(cancelPhone, appointmentTime);
                    if (cancelResult){
                        System.out.println("取消成功");
                    } else {
                        System.out.println("取消失败");
                    }
                } else if (cancelChoice == 2){
                    System.out.print("\n请输入医生ID:");
                    int cancelStaffID;
                    if (input.hasNextInt()){
                        cancelStaffID = input.nextInt();
                    } else {
                        input.nextLine();
                        System.out.println("请输入数字");
                        continue;
                    }
                    input.nextLine();
                    LocalDateTime appointmentTime = readAppointmentTime(input);
                    boolean cancelResult = manager.cancelAppointment(cancelStaffID, appointmentTime);
                    if (cancelResult){
                        System.out.println("预约取消成功");
                    } else {
                        System.out.println("预约取消失败");
                    }
                } else {
                    System.out.println("Invalid cancellation option.");
                }
            } else if (choice == 6){
                checkAvailableTimeSlots(staffs, manager, input);
            } else if (choice == 7){
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
           
        }
        }

        
    

    public static void bookAppointment(MedicalStaff[] staffs, AppointmentManager manager, Scanner input){
            // 让患者自己输入信息
            System.out.print("\nEnter patient name: ");
            String patientName = input.nextLine();

            while (patientName.trim().isEmpty()) {
                System.out.println("Patient name cannot be empty. Please enter again:");
                patientName = input.nextLine();
            }
            String patientPhone = readPatientPhone(input);


            // 让患者选择工作人员
            System.out.println("\nSelect a medical staff by entering their ID: ");
            for (MedicalStaff staff : staffs){
                System.out.println(staff.getStaffId() + " - " + staff.getName());
            }
            System.out.print("\nEnter medical staff ID: ");
            int staffId;
            if (input.hasNextInt()){
                staffId = input.nextInt();
            } else {
                input.nextLine();
                System.out.println("请输入数字：");
                return;
            }
            input.nextLine();
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
            LocalDateTime appointmentTime = null;
            while (true) { 
                System.out.println("Appointment booking rules:\n" + //
                                "- Appointments are available for the current day only.\n" + //
                                "- Available time slots are from 08:00 to 16:00, at 30-minute intervals.\n" + //
                                "- You can only book on the selected medical staff's working days.\n" + //
                                "- The selected time must be in the future.");
                System.out.print("Enter appointment time (yyyy-MM-ddTHH:mm): ");
                String timeInput = input.nextLine();
                try {
                    
                    appointmentTime = LocalDateTime.parse(timeInput);
                    break;
            } catch (DateTimeParseException e){
                System.out.println("Invalid date format. Please try again.");
            }
            
            }                   
            
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

    public static String readPatientPhone(Scanner input) {
        while (true) {
            System.out.print("Enter patient phone (11 digits): ");
            String phone = input.nextLine();

            if (phone.matches("\\d{11}")) {
                return phone;
            }

            System.out.println("Phone number must contain exactly 11 digits. Please try again.");
        }
    }

    public static void checkAvailableTimeSlots(MedicalStaff[] staffs, AppointmentManager manager, Scanner input) {
        // 展示医生ID和姓名
        for (MedicalStaff staff : staffs){
            System.out.println(staff.getStaffId() + " - " + staff.getName());
        }

        System.out.println("请输入医生ID");
        int staffId;
        if (input.hasNextInt()){
            staffId = input.nextInt();
        } else{
            input.nextLine();
            System.out.println("请输入有效的医生ID.");
            return;
        }
        input.nextLine();

        MedicalStaff selectedStaff = null;
        for (MedicalStaff staff : staffs){
            if (staff.getStaffId() == staffId){
                selectedStaff = staff;
                break;
            }
        }
        if (selectedStaff == null){
            System.out.println("ID不存在");
            return;
        }
        manager.displayAvailableTimeSlots(selectedStaff);
    }

    private static LocalDateTime readAppointmentTime(Scanner input) {
        while (true) {
            System.out.print("请输入预约时间（yyyy-MM-ddTHH:mm）：");
            String targetTime = input.nextLine();

            try {
                return LocalDateTime.parse(targetTime);
            } catch (DateTimeParseException e) {
                System.out.println("日期格式错误，请按照 yyyy-MM-ddTHH:mm 重新输入。");
            }
        }
    }
}