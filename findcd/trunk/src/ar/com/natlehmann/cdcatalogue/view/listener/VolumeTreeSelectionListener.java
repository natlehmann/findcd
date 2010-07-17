package ar.com.natlehmann.cdcatalogue.view.listener;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;

public class VolumeTreeSelectionListener implements TreeSelectionListener {
	
	private JTree tree;
	private CdCatalogueViewFacade viewFacade;
	
	public VolumeTreeSelectionListener(JTree tree, CdCatalogueViewFacade viewFacade) {
		this.tree = tree;
		this.viewFacade = viewFacade;
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		
		if (node !=null) {
			
			viewFacade.clearMessages();
			
			if (node.getParent() != null) {
				
				if (node.getParent().equals(tree.getModel().getRoot())) {
					viewFacade.filterResultsByCategoryName(node.getUserObject().toString());
					
				} else {
					viewFacade.filterResultsByVolumeCategory(
							((DefaultMutableTreeNode)node.getParent()).getUserObject().toString(), 
							node.getUserObject().toString());
				}
			}
		}
	}

}
