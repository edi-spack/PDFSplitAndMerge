import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.Image;
import java.io.IOException;

public class PDFPage {
    private final PDDocument page;
    private boolean selected;

    public PDFPage(PDDocument page) {
        this.page = page;
        selected = false;
    }

    public PDDocument getPDDocument() {
        return page;
    }

    public Image getThumbnail() {
        Image thumbnail = null;
        try {
            thumbnail = new PDFRenderer(page).renderImage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thumbnail;
    }

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }
}
