package component;

public class Player {

    private int skillPoints;
    private int credits;

    public Player() {
        skillPoints = 0;
        credits = 0;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getCredits() {
        return credits;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
