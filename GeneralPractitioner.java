/**
 * @author Yifan Liu
 * number: 25259081
 * This is a represents of GeneralPractitioner, and it is a subclass of MedicalStaff.
 */

/**
 * Initialize the inherited attributes from the parent class using nine parameters.
 * @param name the name of the general practitioner member
 * @param staffId the staff member's ID
 * @param phone the staff member's contact number
 * @param department the department where the staff member works
 * @param yearsOfExperience the number of years of experience of the staff
 * @param title the title of general practitioner
 * @param registrationFee the registration fee of general practitoner
 * @param introduction the personal introduction of the staff
 * @param workingDays what is saved is the working days of the staff during a week.
 */
class GeneralPractitioner extends MedicalStaff{
    public GeneralPractitioner(String name, int staffId, String phone, String department, 
        int yearsOfExperience, String title, double registrationFee, String introduction,
        String[] workingDays){
        super(name, staffId, phone, department, yearsOfExperience, title, registrationFee, 
            introduction,workingDays);

    }

    // This method overrides the abstract method of the parent class and demonstrates two services provided by general practitioners: general medical consultations and basic health assessments.
    @Override
    public void provideService(){
        System.out.println("Provides general medical consultations.");
        System.out.println("Provides basic health assessments.");
    }
} 