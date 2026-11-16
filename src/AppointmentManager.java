import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Yifan Liu
 * number: 25259081
 *
 * AppointmentManager is the data and business-rule layer of the system.
 * It keeps all appointment records in memory (an ArrayList), and it centralises every
 * rule that decides whether a booking is allowed: whether the time is legal, whether the
 * medical staff member is on duty that day, and whether the slot has already been taken.
 * The user interface (AssignmentOne) only prints menus and reads keyboard input, so the
 * rules are stored and maintained in exactly one place.
 */
public class AppointmentManager {
    /**
     * Holds every appointment record.
     * A record is added only after a booking passes all checks, and removed when it is cancelled.
     */
    private ArrayList<Appointment> appointments = new ArrayList<>();

    /**
     * Add the given appointment record to the appointment list.
     *
     * The checks run in this order, and the record is stored only when every check passes:
     * 1. The appointment object cannot be null.
     * 2. The appointment time must satisfy the booking rules (see isValidAppointmentTime).
     * 3. The medical staff member must be on duty on the appointment day.
     * 4. The same staff member cannot already have another appointment at the same time.
     *
     * @param appointment the appointment record the patient wants to book
     * @return true if the record is stored; false if the time is invalid, the staff member is
     *         off duty, or the time slot is already taken
     * @throws IllegalArgumentException if appointment is null (passing null raises an
     *         exception instead of returning false)
     */
    public boolean addAppointment(Appointment appointment){
        // Check 1: the object itself must not be null. This is a mistake made by the caller,
        // so an exception is more appropriate than returning false.
        if (appointment == null){
            throw new IllegalArgumentException("Appointment cannot be null.");
        }
        // Check 2: the time must be today, between 08:00 and 16:00, on the hour or half hour,
        // and in the future.
        if (isValidAppointmentTime(appointment.getAppointmentTime())){
            // Check 3: is the staff member working on the day of this appointment
            // (that is, does getWorkingDays() contain that day of the week)?
            if (isWorkingDay(appointment)){
                // Check 4: does the same staff member already have an appointment at this time?
                if (hasConflict(appointment)){
                    System.out.println("This doctor is already scheduled for an appointment during this period. Please choose another time.");
                    return false;
                } else {
                    // All checks passed, so the record is actually stored here.
                    appointments.add(appointment);
                    return true;
                }
            } else {
                System.out.println("This doctor is not on duty today. Please choose another doctor or another date.");
                return false;

            }
            
        } else {
            System.out.println("Invalid appointment time. Please choose a future time between 08:00 and 16:00, at 30-minute intervals.");
            return false;
        }
        
    }

    /**
     * Get the total number of appointment records currently stored.
     *
     * @return the size of the internal appointment list
     */
    public int getAppointmentCount(){
        return appointments.size();
    }

    /**
     * Search all appointments that belong to one patient, matched by phone number.
     * One phone number may have several appointments, so a new list is returned and the
     * internal list is not modified.
     *
     * @param phone the patient's 11-digit phone number used when booking
     * @return a new ArrayList holding every appointment of that phone number;
     *         an empty list if nothing matches
     */
    public ArrayList<Appointment> findAppointmentsByPatientPhone(String phone){
        ArrayList<Appointment> results = new ArrayList<>();
        for (Appointment appointment : appointments){
            // Strings are compared with equals(), which compares content.
            // The == operator would only compare object references.
            if (appointment.getPatientPhone().equals(phone)){
                results.add(appointment);
            }
        }
        return results;
    }

    /**
     * Search all appointments handled by one medical staff member, matched by staff ID.
     * A staff ID is an int, so == is the correct comparison here.
     *
     * @param staffId the unique ID of the medical staff member (for example 1001)
     * @return a new ArrayList holding every appointment of that staff member;
     *         an empty list if nothing matches
     */
    public ArrayList<Appointment> findAppointmentsByStaffId(int staffId){
        ArrayList<Appointment> results = new ArrayList<>();
        for (Appointment appointment : appointments){
            if (appointment.getMedicalStaff().getStaffId() == staffId){
                results.add(appointment);
            }
        }
        return results;
    }

    /**
     * Sort all stored appointments in ascending order of appointment time, using bubble sort.
     * The order is changed inside the internal list, so the method returns nothing.
     *
     * The outer loop (j) counts how many passes have been completed. The inner loop (i)
     * compares two neighbouring records from left to right and swaps them when the earlier
     * record has the later time, so each pass pushes the largest remaining time to the end.
     * The end of the list is already sorted after each pass, which is why the inner bound
     * shrinks by j (size()-1-j) instead of always running to the last position.
     */
    public void sortAppointmentsByTime(){
        for (int j = 0; j < appointments.size()-1; j++){
            for (int i = 0; i<appointments.size()-1-j; i++) {
                // isAfter returns true when the earlier record has the later time,
                // which means the pair is in the wrong order and must be swapped.
                if (appointments.get(i).getAppointmentTime().isAfter(appointments.get(i+1).getAppointmentTime())){
                    Appointment temp = appointments.get(i); 
                    appointments.set(i, appointments.get(i+1));
                    appointments.set(i+1, temp);
                }
            }
        }
    }

