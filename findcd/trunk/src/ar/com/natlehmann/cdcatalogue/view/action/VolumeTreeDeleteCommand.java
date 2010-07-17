package ar.com.natlehmann.cdcatalogue.view.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.DefaultMutableTreeNode;

import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;
import ar.com.natlehmann.cdcatalogue.view.dataModel.VolumeTree;

public class VolumeTreeDeleteCommand extends CommandActionListener {
	
	private VolumeTree volumeTree;

	public VolumeTreeDeleteCommand(CdCatalogueViewFacade viewFacade, VolumeTree volumeTree) {
		super(viewFacade);
		this.volumeTree = volumeTree;
	}

	@Override
	public void execute(ActionEvent event) {
		
		DefaultMutableTreeNode node = 
					(DefaultMutableTreeNode)this.volumeTree.getLastSelectedPathComponent();
		
		if ( !node.equals(this.volumeTree.getModel().getRoot()) ) {
			
			if (node.getParent().equals(this.volumeTree.getModel().getRoot())) {
				
				String categoryName = node.getUserObject().toString();
				if (getViewFacade().isConfirmed("Are you sure you want to delete the category " 
						+ categoryName + "? This cannot be undone.")) {
				
					getViewObserver().fireDeleteCategory(categoryName);
				}
			
			} else {
				
				String volumeName = node.getUserObject().toString();
				if (getViewFacade().isConfirmed("Are you sure you want to delete the volume " 
						+ volumeName + "? This cannot be undone.")) {
					
					getViewObserver().fireDeleteVolume(volumeName);
				}
			}			
		}

	}

}
