package component;


public class Player {

    // Static variable single_instance of type
    private static Player single_instance = null;

    public int skillPoints;
    public int credits;
    public int[] skills;
    public String name;
    public Region currentRegion;

    private Player() {
        skillPoints = 0;
        credits = 0;
        skills = new int[]{0, 0, 0, 0, };
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
