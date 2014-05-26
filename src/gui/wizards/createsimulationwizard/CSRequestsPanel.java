package gui.wizards.createsimulationwizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import model.Request;

public class CSRequestsPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Title panel elements
    private JPanel titlePanel;
    private JLabel title;
    private JSeparator separator;
	
	// Main panel elements
    @SuppressWarnings("rawtypes")
	private JList availableList;
    @SuppressWarnings("rawtypes")
	private DefaultListModel availableListModel;
    @SuppressWarnings("rawtypes")
	private JList selectedList;
    @SuppressWarnings("rawtypes")
	private DefaultListModel selectedListModel;
    private JButton addButton;
    private JButton addAllButton;
    private JButton removeButton;
    private JButton removeAllButton;
    private JPanel westPanel;
    private JPanel centerPanel;
    private JPanel eastPanel;
    
    // Model elements
    private List<Request> requests;
    
	private JPanel contentPanel;
    
    public CSRequestsPanel(List<Request> requests) {
        
    	this.requests = requests;
    	
        contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));

        setLayout(new java.awt.BorderLayout()); 
        
        // Setting title
        titlePanel = new JPanel();
        separator = new JSeparator();
        titlePanel.setLayout(new java.awt.BorderLayout());
        titlePanel.setBackground(Color.gray);
        
        title = new JLabel();
        title.setFont(new Font("MS Sans Serif", Font.BOLD, 14));
        title.setText("Select requests for the simulation");
        title.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        title.setOpaque(true);
        
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(separator, BorderLayout.SOUTH);

        add(titlePanel, BorderLayout.NORTH);
        JPanel secondaryPanel = new JPanel();
        secondaryPanel.add(contentPanel, BorderLayout.NORTH);
        add(secondaryPanel, BorderLayout.CENTER);

    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JPanel getContentPanel() {
		
		JPanel contentPanel1 = new JPanel();
        
		//West panel
        westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel,BoxLayout.PAGE_AXIS));
        JLabel availableLabel = new JLabel("Available requests");
        availableLabel.setFont(new Font("MS Sans Serif", Font.BOLD, 12));
        availableLabel.setAlignmentX(CENTER_ALIGNMENT);
        westPanel.add(availableLabel);
        westPanel.add(Box.createRigidArea(new Dimension(0,5)));
        availableListModel = new DefaultListModel();
		availableList = new JList(availableListModel);
		availableList.setName("availableList");
		RequestListCellRenderer rlcr = new RequestListCellRenderer();
		availableList.setCellRenderer(rlcr);
		initAvailableList();
		JScrollPane availablePane = new JScrollPane(availableList);
		availablePane.setPreferredSize(new Dimension(200,250));
		availablePane.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
		westPanel.add(availablePane);

		//Center panel
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(0, 1));
		addButton = new JButton("Add >>");
		addButton.setName("add");
		addButton.setEnabled(false);
		centerPanel.add(addButton);
		addAllButton = new JButton("Add All >>");
		addAllButton.setName("addAll");
		addAllButton.setEnabled(true);
		centerPanel.add(addAllButton);
		centerPanel.add(new JLabel());
		removeButton = new JButton("<< Remove");
		removeButton.setName("remove");
		removeButton.setEnabled(false);
		centerPanel.add(removeButton);
		removeAllButton = new JButton("<< Remove All");
		removeAllButton.setName("removeAll");
		removeAllButton.setEnabled(false);
		centerPanel.add(removeAllButton);
		
		//East panel
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel,BoxLayout.Y_AXIS));
		JLabel selectedLabel = new JLabel("Selected requests");
		selectedLabel.setFont(new Font("MS Sans Serif", Font.BOLD, 12));
		selectedLabel.setAlignmentX(CENTER_ALIGNMENT);
        eastPanel.add(selectedLabel);
        eastPanel.add(Box.createRigidArea(new Dimension(0,5)));
        selectedListModel = new DefaultListModel();
        selectedList = new JList(selectedListModel);
        selectedList.setName("selectedList");
        selectedList.setCellRenderer(rlcr);
        JScrollPane selectedPane = new JScrollPane(selectedList);
		selectedPane.setPreferredSize(new Dimension(200,250));
		selectedPane.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
		eastPanel.add(selectedPane);
        
        contentPanel1.add(westPanel);
        contentPanel1.add(centerPanel);
        contentPanel1.add(eastPanel);
        
        return contentPanel1;
	}

	@SuppressWarnings("unchecked")
	private void initAvailableList() {
		for (Request request : requests)
			availableListModel.addElement(request);
	}

	public void addListeners(ActionListener l, MouseListener r, ListSelectionListener s) {
		availableList.addMouseListener(r);
		availableList.addListSelectionListener(s);
		selectedList.addMouseListener(r);
		selectedList.addListSelectionListener(s);
		addButton.addActionListener(l);
		addAllButton.addActionListener(l);
		removeButton.addActionListener(l);
		removeAllButton.addActionListener(l);
    }

	public boolean canFinish() {
		return selectedListModel.getSize()>0;
	}

	/** Refresh elements according the double clicked element **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doubleClickPerformed(JList theList, int index) {
		Object o = theList.getModel().getElementAt(index);
		if (theList.getName().equals("availableList")) {
			selectedListModel.addElement(o);
			availableListModel.removeElementAt(index);
		}
		else {
			availableListModel.addElement(o);
			selectedListModel.removeElementAt(index);
		}
	}

	/** Perform button actions **/
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void performButtonAction(JButton button) {
		if (button.getName().equals("add")) {
			Object[] os = availableList.getSelectedValues();
			for (Object o : os) {
				selectedListModel.addElement(o);
				availableListModel.removeElement(o);
			}
		} else if (button.getName().equals("addAll")) {
			for (int i=0; i<availableListModel.getSize(); i++)
				selectedListModel.addElement(availableListModel.getElementAt(i));
			availableListModel.removeAllElements();
		} else if (button.getName().equals("remove")) {
			Object[] os = selectedList.getSelectedValues();
			for (Object o : os) {
				availableListModel.addElement(o);
				selectedListModel.removeElement(o);
			}
		} else {
			for (int i=0; i<selectedListModel.getSize(); i++)
				availableListModel.addElement(selectedListModel.getElementAt(i));
			selectedListModel.removeAllElements();
		}
	}

	public void updateButtons() {
		if (!availableList.isSelectionEmpty())
			addButton.setEnabled(true);
		else addButton.setEnabled(false);
		if (availableListModel.getSize()>0)
			addAllButton.setEnabled(true);
		else addAllButton.setEnabled(false);
		if (!selectedList.isSelectionEmpty())
			removeButton.setEnabled(true);
		else removeButton.setEnabled(false);
		if (selectedListModel.getSize()>0)
			removeAllButton.setEnabled(true);
		else removeAllButton.setEnabled(false);
	}

	public List<Request> getSelectedRequests() {
		List<Request> selectedRequests = new ArrayList<Request>();
		for (int i = 0; i<selectedListModel.size(); i++) {
			selectedRequests.add((Request) selectedListModel.get(i));
		}
		return selectedRequests;
	}

}
