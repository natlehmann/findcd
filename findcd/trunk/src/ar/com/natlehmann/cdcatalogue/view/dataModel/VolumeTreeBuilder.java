package ar.com.natlehmann.cdcatalogue.view.dataModel;

import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelFacade;
import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;
import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;
import ar.com.natlehmann.cdcatalogue.view.action.VolumeTreeDeleteCommand;
import ar.com.natlehmann.cdcatalogue.view.action.VolumeTreeEditCommand;
import ar.com.natlehmann.cdcatalogue.view.listener.VolumeTreeModelListener;
import ar.com.natlehmann.cdcatalogue.view.listener.VolumeTreeMouseListener;
import ar.com.natlehmann.cdcatalogue.view.listener.VolumeTreeSelectionListener;

public class VolumeTreeBuilder {
	
	public static VolumeTree buildVolumeTree(CdCatalogueModelFacade modelFacade,
			CdCatalogueViewFacade viewFacade) {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");
		createNodes(root, modelFacade);
		
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		treeModel.addTreeModelListener(new VolumeTreeModelListener());		
		
		VolumeTree tree = new VolumeTree(treeModel);
		tree.setActionsPopUpMenu(getActionsPopUpMenu(viewFacade, tree));
		
		tree.addTreeSelectionListener(
				new VolumeTreeSelectionListener(tree, viewFacade));
		tree.addMouseListener(new VolumeTreeMouseListener(tree));
		
		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.putClientProperty("JTree.lineStyle", "Horizontal");
		
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		//renderer.setLeafIcon(IconFactory.getTreeLeafIcon());
		
		tree.setCellRenderer(renderer);

		return tree;
	}

	private static JPopupMenu getActionsPopUpMenu(
			CdCatalogueViewFacade viewFacade, VolumeTree volumeTree) {
		
		JPopupMenu menu = new JPopupMenu("Actions");
		
		JMenuItem editItem = new JMenuItem("Edit");
		editItem.addActionListener(new VolumeTreeEditCommand(viewFacade, volumeTree));
		
		JMenuItem deleteItem = new JMenuItem("Delete");
		deleteItem.addActionListener(new VolumeTreeDeleteCommand(viewFacade, volumeTree));
		
		menu.add(editItem);
		menu.add(deleteItem);
		
		return menu;
	}

	private static void createNodes(DefaultMutableTreeNode root, CdCatalogueModelFacade modelFacade) {
		
		List<Category> categories = modelFacade.getCategories();
		for (Category category : categories) {
			
			DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(
					category.getCategoryName());
			root.add(categoryNode);
			
			List<Volume> volumes = modelFacade.getVolumesByCategory(category);
			for (Volume volume : volumes) {

				DefaultMutableTreeNode volumeNode = new DefaultMutableTreeNode(
						volume.getVolumeName());
				categoryNode.add(volumeNode);
			}
		}
		
	}

	public static void addCategory(JTree volumeTree, String newCategoryName) {
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)volumeTree.getModel().getRoot();		
		addNode(volumeTree, root, newCategoryName);
		
		DefaultMutableTreeNode newNode = findCategoryNode(volumeTree, newCategoryName);
		scrollToNode(volumeTree, newNode);
		
	}

	public static void addVolume(JTree volumeTree, String categoryName, String newVolumeName) {
		
		DefaultMutableTreeNode categoryNode = findCategoryNode(volumeTree, categoryName);		
		addNode(volumeTree, categoryNode, newVolumeName);
		
		DefaultMutableTreeNode newNode = findNode(volumeTree, categoryNode, newVolumeName);
		scrollToNode(volumeTree, newNode);
		
	}
	
	
	private static DefaultMutableTreeNode findNode(JTree volumeTree, 
			DefaultMutableTreeNode parentNode, String nodeName) {
		
		DefaultTreeModel treeModel = (DefaultTreeModel)volumeTree.getModel();
		
		DefaultMutableTreeNode node = null;
		
		boolean found = false;
		int i = 0;
		
		while (i < treeModel.getChildCount(parentNode) && !found) {			
			
			String categoryFound = treeModel.getChild(parentNode, i).toString();
			if (categoryFound.equals(nodeName)) {

				found = true;
				node = (DefaultMutableTreeNode)treeModel.getChild(parentNode, i);
			}
			
			i++;
		}
		
		return node;
	}
	
	
	private static DefaultMutableTreeNode findCategoryNode(JTree volumeTree,
			String categoryName) {
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)volumeTree.getModel().getRoot();
		return findNode(volumeTree, root, categoryName);
	}

	
	private static void addNode(JTree volumeTree, DefaultMutableTreeNode parent, 
			String newNodeContent) {
		
		DefaultTreeModel treeModel = (DefaultTreeModel)volumeTree.getModel();
		
		boolean found = false;
		int i = 0;
		int index = 0;
		
		while (i < treeModel.getChildCount(parent) && !found) {			
			
			String contentFound = treeModel.getChild(parent, i).toString();
			if (contentFound.compareToIgnoreCase(newNodeContent) > 0) {
				
				index = i;
				found = true;
			}
			
			i++;
		}
		
		if (!found) {
			index = treeModel.getChildCount(parent);
		}
		
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newNodeContent);		
		treeModel.insertNodeInto(newNode, parent, index);
		
	}

	private static void scrollToNode(JTree volumeTree,
			DefaultMutableTreeNode node) {
		
		TreePath path = new TreePath(node.getPath());
		volumeTree.scrollPathToVisible(path);
		volumeTree.setSelectionPath(path);
		volumeTree.expandPath(path);
	}

	public static void mergeCategories(VolumeTree volumeTree, String oldCategoryName,
			String existentCategoryName) {
		
		DefaultMutableTreeNode oldCategoryNode = findCategoryNode(volumeTree, oldCategoryName);
		DefaultMutableTreeNode existentCategoryNode = findCategoryNode(volumeTree, existentCategoryName);
		
		for (int i=0; i < oldCategoryNode.getChildCount(); i++) {
			addNode(volumeTree, existentCategoryNode, oldCategoryNode.getChildAt(i).toString());
		}
		
		DefaultTreeModel treeModel = (DefaultTreeModel)volumeTree.getModel();
		treeModel.removeNodeFromParent(oldCategoryNode);
		
		scrollToNode(volumeTree, existentCategoryNode);
		
	}

	public static void refreshCategory(VolumeTree volumeTree,
			String oldCategoryName, String newCategoryName) {
		
		DefaultMutableTreeNode oldCategoryNode = findCategoryNode(volumeTree, oldCategoryName);
		
		addNode(volumeTree, (DefaultMutableTreeNode)volumeTree.getModel().getRoot(), newCategoryName);
		DefaultMutableTreeNode newCategoryNode = findCategoryNode(volumeTree, newCategoryName);
		
		for (int i=0; i < oldCategoryNode.getChildCount(); i++) {
			addNode(volumeTree, newCategoryNode, oldCategoryNode.getChildAt(i).toString());
		}
		
		DefaultTreeModel treeModel = (DefaultTreeModel)volumeTree.getModel();
		treeModel.removeNodeFromParent(oldCategoryNode);
		
		scrollToNode(volumeTree, newCategoryNode);
		
	}

}
