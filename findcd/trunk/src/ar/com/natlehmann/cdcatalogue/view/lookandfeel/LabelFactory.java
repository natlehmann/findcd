package ar.com.natlehmann.cdcatalogue.view.lookandfeel;

import java.awt.Dimension;

import javax.swing.JLabel;

public class LabelFactory {
	
	public static JLabel getCreateCatalogueLabel(String text) {
		
		JLabel label = new JLabel(text);
		label.setPreferredSize(new Dimension(140,35));
		return label;
	}

}
