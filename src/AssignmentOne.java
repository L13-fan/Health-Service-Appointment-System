import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Yifan Liu
 * number: 25259081
 *
 * AssignmentOne is the console entry point of the Health Service Appointment System.
 * It builds a fixed list of medical staff, prints their details, and then shows a numbered
 * text menu in a loop. Each menu choice is passed to the matching method of
 * AppointmentManager, which owns all the data and all the business rules.
 *
 * This class stores no data and makes no booking decisions of its own. It only prints the
 * menu, reads keyboard input, and forwards the user's choice to AppointmentManager, so the
 * user interface and the business rules stay separate and the rules only need to be
 * maintained in one place.
 */
public class AssignmentOne {
    /**
     * Program entry point.
     *
     * The steps are:
     * 1. create the four medical staff members (polymorphism: a parent-class array holds four subclass objects);
     * 2. print each staff member and call that member's own provideService();
     * 3. create one AppointmentManager, which stores every appointment;
     * 4. enter a while (true) menu loop: read a number, run the matching feature, print the result, and then start another round;
     * 5. choosing 7 returns from main, which ends the program.
     *
     * @param args command line arguments (not used here)
     */
    public static void main(String[] args) {
        /* 
         *  ---------- 1. Create the four medical staff members ----------
         * MedicalStaff is the abstract parent class, while GeneralPractitioner, Surgeon,
         * Dentist and Nurse are the four subclasses. Putting subclass objects into an array
         * of the parent type is polymorphism: the loop below uses one variable name (staff),
         * yet provideService() still runs the version that each subclass overrides.
         * The arguments follow the parent constructor:
         * name, ID, phone, department, years of experience, title, registration fee,
         * introduction, working days array.
         * The subclasses take one extra personal field last, for example the Surgeon's
         * "Orthopaedic Surgery" specialised area. 
        */
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

        /*
         * 2. Print each staff member and demonstrate their service 
         * staff is of the parent type, but System.out.println(staff) actually calls the
         * toString() of the subclass. provideService() behaves the same way, because Java
         * decides at run time which overridden version to run (dynamic binding). 
         */
        for (MedicalStaff staff: staffs){
            System.out.println(staff);
            staff.provideService();
            System.out.println(" ");
        }

        /*
         *  3. Create the appointment manager 
         * It is the only data store in the program: every insert, search, sort and delete
         * below goes through this object. 
         */
        AppointmentManager manager = new AppointmentManager();

        /*
         * 4. Create the keyboard input object
         * The Scanner is created once and then passed to every method, so all input 
         * readings share the same input stream. 
         */
        Scanner input = new Scanner(System.in);

        /*
         * 5. Main menu loop
         * while (true) repeats until option 7 calls return and leaves main. 
         */
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

            // Test the next input before reading it as an int. If the user types letters, nextInt() throws an exception and does not consume the input, which would print the menu again and again in an endless loop. This block clears that line and starts a fresh round instead. 
            if (input.hasNextInt()){
                choice = input.nextInt();
            } else {
                input.nextLine();
                System.out.println("Please enter a number:");
                continue;
            }
            
            // nextInt() reads only the digits and leaves the line-break character "\n" in the buffer. This nextLine() consumes that leftover line break, otherwise the next nextLine() call would read an empty string and skip a real input.
            input.nextLine();
            if (choice == 1){
                 // Call bookAppointment to run the whole booking flow.
                bookAppointment(staffs, manager, input);
                
            } else if (choice == 2) {
                    // 2. View every appointment: the manager prints the list itself.
                    manager.displayAppointments();
            } else if (choice == 3){
                // 3. Search appointments: first ask whether to search by phone or by staff ID.
                System.out.println("Choose how to search appointment records:");
                System.out.print("Enter 1 to search by phone number, 2 to search by medical staff ID:");
                int searchChoice;
                if (input.hasNextInt()){
                    searchChoice = input.nextInt();
                } else {
                    input.nextLine();
                    System.out.println("Please enter a number:");
                    continue;
                }
                input.nextLine();
                ArrayList<Appointment> results;
                if (searchChoice == 1){
                    // Search by phone: read a valid phone number, search, then sort and print every matching record.
                    String phone = readPatientPhone(input);
                    results = manager.findAppointmentsByPatientPhone(phone);
                    if (results.isEmpty()){
                        System.out.println("No appointment found.");
                    } else {
                        // Only the result list of this search is sorted, not the full list.
                        manager.sortAppointmentsByTime(results);
                        for (Appointment result : results){
                            System.out.println(result);
                        }
                    }

                } else if (searchChoice == 2){
                    // Search by staff ID: same flow, using findAppointmentsByStaffId instead.
                    System.out.print("Enter medical staff ID: ");
                    int staffId;
                    if (input.hasNextInt()){
                        staffId = input.nextInt();
                    } else {
                        input.nextLine();
                        System.out.println("Please enter a number:");
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
                // 4. Sort every appointment. The internal list of the manager is sorted directly, so no return value is needed.
                manager.sortAppointmentsByTime();
                System.out.println("Sorting completed.");
                manager.displayAppointments();
            } else if (choice == 5){
                // 5. Cancel an appointment, again in two ways: by patient phone or by staff ID.
                System.out.println("Please select cancellation method:\n" + //
                                        "1. Cancel by patient phone and appointment time\n" + //
                                        "2. Cancel by medical staff ID and appointment time");
                System.out.println("Please enter your choice: ");
                int cancelChoice;
                if (input.hasNextInt()){
                    cancelChoice = input.nextInt();
                } else {
                    input.nextLine();
                    System.out.println("Please enter a number:");
                    continue;
                }
                input.nextLine();
                if (cancelChoice == 1){
                    // Option 1: phone number plus appointment time, both must match.
                    String cancelPhone = readPatientPhone(input);
                    LocalDateTime appointmentTime = readAppointmentTime(input);
                    boolean cancelResult = manager.cancelAppointment(cancelPhone, appointmentTime);
                    if (cancelResult){
                        System.out.println("Appointment cancellation successful.");
                    } else {
                        System.out.println("Appointment cancellation failed.");
                    }
                } else if (cancelChoice == 2){
                    // Option 2: staff ID plus appointment time.
                    System.out.print("\nEnter medical staff ID:");
                    int cancelStaffID;
                    if (input.hasNextInt()){
                        cancelStaffID = input.nextInt();
                    } else {
                        input.nextLine();
                        System.out.println("Please enter a number.");
                        continue;
                    }
                    input.nextLine();
                    LocalDateTime appointmentTime = readAppointmentTime(input);
                    boolean cancelResult = manager.cancelAppointment(cancelStaffID, appointmentTime);
                    if (cancelResult){
                        System.out.println("Appointment cancellation successful.");
                    } else {
                        System.out.println("Appointment cancellation failed.");
                    }
                } else {
                    System.out.println("Invalid cancellation option.");
                }
            } else if (choice == 6){
                // 6. Show the time slots that one doctor still has free today.
                checkAvailableTimeSlots(staffs, manager, input);
            } else if (choice == 7){
                // 7. Exit: return leaves main, which ends the whole program.
                return;
            } else {
                // A number other than 1 to 7 was entered.
                System.out.println("Invalid choice. Please try again.");
            }
           
        }
        }

        
    
    /**
     * Handle the whole "Book an appointment" flow (menu option 1).
     *
     * The steps are:
     * 1. read the patient name, which may not be blank (trim() removes leading and trailing
     *    spaces before the length check);
     * 2. read the patient phone number through readPatientPhone, which validates 11 digits;
     * 3. list every staff member so the user can choose by ID, and go back to the menu when
     *    the ID is unknown;
     * 4. keep asking for the appointment time until it parses, because
     *    LocalDateTime.parse needs the exact format "yyyy-MM-ddTHH:mm";
     * 5. pack the four values into an Appointment object and let
     *    manager.addAppointment apply the business rules;
     * 6. print all current appointments at the end, whether the booking succeeded or not,
     *    so the user can see the result.
     *
     * @param staffs the array of medical staff the patient can choose from
     * @param manager the appointment manager that stores and validates the record
     * @param input the shared Scanner connected to the keyboard
     */
    public static void bookAppointment(MedicalStaff[] staffs, AppointmentManager manager, Scanner input){
            // Let the patient enter their own information.
            System.out.print("\nEnter patient name: ");
            String patientName = input.nextLine();

            // A name may not be blank: trim() removes spaces, then the length is checked.
            while (patientName.trim().isEmpty()) {
                System.out.println("Patient name cannot be empty. Please enter again:");
                patientName = input.nextLine();
            }
            String patientPhone = readPatientPhone(input);


            // Let the patient choose a medical staff member.
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
                System.out.println("Please enter a number:");
                return;
            }
            input.nextLine();
            // Look for the object with that ID in the array. selectedStaff starts as null, so it is still null after the loop when the ID does not exist.
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
            // Read the appointment time.
            LocalDateTime appointmentTime = null;
            // while (true) with try/catch: an invalid format asks again, and only a successful parse breaks out of the loop.
            while (true) { 
                System.out.println("Appointment booking rules:\n" + //
                                "- Appointments are available for the current day only.\n" + //
                                "- Available time slots are from 08:00 to 16:00, at 30-minute intervals.\n" + //
                                "- You can only book on the selected medical staff's working days.\n" + //
                                "- The selected time must be in the future.");
                System.out.print("Enter appointment time (yyyy-MM-ddTHH:mm): ");
                String timeInput = input.nextLine();
                try {
                    
                    // parse requires the exact format yyyy-MM-ddTHH:mm, for example 2026-11-16T10:30
                    appointmentTime = LocalDateTime.parse(timeInput);
                    break;
            } catch (DateTimeParseException e){
                // The format is wrong: print a hint and let the loop ask again (no break and no return here).
                System.out.println("Invalid date format. Please try again.");
            }
            
            }                   
            
            /*
             * Add the appointment and check whether the booking can succeed.
             * This block only packs the data; whether the booking is allowed is decided by manager.addAppointment.
            */
            Appointment patient = new Appointment(patientName,patientPhone,appointmentTime,selectedStaff);
            if (manager.addAppointment(patient)){
                System.out.println("Appointment booked successfully");
            } 
            // The reason for a failure has already been printed inside addAppointment, so here the current list is printed again so the user can confirm the result.
            System.out.println("\nCurrent appointments: ");
            manager.displayAppointments();

    
    }

