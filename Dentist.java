class Dentist extends MedicalStaff{
    private String treatmentTypes;

    public Dentist(String name, int staffId, String phone, String department, 
        int yearsOfExperience, String title, double registrationFee, String introduction,
        String treatmentTypes){
        super(name, staffId, phone, department, yearsOfExperience, title, registrationFee, 
            introduction);
        this.treatmentTypes = treatmentTypes;
    }        

    public String getTreatmentTypes(){
        return treatmentTypes;
    }

    @Override
    public void provideService(){
        System.out.println("We offer teeth cleaning, root canal treatment and filling of teeth.");
    }
}