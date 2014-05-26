package gui.wizards.createrequestwizard;

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

import model.Request;
import model.Simulator;

public class CRRandomPanel extends JPanel {

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
	private JLabel percentageLabel;
	private ButtonGroup linkConnectivityGroup;
	private JRadioButton linkPerNodeButton;
	private JRadioButton linkPercentageButton;
	private JTextField nameTextField;
	private JTextField numTextField;
	private JTextField minNodesTextField;
	private JTextField maxNodesTextField;
	private JTextField minLinksTextField;
	private JTextField maxLinksTextField;
	private JTextField linkPercentageTextField;
	
    // Time distribution panel
	private JPanel timeDistributionPanel;
	private ButtonGroup timeDistributionGroup;
    private JRadioButton fixButton;
    private JRadioButton uniformButton;
    private JRadioButton normalButton;
    private JRadioButton poissonButton;
    private JTextField fixStartTextField;
    private JTextField uniformMinStartTextField;
    private JTextField uniformMaxStartTextField;
    private JTextField normalMeanTextField;
    private JTextField normalVarianceTextField;
    private JTextField poissonMeanTextField;
    private JTextField durationMinTextField;
    private JTextField durationMaxTextField;
    private JLabel uniformStartLabel;
    private JLabel normalMeanLabel;
    private JLabel normalVarianceLabel;
    private JLabel poissonMeanLabel;
    private JLabel durationLabel;
    
    // Error panel
    JPanel errorPanel;
    private JLabel errorLabel;
    
    public CRRandomPanel() {
        
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
        title.setText("Fill random requests options");
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
    	numLabel = new JLabel("Number of requests: ");
    	nodesLabel = new JLabel("Number of nodes range: ");
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
    	
    	timeDistributionPanel = new JPanel();
    	timeDistributionGroup = new ButtonGroup();
        fixButton = new JRadioButton();
        uniformButton = new JRadioButton();
        normalButton = new JRadioButton();
        poissonButton = new JRadioButton();
        fixStartTextField = new JTextField();
        fixStartTextField.setPreferredSize(new Dimension(30,20));
        uniformMinStartTextField = new JTextField();
        uniformMinStartTextField.setPreferredSize(new Dimension(30,20));
        uniformMaxStartTextField = new JTextField();
        uniformMaxStartTextField.setPreferredSize(new Dimension(30,20));
        normalMeanTextField = new JTextField();
        normalMeanTextField.setPreferredSize(new Dimension(30,20));
        normalVarianceTextField = new JTextField();
        normalVarianceTextField.setPreferredSize(new Dimension(30,20));
        poissonMeanTextField = new JTextField();
        poissonMeanTextField.setPreferredSize(new Dimension(30,20));
        durationMinTextField = new JTextField();
        durationMinTextField.setPreferredSize(new Dimension(30,20));
        durationMaxTextField = new JTextField();
        durationMaxTextField.setPreferredSize(new Dimension(30,20));
        uniformStartLabel = new JLabel("Start date range: ");
        normalMeanLabel = new JLabel("Mean: ");
        normalVarianceLabel = new JLabel("Variance: ");
        poissonMeanLabel = new JLabel("Mean: ");
        durationLabel = new JLabel("Lifetime range: ");
        
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
        // number of requests
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
        // link connectivity
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
        
        // Time distribution
        timeDistributionPanel.setLayout(new GridLayout(0,1));
        blackline = BorderFactory.createLineBorder(Color.black);
        title = BorderFactory.createTitledBorder(
 		       blackline, "Time distribution");
        title.setTitleJustification(TitledBorder.CENTER);
        timeDistributionPanel.setBorder(title);
        //fix start
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fixButton.setText("All requests starts at: ");
        fixButton.setSelected(true);
        auxPanel.add(fixButton);
        auxPanel.add(fixStartTextField);
        timeDistributionPanel.add(auxPanel);
        //uniform 
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uniformButton.setText("Uniform distribution");
        auxPanel.add(uniformButton);
        timeDistributionPanel.add(auxPanel);
        uniformStartLabel.setEnabled(false);
        uniformMinStartTextField.setEnabled(false);
        uniformMaxStartTextField.setEnabled(false);
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        auxPanel.add(uniformStartLabel);
        auxPanel.add(uniformMinStartTextField);
        auxPanel.add(new JLabel("-"));
        auxPanel.add(uniformMaxStartTextField);
        timeDistributionPanel.add(auxPanel);
        //normal
        normalButton.setText("Normal distribution");
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        auxPanel.add(normalButton);
        timeDistributionPanel.add(auxPanel);
        normalMeanLabel.setEnabled(false);
        normalMeanTextField.setEnabled(false);
        normalVarianceLabel.setEnabled(false);
        normalVarianceTextField.setEnabled(false);
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        auxPanel.add(normalMeanLabel);
        auxPanel.add(normalMeanTextField);
        timeDistributionPanel.add(auxPanel);
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        auxPanel.add(normalVarianceLabel);
        auxPanel.add(normalVarianceTextField);
        timeDistributionPanel.add(auxPanel);
        //poisson
        poissonButton.setText("Poisson distribution");
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        auxPanel.add(poissonButton);
        timeDistributionPanel.add(auxPanel);
        poissonMeanLabel.setEnabled(false);
        poissonMeanTextField.setEnabled(false);
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        auxPanel.add(poissonMeanLabel);
        auxPanel.add(poissonMeanTextField);
        timeDistributionPanel.add(auxPanel);
        //Duration
        auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        auxPanel.add(durationLabel);
        auxPanel.add(durationMinTextField);
        auxPanel.add(new JLabel("-"));
        auxPanel.add(durationMaxTextField);
        timeDistributionPanel.add(auxPanel);
        
        timeDistributionGroup.add(fixButton);
        timeDistributionGroup.add(uniformButton);
        timeDistributionGroup.add(normalButton);
        timeDistributionGroup.add(poissonButton);
        
        // Error message
        errorPanel.add(errorLabel);
        errorLabel.setForeground(Color.RED);
        
        contentPanel1.add(jPanel1, java.awt.BorderLayout.NORTH);
        contentPanel1.add(timeDistributionPanel, java.awt.BorderLayout.CENTER);
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
		fixStartTextField.getDocument().addDocumentListener(d);
		uniformMinStartTextField.getDocument().addDocumentListener(d);
		uniformMaxStartTextField.getDocument().addDocumentListener(d);
		normalMeanTextField.getDocument().addDocumentListener(d);
		normalVarianceTextField.getDocument().addDocumentListener(d);
		poissonMeanTextField.getDocument().addDocumentListener(d);
		durationMinTextField.getDocument().addDocumentListener(d);
		durationMaxTextField.getDocument().addDocumentListener(d);
		// Radio button listeners
		linkPerNodeButton.addActionListener(l);
		linkPercentageButton.addActionListener(l);
		fixButton.addActionListener(l);
		uniformButton.addActionListener(l);
		normalButton.addActionListener(l);
		poissonButton.addActionListener(l);
	}

