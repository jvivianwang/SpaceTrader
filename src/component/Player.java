package component;

//import application.Main;

public class Player {

    private int skillPoints;
    private int credits;
    private int[] skills;
    private String name;
    private Region currentRegion;

    public Player() {
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

    public  void setCurrentRegion(Region currentRegion) {
        this.currentRegion = currentRegion;
    }
}
