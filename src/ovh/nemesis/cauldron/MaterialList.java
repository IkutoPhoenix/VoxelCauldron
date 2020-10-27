package ovh.nemesis.cauldron;

public class MaterialList {

    private Material[] materials = new Material[255];

    public MaterialList () {
        for (int i = 0; i < materials.length; i++) {
            materials[i] = new Material();
        }
    }

    public void setMaterial (int index, Material material) {
        materials[index] = material;
    }

    public Material[] getMaterials() {
        return materials;
    }

}
