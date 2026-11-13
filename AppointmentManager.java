import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class AppointmentManager {
    private ArrayList<Appointment> appointments = new ArrayList<>();

    public boolean addAppointment(Appointment appointment){
        if (isValidAppointmentTime(appointment.getAppointmentTime())){
            if (hasConflict(appointment)){
                return false;
            } else {
                appointments.add(appointment);
                return true;
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
        appointments.sort(new Comparator<Appointment>(){
            @Override
            public int compare(Appointment a, Appointment b){
                return a.getAppointmentTime().compareTo(b.getAppointmentTime());
            }
        });
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
        return (time.isBefore(endTime) || time.equals(endTime)) && (time.getHour() >= 8) &&
        (time.getMinute() == 0 || time.getMinute() == 30);
    }

    public void displayAppointments(){
        for (Appointment appointment : appointments){
            System.out.println(appointment);
            System.out.println(" ");
        }
    }
}
