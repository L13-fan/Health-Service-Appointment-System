class Surgeon extends MedicalStaff{
    private String specialty;

    public Surgeon(String name, int staffId, String phone, String department, 
        int yearsOfExperience, String title, double registrationFee, String introduction,
        String specialty){
        super(name, staffId, phone, department, yearsOfExperience, title, registrationFee, 
            introduction);
        this.specialty = specialty;
    }        

    public String getSpecialty(){
        return specialty;
    }

    @Override
    public void provideService(){
        System.out.println("Provides surgical services such as cardiothoracic surgery, neurosurgery, and orthopedic surgery.");
    }
}
