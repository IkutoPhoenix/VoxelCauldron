package ovh.nemesis.cauldron;

import java.util.ArrayList;
import java.util.List;

public class exportToVox {

    public static byte[] exportToByteArray(Model model, Palette palette) {
        List<Model> models = new ArrayList<>();
        models.add(model);
        return exportToByteArray(models, palette);
    }

    public static byte[] exportToByteArray(List<Model> models, Palette palette) {
        List<Byte> bytes = export(models, palette);

        byte[] array = new byte[bytes.size()];

        for (int i = 0; i < bytes.size(); i++) {
            array[i] = bytes.get(i);
        }

        return array;
    }

    public static List<Byte> export(Model model, Palette palette) {
        List<Model> models = new ArrayList<>();
        models.add(model);
        return export(models, palette);
    }

    public static List<Byte> export(List<Model> models, Palette palette) {
        List<Byte> temp;
        int size = 0;

        List<Byte> bytes = new ArrayList<>(initFile());

        temp = initPACK(models.size());
        bytes.addAll(temp);
        size += temp.size();

        for (Model model : models) {
            Coordinates maxCoords = model.getMaxCoords();
            temp = initSIZE(maxCoords);
            bytes.addAll(temp);
            size += temp.size();

            temp = initXYZI(model);
            bytes.addAll(temp);
            size += temp.size();
        }

        temp = initRGBA(palette);
        bytes.addAll(temp);
        size += temp.size();

        bytes.set(16, (byte) (size & 0x000000ff));
        bytes.set(17, (byte) (size & 0x0000ff00));
        bytes.set(18, (byte) (size & 0x00ff0000));
        bytes.set(19, (byte) (size & 0xff000000));

        return bytes;
    }

    public static List<Byte> initFile() {
        List<Byte> bytes = new ArrayList<>();

        // 'VOX '
        bytes.addAll(charsToBytes('V', 'O', 'X', ' '));

        // Version number (150)
        bytes.addAll(decToHex(150));

        // Chunk 'MAIN'
        bytes.addAll(charsToBytes('M', 'A', 'I', 'N'));

        // Size of chunk content of 'MAIN' (0)
        bytes.addAll(decToHex(0));

        // Size of children chunks of 'MAIN' (0 but changed later)
        bytes.addAll(decToHex(0));

        return bytes;
    }

    public static List<Byte> initPACK(int n_models) {
        List<Byte> bytes = new ArrayList<>();

        // Name of Chunk
        bytes.addAll(charsToBytes('P', 'A', 'C', 'K'));

        // Size of chunk content (4)
        bytes.addAll(decToHex(4));

        // Size of children chunks (0)
        bytes.addAll(decToHex(0));

        // Number of models (convert dec to hex)
        bytes.addAll(decToHex(n_models));

        return bytes;
    }

    public static List<Byte> initSIZE(Coordinates coordinates) {
        List<Byte> bytes = new ArrayList<>();

        // Name of Chunk
        bytes.addAll(charsToBytes('S', 'I', 'Z', 'E'));

        // Size of chunk content (12)
        bytes.addAll(decToHex(12));

        // Size of children chunks (0)
        bytes.addAll(decToHex(0));

        // Model's x size
        bytes.addAll(decToHex(coordinates.getX()));

        // Model's y size
        bytes.addAll(decToHex(coordinates.getY()));

        // Model's z size
        bytes.addAll(decToHex(coordinates.getZ()));

        return bytes;
    }

    public static List<Byte> initXYZI (Model model) {
        List<Byte> bytes = new ArrayList<>();

        // Name of Chunk
        bytes.addAll(charsToBytes('X', 'Y', 'Z', 'I'));

        // Size of Chunk content (4 + 4 * n_voxels)
        bytes.addAll(decToHex(4 + (4 * model.getVoxels().size())));

        // Size of Children Chunks (0)
        bytes.addAll(decToHex(0));

        // Number of Voxels
        bytes.addAll(decToHex(model.getVoxels().size()));

        // Each Voxel elements
        for (Voxel voxel : model.getVoxels()) {
            bytes.add((byte) voxel.getX());
            bytes.add((byte) voxel.getY());
            bytes.add((byte) voxel.getZ());
            bytes.add((byte) voxel.getI());
        }

        return bytes;
    }

    public static List<Byte> initRGBA (Palette palette) {
        List<Byte> bytes = new ArrayList<>();

        if (palette == null) {
            return bytes;
        }

        if (palette.getColors().length != 255) {
            return bytes;
        }

        // Name of Chunk
        bytes.addAll(charsToBytes('R', 'G', 'B', 'A'));

        // Size of chunk content (4*255)
        bytes.addAll(decToHex(4*255));

        // Each Colors
        for (Color color : palette.getColors()) {
            bytes.add((byte) color.getR());
            bytes.add((byte) color.getG());
            bytes.add((byte) color.getB());
            bytes.add((byte) color.getA());
        }

        return bytes;
    }

    public static byte charToByte(char c) { // Char to Byte
        return ((byte) (c & 0xff));
    }

    public static List<Byte> charsToBytes (char c0, char c1, char c2, char c3) {
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) (c0 & 0xff));
        bytes.add((byte) (c1 & 0xff));
        bytes.add((byte) (c2 & 0xff));
        bytes.add((byte) (c3 & 0xff));

        return bytes;
    }

    public static List<Byte> repeat(int n, int hex) {
        List<Byte> bytes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            bytes.add((byte) hex);
        }
        return bytes;
    }

    public static List<Byte> decToHex (int dec) {
        List<Byte> bytes = new ArrayList<>();

        bytes.add((byte) (dec & 0x000000ff));
        bytes.add((byte) (dec & 0x0000ff00));
        bytes.add((byte) (dec & 0x00ff0000));
        bytes.add((byte) (dec & 0xff000000));

        return bytes;
    }
}
