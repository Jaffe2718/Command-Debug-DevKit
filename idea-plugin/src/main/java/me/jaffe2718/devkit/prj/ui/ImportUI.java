package me.jaffe2718.devkit.prj.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import me.jaffe2718.devkit.prj.unit.DatapackImporter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportUI {

    private static final JBColor ERROR_COLOR = new JBColor(new Color(128, 0, 0), new Color(255, 0, 0));
    private static final JBColor WARNING_COLOR = new JBColor(new Color(128, 128, 0), new Color(255, 255, 0));
    private static final JBColor INFO_COLOR = new JBColor(new Color(0, 128, 0), new Color(0, 255, 0));

    private JPanel main;
    private JLabel labelPckPth;
    private JLabel labelExtractDir;
    private JLabel labelPrjName;
    public JTextField textFieldPckPth;
    public JTextField textFieldExtDir;
    public JTextField textFieldPrjName;
    private JButton buttonPckPth;       // to find datapack zip path
    private JButton buttonExtDir;       // to find extract directory
    private JLabel labelMsg;

    public ImportUI() {
        this.textFieldExtDir.setToolTipText("The parent directory of the project.");
        this.textFieldPckPth.setToolTipText("The path to the datapack zip file.");
        this.textFieldPrjName.setToolTipText("The name of the project.");

        this.textFieldPckPth.getDocument().addDocumentListener(new DatapackPthListener());
        this.textFieldExtDir.getDocument().addDocumentListener(new ExtDirAndPrjNameListener());
        this.textFieldPrjName.getDocument().addDocumentListener(new ExtDirAndPrjNameListener());
        this.buttonPckPth.addMouseListener(new ExploreButtonListener(this.textFieldPckPth, true));
        this.buttonExtDir.addMouseListener(new ExploreButtonListener(this.textFieldExtDir, false));
    }

    private final class DatapackPthListener implements DocumentListener, DatapackImporter {
        @Override
        public void insertUpdate(DocumentEvent e) {
            onUpdated();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            onUpdated();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            onUpdated();
        }

        private void onUpdated() {
            try {
                String packName = textFieldPckPth.getText();
                if (this.validateDatapack(packName)) {
                    textFieldPrjName.setText(Path.of(packName).getFileName().toString().replace(".zip", ""));
                } else {
                    labelMsg.setText("Datapack path must be a zip file.");
                    labelMsg.setForeground(ERROR_COLOR);
                }
            } catch (Exception e) {
                labelMsg.setText("Invalid datapack path.");
                labelMsg.setForeground(ERROR_COLOR);
            }
        }
    }

    private final class ExtDirAndPrjNameListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            onUpdated();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            onUpdated();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            onUpdated();
        }

        private void onUpdated() {
            try {
                if (textFieldExtDir.getText().isBlank()) {
                    labelMsg.setText("External directory cannot be empty.");
                    labelMsg.setForeground(ERROR_COLOR);
                } else if (!Path.of(textFieldExtDir.getText()).isAbsolute()) {
                    labelMsg.setText("Invalid path of external directory: " +
                            textFieldExtDir.getText());
                    labelMsg.setForeground(ERROR_COLOR);
                } else if (textFieldPrjName.getText().isBlank()) {
                    labelMsg.setText("Project name cannot be empty.");
                    labelMsg.setForeground(ERROR_COLOR);
                } else {
                    Path prjDir = Paths.get(textFieldExtDir.getText(), textFieldPrjName.getText());
                    if (!prjDir.getParent().toString().equals(Path.of(textFieldExtDir.getText()).toString())) {
                        labelMsg.setText("Invalid project name: " + textFieldPrjName.getText());
                        labelMsg.setForeground(ERROR_COLOR);
                    } else if (prjDir.toFile().isDirectory()) {
                        labelMsg.setText("<html><body><p align=\"center\">The target directory <i>" +
                                prjDir + "</i> already exists.<br>" +
                                "Please be cautious when creating a new project here.<p></body><html>");
                        labelMsg.setForeground(WARNING_COLOR);
                    } else {
                        labelMsg.setText("<html><body><p align=\"center\">The new project will be created at <br><i>" +
                                prjDir + "</i><p></body><html>");
                        labelMsg.setForeground(INFO_COLOR);
                    }
                }
            } catch (InvalidPathException ignored) {
                labelMsg.setText(String.format("""
                        <html>
                            <body>
                                <p align="center">
                                    Invalid path of project file directory:<br>
                                    Location: <i>%s</i><br>
                                    Datapack Dir: <i>%s</i>
                                </p>
                            </body>
                        </html>
                        """, textFieldExtDir.getText(),
                        textFieldPrjName.getText())
                );
                labelMsg.setForeground(ERROR_COLOR);
            }
        }
    }

    private static final class ExploreButtonListener implements MouseListener {
        private final JTextField TARGET;
        private final boolean IS_FILE;
        public ExploreButtonListener(JTextField target, boolean isFile) {
            TARGET = target;
            IS_FILE = isFile;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            VirtualFile chosen = FileChooser.chooseFile(
                    new FileChooserDescriptor(false, !IS_FILE, IS_FILE,
                            true, false, false),
                    null,
                    null
            );
            if (chosen != null) {
                TARGET.setText(chosen.getPath());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


    public JPanel getPanel() {
        return main;
    }
}
