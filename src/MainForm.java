import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainForm {
    private final PDFEditor editor;
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
    private JSlider columnsSlider;
    private ImagePanel highlightedImagePanel;
    private int editorColumns;

    public MainForm() {
        editorColumns = columnsSlider.getValue();
        editorPanel.setLayout(new GridLayout(0, editorColumns));
        editor = new PDFEditor();
        highlightedImagePanel = null;

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
                updateSelected();
                editor.invertSelection();
                updateEditorPanel();
            }
        });

        selectAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.selectAllPages();
                updateEditorPanel();
            }
        });

        unselectAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.unselectAllPages();
                updateEditorPanel();
            }
        });

        moveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i;

                for(i = 0; i < editorPanel.getComponentCount(); i++) {
                    if(editorPanel.getComponent(i) == highlightedImagePanel) {
                        editor.highlightPage(i);
                        break;
                    }
                }

                if(i > 0) {
                    updateSelected();
                    editor.moveUpHighlightedPage();
                    updateEditorPanel();
                    highlightedImagePanel = (ImagePanel)editorPanel.getComponent(i - 1);
                    highlightedImagePanel.setHighlightState(ImagePanel.HIGHLIGHTED);
                }
            }
        });

        moveDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i;

                for(i = 0; i < editorPanel.getComponentCount(); i++) {
                    if(editorPanel.getComponent(i) == highlightedImagePanel) {
                        editor.highlightPage(i);
                        break;
                    }
                }

                if(i < editorPanel.getComponentCount() - 1) {
                    updateSelected();
                    editor.moveDownHighlightedPage();
                    updateEditorPanel();
                    highlightedImagePanel = (ImagePanel)editorPanel.getComponent(i + 1);
                    highlightedImagePanel.setHighlightState(ImagePanel.HIGHLIGHTED);
                }
            }
        });

        exportSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Documents", "pdf");
                chooser.setFileFilter(filter);
                updateSelected();

                if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        editor.exportSelected(chooser.getSelectedFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        exportAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Documents", "pdf");
                chooser.setFileFilter(filter);

                if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        editor.exportAll(chooser.getSelectedFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        multiExportSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Documents", "pdf");
                chooser.setFileFilter(filter);
                updateSelected();

                if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        editor.multiExportSelected(chooser.getSelectedFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        multiExportAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Documents", "pdf");
                chooser.setFileFilter(filter);

                if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        editor.multiExportAll(chooser.getSelectedFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        columnsSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                editorColumns = columnsSlider.getValue();
                editorPanel.setLayout(new GridLayout(0, editorColumns));
                updateEditorPanel();
            }
        });

        editorScrollPane.getVerticalScrollBar().addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateEditorPanel();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // DO NOTHING
            }

            @Override
            public void componentShown(ComponentEvent e) {
                // DO NOTHING
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                // DO NOTHING
            }
        });

        rootPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateEditorPanel();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PDF Split & Merge");
        frame.setContentPane(new MainForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void updateEditorPanel() {
        int editorWidth = editorScrollPane.getWidth() - editorScrollPane.getVerticalScrollBar().getWidth();
        editorPanel.removeAll();

        for(int i = 0; i < editor.getSize(); i++) {
            ImagePanel imagePanel = new ImagePanel(editor.getThumbnail(i), editorWidth / editorColumns);
            imagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            imagePanel.setPreferredSize(new Dimension(editorWidth / editorColumns, (int)((editorWidth / editorColumns) * 1.4142)));
            JCheckBox checkBox = new JCheckBox();
            checkBox.setMargin(new Insets(10, 10, 10, 10));
            checkBox.setSelected(editor.isSelected(i));
            imagePanel.add(checkBox);
            imagePanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(highlightedImagePanel != null)
                        highlightedImagePanel.setHighlightState(ImagePanel.NORMAL);

                    imagePanel.setHighlightState(ImagePanel.HIGHLIGHTED);
                    highlightedImagePanel = (ImagePanel) e.getSource();
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
                    if(e.getSource() != highlightedImagePanel)
                        imagePanel.setHighlightState(ImagePanel.HOVER);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(imagePanel.getHighlightState() == ImagePanel.HOVER)
                        imagePanel.setHighlightState(ImagePanel.NORMAL);
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
}
