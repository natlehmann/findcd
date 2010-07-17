package ar.com.natlehmann.cdcatalogue.view.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ar.com.natlehmann.cdcatalogue.view.dataModel.VolumeTree;

public class VolumeTreeMouseListener implements MouseListener {
	
	private VolumeTree volumeTree;
	
	public VolumeTreeMouseListener(VolumeTree volumeTree) {
		this.volumeTree = volumeTree;
	}

	public VolumeTree getVolumeTree() {
		return volumeTree;
	}

	public void setVolumeTree(VolumeTree volumeTree) {
		this.volumeTree = volumeTree;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
		switch(event.getButton()) {
		
		case 1:
			if (volumeTree.getPathForLocation(event.getX(), event.getY()) == null) {
				this.volumeTree.setSelectionPath(null);
			}
			break;
			
		case 3:
			if (this.volumeTree.getSelectionPath() != null
					&& this.volumeTree.getSelectionPath().equals(
							volumeTree.getClosestPathForLocation(event.getX(), event.getY())) ) {
				
				this.volumeTree.getActionsPopUpMenu().show(
						this.volumeTree, event.getX(), event.getY());
			}
			break;
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
