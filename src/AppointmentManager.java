import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class AppointmentManager {
    private ArrayList<Appointment> appointments = new ArrayList<>();

    public boolean addAppointment(Appointment appointment){
        if (appointment == null){
            throw new IllegalArgumentException("Appointment cannot be null.");
        }
        if (isValidAppointmentTime(appointment.getAppointmentTime())){
            if (isWorkingDay(appointment)){
                if (hasConflict(appointment)){
                    return false;
                } else {
                    appointments.add(appointment);
                    return true;
                }
            } else {
                return false;
            }
            
        } else {
            return false;
        }
        
    }

    public int getAppointmentCount(){
        return appointments.size();
    }

    public ArrayList<Appointment> findAppointmentsByPatientPhone(String phone){
        ArrayList<Appointment> results = new ArrayList<>();
        for (Appointment appointment : appointments){
            if (appointment.getPatientPhone().equals(phone)){
                results.add(appointment);
            }
        }
        return results;
    }

    public ArrayList<Appointment> findAppointmentsByStaffId(int staffId){
        ArrayList<Appointment> results = new ArrayList<>();
        for (Appointment appointment : appointments){
            if (appointment.getMedicalStaff().getStaffId() == staffId){
                results.add(appointment);
            }
        }
        return results;
    }

    public void sortAppointmentsByTime(){
        for (int j = 0; j < appointments.size()-1; j++){
            for (int i = 0; i<appointments.size()-1-j; i++) {
                if (appointments.get(i).getAppointmentTime().isAfter(appointments.get(i+1).getAppointmentTime())){
                    Appointment temp = appointments.get(i); 
                    appointments.set(i, appointments.get(i+1));
                    appointments.set(i+1, temp);
                }
            }
        }
    }

    public boolean cancelAppointment(String phone, LocalDateTime targetTime){
        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()){
            Appointment appointment = iterator.next();
            if (appointment.getPatientPhone().equals(phone) && 
                appointment.getAppointmentTime().equals(targetTime)){
                iterator.remove();
                return true;
            } 
            
        }

        return false;
    }
    
    public boolean cancelAppointment(int staffId, LocalDateTime targetTime){
        for (int i = 0; i < appointments.size(); i++){
            if ((appointments.get(i).getMedicalStaff().getStaffId() == staffId) && (appointments.get(i).getAppointmentTime().equals(targetTime))){
                appointments.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean hasConflict(Appointment newAppointment){
        for (Appointment appointment : appointments){
            if (newAppointment.getMedicalStaff().getStaffId() == appointment.getMedicalStaff().getStaffId()
                && newAppointment.getAppointmentTime().equals(appointment.getAppointmentTime())){
                return true;
            }
        }
        return false;
    }

    public boolean isValidAppointmentTime(LocalDateTime time){
        LocalDateTime endTime = LocalDateTime.of(
            time.getYear(), 
            time.getMonth(),
            time.getDayOfMonth(),
            16,
            0
        );
        LocalDateTime now = LocalDateTime.now();
        return (time.isBefore(endTime) || time.equals(endTime)) && (time.getHour() >= 8) &&
        (time.getMinute() == 0 || time.getMinute() == 30) && time.isAfter(now) && time.toLocalDate().equals(now.toLocalDate());
    }

    public boolean isWorkingDay(Appointment appointment){
        String[] workingDays;
        LocalDateTime appointmentTime;
        workingDays = appointment.getMedicalStaff().getWorkingDays();
        appointmentTime = appointment.getAppointmentTime();
        for (String workingDay : workingDays){
            if (appointmentTime.getDayOfWeek().toString().equalsIgnoreCase(workingDay)){
                return true;
            }    
        }
        return false;
    }

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

    // 传入工作人员对象和要预约的时间，他从预约里有的找
    public boolean isTimeBooked(MedicalStaff medicalStaff, LocalDateTime bookTime){
        
        for (Appointment appointment : appointments){
            if ((appointment.getAppointmentTime().equals(bookTime)) && (medicalStaff.getStaffId()== appointment.getMedicalStaff().getStaffId())){
                return true;
            }
        }
        return false;
    }
}

//LocalDateTime startTime = LocalDateTime.of(2026, 11, 14, 8, 0);
//for (int i = 0; i < 17; i++){
    //System.out.println(startTime);
    //startTime = startTime.plusMinutes(30);
//}

