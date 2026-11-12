class GeneralPractitioner extends MedicalStaff{
    public GeneralPractitioner(String name, int staffId, String phone, String department, 
        int yearsOfExperience, String title, double registrationFee, String introduction){
        super(name, staffId, phone, department, yearsOfExperience, title, registrationFee, 
            introduction);

    }

    @Override
    public void provideService(){
        System.out.println("Provides general medical consultations.");
        System.out.println("Provides basic health assessments.");
    };
} 