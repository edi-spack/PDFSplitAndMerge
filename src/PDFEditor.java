import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PDFEditor {
    private final ArrayList<PDFPage> pages;
    private int highlightedPage;

    public PDFEditor() {
        pages = new ArrayList<PDFPage>();
        highlightedPage = -1;
    }

    public void addPDFDocument(File file) throws IOException {
        PDDocument document = Loader.loadPDF(file);
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

        highlightedPage = -1;
    }

    public void highlightPage(int pageIndex) {
        if(pageIndex >= 0 && pageIndex < pages.size())
            highlightedPage = pageIndex;
        else
            highlightedPage = -1;
    }

    public void moveUpHighlightedPage() {
        if(highlightedPage > 0) {
            Collections.swap(pages, highlightedPage, highlightedPage - 1);
            highlightedPage--;
        }
    }

    public void moveDownHighlightedPage() {
        if(highlightedPage >= 0 && highlightedPage < pages.size() - 1) {
            Collections.swap(pages, highlightedPage, highlightedPage + 1);
            highlightedPage++;
        }
    }

    public void selectPage(int pageIndex) {
        pages.get(pageIndex).select();
    }

    public void unselectPage(int pageIndex) {
        pages.get(pageIndex).unselect();
    }

    public void selectAllPages() {
        for(PDFPage page : pages)
            page.select();
    }

    public void unselectAllPages() {
        for(PDFPage page : pages)
            page.unselect();
    }

    public void invertSelection() {
        for(PDFPage page : pages) {
            if(page.isSelected())
                page.unselect();
            else
                page.select();
        }
    }

    public void exportSelected(File file) throws IOException {
        PDDocument document = new PDDocument();
        PDFMergerUtility mergerUtility = new PDFMergerUtility();

        for(PDFPage page : pages) {
            if(page.isSelected())
                mergerUtility.appendDocument(document, page.getPDDocument());
        }

        file = new File(file.getAbsolutePath() + ".pdf");
        document.save(file);
    }

    public void exportAll(File file) throws IOException {
        PDDocument document = new PDDocument();
        PDFMergerUtility mergerUtility = new PDFMergerUtility();

        for(PDFPage page : pages)
            mergerUtility.appendDocument(document, page.getPDDocument());

        file = new File(file.getAbsolutePath() + ".pdf");
        document.save(file);
    }

    public void multiExportSelected(File file) throws IOException {
        for(int i = 0; i < pages.size(); i++) {
            if(pages.get(i).isSelected()) {
                File pageFile = new File(file.getAbsolutePath() + "-" + (i + 1) + ".pdf");
                pages.get(i).getPDDocument().save(pageFile);
            }
        }
    }

    public void multiExportAll(File file) throws IOException {
        for(int i = 0; i < pages.size(); i++) {
            File pageFile = new File(file.getAbsolutePath() + "-" + (i + 1) + ".pdf");
            pages.get(i).getPDDocument().save(pageFile);
        }
    }

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
