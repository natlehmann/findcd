package ar.com.natlehmann.cdcatalogue.view.lookandfeel;

import java.awt.Font;

public class FontFactory {

	public static Font getTableRowFont() {
		
		Font font = new Font("arial",Font.ROMAN_BASELINE, 11);
		return font;
	}
	
	public static Font getSuccessMessageFont() {
		
		Font font = new Font("arial",Font.ROMAN_BASELINE, 15);
		return font;
	}

}
