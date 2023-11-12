package me.jaffe2718.devkit.action.help.ui;

import me.jaffe2718.devkit.action.help.ui.content.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HelpCatalogueMouseAdapter extends MouseAdapter {

    private final HelpUI helpUI;

    public HelpCatalogueMouseAdapter(HelpUI helpUI) {
        this.helpUI = helpUI;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            int row = this.helpUI.catalogueTree.getClosestRowForLocation(e.getX(), e.getY());
            this.helpUI.catalogueTree.setSelectionRow(row);
            // get the node
            Object node = this.helpUI.catalogueTree.getLastSelectedPathComponent();
            if (node instanceof DefaultMutableTreeNode dNode) {
                String nodeName = dNode.getUserObject().toString();
                switch (nodeName) {
                    case "Overview" -> this.helpUI.contentScrollPanel.setViewportView(new OverviewUI().rootPanel);
                    case "Features" -> this.helpUI.contentScrollPanel.setViewportView(new FeaturesUI().rootPanel);
                    case "Information" -> this.helpUI.contentScrollPanel.setViewportView(new InfoUI().rootPanel);
                    case "Project Setup & Configuration" -> this.helpUI.contentScrollPanel.setViewportView(new FeaturesProjectSetupUI().rootPanel);
                    case "Minecraft Function Editing" -> this.helpUI.contentScrollPanel.setViewportView(new FeaturesFunctionEditingUI().rootPanel);
                    case "Datapack Generation & Management" -> this.helpUI.contentScrollPanel.setViewportView(new FeaturesDatapackGenManagementUI().rootPanel);
                    case "Miscellaneous" -> this.helpUI.contentScrollPanel.setViewportView(new FeaturesMiscellaneousUI().rootPanel);
                    case "Dependencies" -> this.helpUI.contentScrollPanel.setViewportView(new InfoDependenciesUI().rootPanel);
                    case "License" -> this.helpUI.contentScrollPanel.setViewportView(new InfoLicenseUI().rootPanel);
                    case "About" -> this.helpUI.contentScrollPanel.setViewportView(new InfoAboutUI().rootPanel);
                }
            }
        }
    }
}
