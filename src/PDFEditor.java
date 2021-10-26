import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFEditor {
    private ArrayList<PDFPage> pages;
    private int highlightedPage;

    public PDFEditor() {
        pages = new ArrayList<PDFPage>();
        highlightedPage = 0;
    }

    public void addPDFDocument(File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        Splitter splitter = new Splitter();
        List<PDDocument> splitPages = splitter.split(document);
        
        for(PDDocument splitPage : splitPages) {
            PDFPage page = new PDFPage(splitPage);
            pages.add(page);
        }
    }

    public void removeSelectedPages() {
        boolean moreSelected = true;
        int i;
        while(moreSelected) {
            moreSelected = false;
            i = 0;
            while(i < pages.size()) {
                if(pages.get(i).isSelected()) {
                    pages.remove(i);
                    moreSelected = true;
                    break;
                }
                i++;
            }
        }

        highlightedPage = 0;
    }

    public void highlightPage(int pageIndex) {
        //
    }

    public void selectPage(int pageIndex) {
        pages.get(pageIndex).select();
    }

    public void unselectPage(int pageIndex) {
        pages.get(pageIndex).unselect();
    }

    public void selectAllPages() {
        //
    }

    public void unselectAllPages() {
        //
    }

    public void invertSelection() {
        //
    }

    public void moveUpHighlightedPage() {
        //
    }

    public void moveDownHighlightedPage() {
        //
    }

    // Exports

    public int getSize() {
        return pages.size();
    }

    public Image getThumbnail(int index) {
        return pages.get(index).getThumbnail();
    }

    public boolean isSelected(int index) {
        return pages.get(index).isSelected();
    }
}
