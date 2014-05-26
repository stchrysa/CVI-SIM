package gui.wizards.createsubstratewizard;

import gui.SimulatorConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;

import model.Simulator;
import model.Substrate;

public class CSRandomPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Simulator simulator;
	
	// Title panel elements
	private JPanel titlePanel;
    private JLabel title;
    private JSeparator separator;
	
	// Main panel elements
	private JPanel contentPanel;
	private JPanel jPanel1;
	private JLabel nameLabel;
	private JLabel numLabel;
	private JLabel nodesLabel;
	@SuppressWarnings("unused")
	private JLabel linksLabel;
	private JLabel percentageLabel;
	private JTextField nameTextField;
	private JTextField numTextField;
	private JTextField minNodesTextField;
	private JTextField maxNodesTextField;
	private JTextField minLinksTextField;
	private JTextField maxLinksTextField;
	private JTextField linkPercentageTextField;
	private ButtonGroup linkConnectivityGroup;
	private JRadioButton linkPerNodeButton;
	private JRadioButton linkPercentageButton;
	
    // Error panel
    JPanel errorPanel;
    private JLabel errorLabel;
    
    public CSRandomPanel() {
        
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
        title.setText("Fill random substrates options");
        title.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        title.setOpaque(true);
        
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(separator, BorderLayout.SOUTH);

        add(titlePanel, BorderLayout.NORTH);
        JPanel secondaryPanel = new JPanel();
        secondaryPanel.add(contentPanel, BorderLayout.NORTH);
        add(secondaryPanel, BorderLayout.CENTER);
        
    }

	private JPanel getContentPanel() {
		
		JPanel contentPanel1 = new JPanel();
		jPanel1 = new JPanel();
        
        nameLabel = new JLabel("Prefix name: ");
    	numLabel = new JLabel("Number of substrates: ");
    	nodesLabel = new JLabel("Number of nodes range: ");
    	linksLabel = new JLabel("Links per node range: ");
    	percentageLabel = new JLabel("(%)");
    	linkConnectivityGroup = new ButtonGroup();
        linkPerNodeButton = new JRadioButton();
        linkPercentageButton = new JRadioButton();
    	nameTextField = new JTextField();
    	nameTextField.setPreferredSize(new Dimension(100,20));
    	numTextField = new JTextField();
    	numTextField.setPreferredSize(new Dimension(100,20));
    	minNodesTextField = new JTextField();
    	minNodesTextField.setPreferredSize(new Dimension(30,20));
    	maxNodesTextField = new JTextField();
    	maxNodesTextField.setPreferredSize(new Dimension(30,20));
    	minLinksTextField = new JTextField();
    	minLinksTextField.setPreferredSize(new Dimension(30,20));
    	maxLinksTextField = new JTextField();
    	maxLinksTextField.setPreferredSize(new Dimension(30,20));
    	linkPercentageTextField = new JTextField();
    	linkPercentageTextField.setPreferredSize(new Dimension(30,20));
        
        errorPanel = new JPanel();
    	errorLabel = new JLabel("");
    	
        contentPanel1.setLayout(new BorderLayout());
        JPanel auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // General information
        jPanel1.setLayout(new GridLayout(0,1));
        Border blackline = BorderFactory.createLineBorder(Color.black);
        TitledBorder title = BorderFactory.createTitledBorder(
 		       blackline, "General options");
        title.setTitleJustification(TitledBorder.CENTER);
        jPanel1.setBorder(title);
        // name
        auxPanel.add(nameLabel);
        auxPanel.add(nameTextField);
        jPanel1.add(auxPanel);
        // number of substrates
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        auxPanel.add(numLabel);
        auxPanel.add(numTextField);
        jPanel1.add(auxPanel);
        // nodes range
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        auxPanel.add(nodesLabel);
        auxPanel.add(minNodesTextField);
        auxPanel.add(new JLabel("-"));
        auxPanel.add(maxNodesTextField);
        jPanel1.add(auxPanel);
        // links range
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linkPerNodeButton.setText("Links per node range: ");
        linkPerNodeButton.setSelected(true);
        auxPanel.add(linkPerNodeButton);
        auxPanel.add(minLinksTextField);
        auxPanel.add(new JLabel("-"));
        auxPanel.add(maxLinksTextField);
        jPanel1.add(auxPanel);
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linkPercentageButton.setText("Link probability: ");
        auxPanel.add(linkPercentageButton);
        linkPercentageTextField.setEnabled(false);
        percentageLabel.setEnabled(false);
        auxPanel.add(linkPercentageTextField);
        auxPanel.add(percentageLabel);
        jPanel1.add(auxPanel);
        linkConnectivityGroup.add(linkPercentageButton);
        linkConnectivityGroup.add(linkPerNodeButton);
        
        // Error message
        errorPanel.add(errorLabel);
        errorLabel.setForeground(Color.RED);
        
        contentPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);
        contentPanel1.add(errorPanel, java.awt.BorderLayout.SOUTH);
        
        return contentPanel1;
	}

	public void addListeners(DocumentListener d, ActionListener l) {
		// Textfield listeners
		nameTextField.getDocument().addDocumentListener(d);
		nameTextField.getDocument().addDocumentListener(d);
		numTextField.getDocument().addDocumentListener(d);
		minNodesTextField.getDocument().addDocumentListener(d);
		maxNodesTextField.getDocument().addDocumentListener(d);
		minLinksTextField.getDocument().addDocumentListener(d);
		maxLinksTextField.getDocument().addDocumentListener(d);
		linkPercentageTextField.getDocument().addDocumentListener(d);
		// Radio button listeners
		linkPerNodeButton.addActionListener(l);
		linkPercentageButton.addActionListener(l);
	}

	public boolean canFinish() {
		errorLabel.setText("");
		
		if (!nameTextField.getText().equals("") && !checkNameSyntax()) {
			errorLabel.setText("Name must be a string consisting of letters");
			return false;
		}
		if (!nameTextField.getText().equals("") && !checkNameAvailability()) {
			errorLabel.setText("Substrate prefix name already exists");
			return false;
		}
		if (!numTextField.getText().equals("") && !checkNum()) {
			errorLabel.setText("Incorrect number of substrates");
			return false;
		}
		if (!minNodesTextField.getText().equals("") && !maxNodesTextField.getText().equals("") && !checkNodes()) {
			errorLabel.setText("Incorrect node range");
			return false;
		}
		if (!checkLinkConnectivity())
			return false;
		if (!nameTextField.getText().equals("") &&
				!numTextField.getText().equals("") &&
				!minNodesTextField.getText().equals("") &&
				!maxNodesTextField.getText().equals(""))
			return true;
		else return false;

	}

	private boolean checkLinkConnectivity() {
		if (this.linkPerNodeButton.isSelected()) {
			if (!minLinksTextField.getText().equals("") && 
					!maxLinksTextField.getText().equals("") && 
					!checkLinksPerNode()) {
				errorLabel.setText("Incorrect link range");
				return false;
			}
			if (!minLinksTextField.getText().equals("") &&
					!maxLinksTextField.getText().equals(""))
				return true;
			else return false;
		} else {
			if (!linkPercentageTextField.getText().equals("") &&
					!checkLinkPercentage()) {
				errorLabel.setText("Link probability must be a percentage");
				return false;
			}
			if (!linkPercentageTextField.getText().equals(""))
				return true;
			else return false;
		}
	}
	
	private boolean checkLinksPerNode() {
		if (!maxLinksTextField.getText().matches("[0]*[1-9]+[0-9]*")
				|| !minLinksTextField.getText().matches("[0]*[1-9]+[0-9]*"))
			return false;
		else
			if (Integer.parseInt(minLinksTextField.getText()) <= 
					Integer.parseInt(maxLinksTextField.getText()))
				return true;
			else return false;
	}
	
	private boolean checkLinkPercentage() {
		int value;
		try {
			value = Integer.parseInt(linkPercentageTextField.getText());
		} catch (NumberFormatException e) {
			return false;
		}
		if ((0 > value)||(value > 100))
			return false;
		return true;
	}
	
	private boolean checkNodes() {
		if (!maxNodesTextField.getText().matches("[0]*[1-9]+[0-9]*")
				|| !minNodesTextField.getText().matches("[0]*[1-9]+[0-9]*"))
			return false;
		else
			if (Integer.parseInt(minNodesTextField.getText()) <= 
					Integer.parseInt(maxNodesTextField.getText()))
				return true;
			else return false;
	}

	private boolean checkNum() {
		return numTextField.getText().matches("[0]*[1-9]+[0-9]*");
	}

	private boolean checkNameSyntax() {
		return nameTextField.getText().matches("[a-zA-Z]+");
	}

	private boolean checkNameAvailability() {
		for (Substrate subs : simulator.getSubstrates()) {
			if (subs.getId().startsWith(nameTextField.getText())) {
				if (subs.getId().matches(nameTextField.getText()+"[0-9]+"))
					return false;
			}	
		}
		return true;
	}
	
	public String getPrefix() {
		return nameTextField.getText();
	}

	public int getNumRequests() {
		return Integer.parseInt(numTextField.getText());
	}

	public int getMinNodes() {
		return Integer.parseInt(minNodesTextField.getText());
	}

	public int getMaxNodes() {
		return Integer.parseInt(maxNodesTextField.getText());
	}
	
	public int getMinLinks() {
		return Integer.parseInt(minLinksTextField.getText());
	}

	public int getMaxLinks() {
		return Integer.parseInt(maxLinksTextField.getText());
	}
	
	public double getLinkProbability() {
		return Double.parseDouble(linkPercentageTextField.getText())/100;
	}
	
	public String getLinkConnectivity() {
		if (linkPerNodeButton.isSelected())
			return SimulatorConstants.LINK_PER_NODE_CONNECTIVITY;
		else
			return SimulatorConstants.PERCENTAGE_CONNECTIVITY;
	}
	
	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}
	
	public void refreshLinkConnectivity() {
		minLinksTextField.setEnabled(false);
		maxLinksTextField.setEnabled(false);
		percentageLabel.setEnabled(false);
		linkPercentageTextField.setEnabled(false);
		if (linkPerNodeButton.isSelected()) {
			minLinksTextField.setEnabled(true);
			maxLinksTextField.setEnabled(true);
		} else {
			percentageLabel.setEnabled(true);
			linkPercentageTextField.setEnabled(true);
		}
	}

}
