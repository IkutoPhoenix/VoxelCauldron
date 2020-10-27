package ovh.nemesis.cauldron;

import com.sun.istack.internal.NotNull;

public class MaterialProperty {

    private String key, friendlyName = null;
    private float value = 0f;

    public MaterialProperty(@NotNull String key, String friendlyName, float value) {
        this.key = key;
        this.friendlyName = friendlyName;
        this.value = value;
    }

    public MaterialProperty(@NotNull String key, float value) {
        this.key = key;
        this.value = value;
    }

    public MaterialProperty(@NotNull String key) {
        this.key = key;
    }

    public MaterialProperty(@NotNull String key, String friendlyName) {
        this.friendlyName = friendlyName;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