    /**
     * Overloaded version of the bubble sort above: it sorts the list passed as an argument
     * instead of the internal list.
     * The search functions pass their result list here, so only the result set is sorted and
     * the order of the complete appointment list is left untouched.
     * @param targetAppointments the list that should be sorted by appointment time
     */
    public void sortAppointmentsByTime(ArrayList<Appointment> targetAppointments){
        for (int i = 0; i < targetAppointments.size() - 1; i ++){
            for (int j = 0; j < targetAppointments.size() -1 - i; j++){
                if (targetAppointments.get(j).getAppointmentTime().isAfter(targetAppointments.get(j+1).getAppointmentTime())){
                    Appointment temp = targetAppointments.get(j);
                    targetAppointments.set(j,targetAppointments.get(j+1));
                    targetAppointments.set(j+1,temp);
                }
            }
        }
    }

    /**
     * Cancel one appointment by patient phone number plus appointment time.
     * Both values must match, which stops a second appointment of the same patient from
     * being removed by accident.
     *
     * An Iterator is used here instead of for-each, because changing the list while for-each
     * is walking through it throws a ConcurrentModificationException. Calling the iterator's
     * own remove() method is the safe way to delete an element during a traversal.
     *
     * @param phone the patient's phone number
     * @param targetTime the appointment time to cancel
     * @return true if one matching record was found and removed; false otherwise
     */
    public boolean cancelAppointment(String phone, LocalDateTime targetTime){
        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()){
            Appointment appointment = iterator.next();
            // Both conditions must hold: same phone number and same appointment time.
            if (appointment.getPatientPhone().equals(phone) && 
                appointment.getAppointmentTime().equals(targetTime)){
                iterator.remove();
                return true;
            } 
            
        }

