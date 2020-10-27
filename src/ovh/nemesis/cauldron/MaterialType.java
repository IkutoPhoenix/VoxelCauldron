package ovh.nemesis.cauldron;

public enum MaterialType {

    DIFFUSE("_diffuse"), METAL("_metal"), GLASS("_glass"), EMIT("_emit"), CLOUD("_media"), PLASTIC("_plastic");

    private String type;

    private MaterialType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
