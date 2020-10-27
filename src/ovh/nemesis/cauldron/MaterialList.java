package ovh.nemesis.cauldron;

public class MaterialList {

    private Material[] materials = new Material[255];

    public MaterialList () {
        for (int i = 0; i < materials.length; i++) {
            materials[i] = new Material();
        }
    }

    public void setMaterial (int id, Material material) {
        materials[id + 1] = material;
    }

    public Material[] getMaterials() {
        return materials;
    }

}