	public boolean canFinish() {
		errorLabel.setText("");
		
		if (!nameTextField.getText().equals("") && !checkNameSyntax()) {
			errorLabel.setText("Name must be a string consisting of letters");
			return false;
		}
		if (!nameTextField.getText().equals("") && !checkNameAvailability()) {
			errorLabel.setText("Request prefix name already exists");
			return false;
		}
		if (!numTextField.getText().equals("") && !checkNum()) {
			errorLabel.setText("Incorrect number of requests");
			return false;
		}
		if (!minNodesTextField.getText().equals("") && !maxNodesTextField.getText().equals("") && !checkNodes()) {
			errorLabel.setText("Incorrect node range");
			return false;
		}
		if (!durationMinTextField.getText().equals("") && !durationMaxTextField.getText().equals("") && !checkDuration()) {
			errorLabel.setText("Incorrect duration range");
			return false;
		}
		if (!checkLinkConnectivity())
			return false;
		if (!checkTimeDistribution())
			return false;
		if (!nameTextField.getText().equals("") &&
				!numTextField.getText().equals("") &&
				!minNodesTextField.getText().equals("") &&
				!maxNodesTextField.getText().equals("") &&
				!durationMinTextField.getText().equals("") &&
				!durationMaxTextField.getText().equals(""))
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

	private boolean checkTimeDistribution() {
		if (this.fixButton.isSelected()) {
			if (!fixStartTextField.getText().equals("") &&
					!fixStartTextField.getText().matches("[0-9]+")) {
				errorLabel.setText("Incorrect start range");
				return false;
			}
			if (!fixStartTextField.getText().equals(""))
				return true;
			else return false;
		}
		else if (this.uniformButton.isSelected()) {
			if (!uniformMinStartTextField.getText().equals("") && 
					!uniformMaxStartTextField.getText().equals("") && 
					!checkUniform()) {
				errorLabel.setText("Incorrect uniform distribution range");
				return false;
			}
			if (!uniformMinStartTextField.getText().equals("") &&
					!uniformMaxStartTextField.getText().equals(""))
				return true;
			else return false;
		}
		else if (this.normalButton.isSelected()) {
			if (!normalMeanTextField.getText().equals("") && 
					!normalVarianceTextField.getText().equals("") && 
					!checkNormal()) {
				errorLabel.setText("Incorrect normal distribution parameters");
				return false;
			}
			if (!normalMeanTextField.getText().equals("") &&
					!normalVarianceTextField.getText().equals(""))
				return true;
			else return false;
		}
		else {
			if (!poissonMeanTextField.getText().equals("") &&
					!poissonMeanTextField.getText().matches("[0-9]+")) {
				errorLabel.setText("Incorrect Poisson's mean parameter");
				return false;
			}
			if (!poissonMeanTextField.getText().equals(""))
				return true;
			else return false;
		}
	}

	private boolean checkNormal() {
		if (!normalMeanTextField.getText().matches("[0-9]+")
				|| !normalVarianceTextField.getText().matches("[0-9]+"))
			return false;
		else
			return true;
	}

	private boolean checkUniform() {
		if (!uniformMaxStartTextField.getText().matches("[0-9]+")
				|| !uniformMinStartTextField.getText().matches("[0-9]+"))
			return false;
		else
			if (Integer.parseInt(uniformMinStartTextField.getText()) <= 
					Integer.parseInt(uniformMaxStartTextField.getText()))
				return true;
			else return false;
	}

	private boolean checkDuration() {
		if (!durationMaxTextField.getText().matches("[0]*[1-9]+[0-9]*")
				|| !durationMinTextField.getText().matches("[0]*[1-9]+[0-9]*"))
			return false;
		else
			if (Integer.parseInt(durationMinTextField.getText()) <= 
					Integer.parseInt(durationMaxTextField.getText()))
				return true;
			else return false;
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
		for (Request req : simulator.getRequests()) {
			if (req.getId().startsWith(nameTextField.getText())) {
				if (req.getId().matches(nameTextField.getText()+"[0-9]+"))
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
	
	public int getFixStart() {
		return Integer.parseInt(fixStartTextField.getText());
	}
	
	public int getUniformMin() {
		return Integer.parseInt(uniformMinStartTextField.getText());
	}
	
	public int getUniformMax() {
		return Integer.parseInt(uniformMaxStartTextField.getText());
	}
	
	public int getNormalMean() {
		return Integer.parseInt(normalMeanTextField.getText());
	}
	
	public int getNormalVariance() {
		return Integer.parseInt(normalVarianceTextField.getText());
	}
	
	public int getPoissonMean() {
		return Integer.parseInt(poissonMeanTextField.getText());
	}
	
	public int getDurationMin() {
		return Integer.parseInt(durationMinTextField.getText());
	}
	
	public int getDurationMax() {
		return Integer.parseInt(durationMaxTextField.getText());
	}
	
	public String getTimeDistribution() {
		if (fixButton.isSelected())
			return SimulatorConstants.FIXED_DISTRIBUTION;
		else if (uniformButton.isSelected())
			return SimulatorConstants.UNIFORM_DISTRIBUTION;
		else if (normalButton.isSelected())
			return SimulatorConstants.NORMAL_DISTRIBUTION;
		else return SimulatorConstants.POISSON_DISTRIBUTION;
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
	
	public void refreshTimeDistribution() {
		
        fixStartTextField.setEnabled(false);
        uniformMinStartTextField.setEnabled(false);
        uniformMaxStartTextField.setEnabled(false);
        normalMeanTextField.setEnabled(false);
        normalVarianceTextField.setEnabled(false);
        poissonMeanTextField.setEnabled(false);
        uniformStartLabel.setEnabled(false);
        normalMeanLabel.setEnabled(false);
        normalVarianceLabel.setEnabled(false);
        poissonMeanLabel.setEnabled(false);
		
		if (fixButton.isSelected())
			fixStartTextField.setEnabled(true);
		else if (uniformButton.isSelected()) {
			uniformStartLabel.setEnabled(true);
			uniformMinStartTextField.setEnabled(true);
			uniformMaxStartTextField.setEnabled(true);
		}
		else if (normalButton.isSelected()) {
			normalMeanLabel.setEnabled(true);
			normalMeanTextField.setEnabled(true);
			normalVarianceLabel.setEnabled(true);
			normalVarianceTextField.setEnabled(true);
		}
		else {
			poissonMeanLabel.setEnabled(true);
			poissonMeanTextField.setEnabled(true);
		}
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