package me.jaffe2718.devkit.action.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import me.jaffe2718.devkit.action.ui.node.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DatapackTreeMouseAdapter extends MouseAdapter {

    /**
     * @param operation the operation to execute ["import", "link", "disable", "enable", "delete", "unlink", "query"(void)]
     * @param parameter the parameter of the operation, may be datapack name, datapack path, etc.
     * */
    public record DatapackOperation(String operation, String parameter) {}

    private List<DatapackOperation> datapackOperationWorkflow = new ArrayList<>();
    private MouseEvent mouseEvent;

    @Override
    public void mouseClicked(MouseEvent e) {
        this.mouseEvent = e;      // save the mouse event
        if (SwingUtilities.isRightMouseButton(e)) {
            JTree tree = (JTree) e.getSource();
            int row = tree.getClosestRowForLocation(e.getX(), e.getY());
            tree.setSelectionRow(row);
            // get the node
            Object node = tree.getLastSelectedPathComponent();
            JPopupMenu popupMenu = new JPopupMenu();
            if (node instanceof DirTreeNode dNode) {
                if (dNode.getUserObject().toString().equals("Common")) {
                    JMenuItem import_ = new JMenuItem("Import...");  // to import a datapack
                    import_.addActionListener(this::onImport);
                    popupMenu.add(import_);
                } else if (dNode.getUserObject().toString().equals("Linked")) {
                    JMenuItem link = new JMenuItem("Link...");       // to link a datapack
                    link.addActionListener(this::onLink);
                    popupMenu.add(link);
                }
            } else if (node instanceof ItemTreeNode iNode) {
                // get the parent node String
                switch (((DirTreeNode) iNode.getParent()).getUserObject().toString()) {
                    case "Enabled" -> {
                        JMenuItem disable = new JMenuItem("Disable");
                        disable.addActionListener(this::onDisable);
                        popupMenu.add(disable);
                    }
                    case "Available" -> {
                        JMenuItem enable = new JMenuItem("Enable");
                        enable.addActionListener(this::onEnable);
                        popupMenu.add(enable);
                    }
                    case "Common" -> {
                        if (iNode.getUserObject().toString().startsWith("\"file/")) {
                            JMenuItem delete = new JMenuItem("Delete");
                            delete.addActionListener(this::onDelete);
                            popupMenu.add(delete);
                        }
                    }
                    case "Linked" -> {
                        JMenuItem unlink = new JMenuItem("Unlink");
                        unlink.addActionListener(this::onUnlink);
                        popupMenu.add(unlink);
                    }
                }
            }
            popupMenu.show(tree, e.getX(), e.getY());
        }
    }

    private void removePackInfoFromDirNode(@NotNull DirTreeNode target, String packInfo) {
        for (int i = 0; i < target.getChildCount(); i++) {
            ItemTreeNode child = (ItemTreeNode) target.getChildAt(i);
            if (child.getUserObject().toString().equals(packInfo)) {
                child.removeFromParent();
                break;
            }
        }
    }

    private void onImport(ActionEvent ae) {
        VirtualFile chosen = FileChooser.chooseFile(
                new FileChooserDescriptor(false, false, true, true, false, false),
                null,
                null);
        if (chosen != null) {
            JTree tree = (JTree) this.mouseEvent.getSource();
            String packInfo = "\"file/" + chosen.getName() + "\"";
            DirTreeNode commonNode = (DirTreeNode) tree.getLastSelectedPathComponent();
            DirTreeNode linkedNode = (DirTreeNode) commonNode.getParent().getChildAt(1);
            DirTreeNode enabledNode = (DirTreeNode) commonNode.getParent().getParent().getChildAt(0).getChildAt(0);
            DirTreeNode availableNode = (DirTreeNode) commonNode.getParent().getParent().getChildAt(0).getChildAt(1);
            // remove duplicate
            this.removePackInfoFromDirNode(commonNode, packInfo);
            this.removePackInfoFromDirNode(linkedNode, packInfo);
            this.removePackInfoFromDirNode(enabledNode, packInfo);
            this.removePackInfoFromDirNode(availableNode, packInfo);
            commonNode.add(new ItemTreeNode(packInfo));
            enabledNode.add(new ItemTreeNode(packInfo));
            datapackOperationWorkflow.add(new DatapackOperation("import", chosen.getPath()));
            tree.updateUI();
        }
    }

    private void onLink(ActionEvent ae) {
        VirtualFile chosen = FileChooser.chooseFile(
                new FileChooserDescriptor(false, false, true, true, false, false),
                null,
                null);
        if (chosen != null) {
            JTree tree = (JTree) this.mouseEvent.getSource();
            String packInfo = "\"file/" + chosen.getName() + "\"";
            DirTreeNode linkedNode = (DirTreeNode) tree.getLastSelectedPathComponent();
            DirTreeNode commonNode = (DirTreeNode) linkedNode.getParent().getChildAt(0);
            DirTreeNode enabledNode = (DirTreeNode) linkedNode.getParent().getParent().getChildAt(0).getChildAt(0);
            DirTreeNode availableNode = (DirTreeNode) linkedNode.getParent().getParent().getChildAt(0).getChildAt(1);
            // remove duplicate
            this.removePackInfoFromDirNode(linkedNode, packInfo);
            this.removePackInfoFromDirNode(commonNode, packInfo);
            this.removePackInfoFromDirNode(enabledNode, packInfo);
            this.removePackInfoFromDirNode(availableNode, packInfo);
            linkedNode.add(new ItemTreeNode(packInfo));
            enabledNode.add(new ItemTreeNode(packInfo));
            datapackOperationWorkflow.add(new DatapackOperation("link", chosen.getPath()));
            tree.updateUI();
        }
    }

    private void onDisable(ActionEvent ae) {
        JTree tree = (JTree) this.mouseEvent.getSource();
        ItemTreeNode node = (ItemTreeNode) tree.getLastSelectedPathComponent();
        // move the node to the "Available" node
        DirTreeNode availableNode = (DirTreeNode) node.getParent().getParent().getChildAt(1);
        availableNode.add(node);
        // add the operation to the workflow
        datapackOperationWorkflow.add(new DatapackOperation("disable", node.getUserObject().toString()));
        tree.updateUI();
    }

    private void onEnable(ActionEvent ae) {
        JTree tree = (JTree) this.mouseEvent.getSource();
        ItemTreeNode node = (ItemTreeNode) tree.getLastSelectedPathComponent();
        // move the node to the "Enabled" node
        DirTreeNode enabledNode = (DirTreeNode) node.getParent().getParent().getChildAt(0);
        enabledNode.add(node);
        // add the operation to the workflow
        datapackOperationWorkflow.add(new DatapackOperation("enable", node.getUserObject().toString()));
        tree.updateUI();
    }

    private void onDelete(ActionEvent ae) {
        JTree tree = (JTree) this.mouseEvent.getSource();
        ItemTreeNode node = (ItemTreeNode) tree.getLastSelectedPathComponent();
        // add the operation to the workflow
        datapackOperationWorkflow.add(new DatapackOperation("delete", node.getUserObject().toString()));
        // remove the node from the "Enabled" node or "Available" node
        DirTreeNode enabledNode = (DirTreeNode) node.getParent().getParent().getParent().getChildAt(0).getChildAt(0);
        DirTreeNode availableNode = (DirTreeNode) node.getParent().getParent().getParent().getChildAt(0).getChildAt(1);
        this.removePackInfoFromDirNode(enabledNode, node.getUserObject().toString());
        this.removePackInfoFromDirNode(availableNode, node.getUserObject().toString());
        // remove the node
        node.removeFromParent();
        tree.updateUI();
    }

    private void onUnlink(ActionEvent ae) {
        JTree tree = (JTree) this.mouseEvent.getSource();
        ItemTreeNode node = (ItemTreeNode) tree.getLastSelectedPathComponent();
        // add the operation to the workflow
        datapackOperationWorkflow.add(new DatapackOperation("unlink", node.getUserObject().toString()));
        // remove the node from the "Enabled" node or "Available" node
        DirTreeNode enabledNode = (DirTreeNode) node.getParent().getParent().getParent().getChildAt(0).getChildAt(0);
        DirTreeNode availableNode = (DirTreeNode) node.getParent().getParent().getParent().getChildAt(0).getChildAt(1);
        this.removePackInfoFromDirNode(enabledNode, node.getUserObject().toString());
        this.removePackInfoFromDirNode(availableNode, node.getUserObject().toString());
        // remove the node
        node.removeFromParent();
        tree.updateUI();
    }

    public ArrayList<DatapackOperation> getDatapackOperationWorkflow() {
        return new ArrayList<>(this.datapackOperationWorkflow);
    }
}