    /**
     * Read a patient phone number from the keyboard and make sure it is exactly 11 digits.
     *
     * The regular expression "\\d{11}" means "eleven digit characters", and matches()
     * requires the whole string to fit that pattern. An invalid value keeps the loop running,
     * so the caller always receives a valid phone number.
     *
     * @param input the shared Scanner
     * @return a string consisting of exactly 11 digits
     */
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

    /**
     * Handle the "Check available time slots" flow (menu option 6).
     *
     * It first lists the ID and name of every staff member, reads the chosen ID, and then
     * asks the manager to print the time slots that are still free today.
     *
     * @param staffs the array of medical staff
     * @param manager the appointment manager that knows which slots are taken
     * @param input the shared Scanner
     */
    public static void checkAvailableTimeSlots(MedicalStaff[] staffs, AppointmentManager manager, Scanner input) {
        // Show the ID and name of every staff member.
        for (MedicalStaff staff : staffs){
            System.out.println(staff.getStaffId() + " - " + staff.getName());
        }

        System.out.println("Enter medical staff ID:");
        int staffId;
        if (input.hasNextInt()){
            staffId = input.nextInt();
        } else{
            input.nextLine();
            System.out.println("Please enter a valid medical staff ID.");
            return;
        }
        input.nextLine();

        // Find the object with that ID in the array and report an unknown ID.
        MedicalStaff selectedStaff = null;
        for (MedicalStaff staff : staffs){
            if (staff.getStaffId() == staffId){
                selectedStaff = staff;
                break;
            }
        }
        if (selectedStaff == null){
            System.out.println("Medical staff ID not found.");
            return;
        }
        manager.displayAvailableTimeSlots(selectedStaff);
    }

    /**
     * Keep asking the user for an appointment time until it can be parsed.
     *
     * Cancelling needs the time of the record to delete. Only the format is checked here,
     * not the booking rules, because a record that should be cancelled may well hold a time
     * that is already in the past.
     *
     * @param input the shared Scanner
     * @return a valid LocalDateTime parsed from the user's input
     */
    private static LocalDateTime readAppointmentTime(Scanner input) {
        while (true) {
            System.out.print("Enter appointment time (yyyy-MM-ddTHH:mm): ");
            String targetTime = input.nextLine();

            try {
                return LocalDateTime.parse(targetTime);
            } catch (DateTimeParseException e) {
                // A failed parse prints the expected format and the loop asks again.
                System.out.println("Invalid date format. Please enter the time again in the format yyyy-MM-ddTHH:mm.");
            }
        }
    }
}
