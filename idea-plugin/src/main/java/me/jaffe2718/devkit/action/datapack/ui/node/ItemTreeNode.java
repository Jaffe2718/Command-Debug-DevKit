package me.jaffe2718.devkit.action.datapack.ui.node;

import javax.swing.tree.DefaultMutableTreeNode;

public class ItemTreeNode extends DefaultMutableTreeNode {

        public ItemTreeNode(String name) {
            super(name);
            this.setAllowsChildren(false);
        }

        @Override
        public boolean isLeaf() {
            return true;
        }
}
