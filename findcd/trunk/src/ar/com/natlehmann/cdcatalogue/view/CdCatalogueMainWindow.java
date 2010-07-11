package ar.com.natlehmann.cdcatalogue.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.io.File;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ar.com.natlehmann.cdcatalogue.Configuration;
import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelFacade;
import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.SearchField;
import ar.com.natlehmann.cdcatalogue.util.Validator;
import ar.com.natlehmann.cdcatalogue.view.action.CleanSearchCommand;
import ar.com.natlehmann.cdcatalogue.view.action.CreateCatalogueCommand;
import ar.com.natlehmann.cdcatalogue.view.action.FirstPageCommand;
import ar.com.natlehmann.cdcatalogue.view.action.LastPageCommand;
import ar.com.natlehmann.cdcatalogue.view.action.NextPageCommand;
import ar.com.natlehmann.cdcatalogue.view.action.PreviousPageCommand;
import ar.com.natlehmann.cdcatalogue.view.action.SearchCommand;
import ar.com.natlehmann.cdcatalogue.view.dataModel.ResourceResultsTableModel;
import ar.com.natlehmann.cdcatalogue.view.dataModel.VolumeTree;
import ar.com.natlehmann.cdcatalogue.view.dataModel.VolumeTreeBuilder;
import ar.com.natlehmann.cdcatalogue.view.listener.ShutDownAppListener;
import ar.com.natlehmann.cdcatalogue.view.lookandfeel.FontFactory;

public class CdCatalogueMainWindow extends JFrame implements CdCatalogueViewFacade {

	private static final long serialVersionUID = 8893958615180463540L;
	
	private JPanel topPanel;		// header
	private JPanel centralPanel;	// body
	private JPanel leftPanel;		// tree container
	private JPanel rightPanel;		// search and results table container
	
	private JPanel searchPanel;
	private JPanel resultsPanel;
	
	private JPanel searchTopPanel;		// where the "Catalog" button is
	private JPanel searchCentralPanel;	// where the search fields are
	private JPanel searchBottomPanel;	// "Clean" and "Search" buttons
	private JPanel resultsBottomPanel;	// Pagination buttons
	private JPanel resultsTopPanel;		// Number of results
	
	private JScrollPane resultsTableScrollPane;
	private JScrollPane volumeTreeScrollPane;
	
	private JButton btCatalogue;
	private JButton btClean;
	private JButton btSearch;
	private JButton btNextPage;
	private JButton btLastPage;
	private JButton btPreviousPage;
	private JButton btFirstPage;
	
	private JLabel lblNumberOfResults;
	private JLabel lblPageNumber;
	
	private VolumeTree volumeTree;
	private JTable resultsTable;
	
	private JTextArea messagesArea;
	
	private JPanel searchPanel1;
	private JPanel searchPanel2;
	private JPanel searchPanel3;
	private JPanel searchPanel4;
	private JPanel searchPanel5;
	private JPanel searchPanel6;
	private JPanel searchPanel7;
	private JPanel searchPanel8;
	
	private JTextField searchFdCategoryName;
	private JTextField searchFdVolumeName;
	private JTextField searchFdResourceName;
	private JTextField searchFdResourcePath;
	private JTextField searchFdResourceType;
	private JTextField searchFdDateFrom;
	private JTextField searchFdDateTo;
	private JTextField searchFdComments;
	
	private CdCatalogueModelFacade modelFacade;
	private CdCatalogueViewObserver viewObserver;

	private ResourceResultsTableModel resultsTableModel;
	
