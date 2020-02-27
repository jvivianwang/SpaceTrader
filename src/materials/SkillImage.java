package materials;

public enum SkillImage {
    PILOT("materials/image/YellowSkillBar.png"),
    FIGHTER("materials/image/BlackSkillBar.png"),
    MERCHANT("materials/image/PurpleSkillBar.png"),
    ENGINEER("materials/image/RedSkillBar.png");

    private String urlSkill;

    SkillImage(String urlSkill) {
        this.urlSkill = urlSkill;
    }

    public String getUrl() {
        return this.urlSkill;
    }
}
