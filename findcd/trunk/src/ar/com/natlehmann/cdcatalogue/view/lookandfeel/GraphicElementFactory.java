package ar.com.natlehmann.cdcatalogue.view.lookandfeel;

import java.awt.Color;

import javax.swing.JPanel;

public class GraphicElementFactory {
	
	public static JPanel getJPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		
		return panel;
	}

}
