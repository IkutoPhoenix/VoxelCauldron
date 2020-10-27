package ovh.nemesis.cauldron;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private List<Voxel> voxels = new ArrayList<Voxel>();

    public List<Voxel> getVoxels() {
        return voxels;
    }

    public void setVoxels(List<Voxel> voxels) {
        this.voxels = voxels;
    }

    public void addVoxel(Voxel voxel) {
        voxels.add(voxel);
    }

    public void removeVoxel(Voxel voxel) {
        voxels.remove(voxel);
    }

    public Coordinates getMaxCoords() {
        int x_max = 0, y_max = 0, z_max = 0;
        for (Voxel v : voxels) {
            if (v.getX() + 1 > x_max) x_max = v.getX() + 1;
            if (v.getY() + 1 > y_max) y_max = v.getY() + 1;
            if (v.getZ() + 1 > z_max) z_max = v.getZ() + 1;
        }
        return new Coordinates(x_max, y_max, z_max);
    }
}
