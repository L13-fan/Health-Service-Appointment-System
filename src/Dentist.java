/**
 * @author Yifan Liu
 * number: 25259081
 * This is a class of dentists, and it is a subclass of MedicalStaff.
 * It not only initializes the inherited attributes from the parent class using nine parameters, but also adds an exclusive attribute: treatmentTpye.
 */
class Dentist extends MedicalStaff{
    
    // This attribute represents the type of treatment provided by the dentist.
    private String treatmentTypes;

    /**
     * The first nine parameters are used to initialize the attributes of the parent class.
     * The last treatmentTypes parameter is used to initialize the attributes of the dentist itself.
     * Verify the dentist's treatmentTypes and ensure that the treatmentTypes is not empty or null. Throw IllegalArgumentException if any argument is invalid.
     * @param name the name of the dentist 
     * @param staffId the staff member's ID
     * @param phone the staff member's contact number
     * @param department the department where the staff member works
     * @param yearsOfExperience the number of years of experience of the staff
     * @param title the title of dentist
     * @param registrationFee the registration fee of dentist
     * @param introduction the personal introduction of the staff
     * @param workingDays the working days of the dentist per week
     * @param treatmentTypes the dentist's treatments types
     */
    public Dentist(String name, int staffId, String phone, String department, 
        int yearsOfExperience, String title, double registrationFee, String introduction,
        String[] workingDays, String treatmentTypes){
        super(name, staffId, phone, department, yearsOfExperience, title, registrationFee, 
            introduction,workingDays);
        if (treatmentTypes == null || treatmentTypes.isEmpty()){
             throw new IllegalArgumentException("TreatmentTpyes cannot be empty.");
        }
        this.treatmentTypes = treatmentTypes;
    }        

    // Type of treatment provided by the dentist
    public String getTreatmentTypes(){
        return treatmentTypes;
    }

    // Rewrite the toString() method so that it not only displays the relevant information of the dentist, but also shows the dental treatments provided by the dentist.
    @Override
    public String toString(){
        return super.toString() + "\nStaff Type: Dentist" + String.format("\nTreatment types: %s", treatmentTypes);
    }

    // Display the treatmentTypes services provided by the dentist
    @Override
    public void provideService(){
        System.out.println(treatmentTypes);
    }
}