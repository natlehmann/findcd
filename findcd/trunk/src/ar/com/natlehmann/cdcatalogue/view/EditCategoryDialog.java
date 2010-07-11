package ar.com.natlehmann.cdcatalogue.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ar.com.natlehmann.cdcatalogue.view.action.CloseWindowCommand;
import ar.com.natlehmann.cdcatalogue.view.lookandfeel.FontFactory;
import ar.com.natlehmann.cdcatalogue.view.lookandfeel.LabelFactory;

public class EditCategoryDialog extends JDialog {

	private static final long serialVersionUID = 2870132370143601685L;
	
	private JPanel topPanel;
	private JPanel centralPanel;
	private JPanel buttonPanel;
	
	private JPanel categoryPanel;	
	private JTextField fdNewCategory;
	
	private JButton btAccept;
	private JButton btCancel;
	
	private JTextArea messagesArea;
	
	private CdCatalogueMainWindow owner;

	
	public EditCategoryDialog(CdCatalogueMainWindow owner) {
		super(owner, false);
		this.owner = owner;
		this.setTitle("Confirmation");
		this.init();
	}

	private void init() {
		
		this.setLayout(new BorderLayout());
		this.add(this.getTopPanel(), BorderLayout.NORTH);
		this.add(this.getCentralPanel(), BorderLayout.CENTER);
		this.add(this.getButtonPanel(), BorderLayout.SOUTH);
		
		this.getTopPanel().add(this.getMessagesArea());
		
		this.getCentralPanel().add(this.getCategoryPanel());
		
		this.getButtonPanel().add(this.getBtAccept());
		this.getButtonPanel().add(this.getBtCancel());
		
		this.pack();
	}
	
	
	public void show(String categoryName) {
		this.getFdNewCategory().setText(categoryName);
		
		this.setVisible(true);
		
	}

	
	public JPanel getCategoryPanel() {
		if (this.categoryPanel == null){
			this.categoryPanel = new JPanel();
			this.categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			this.categoryPanel.add(LabelFactory.getCreateCatalogueLabel("Category:"));
			this.categoryPanel.add(this.getFdNewCategory());
		}
		return categoryPanel;
	}
	
	
	public JPanel getTopPanel() {
		if (this.topPanel == null) {
			this.topPanel = new JPanel();
			this.topPanel.setLayout(new GridLayout(1,1));
		}
		return topPanel;
	}
	
	public void setTopPanel(JPanel topPanel) {
		this.topPanel = topPanel;
	}
	
	public JPanel getCentralPanel() {
		if (this.centralPanel == null) {
			this.centralPanel = new JPanel();
			this.centralPanel.setLayout(new GridLayout(4,1));
		}
		return centralPanel;
	}
	
	public void setCentralPanel(JPanel centralPanel) {
		this.centralPanel = centralPanel;
	}
	
	public JPanel getButtonPanel() {
		if (this.buttonPanel == null) {
			this.buttonPanel = new JPanel();
			this.buttonPanel.setLayout(new FlowLayout());
		}
		return buttonPanel;
	}
	
	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}
	
	public JTextArea getMessagesArea() {
		if (this.messagesArea == null){
			this.messagesArea = new JTextArea(2,17);
			this.messagesArea.setEditable(false);
			this.messagesArea.setBackground(new Color(240,240,240));
			this.messagesArea.setFont(FontFactory.getSuccessMessageFont());
		}
		return messagesArea;
	}
	
	public void setMessagesArea(JTextArea messagesArea) {
		this.messagesArea = messagesArea;
	}
	
	public JTextField getFdNewCategory() {
		if (this.fdNewCategory == null) {
			this.fdNewCategory = new JTextField(20);
			this.fdNewCategory.addActionListener(new EditCategoryActionListener());
		}
		return fdNewCategory;
	}
	
	public void setFdNewCategory(JTextField fdNewCategory) {
		this.fdNewCategory = fdNewCategory;
	}

	public JButton getBtAccept() {
		if (this.btAccept == null) {
			this.btAccept = new JButton("Accept");
			this.btAccept.addActionListener(new EditCategoryActionListener());
		}
		return btAccept;
	}
	
	public void setBtAccept(JButton btAccept) {
		this.btAccept = btAccept;
	}
	
	public JButton getBtCancel() {
		if (this.btCancel == null) {
			this.btCancel = new JButton("Cancel");
			this.btCancel.addActionListener(new CloseWindowCommand(owner, this));
		}
		return btCancel;
	}
	
	public void setBtCancel(JButton btCancel) {
		this.btCancel = btCancel;
	}
	
	
	public class EditCategoryActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			owner.getViewObserver().fireEditCategory(fdNewCategory.getText(), owner);
		}		
	}

}
