/**
 * @author Yifan Liu
 * number: 25259081
 * This is a represents of nurse. It is a subclass that inherits from MedicalStaff.
 * It not only initializes the inherited attributes from the parent class using nine parameters, but also adds an exclusive attribute: nursingSpecialty.
 */
class Nurse extends MedicalStaff{
    // This indicates the professional field of the nurse.
    private String nursingSpecialty;

    /**
     * The first nine parameters are used to initialize the attributes of the parent class.
     * The last parameter initializes the nursingSpecialty attribute of the Nurse object itself.
     * @param name the name of the nurse 
     * @param staffId the staff member's ID
     * @param phone the staff member's contact number
     * @param department the department where the staff member works
     * @param yearsOfExperience the number of years of experience of the staff
     * @param title the title of nurse
     * @param registrationFee the registration fee of nurse
     * @param introduction the personal introduction of the staff
     * @param workingDays the working days of the nurse per week
     * @param nursingSpecialty The professional field of nurses
     */
    public Nurse(String name, int staffId, String phone, String department, 
        int yearsOfExperience, String title, double registrationFee, String introduction,
        String[] workingDays, String nursingSpecialty){
        super(name, staffId, phone, department, yearsOfExperience, title, registrationFee, 
            introduction,workingDays);
        this.nursingSpecialty = nursingSpecialty;
    }
    
    // Return the professional field of nursing.
    public String getNursingSpecialty(){
        return nursingSpecialty;
    }

    // Display the nursing services provided by the nurses.
    @Override
    public void provideService(){
        System.out.println("Provides general nursing care, emergency nursing care, and critical care.");
    }
}
