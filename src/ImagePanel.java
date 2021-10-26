import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    public static final int NORMAL = 0;
    public static final int HOVER = 1;
    public static final int HIGHLIGHTED = 2;

    private Image backgroundImage;
    private JCheckBox checkBox;
    private int highlightState;

    private boolean firstRender = true;

    public ImagePanel(Image backgroundImage) {
        super();
        this.backgroundImage = backgroundImage;
        checkBox = null;
        highlightState = 0;
    }

    public int getHighlightState() {
        return highlightState;
    }

    public void setHighlightState(int highlightState) {
        if(highlightState >= 0 && highlightState <= 2)
            this.highlightState = highlightState;
        else
            this.highlightState = 0;

        super.updateUI();
    }

    public boolean getSelectState() {
        if(checkBox != null && checkBox.isSelected())
            return true;
        else
            return false;
    }

    public void setSelectState(boolean selectedState) {
        if(checkBox != null)
            checkBox.setSelected(selectedState);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(highlightState == NORMAL)
            super.setBackground(new Color(238, 238, 238));
        else if(highlightState == HOVER)
            super.setBackground(Color.LIGHT_GRAY);
        else if(highlightState == HIGHLIGHTED)
            super.setBackground(new Color(51, 104, 196));

        if(firstRender) {
            backgroundImage = backgroundImage.getScaledInstance(this.getWidth() - 20, (int)(this.getWidth() * 1.4142) - 20, Image.SCALE_SMOOTH);
            firstRender = false;
        }
        g.drawImage(backgroundImage, 10, 10, null);
    }

    @Override
    public Component add(Component component) {
        super.add(component);
        if(component instanceof JCheckBox && checkBox == null) {
            checkBox = (JCheckBox) component;
        }

        return component;
    }
}
