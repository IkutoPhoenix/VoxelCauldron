package ovh.nemesis.cauldron;

import java.util.ArrayList;
import java.util.List;

public class exportToVox {

    public static byte[] exportToByteArray(Model model, Palette palette, MaterialList materialList) {
        List<Model> models = new ArrayList<>();
        models.add(model);
        return exportToByteArray(models, palette, materialList);
    }

    public static byte[] exportToByteArray(List<Model> models, Palette palette, MaterialList materialList) {
        List<Byte> bytes = export(models, palette, materialList);

        byte[] array = new byte[bytes.size()];

        for (int i = 0; i < bytes.size(); i++) {
            array[i] = bytes.get(i);
        }

        return array;
    }

    public static List<Byte> export(Model model, Palette palette, MaterialList materialList) {
        List<Model> models = new ArrayList<>();
        models.add(model);
        return export(models, palette, materialList);
    }

    public static List<Byte> export(List<Model> models, Palette palette, MaterialList materialList) {
        // New Byte list with file headers and MAIN chunk
        List<Byte> bytes = new ArrayList<>(initFile());

        // PACK chunk
        bytes.addAll(initPACK(models.size()));

        // SIZE and XYZI chunks
        for (Model model : models) {
            bytes.addAll(initSIZE(model.getMaxCoords()));
            bytes.addAll(initXYZI(model));
        }

        // RGBA Chunk
        bytes.addAll(initRGBA(palette));

        // MATL Chunks
        bytes.addAll(initMATL(materialList));

        // Size of children chunks (total bytes minus headers length)
        int size = bytes.size() - 20;

        // Change children chunks size
        bytes.set(16, (byte) (size & 0x000000ff));
        bytes.set(17, (byte) (size & 0x0000ff00));
        bytes.set(18, (byte) (size & 0x00ff0000));
        bytes.set(19, (byte) (size & 0xff000000));

        return bytes;
    }

    public static List<Byte> initFile() {
        List<Byte> bytes = new ArrayList<>();

        // 'VOX '
        bytes.addAll(stringToHex("VOX "));

        // Version number (150)
        bytes.addAll(decToHex(150));

        // Chunk 'MAIN'
        bytes.addAll(stringToHex("MAIN"));

        // Size of chunk content of 'MAIN' (0)
        bytes.addAll(decToHex(0));

        // Size of children chunks of 'MAIN' (0 but changed later)
        bytes.addAll(decToHex(0));

        return bytes;
    }

    public static List<Byte> initPACK(int n_models) {
        List<Byte> bytes = new ArrayList<>();

        // Name of Chunk
        bytes.addAll(stringToHex("PACK"));

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
        bytes.addAll(stringToHex("SIZE"));

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
        bytes.addAll(stringToHex("XYZI"));

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
        bytes.addAll(stringToHex("RGBA"));

        // Size of chunk content (4*255)
        bytes.addAll(decToHex(4*255));

        // Size of children chunks (0)
        bytes.addAll(decToHex(0));

        // Each Colors
        for (Color color : palette.getColors()) {
            bytes.add((byte) color.getR());
            bytes.add((byte) color.getG());
            bytes.add((byte) color.getB());
            bytes.add((byte) color.getA());
        }

        return bytes;
    }

    public static List<Byte> initMATL (MaterialList materialList) {
        List<Byte> bytes = new ArrayList<>();

        if (materialList == null) {
            return bytes;
        }

        if (materialList.getMaterials().length != 255) {
            return bytes;
        }

        // Each Material
        for (int i = 0; i < materialList.getMaterials().length; i++) {
            List<Byte> matBytes = new ArrayList<>();
            List<Byte> temp = new ArrayList<>();
            Material m = materialList.getMaterials()[i];

            // Name of chunk
            matBytes.addAll(stringToHex("MATL"));

            // Size of chunk content (changed later)
            matBytes.addAll(decToHex(0));

            // Size of children (0)
            matBytes.addAll(decToHex(0));

            // Id of Material (color id)
            matBytes.addAll(decToHex(i));

            // Number of properties (type + properties)
            matBytes.addAll(decToHex(1 + m.getList().length));

            // Type key : str key length, str key
            temp = stringToHex("_type");
            matBytes.addAll(decToHex(temp.size()));
            matBytes.addAll(temp);

            // Type value : str value length, str value (float)
            temp = stringToHex(m.getMaterialType().getType());
            matBytes.addAll(decToHex(temp.size()));
            matBytes.addAll(temp);

            // Each properties
            for (MaterialProperty mp : m.getList()) {
                // Property key : str key length, str key
                temp = stringToHex(mp.getKey());
                matBytes.addAll(decToHex(temp.size()));
                matBytes.addAll(temp);

                // Property value : str value length, str value (float)
                temp = stringToHex(String.format("%s", mp.getValue()));
                matBytes.addAll(decToHex(temp.size()));
                matBytes.addAll(temp);
            }

            // Change chunk content size (total bytes minus headers length)
            int size = matBytes.size() - 12;

            matBytes.set(4, (byte) (size & 0x000000ff));
            matBytes.set(5, (byte) (size & 0x0000ff00));
            matBytes.set(6, (byte) (size & 0x00ff0000));
            matBytes.set(7, (byte) (size & 0xff000000));

            bytes.addAll(matBytes);
        }

        return bytes;
    }

    public static List<Byte> stringToHex (String str) {
        List<Byte> bytes = new ArrayList<>();
        for (char c : str.toCharArray()) {
            bytes.add((byte) (c & 0xff));
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
