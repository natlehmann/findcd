package ar.com.natlehmann.cdcatalogue.view.dataModel;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class VolumeTree extends JTree {

	private static final long serialVersionUID = -6407253669141682158L;
	
	private JPopupMenu actionsPopUpMenu;

	public VolumeTree(JPopupMenu actionsPopUpMenu) {
		this.actionsPopUpMenu = actionsPopUpMenu;
	}

	public VolumeTree(TreeNode root, JPopupMenu actionsPopUpMenu) {
		super(root);
		this.actionsPopUpMenu = actionsPopUpMenu;
	}

	public VolumeTree(TreeModel newModel, JPopupMenu actionsPopUpMenu) {
		super(newModel);
		this.actionsPopUpMenu = actionsPopUpMenu;
	}

	public VolumeTree(TreeModel newModel) {
		super(newModel);
	}

	public JPopupMenu getActionsPopUpMenu() {
		return actionsPopUpMenu;
	}

	public void setActionsPopUpMenu(JPopupMenu actionsPopUpMenu) {
		this.actionsPopUpMenu = actionsPopUpMenu;
	}

}
