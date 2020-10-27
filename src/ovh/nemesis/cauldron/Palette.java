package ovh.nemesis.cauldron;

public class Palette {

    private Color[] colors = new Color[255];

    public Palette() {
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(0, 0, 0);
        }
    }

    public void setColor(int index, Color color) {
        colors[index] = color;
    }

    public Color[] getColors() {
        return colors;
    }
}