	private JFileChooser fileChooser;
	private CreateCatalogueDialog createCatalogueDialog;
	private EditCategoryDialog editCategoryDialog;
	
	
	public CdCatalogueMainWindow() {
		super("Cd Catalogue");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	
	public void setModelFacade(CdCatalogueModelFacade modelFacade) {
		this.modelFacade = modelFacade;
	}
	
	public CdCatalogueModelFacade getModelFacade() {
		return modelFacade;
	}
	
	public void setViewObserver(CdCatalogueViewObserver viewObserver) {
		this.viewObserver = viewObserver;
	}
	
	public CdCatalogueViewObserver getViewObserver() {
		return viewObserver;
	}
	
	public void init() {
		
		this.setLayout(new BorderLayout());
		this.add(this.getTopPanel(), BorderLayout.NORTH);
		this.add(this.getCentralPanel(), BorderLayout.CENTER);
		
		this.getCentralPanel().add(this.getLeftPanel(),BorderLayout.WEST);
		this.getCentralPanel().add(this.getRightPanel(), BorderLayout.CENTER);
		
		this.getRightPanel().add(this.getSearchPanel(), BorderLayout.NORTH);
		this.getRightPanel().add(this.getResultsPanel(), BorderLayout.CENTER);
		
		this.getSearchPanel().add(this.getSearchTopPanel(), BorderLayout.NORTH);
		this.getSearchPanel().add(this.getSearchCentralPanel(), BorderLayout.CENTER);
		
		this.getResultsPanel().add(this.getResultsTopPanel(), BorderLayout.NORTH);
		this.getResultsPanel().add(this.getResultsBottomPanel(), BorderLayout.SOUTH);
		
		// JButtons
		this.getSearchTopPanel().add(this.getMessagesArea());
		this.getSearchTopPanel().add(this.getBtCatalogue());		
		this.getResultsBottomPanel().add(this.getBtFirstPage());
		this.getResultsBottomPanel().add(this.getBtPreviousPage());
		this.getResultsBottomPanel().add(this.getLblPageNumber());
		this.getResultsBottomPanel().add(this.getBtNextPage());
		this.getResultsBottomPanel().add(this.getBtLastPage());
		this.checkDisabledButtons();
		
		// Table and Tree
		this.getLeftPanel().add(this.getVolumeTreeScrollPane());
		this.getResultsPanel().add(this.getResultsTableScrollPane(), BorderLayout.CENTER);
		
		this.getResultsTopPanel().add(this.getLblNumberOfResults());
		
		
		this.addWindowListener(new ShutDownAppListener(this));
		this.setSize(1100,700);
		this.setVisible(true);
	}

	
	public JPanel getTopPanel() {
		if (this.topPanel == null){
			this.topPanel = new JPanel();
			this.topPanel.setBackground(Color.RED);
		}
		return topPanel;
	}
	
	public void setTopPanel(JPanel topPanel) {
		this.topPanel = topPanel;
	}
	
	public JPanel getCentralPanel() {
		if (this.centralPanel == null) {
			this.centralPanel = new JPanel();
			this.centralPanel.setLayout(new BorderLayout());
		}
		return centralPanel;
	}
	
	public void setCentralPanel(JPanel centralPanel) {
		this.centralPanel = centralPanel;
	}
	
	public JPanel getLeftPanel() {
		if (this.leftPanel == null) {
			this.leftPanel = new JPanel();
			this.leftPanel.setLayout(new BorderLayout());
		}
		return leftPanel;
	}
	
	public void setLeftPanel(JPanel leftPanel) {
		this.leftPanel = leftPanel;
	}
	
	public JPanel getRightPanel() {
		if (this.rightPanel == null) {
			this.rightPanel = new JPanel();
			this.rightPanel.setLayout(new BorderLayout());
		}
		return rightPanel;
	}
	
	public void setRightPanel(JPanel rightPanel) {
		this.rightPanel = rightPanel;
	}
	
	public JPanel getSearchPanel() {
		if (this.searchPanel == null) {
			this.searchPanel = new JPanel();
			this.searchPanel.setLayout(new BorderLayout());
		}
		return searchPanel;
	}
	
	public void setSearchPanel(JPanel searchPanel) {
		this.searchPanel = searchPanel;
	}
	
	public JPanel getResultsPanel() {
		if (this.resultsPanel == null) {
			this.resultsPanel = new JPanel();
			this.resultsPanel.setLayout(new BorderLayout());
		}
		return resultsPanel;
	}
	
	public void setResultsPanel(JPanel resultsPanel) {
		this.resultsPanel = resultsPanel;
	}
	
	public JPanel getSearchTopPanel() {
		if (this.searchTopPanel == null) {
			this.searchTopPanel = new JPanel();
			this.searchTopPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		}
		return searchTopPanel;
	}
	
	public void setSearchTopPanel(JPanel searchTopPanel) {
		this.searchTopPanel = searchTopPanel;
	}
	
	public JPanel getSearchCentralPanel() {
		if (this.searchCentralPanel == null) {
			this.searchCentralPanel = new JPanel();
			this.searchCentralPanel.setLayout(new GridLayout(3,3));
			
			this.searchCentralPanel.add(this.getSearchPanel1());
			this.searchCentralPanel.add(this.getSearchPanel2());
			this.searchCentralPanel.add(this.getSearchPanel3());
			
			this.searchCentralPanel.add(this.getSearchPanel4());
			this.searchCentralPanel.add(this.getSearchPanel5());
			this.searchCentralPanel.add(this.getSearchPanel8());
			
			this.searchCentralPanel.add(this.getSearchPanel6());
			this.searchCentralPanel.add(this.getSearchPanel7());
			this.searchCentralPanel.add(this.getSearchBottomPanel());			
		}
		return searchCentralPanel;
	}
	
	public void setSearchCentralPanel(JPanel searchCentralPanel) {
		this.searchCentralPanel = searchCentralPanel;
	}
	
	public JPanel getSearchBottomPanel() {
		if (this.searchBottomPanel == null) {
			this.searchBottomPanel = new JPanel();
			this.searchBottomPanel.setLayout(new FlowLayout());
			
			this.searchBottomPanel.add(this.getBtClean());
			this.searchBottomPanel.add(this.getBtSearch());
		}
		return searchBottomPanel;
	}
	
	public void setSearchBottomPanel(JPanel searchBottomPanel) {
		this.searchBottomPanel = searchBottomPanel;
	}
	
	public JPanel getResultsBottomPanel() {
		if (this.resultsBottomPanel == null) {
			this.resultsBottomPanel = new JPanel();
			this.resultsBottomPanel.setLayout(new FlowLayout());
		}
		return resultsBottomPanel;
	}
	
	public void setResultsBottomPanel(JPanel resultsBottomPanel) {
		this.resultsBottomPanel = resultsBottomPanel;
	}
	
	public JPanel getResultsTopPanel() {
		if (this.resultsTopPanel == null) {
			this.resultsTopPanel = new JPanel();
			this.resultsTopPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		}
		return resultsTopPanel;
	}
	
	public void setResultsTopPanel(JPanel resultsTopPanel) {
		this.resultsTopPanel = resultsTopPanel;
	}
	
	public JButton getBtCatalogue() {
		if (this.btCatalogue == null) {
			this.btCatalogue = new JButton("Catalogue");
			this.btCatalogue.addActionListener(new CreateCatalogueCommand(this));
		}
		return btCatalogue;
	}

	public void setBtCatalogue(JButton btCatalogue) {
		this.btCatalogue = btCatalogue;
	}

	public JButton getBtClean() {
		if (this.btClean == null) {
			this.btClean = new JButton("Clean");
			this.btClean.addActionListener(new CleanSearchCommand(this));
		}
		return btClean;
	}

	public void setBtClean(JButton btClean) {
		this.btClean = btClean;
	}

	public JButton getBtSearch() {
		if (this.btSearch == null) {
			this.btSearch = new JButton("Search");
			this.btSearch.addActionListener(new SearchCommand(this));
		}
		return btSearch;
	}

	public void setBtSearch(JButton btSearch) {
		this.btSearch = btSearch;
	}

	public JButton getBtNextPage() {
		if (this.btNextPage == null) {
			this.btNextPage = new JButton(">");
			this.btNextPage.addActionListener(new NextPageCommand(this));
		}
		return btNextPage;
	}

	public void setBtNextPage(JButton btNextPage) {
		this.btNextPage = btNextPage;
	}

	public JButton getBtLastPage() {
		if (this.btLastPage == null) {
			this.btLastPage = new JButton(">>");
			this.btLastPage.addActionListener(new LastPageCommand(this));
		}
		return btLastPage;
	}

	public void setBtLastPage(JButton btLastPage) {
		this.btLastPage = btLastPage;
	}

	public JButton getBtPreviousPage() {
		if (this.btPreviousPage == null) {
			this.btPreviousPage = new JButton("<");
			this.btPreviousPage.addActionListener(new PreviousPageCommand(this));
		}
		return btPreviousPage;
	}

	public void setBtPreviousPage(JButton btPreviousPage) {
		this.btPreviousPage = btPreviousPage;
	}

	public JButton getBtFirstPage() {
		if (this.btFirstPage == null) {
			this.btFirstPage = new JButton("<<");
			this.btFirstPage.addActionListener(new FirstPageCommand(this));
		}
		return btFirstPage;
	}

	public void setBtFirstPage(JButton btFirstPage) {
		this.btFirstPage = btFirstPage;
	}
	
	public JLabel getLblNumberOfResults() {
		if (this.lblNumberOfResults == null) {
			this.lblNumberOfResults = new JLabel(this.getResultsTableModel().getNumberOfResults());
		}
		return lblNumberOfResults;
	}
	
	public void setLblNumberOfResults(JLabel numberOfResults) {
		this.lblNumberOfResults = numberOfResults;
	}
	
	public JLabel getLblPageNumber() {
		if (this.lblPageNumber == null) {
			this.lblPageNumber = new JLabel(this.getCurrentPageNumber());
		}
		return this.lblPageNumber;
	}
	
	public void setLblPageNumber(JLabel lblPageNumber) {
		this.lblPageNumber = lblPageNumber;
	}
	
	public JTextArea getMessagesArea() {
		if (this.messagesArea == null){
			this.messagesArea = new JTextArea(2,55);
			this.messagesArea.setEditable(false);
			this.messagesArea.setBackground(new Color(240,240,240));
			this.messagesArea.setFont(FontFactory.getSuccessMessageFont());
		}
		return messagesArea;
	}
	
	public void setMessagesArea(JTextArea messagesArea) {
		this.messagesArea = messagesArea;
	}

	public VolumeTree getVolumeTree() {
		if (this.volumeTree == null) {
			this.volumeTree = VolumeTreeBuilder.buildVolumeTree(modelFacade,this);
		}
		return volumeTree;
	}

	public void setVolumeTree(VolumeTree volumeTree) {
		this.volumeTree = volumeTree;
	}
	
	public ResourceResultsTableModel getResultsTableModel() {
		if (this.resultsTableModel == null) {
			this.resultsTableModel = new ResourceResultsTableModel(modelFacade);
		}
		return this.resultsTableModel;
	}

	public JTable getResultsTable() {
		if (this.resultsTable == null) {			
			this.resultsTable = new JTable(this.getResultsTableModel());
			
			this.resultsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			this.resultsTable.getColumnModel().getColumn(1).setPreferredWidth(70);
			this.resultsTable.getColumnModel().getColumn(2).setPreferredWidth(150);
			this.resultsTable.getColumnModel().getColumn(3).setPreferredWidth(250);
			this.resultsTable.getColumnModel().getColumn(4).setPreferredWidth(10);
			this.resultsTable.getColumnModel().getColumn(5).setPreferredWidth(35);
			this.resultsTable.getColumnModel().getColumn(6).setPreferredWidth(50);
			
			this.resultsTable.setRowHeight(16);
			this.resultsTable.setFont(FontFactory.getTableRowFont());
			
			this.resultsTable.setAutoCreateRowSorter(true);

		}
		return resultsTable;
	}

	public void setResultsTable(JTable resultsTable) {
		this.resultsTable = resultsTable;
	}
	
	public JScrollPane getResultsTableScrollPane() {
		if (this.resultsTableScrollPane == null) {
			this.resultsTableScrollPane = new JScrollPane(this.getResultsTable());
		}
		
		return this.resultsTableScrollPane;
	}
	
	public JScrollPane getVolumeTreeScrollPane() {
		if (this.volumeTreeScrollPane == null) {
			this.volumeTreeScrollPane = new JScrollPane(this.getVolumeTree());
		}
		
		return this.volumeTreeScrollPane;
	}

	public JPanel getSearchPanel1() {
		if (this.searchPanel1 == null) {
			this.searchPanel1 = new JPanel();
			this.searchPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			this.searchPanel1.add(new JLabel("Category name:"));
			this.searchPanel1.add(this.getSearchFdCategoryName());
		}
		return searchPanel1;
	}

	public void setSearchPanel1(JPanel searchPanel1) {
		this.searchPanel1 = searchPanel1;
	}

	public JPanel getSearchPanel2() {
		if (this.searchPanel2 == null) {
			this.searchPanel2 = new JPanel();
			this.searchPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			this.searchPanel2.add(new JLabel("Volume name:"));
			this.searchPanel2.add(this.getSearchFdVolumeName());
		}
		return searchPanel2;
	}

	public void setSearchPanel2(JPanel searchPanel2) {
		this.searchPanel2 = searchPanel2;
	}

	public JPanel getSearchPanel3() {
		if (this.searchPanel3 == null) {
			this.searchPanel3 = new JPanel();
			this.searchPanel3.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			this.searchPanel3.add(new JLabel("Resource name:"));
			this.searchPanel3.add(this.getSearchFdResourceName());
		}
		return searchPanel3;
	}

	public void setSearchPanel3(JPanel searchPanel3) {
		this.searchPanel3 = searchPanel3;
	}

	public JPanel getSearchPanel4() {
		if (this.searchPanel4 == null) {
			this.searchPanel4 = new JPanel();
			this.searchPanel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			this.searchPanel4.add(new JLabel("Resource path:"));
			this.searchPanel4.add(this.getSearchFdResourcePath());
		}
		return searchPanel4;
	}

	public void setSearchPanel4(JPanel searchPanel4) {
		this.searchPanel4 = searchPanel4;
	}

	public JPanel getSearchPanel5() {
		if (this.searchPanel5 == null) {
			this.searchPanel5 = new JPanel();
			this.searchPanel5.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			this.searchPanel5.add(new JLabel("Resource type:"));
			this.searchPanel5.add(this.getSearchFdResourceType());
		}
		return searchPanel5;
	}

	public void setSearchPanel5(JPanel searchPanel5) {
		this.searchPanel5 = searchPanel5;
	}

	public JPanel getSearchPanel6() {
		if (this.searchPanel6 == null) {
			this.searchPanel6 = new JPanel();
			this.searchPanel6.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			this.searchPanel6.add(new JLabel("Modified date from:"));
			this.searchPanel6.add(this.getSearchFdDateFrom());
		}
		return searchPanel6;
	}

	public void setSearchPanel6(JPanel searchPanel6) {
		this.searchPanel6 = searchPanel6;
	}

	public JPanel getSearchPanel7() {
		if (this.searchPanel7 == null) {
			this.searchPanel7 = new JPanel();
			this.searchPanel7.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			this.searchPanel7.add(new JLabel("Modified date to:"));
			this.searchPanel7.add(this.getSearchFdDateTo());
		}
		return searchPanel7;
	}

	public void setSearchPanel7(JPanel searchPanel7) {
		this.searchPanel7 = searchPanel7;
	}

	public JPanel getSearchPanel8() {
		if (this.searchPanel8 == null) {
			this.searchPanel8 = new JPanel();
			this.searchPanel8.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			this.searchPanel8.add(new JLabel("Comments:"));
			this.searchPanel8.add(this.getSearchFdComments());
		}
		return searchPanel8;
	}

	public void setSearchPanel8(JPanel searchPanel8) {
		this.searchPanel8 = searchPanel8;
	}

	public JTextField getSearchFdCategoryName() {
		if (this.searchFdCategoryName == null) {
			this.searchFdCategoryName = new JTextField(12);
			this.searchFdCategoryName.addActionListener(new SearchCommand(this));
		}
 		return searchFdCategoryName;
	}

	public void setSearchFdCategoryName(JTextField searchFdCategoryName) {
		this.searchFdCategoryName = searchFdCategoryName;
	}

	public JTextField getSearchFdVolumeName() {
		if (this.searchFdVolumeName == null) {
			this.searchFdVolumeName = new JTextField(12);
			this.searchFdVolumeName.addActionListener(new SearchCommand(this));
		}
		return searchFdVolumeName;
	}

	public void setSearchFdVolumeName(JTextField searchFdVolumeName) {
		this.searchFdVolumeName = searchFdVolumeName;
	}

	public JTextField getSearchFdResourceName() {
		if (this.searchFdResourceName == null) {
			this.searchFdResourceName = new JTextField(12);
			this.searchFdResourceName.addActionListener(new SearchCommand(this));
		}
		return searchFdResourceName;
	}

	public void setSearchFdResourceName(JTextField searchFdResourceName) {
		this.searchFdResourceName = searchFdResourceName;
	}

	public JTextField getSearchFdResourcePath() {
		if (this.searchFdResourcePath == null) {
			this.searchFdResourcePath = new JTextField(12);
			this.searchFdResourcePath.addActionListener(new SearchCommand(this));
		}
		return searchFdResourcePath;
	}

	public void setSearchFdResourcePath(JTextField searchFdResourcePath) {
		this.searchFdResourcePath = searchFdResourcePath;
	}

	public JTextField getSearchFdResourceType() {
		if (this.searchFdResourceType == null) {
			this.searchFdResourceType = new JTextField(12);
			this.searchFdResourceType.addActionListener(new SearchCommand(this));
		}
		return searchFdResourceType;
	}

	public void setSearchFdResourceType(JTextField searchFdResourceType) {
		this.searchFdResourceType = searchFdResourceType;
	}

	public JTextField getSearchFdDateFrom() {
		if (this.searchFdDateFrom == null) {
			this.searchFdDateFrom = new JTextField(12);
			this.searchFdDateFrom.addActionListener(new SearchCommand(this));
		}
		return searchFdDateFrom;
	}

	public void setSearchFdDateFrom(JTextField searchFdDateFrom) {
		this.searchFdDateFrom = searchFdDateFrom;
	}

	public JTextField getSearchFdDateTo() {
		if (this.searchFdDateTo == null) {
			this.searchFdDateTo = new JTextField(12);
			this.searchFdDateTo.addActionListener(new SearchCommand(this));
		}
		return searchFdDateTo;
	}

	public void setSearchFdDateTo(JTextField searchFdDateTo) {
		this.searchFdDateTo = searchFdDateTo;
	}

	public JTextField getSearchFdComments() {
		if (this.searchFdComments == null) {
			this.searchFdComments = new JTextField(12);
			this.searchFdComments.addActionListener(new SearchCommand(this));
		}
		return searchFdComments;
	}

	public void setSearchFdComments(JTextField searchFdComments) {
		this.searchFdComments = searchFdComments;
	}
	
	public JFileChooser getFileChooser() {
		if (this.fileChooser == null) {
			this.fileChooser = new JFileChooser();
			this.fileChooser.setCurrentDirectory(new File("/home/nlehmann/Desktop"));
			this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		return fileChooser;
	}
	
	public CreateCatalogueDialog getCreateCatalogueDialog() {
		if (this.createCatalogueDialog == null) {
			this.createCatalogueDialog = new CreateCatalogueDialog(this);
		}
		return this.createCatalogueDialog;
	}
	
	public EditCategoryDialog getEditCategoryDialog() {
		if (this.editCategoryDialog == null) {
			this.editCategoryDialog = new EditCategoryDialog(this);
		}
		return this.editCategoryDialog;
	}
	

	@Override
	public List<Parameter> getSearchValues() {
		
		List<Parameter> searchValues = new LinkedList<Parameter>();
		
		if (Validator.isNotNull(this.getSearchFdCategoryName().getText())) {
			searchValues.add(new Parameter(
					SearchField.CATEGORY_NAME, this.getSearchFdCategoryName().getText()));
		}
		if (Validator.isNotNull(this.getSearchFdResourceName().getText())) {
			searchValues.add(new Parameter(
					SearchField.RESOURCE_NAME, this.getSearchFdResourceName().getText()));
		}
		if (Validator.isNotNull(this.getSearchFdComments().getText())) {
			searchValues.add(new Parameter(
					SearchField.RESOURCE_COMMENTS, 
					this.getSearchFdComments().getText()));
		}
		if (Validator.isNotNull(this.getSearchFdDateFrom().getText())) {
			try {
				searchValues.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_FROM, 
						Configuration.getStringAsDate(this.getSearchFdDateFrom().getText())));
				
			} catch (ParseException e) {
				this.showErrorMessage("Wrong date format");
				// TODO
				e.printStackTrace();
			}
		}
		if (Validator.isNotNull(this.getSearchFdDateTo().getText())) {
			try {
				searchValues.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_TO, 
						Configuration.getStringAsDate(this.getSearchFdDateTo().getText())));
			} catch (ParseException e) {
				this.showErrorMessage("Wrong date format");
				//TODO
				e.printStackTrace();
			}
		}
		if (Validator.isNotNull(this.getSearchFdResourcePath().getText())) {
			searchValues.add(new Parameter(SearchField.RESOURCE_PATH, 
					this.getSearchFdResourcePath().getText()));
		}
		if (Validator.isNotNull(this.getSearchFdResourceType().getText())) {
			searchValues.add(new Parameter(SearchField.RESOURCE_TYPE, 
					this.getSearchFdResourceType().getText()));
		}
		if (Validator.isNotNull(this.getSearchFdVolumeName().getText())) {
			searchValues.add(new Parameter(SearchField.VOLUME_NAME, 
					this.getSearchFdVolumeName().getText()));
		}
		
		return searchValues;
	}

	@Override
	public Page getCurrentPage() {
		return this.getResultsTableModel().getPage();
	}
	
	private String getCurrentPageNumber() {
		return this.getCurrentPage().toString() + " of " 
				+ this.getResultsTableModel().getLastPageNumber();
	}

	@Override
	public OrderBy getOrderByField() {
		return this.resultsTableModel.getOrderBy();
	}
	
	@Override
	public void refreshResults() {
		this.getResultsTableModel().setSearchParameters(this.getSearchValues());
		this.getLblNumberOfResults().setText(this.getResultsTableModel().getNumberOfResults());
		this.showFirstPage();	
	}
	
	@Override
	public void cleanSearchFields() {
		this.getSearchFdCategoryName().setText("");
		this.getSearchFdComments().setText("");
		this.getSearchFdDateFrom().setText("");
		this.getSearchFdDateTo().setText("");
		this.getSearchFdResourceName().setText("");
		this.getSearchFdResourcePath().setText("");
		this.getSearchFdResourceType().setText("");
		this.getSearchFdVolumeName().setText("");		
	}
	
	public void cleanTreeSelection() {
		this.getVolumeTree().setSelectionPath(null);
	}
	
	private void checkDisabledButtons() {
		if (this.getResultsTableModel().getPage().getPageNumber() == 1) {
			
			this.getBtPreviousPage().setEnabled(false);
			this.getBtFirstPage().setEnabled(false);
		
		} else {
			this.getBtPreviousPage().setEnabled(true);
			this.getBtFirstPage().setEnabled(true);
		}
		
		if (this.getResultsTableModel().getPage().getPageNumber() 
				== this.getResultsTableModel().getLastPageNumber()) {
			
			this.getBtNextPage().setEnabled(false);
			this.getBtLastPage().setEnabled(false);
		
		} else {
			this.getBtNextPage().setEnabled(true);
			this.getBtLastPage().setEnabled(true);
		}
		
		this.getLblNumberOfResults().setText(this.getResultsTableModel().getNumberOfResults());
		this.getLblPageNumber().setText(this.getCurrentPageNumber());
	}

	@Override
	public void showFirstPage() {
		this.getResultsTableModel().setPage(new Page(1));
		this.getResultsTableModel().fireTableDataChanged();		
		this.checkDisabledButtons();
	}

	@Override
	public void showLastPage() {
		this.getResultsTableModel().setPage(
				new Page(this.getResultsTableModel().getLastPageNumber()));
		this.getResultsTableModel().fireTableDataChanged();
		this.checkDisabledButtons();
	}

	@Override
	public void showNextPage() {
		this.getResultsTableModel().setPage(
				this.getResultsTableModel().getPage().next());
		this.getResultsTableModel().fireTableDataChanged();	
		this.checkDisabledButtons();
	}

	@Override
	public void showPreviousPage() {
		this.getResultsTableModel().setPage(
				this.getResultsTableModel().getPage().previous());
		this.getResultsTableModel().fireTableDataChanged();
		this.checkDisabledButtons();
	}

	@Override
	public void filterResultsByVolumeCategory(String categoryName, String volumeName) {
		
		this.cleanSearchFields();
		this.getSearchFdVolumeName().setText(volumeName);
		this.getSearchFdCategoryName().setText(categoryName);
		this.refreshResults();
		
	}
	
	public void filterResultsByCategoryName(String categoryName) {
		
		this.cleanSearchFields();
		this.getSearchFdCategoryName().setText(categoryName);
		this.refreshResults();
		
	}
	
	public void chooseFile() {
		int returnVal = this.getFileChooser().showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		       
		       this.getCreateCatalogueDialog().show(getFileChooser().getSelectedFile());
		    }
	}
	
	private void showMessage(String message) {
		if (getCreateCatalogueDialog().isVisible()) {
			getCreateCatalogueDialog().getMessagesArea().setText(message);
		
		} else {
			
			if (getEditCategoryDialog().isVisible()) {
				getEditCategoryDialog().getMessagesArea().setText(message);
				
			} else {
				this.getMessagesArea().setText(message);
			}
		}
	}
	
	public void showErrorMessage(String message) {
		this.showMessage(message);
	}
	
	public void showSuccessMessage(String message) {
		this.showMessage(message);
	}
	
	public void clearMessages() {
		getCreateCatalogueDialog().getMessagesArea().setText("");
		getEditCategoryDialog().getMessagesArea().setText("");
		this.getMessagesArea().setText("");
	}

	@Override
	public void addCategoryItem(Category newCategory) {
		this.getCreateCatalogueDialog().reloadCategories(newCategory);
		VolumeTreeBuilder.addCategory(this.getVolumeTree(), newCategory.getCategoryName());		
	}
	
	public void addVolumeTreeNode(Category category, String volumeName) {
		VolumeTreeBuilder.addVolume(this.getVolumeTree(), category.getCategoryName(), volumeName);
	}
	
	public void mergeCategories(Category oldCategory, Category existentCategory) {
		VolumeTreeBuilder.mergeCategories(this.getVolumeTree(), oldCategory.getCategoryName(), 
				existentCategory.getCategoryName());
	}
	
	public void refreshCategory(String oldCategoryName, String newCategoryName) {
		VolumeTreeBuilder.refreshCategory(this.getVolumeTree(), 
				oldCategoryName, newCategoryName);
		this.refreshResults();
	}
	
	public void closeDialogues() {
		if (getCreateCatalogueDialog().isVisible()) {
			this.getCreateCatalogueDialog().dispose();
		}
		if (getEditCategoryDialog().isVisible()) {
			this.getEditCategoryDialog().dispose();
		}
	}

	@Override
	public boolean isConfirmed(String message) {
		return (JOptionPane.showConfirmDialog(
				this.getVisibleWindow(), message, 
				"Confirmation", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION);
	}

	@Override
	public void showEditCategoryDialog(String categoryName) {
		this.getEditCategoryDialog().show(categoryName);		
	}
	
	private JDialog getVisibleDialog() {
		
		if (getCreateCatalogueDialog().isVisible()) {
			return this.getCreateCatalogueDialog();
		}
		
		if (getEditCategoryDialog().isVisible()) {
			return this.getEditCategoryDialog();
		}
		
		return null;
	}
	
	private Window getVisibleWindow() {
		
		return (this.getVisibleDialog() != null) ? this.getVisibleDialog() : this;
	}

}
