package ar.com.natlehmann.cdcatalogue.view.listener;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

public class VolumeTreeModelListener implements TreeModelListener {

	@Override
	public void treeNodesChanged(TreeModelEvent arg0) {
		System.out.println("VolumeTreeModelListener.treeNodesChanged()");

	}

	@Override
	public void treeNodesInserted(TreeModelEvent arg0) {
		System.out.println("VolumeTreeModelListener.treeNodesInserted()");

	}

	@Override
	public void treeNodesRemoved(TreeModelEvent arg0) {
		System.out.println("VolumeTreeModelListener.treeNodesRemoved()");

	}

	@Override
	public void treeStructureChanged(TreeModelEvent arg0) {
		System.out.println("VolumeTreeModelListener.treeStructureChanged()");

	}

}
