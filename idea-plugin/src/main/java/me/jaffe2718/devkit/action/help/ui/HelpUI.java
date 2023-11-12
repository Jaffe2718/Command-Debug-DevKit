package me.jaffe2718.devkit.action.help.ui;

import me.jaffe2718.devkit.action.help.ui.content.OverviewUI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class HelpUI {
    private JPanel rootPanel;
    public JTree catalogueTree;
    public JScrollPane contentScrollPanel;

    public HelpUI() {
        this.buildCatalogueTree();
        this.contentScrollPanel.setViewportView(new OverviewUI().rootPanel);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void buildCatalogueTree() {
        DefaultMutableTreeNode catalogueRoot = new DefaultMutableTreeNode("Minecraft Command DevKit");

        DefaultMutableTreeNode overview = new DefaultMutableTreeNode("Overview");

        DefaultMutableTreeNode features = new DefaultMutableTreeNode("Features");
        features.add(new DefaultMutableTreeNode("Project Setup & Configuration"));
        features.add(new DefaultMutableTreeNode("Minecraft Function Editing"));
        features.add(new DefaultMutableTreeNode("Datapack Generation & Management"));
        features.add(new DefaultMutableTreeNode("Miscellaneous"));

        DefaultMutableTreeNode info = new DefaultMutableTreeNode("Information");
        info.add(new DefaultMutableTreeNode("Dependencies"));
        info.add(new DefaultMutableTreeNode("License"));
        info.add(new DefaultMutableTreeNode("About"));

        catalogueRoot.add(overview);
        catalogueRoot.add(features);
        catalogueRoot.add(info);
        this.catalogueTree.setModel(new DefaultTreeModel(catalogueRoot));
        this.catalogueTree.addMouseListener(new HelpCatalogueMouseAdapter(this));
    }

}
