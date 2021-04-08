package ovh.nemesis.cauldron;

public class Palette {

    private Color[] colors = new Color[256];

    public Palette() {
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(0, 0, 0);
        }
    }

    public void setColor(int id, Color color) {
        colors[id - 1] = color;
    }

    public Color[] getColors() {
        return colors;
    }
}
