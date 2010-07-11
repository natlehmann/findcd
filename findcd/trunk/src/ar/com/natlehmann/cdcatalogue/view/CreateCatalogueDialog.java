package ar.com.natlehmann.cdcatalogue.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.view.action.CloseWindowCommand;
import ar.com.natlehmann.cdcatalogue.view.action.CreateCatalogueCommand;
import ar.com.natlehmann.cdcatalogue.view.lookandfeel.FontFactory;
import ar.com.natlehmann.cdcatalogue.view.lookandfeel.LabelFactory;

public class CreateCatalogueDialog extends JDialog {

	private static final long serialVersionUID = 2870132370143601685L;
	
	private JPanel topPanel;
	private JPanel centralPanel;
	private JPanel buttonPanel;
	
	private JPanel volumePanel;
	private JPanel pathPanel;
	private JPanel categoryPanel;
	private JPanel newCategoryPanel;
	
	private JTextField fdVolumeName;
	private JTextField fdSelectedPath;
	private JTextField fdNewCategory;
	private JComboBox selectedCategory;
	
	private JButton btAccept;
	private JButton btCancel;
	private JButton btAddCategory;
	private JButton btChangeSelection;
	private JButton btCreateCategory;
	private JButton btCancelCreateCategory;
	
	private JTextArea messagesArea;
	
	private CdCatalogueMainWindow owner;

	
	public CreateCatalogueDialog(CdCatalogueMainWindow owner) {
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
		
		this.getCentralPanel().add(this.getPathPanel());
		this.getCentralPanel().add(this.getVolumePanel());
		this.getCentralPanel().add(this.getCategoryPanel());
		
		this.getButtonPanel().add(this.getBtAccept());
		this.getButtonPanel().add(this.getBtCancel());
		
		this.pack();
	}
	
	
	public void show(File file) {
		this.getFdVolumeName().setText(file.getName());
		this.getFdSelectedPath().setText(file.getAbsolutePath());
		
		this.setVisible(true);
		
	}
	
	public JPanel getPathPanel() {
		if (this.pathPanel == null) {
			this.pathPanel = new JPanel();
			this.pathPanel.setLayout(new FlowLayout(FlowLayout.LEFT));			
			
			this.pathPanel.add(LabelFactory.getCreateCatalogueLabel("Selected Path:"));
			this.pathPanel.add(this.getFdSelectedPath());
			this.pathPanel.add(this.getBtChangeSelection());
		}
		return pathPanel;
	}
	
	public JPanel getVolumePanel() {
		if (this.volumePanel == null) {
			this.volumePanel = new JPanel();
			this.volumePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			this.volumePanel.add(LabelFactory.getCreateCatalogueLabel("Volume name:"));
			this.volumePanel.add(this.getFdVolumeName());
		}
		return volumePanel;
	}
	
	public JPanel getCategoryPanel() {
		if (this.categoryPanel == null){
			this.categoryPanel = new JPanel();
			this.categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			this.categoryPanel.add(LabelFactory.getCreateCatalogueLabel("Category:"));
			this.categoryPanel.add(this.getSelectedCategory());
			this.categoryPanel.add(this.getBtAddCategory());
		}
		return categoryPanel;
	}
	