        return false;
    }
    
    /**
     * Cancel one appointment by medical staff ID plus appointment time.
     * Both values must match.
     *
     * This overload uses an index-based for loop and returns immediately after removing an
     * element, so the usual problem of an index-based loop skipping the next element after a
     * removal cannot happen here.
     *
     * @param staffId the ID of the medical staff member
     * @param targetTime the appointment time to cancel
     * @return true if one matching record was found and removed; false otherwise
     */
    public boolean cancelAppointment(int staffId, LocalDateTime targetTime){
        for (int i = 0; i < appointments.size(); i++){
            if ((appointments.get(i).getMedicalStaff().getStaffId() == staffId) && (appointments.get(i).getAppointmentTime().equals(targetTime))){
                appointments.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the new appointment conflicts with any stored record.
     * A conflict means the same medical staff member already has an appointment at exactly
     * the same time, so staff ID and time are both compared.
     *
     * @param newAppointment the appointment that is about to be booked
     * @return true if a conflicting record already exists; false if the slot is free
     */
    public boolean hasConflict(Appointment newAppointment){
        for (Appointment appointment : appointments){
            if (newAppointment.getMedicalStaff().getStaffId() == appointment.getMedicalStaff().getStaffId()
                && newAppointment.getAppointmentTime().equals(appointment.getAppointmentTime())){
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether an appointment time is legal. Every rule below must hold:
     * 1. time is not null;
     * 2. it is not later than 16:00 on its own date;
     * 3. the hour is 08:00 or later;
     * 4. the minute is exactly 0 or 30, which gives 30-minute intervals;
     * 5. it is strictly after the current moment, so no time in the past is accepted;
     * 6. its date equals today's date, because appointments are for the current day only;
     * 7. seconds and nanoseconds are 0, so the time really falls on a half-hour point.
     *
     * endTime is the 16:00 of the same date as the tested time, and the combination of
     * isBefore and equals makes it an inclusive upper bound (<= 16:00).
     * All seven conditions are joined with && , so a single false condition makes the whole
     * expression false and the method returns false.
     *
     * @param time the appointment time to be validated
     * @return true if every rule above is satisfied; false otherwise (including time == null)
     */
    public boolean isValidAppointmentTime(LocalDateTime time){
        // A null value is rejected first, so the getters used below cannot throw a
        // NullPointerException.
        if (time == null) {
            return false;
        }
        // Build 16:00 on the same date as the tested time and use it as the latest allowed time.
        LocalDateTime endTime = LocalDateTime.of(
            time.getYear(), 
            time.getMonth(),
            time.getDayOfMonth(),
            16,
            0
        );
        LocalDateTime now = LocalDateTime.now();
        // All rules are chained with && , so the result is true only when every one of them holds.
        return (time.isBefore(endTime) || time.equals(endTime)) && (time.getHour() >= 8) &&
        (time.getMinute() == 0 || time.getMinute() == 30) && time.isAfter(now) && time.toLocalDate().equals(now.toLocalDate()) 
        && time.getSecond() == 0 && time.getNano() == 0;
    }

    /**
     * Check whether the medical staff member of this appointment is on duty on the
     * appointment day.
     *
     * getWorkingDays() returns an array of English day names such as
     * {"Monday","Wednesday","Friday"}. The day of week of the appointment time is converted
     * to the same format and compared with equalsIgnoreCase, so upper or lower case does not
     * matter. One match is enough to show that the staff member works that day.
     *
     * @param appointment the appointment being checked
     * @return true if the staff member's working days contain the day of week of this
     *         appointment; false otherwise
     */
    // Used while booking: the checked date comes from the appointment itself.
    public boolean isWorkingDay(Appointment appointment){
        String[] workingDays;
        LocalDateTime appointmentTime;
        workingDays = appointment.getMedicalStaff().getWorkingDays();
        appointmentTime = appointment.getAppointmentTime();
        for (String workingDay : workingDays){
            // getDayOfWeek().toString() produces an upper case name such as "MONDAY",
            // so the comparison ignores case.
            if (appointmentTime.getDayOfWeek().toString().equalsIgnoreCase(workingDay)){
                return true;
            }    
        }
        return false;
    }

    /**
     * Print every stored appointment to the console, with one blank line after each record.
     * The content of a record is formatted by Appointment.toString(); this method only
     * handles the empty-list message and printing one record after another.
     * It returns early when the list is empty, so no unnecessary blank lines are printed.
     */
    public void displayAppointments(){
        if (appointments.isEmpty()){
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment appointment : appointments){
            System.out.println(appointment);
            System.out.println(" ");
        }
    }

    /**
     * Check whether one specific time slot of one specific staff member is already booked.
     * This is a single-slot check, used by displayAvailableTimeSlots to test each time
     * slot of the day one by one.
     *
     * @param medicalStaff the staff member to check
     * @param bookTime the exact time slot to check
     * @return true if this staff member already has an appointment at that time;
     *         false if the slot is free
     */
    // true means the slot is already booked, false means it is free.
    public boolean isTimeBooked(MedicalStaff medicalStaff, LocalDateTime bookTime){
        
        for (Appointment appointment : appointments){
            if ((appointment.getAppointmentTime().equals(bookTime)) && (medicalStaff.getStaffId()== appointment.getMedicalStaff().getStaffId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Print all time slots of today for which the given staff member can still be booked.
     *
     * The steps are:
     * 1. firstTime starts at 08:00 today;
     * 2. if the staff member works today, the loop runs 17 times, because 08:00 to 16:00 at
     *    30-minute intervals gives (16 - 8) * 2 + 1 = 17 slots;
     * 3. a slot is printed only when it is free and still in the future, and then
     *    hasAvailableSlot is set to true;
     * 4. firstTime is moved forward 30 minutes after every pass, which walks through the
     *    slots in order;
     * 5. if nothing was printed, today is fully booked; if the staff member does not work
     *    today, the whole loop is skipped.
     *
     * @param medicalStaff the staff member whose free time slots should be listed
     */
    public void displayAvailableTimeSlots(MedicalStaff medicalStaff){
        LocalDateTime now = LocalDateTime.now();
        // Start from 08:00 today, the first bookable time slot.
        LocalDateTime firstTime = LocalDateTime.of(now.getYear(),now.getMonthValue(),now.getDayOfMonth(), 8,0);
        boolean hasAvailableSlot = false;
        // The overload below takes a MedicalStaff argument and checks today's date.
        if (isWorkingDay(medicalStaff)){
            for (int i = 0; i < 17; i++){
                // Condition 1: the slot is not booked yet. Condition 2: the slot has not passed.
                if ((! isTimeBooked(medicalStaff, firstTime)) && (firstTime.isAfter(now))){
                    System.out.println(firstTime);
                    hasAvailableSlot = true;
                }
                // Add 30 minutes each pass: 08:00 -> 08:30 -> 09:00 ... -> 16:00
                firstTime = firstTime.plusMinutes(30);
            }
            if (!hasAvailableSlot){
                System.out.println("No available time slots left for this doctor. Please choose another doctor.");
            }
        } else {
            System.out.println("This doctor is not working today. Please choose another doctor.");
        }
    }
    
    /**
     * Check whether the given staff member is on duty today, using the current system date.
     *
     * This is an overload: the argument is a medical staff object rather than an appointment,
     * and it tests today instead of the date stored in an appointment. It is used only when
     * looking up the time slots that are free today.
     *
     * @param medicalStaff the staff member to check
     * @return true if today's day of week appears in the staff member's working days;
     *         false otherwise
     */
    // true means the staff member works today, false means today is a day off.
    public boolean isWorkingDay(MedicalStaff medicalStaff){
        String[] workingDays;
        LocalDateTime now = LocalDateTime.now();
        workingDays = medicalStaff.getWorkingDays();
        
        for (String workingDay : workingDays){
            // Compare today's day of week with the staff member's working days.
            if (now.getDayOfWeek().toString().equalsIgnoreCase(workingDay)){
                return true;
            }    
        }
        return false;
    }
}
