abstract class MedicalStaff{
    private String name;
    private int staffId;
    private String phone;
    private String department;
    private int yearsOfExperience;
    private String title;
    private double registrationFee;
    private String introduction;

    public abstract void provideService();
    
    public MedicalStaff(String name, int staffId, String phone, String department, int yearsOfExperience, 
        String title, double registrationFee, String introduction){
        this.name = name;
        this.staffId = staffId;
        this.phone = phone;
        this.department = department;
        this.yearsOfExperience = yearsOfExperience;
        this.title = title;
        this.registrationFee = registrationFee;
        this.introduction = introduction;
    }

    public String getName(){
        return name;
    }

    public int getStaffId(){
        return staffId;
    }

    public String getPhone(){
        return phone;
    }

    public String getDepartment(){
        return department;
    }

    public int getYearsOfExperience(){
        return yearsOfExperience;
    }

    public String getTitle(){
        return title;
    }

    public double getRegistrationFee(){
        return registrationFee;
    }

    public String getIntroduction(){
        return introduction;
    }

    @Override
    public String toString(){
        return String.format("Name: %s\nStaff Id: %d\nPhone: %s\nDepartment: %s\nYear of Experience: %d\nTitle: %s\nRegistration Fee: %.2f\nPersonal Introduction: %s", name, staffId, phone, department, yearsOfExperience, title, registrationFee, introduction);
    }
}