	public JPanel getNewCategoryPanel() {
		if (this.newCategoryPanel == null){
			this.newCategoryPanel = new JPanel();
			this.newCategoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			this.newCategoryPanel.add(LabelFactory.getCreateCatalogueLabel("Create a Category:"));
			this.newCategoryPanel.add(this.getFdNewCategory());
			this.newCategoryPanel.add(this.getBtCreateCategory());
			this.newCategoryPanel.add(this.getBtCancelCreateCategory());
		}
		return newCategoryPanel;
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
	
	public JTextField getFdVolumeName() {
		if (this.fdVolumeName == null) {
			this.fdVolumeName = new JTextField(20);
			this.fdVolumeName.requestFocus();
		}
		return fdVolumeName;
	}
	
	public void setFdVolumeName(JTextField fdVolumeName) {
		this.fdVolumeName = fdVolumeName;
	}
	
	public JTextField getFdSelectedPath() {
		if (this.fdSelectedPath == null) {
			this.fdSelectedPath = new JTextField(20);
			this.fdSelectedPath.setEnabled(false);
		}
		return fdSelectedPath;
	}
	
	public void setFdSelectedPath(JTextField fdSelectedPath) {
		this.fdSelectedPath = fdSelectedPath;
	}
	
	public JTextField getFdNewCategory() {
		if (this.fdNewCategory == null) {
			this.fdNewCategory = new JTextField(20);
			this.fdNewCategory.addActionListener(new CreateCategoryActionListener());
		}
		return fdNewCategory;
	}
	
	public void setFdNewCategory(JTextField fdNewCategory) {
		this.fdNewCategory = fdNewCategory;
	}
	
	public JComboBox getSelectedCategory() {
		if (this.selectedCategory == null) {
			this.selectedCategory = new JComboBox(
					this.owner.getModelFacade().getCategories().toArray());
			this.selectedCategory.setPreferredSize(new Dimension(225,30));
		}
		return selectedCategory;
	}
	
	public void setSelectedCategory(JComboBox selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	
	public void reloadCategories(Category newCategory) {
		
		boolean found = false;
		int i = 0;
		while (i < this.getSelectedCategory().getItemCount() && !found) {
			
			Category categoryFound = (Category)this.getSelectedCategory().getItemAt(i);
			if (categoryFound.getCategoryName().compareToIgnoreCase(newCategory.getCategoryName()) > 0) {
				
				this.getSelectedCategory().insertItemAt(newCategory, i);
				found = true;
			}
			i++;
		}
		
		if (!found) {
			this.getSelectedCategory().addItem(newCategory);
		}
		
		this.getSelectedCategory().setSelectedItem(newCategory);
		
		this.getSelectedCategory().invalidate();		
		this.getSelectedCategory().repaint();
	}
	
	public JButton getBtAccept() {
		if (this.btAccept == null) {
			this.btAccept = new JButton("Accept");
			this.btAccept.addActionListener(new CatalogueCommand());
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
	
	public JButton getBtChangeSelection() {
		if (this.btChangeSelection == null){
			this.btChangeSelection = new JButton("Change");
			this.btChangeSelection.addActionListener(
					new CreateCatalogueCommand(this.owner));
		}
		return btChangeSelection;
	}
	
	public void setBtChangeSelection(JButton btChangeSelection) {
		this.btChangeSelection = btChangeSelection;
	}
	
	public JButton getBtAddCategory() {
		if (this.btAddCategory == null) {
			this.btAddCategory = new JButton("Add new");
			this.btAddCategory.addActionListener(new AddCategoryActionListener());
		}
		return btAddCategory;
	}
	
	public void setBtAddCategory(JButton btAddCategory) {
		this.btAddCategory = btAddCategory;
	}
	
	public JButton getBtCreateCategory() {
		if (this.btCreateCategory == null) {
			this.btCreateCategory = new JButton("Create");
			this.btCreateCategory.addActionListener(new CreateCategoryActionListener());
		}
		return btCreateCategory;
	}
	
	public void setBtCreateCategory(JButton btCreateCategory) {
		this.btCreateCategory = btCreateCategory;
	}
	
	public JButton getBtCancelCreateCategory() {
		if (this.btCancelCreateCategory == null) {
			this.btCancelCreateCategory = new JButton("Cancel");
			this.btCancelCreateCategory.addActionListener(new CancelCreateCategoryActionListener());
		}
		return btCancelCreateCategory;
	}
	
	public void setBtCancelCreateCategory(JButton btCancelCreateCategory) {
		this.btCancelCreateCategory = btCancelCreateCategory;
	}

	
	private void cleanUpCreateCategoryPanel() {
		getCentralPanel().remove(getNewCategoryPanel());
		
		getFdVolumeName().setEnabled(true);
		getSelectedCategory().setEnabled(true);
		getBtAccept().setEnabled(true);
		getBtCancel().setEnabled(true);
		getBtAddCategory().setEnabled(true);
		getBtChangeSelection().setEnabled(true);
		
		invalidate();
		repaint();
		pack();			
	}
	
	
	public class AddCategoryActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			owner.clearMessages();
			getCentralPanel().add(getNewCategoryPanel());
			
			getFdVolumeName().setEnabled(false);
			getSelectedCategory().setEnabled(false);
			getBtAccept().setEnabled(false);
			getBtCancel().setEnabled(false);
			getBtAddCategory().setEnabled(false);
			getBtChangeSelection().setEnabled(false);
			
			getFdNewCategory().setText("");
			
			invalidate();
			repaint();
			pack();
		}		
		
	}
	
	public class CancelCreateCategoryActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			cleanUpCreateCategoryPanel();
		}
		
	}
	
	public class CreateCategoryActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			owner.getViewObserver().fireAddCategory(fdNewCategory.getText(), owner);
			cleanUpCreateCategoryPanel();
		}		
	}
	
	public class CatalogueCommand implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			owner.getViewObserver().fireCatalogue(
					new File(fdSelectedPath.getText()), 
					(Category)selectedCategory.getSelectedItem(), 
					fdVolumeName.getText());
			
		}
		
	}

}
