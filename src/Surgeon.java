/**
 * @author Yifan Liu
 * number: 25259081
 * This is a representation of surgeon, and it is a subclass that inherits from MedicalStaff.
 * It not only initializes the inherited attributes from the parent class using nine parameters, but also adds an exclusive attribute: specialty
 */ 
class Surgeon extends MedicalStaff{
    // This attribute indicates the specialty of the surgeon.
    private String specialty;

    /**
     * The first nine parameters are used to initialize the attributes of the parent class.
     * The last parameter is used to initialize the attributes of Surgeon itself.
     * @param name the name of the surgeon member
     * @param staffId the staff member's ID
     * @param phone the staff member's contact number
     * @param department the department where the staff member works
     * @param yearsOfExperience the number of years of experience of the staff
     * @param title the title of surgeon
     * @param registrationFee the registration fee of surgeon
     * @param introduction the personal introduction of the staff
     * @param workingDays the working days of the surgeon per week
     * @param specialty the professional field of a surgeon
     */
    public Surgeon(String name, int staffId, String phone, String department, 
        int yearsOfExperience, String title, double registrationFee, String introduction,
        String[] workingDays, String specialty){
        super(name, staffId, phone, department, yearsOfExperience, title, registrationFee, 
            introduction,workingDays);
        this.specialty = specialty;
    }        

    // Return the surgeon's area of expertise
    public String getSpecialty(){
        return specialty;
    }

    // Display the surgical services provided by the surgeons
    @Override
    public void provideService(){
        System.out.println("Provides surgical services such as cardiothoracic surgery, neurosurgery, and orthopedic surgery.");
    }
}
