package ovh.nemesis.cauldron;

public class Voxel {

    private int x, y , z, i;

    public Voxel(int x, int y, int z, int i) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.i = i;
    }

    public Voxel (int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.i = 255;
    }

    public Voxel (Coordinates coords, int i) {
        this.x = coords.getX();
        this.y = coords.getY();
        this.z = coords.getZ();
        this.i = i;
    }

    public Voxel (Coordinates coords) {
        this.x = coords.getX();
        this.y = coords.getY();
        this.z = coords.getZ();
        this.i = 255;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
