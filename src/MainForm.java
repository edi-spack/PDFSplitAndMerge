import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class MainForm {
    private JPanel rootPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton invertButton;
    private JButton selectAllButton;
    private JButton moveUpButton;
    private JButton unselectAllButton;
    private JButton moveDownButton;
    private JButton exportSelectedButton;
    private JButton multiExportSelectedButton;
    private JButton exportAllButton;
    private JButton multiExportAllButton;
    private JScrollPane editorScrollPane;
    private JPanel editorPanel;
    private ImagePanel selectedImagePanel;

    private PDFEditor editor;

    public MainForm() {
        editorPanel.setLayout(new GridLayout(0, 3));
        editor = new PDFEditor();
        selectedImagePanel = null;

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Documents", "pdf");
                chooser.setFileFilter(filter);
                if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        editor.addPDFDocument(chooser.getSelectedFile());
                        updateEditorPanel();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelected();
                editor.removeSelectedPages();
                updateEditorPanel();
            }
        });

        invertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        selectAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        unselectAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        moveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        moveDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        exportSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        exportAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        multiExportSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        multiExportAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });
    }

    public void updateEditorPanel() {
        editorPanel.removeAll();
        for(int i = 0; i < editor.getSize(); i++) {
            ImagePanel imagePanel = new ImagePanel(editor.getThumbnail(i));
            imagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            imagePanel.setPreferredSize(new Dimension(editorPanel.getWidth() / 3 - 10, (int)((editorPanel.getWidth() / 3 - 10) * 1.4142)));
            JCheckBox checkBox = new JCheckBox();
            checkBox.setMargin(new Insets(10, 10, 10, 10));
            imagePanel.add(checkBox);
            imagePanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(selectedImagePanel != null)
                        selectedImagePanel.setHighlightState(ImagePanel.NORMAL);
                    imagePanel.setHighlightState(ImagePanel.HIGHLIGHTED);
                    selectedImagePanel = (ImagePanel) e.getSource();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // DO NOTHING
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // DO NOTHING
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if(e.getSource() != selectedImagePanel)
                        imagePanel.setHighlightState(ImagePanel.HOVER);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(imagePanel.getHighlightState() == ImagePanel.HOVER) {
                        imagePanel.setHighlightState(ImagePanel.NORMAL);
                    }
                }
            });
            editorPanel.add(imagePanel);
        }
        editorPanel.updateUI();
    }

    public void updateSelected() {
        for(int i = 0; i < editorPanel.getComponentCount(); i++) {
            if(((ImagePanel)editorPanel.getComponent(i)).getSelectState())
                editor.selectPage(i);
            else
                editor.unselectPage(i);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PDF Split & Merge");
        frame.setContentPane(new MainForm().rootPanel);
        //frame.setMinimumSize(new Dimension(900, 276));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
