package materials;

public enum SKILL {
    PILOT("materials/image/YellowSkillBar.png"),
    FIGHTER("materials/image/BlackSkillBar.png"),
    MERCHANT("materials/image/PurpleSkillBar.png"),
    ENGINEER("materials/image/RedSkillBar.png");

    String urlSkill;

    private SKILL(String urlSkill) {
        this.urlSkill = urlSkill;
    }

    public String getUrl() {
        return this.urlSkill;
    }
}
