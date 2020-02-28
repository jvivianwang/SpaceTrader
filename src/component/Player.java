package component;


public class Player {

    // Static variable single_instance of type
    private static Player single_instance = null;

    private int skillPoints;
    private int credits;
    private int[] skills;
    private String name;
    private Region currentRegion;
    private int numberOfBroom; // not needed


    private Player() {
        skillPoints = 0;
        credits = 0;
        skills = new int[]{0, 0, 0, 0, };
        numberOfBroom = 0;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getCredits() {
        return credits;
    }

    public int[] getSkills() {
        return skills;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setSkills(int[] skills) {
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  Region getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(Region currentRegion) {
        this.currentRegion = currentRegion;
    }

    public void setNumberOfBroom(int numberOfBroom){
        this.numberOfBroom = this.numberOfBroom;
    }
    public int getNumberOfBroom(){
        return this.numberOfBroom;
    }

    public static Player getInstance() {
        if (single_instance == null) {
            synchronized (Player.class) {
                if (single_instance == null) {
                    single_instance = new Player();
                }
            }
        }
        return single_instance;
    }

}
