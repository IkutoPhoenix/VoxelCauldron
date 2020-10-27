package ovh.nemesis.cauldron;

public class Material {

    private MaterialType materialType = MaterialType.DIFFUSE;
    private MaterialProperty weight = new MaterialProperty("_weight", "weigth", 1f), roughness = new MaterialProperty("_rough", "roughness", 0.1f), specular_metal = new MaterialProperty("_spec", "specular", 0.5f), specular_plastic = new MaterialProperty("_spec_p", "specular_plastic", 0.5f), refraction = new MaterialProperty("_ior", "refraction", 0.3f), attenuation = new MaterialProperty("_att", "attenuation", 0f), power = new MaterialProperty("_flux", "power", 0f), mix = new MaterialProperty("_gw", "mix", 0.7f), phase_0 = new MaterialProperty("_g0", "phase_0", -0.5f), phase_1 = new MaterialProperty("_g1", "phase_1", 0.8f), ldr = new MaterialProperty("_ldr", "ldr", 0f);
    /*
     *  diffuse : nothing
     *  metal : weight, specular_metal (_spec), roughness (_rough)
     *  plastic : weight, specular_plastic (_spec_b), roughness (_rough)
     *  glass : weight, refraction (_ior) [0.0;2.0], roughness (_rough), attenuation (_att)
     *  cloud : weight, mix (_gw), phase_0 (_g0) [-0.90;0.90], phase_1 (_g1) [-0.90;0.90]
     *  emission : weight, power (_flux) [0;4], ldr
     */
    private MaterialProperty[] properties = {weight, roughness, specular_metal, specular_plastic, refraction, attenuation, power, mix, phase_0, phase_1, ldr};

    public Material (MaterialType materialType) {
        this.materialType = materialType;
    }

    public Material () {

    }

    public void setValue (String key, float value) {
        for (MaterialProperty m : properties) {
            if (m.getKey().equals(key) || m.getFriendlyName().equalsIgnoreCase(key)) m.setValue(value);
        }
    }

    public float getValue (String key) {
        for (MaterialProperty m : properties) {
            if (m.getKey().equals(key) || m.getFriendlyName().equalsIgnoreCase(key)) return m.getValue();
        }
        return 0f;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public MaterialProperty[] getList() {
        return properties;
    }
}
