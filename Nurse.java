class Nurse extends MedicalStaff{
    private String nursingSpecialty;

    public Nurse(String name, int staffId, String phone, String department, 
        int yearsOfExperience, String title, double registrationFee, String introduction,
        String[] workingdays, String nursingSpecialty){
        super(name, staffId, phone, department, yearsOfExperience, title, registrationFee, 
            introduction,workingdays);
        this.nursingSpecialty = nursingSpecialty;
    }
    
    public String getNursingSpecialty(){
        return nursingSpecialty;
    }

    @Override
    public void provideService(){
        System.out.println("Offer courses in general nursing, emergency nursing, and critical care nursing");
    }
}
