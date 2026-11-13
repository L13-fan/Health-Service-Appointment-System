/**
 * @author Yifan Liu  
 * number : 25259081
 * This is an abstract base class.
 * It mainly stores the common attributes of the doctors and provides constructors to initialize these attributes. The parent class provides a common implementation of the toString() method.
 * MedicalStaff is an abstract class because it provides the common attributes and behaviors shared by all staff members, while the specific services are implemented by the subclasses.
 */
abstract class MedicalStaff{
    private String name;
    private int staffId;
    private String phone;
    private String department;
    private int yearsOfExperience;
    private String title;
    private double registrationFee;
    private String introduction;
    private String[] workingDays;

    public abstract void provideService();
    
    /**
     * Constructs a MedicalStaff object with the specified name, staffId, phone, department, yearsOfExperience, title, registrationFee, introduction, workingDays.
     * @param name the name of the medical staff member
     * @param staffId the staff member's ID
     * @param phone the staff member's contact number
     * @param department the department where the staff member works
     * @param yearsOfExperience the number of years of experience of the staff
     * @param title the title of medical staff
     * @param registrationFee the registration fee of medical staff
     * @param introduction the personal introduction of the Staff
     * @param workingDays what is saved is the working days of the staff during a week.
     */
    public MedicalStaff(String name, int staffId, String phone, String department, int yearsOfExperience, 
        String title, double registrationFee, String introduction,String[] workingDays){
        this.name = name;
        this.staffId = staffId;
        this.phone = phone;
        this.department = department;
        this.yearsOfExperience = yearsOfExperience;
        this.title = title;
        this.registrationFee = registrationFee;
        this.introduction = introduction;
        this.workingDays = workingDays.clone();
    }

    // Return the name of the staff member
    public String getName(){
        return name;
    }

    // Return the ID of the staff member
    public int getStaffId(){
        return staffId;
    }

    // Return the staff member's mobile phone number
    public String getPhone(){
        return phone;
    }

    // Return the department of the staff member
    public String getDepartment(){
        return department;
    }

    // Return the number of years of work experience of the staff members
    public int getYearsOfExperience(){
        return yearsOfExperience;
    }

    // Return the title of the staff member
    public String getTitle(){
        return title;
    }

    // Return of the registration fee for the staff
    public double getRegistrationFee(){
        return registrationFee;
    }

    // Return the personal introduction of the staff members
    public String getIntroduction(){
        return introduction;
    }

    // An array of the weekly working days of the staff members
    public String[] getWorkingDays(){
        return workingDays.clone();
    }

    @Override
    public String toString(){
        return String.format("Name: %s\nStaff Id: %d\nPhone: %s\nDepartment: %s\nYear of Experience: %d\nTitle: %s\nRegistration Fee: %.2f\nPersonal Introduction: %s\nWorking Days: %s", name, staffId, phone, department, yearsOfExperience, title, registrationFee, introduction, String.join(", ", workingDays));
    }
}