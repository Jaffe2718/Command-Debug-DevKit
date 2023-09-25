package me.jaffe2718.devkit.prj;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.nio.file.Paths;

public class ConfigUI {
    private JPanel main;
    public JTextField datapackNameTextField;
    public JTextField locationTextField;
    private JLabel locationLabel;
    public JTextArea descriptionTextArea;
    public JTextField namespaceTextField;
    private JButton exploreButton;
    public JSpinner packFormatSpinner;
    private JLabel datapackNameLabel;
    private JLabel namespaceLabel;
    private JLabel descriptionLabel;
    private JLabel messageLabel;
    private JLabel packFormatLabel;

    private static final JBColor ERROR_COLOR = new JBColor(new Color(128, 0, 0), new Color(255, 0, 0));
    private static final JBColor INFO_COLOR = new JBColor(new Color(0, 128, 0), new Color(0, 255, 0));

    public ConfigUI() {
        this.datapackNameTextField.setToolTipText("The name of the datapack.");
        this.locationTextField.setToolTipText("The parent directory of the project.");
        this.namespaceTextField.setToolTipText("The namespace of the datapack.");
        this.packFormatSpinner.setValue(10);

        this.datapackNameTextField.getDocument().addDocumentListener(new AutoCompleteNamespaceListener());
        this.locationTextField.getDocument().addDocumentListener(new ConfigCheckListener());
        this.namespaceTextField.getDocument().addDocumentListener(new ConfigCheckListener());
        this.packFormatSpinner.addChangeListener(this::packFormatListener);
    }

    public JPanel getPanel() {
        return main;
    }

    private final class ConfigCheckListener implements DocumentListener {
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
            if (datapackNameTextField.getText().isBlank()) {
                // set color to red
                messageLabel.setForeground(ERROR_COLOR);
                messageLabel.setText("Datapack name cannot be empty.");
            } else if (!Paths.get(locationTextField.getText()).isAbsolute()) {
                messageLabel.setForeground(ERROR_COLOR);
                messageLabel.setText("Invalid path of project file directory: " +
                        locationTextField.getText());
            } else if (!namespaceTextField.getText().matches("[a-z_][a-z0-9_]*")) {
                messageLabel.setForeground(ERROR_COLOR);
                messageLabel.setText("Namespace must be a valid Minecraft namespace.");
            } else {
                messageLabel.setForeground(INFO_COLOR);
                messageLabel.setText("The new project will be created at " +
                        Paths.get(locationTextField.getText(), datapackNameTextField.getText()).toAbsolutePath());
            }
        }
    }

    private final class AutoCompleteNamespaceListener implements DocumentListener {
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
            namespaceTextField.setText(datapackNameTextField.getText().toLowerCase().replaceAll("[\\-+.]", "_" ));
        }
    }

    private void packFormatListener(ChangeEvent e) {
        String value = packFormatSpinner.getValue().toString();
        if (!value.matches("^[1-9]\\d*$")) {
            Messages.showErrorDialog("Pack format must be a positive integer.", "Invalid Pack Format");
            packFormatSpinner.setValue(10);
        }
    }
}
