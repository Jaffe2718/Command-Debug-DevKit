package me.jaffe2718.devkit.action.ui.node;

import javax.swing.tree.DefaultMutableTreeNode;

public class DirTreeNode extends DefaultMutableTreeNode {

    public DirTreeNode(String name) {
        super(name);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
