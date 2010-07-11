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
				getViewObserver().fireDeleteCategory(node.getUserObject().toString());
			
			} else {
				getViewObserver().fireDeleteVolume(node.getUserObject().toString());
				
				//TODO: PRIMERO MANDAR LA PANTALLITA DE CONFIRMACION. DESPUES LANZAR EL EVENTO
			}			
		}

	}

}
