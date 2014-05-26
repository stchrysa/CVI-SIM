package gui.wizards.createrequestwizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

public class CRSelectionPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Main panel elements
	private JPanel contentPanel;
	private JPanel jPanel1;
	private ButtonGroup buttonGroup;
    private JRadioButton randomButton;
    private JRadioButton importButton;
    private JRadioButton designButton;
    
    // Title panel elements
    private JPanel titlePanel;
    private JLabel title;
    private JSeparator separator;
    
    public CRSelectionPanel() {
        
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
        title.setText("Select how to create requests");
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
        
        buttonGroup = new ButtonGroup();
        randomButton = new JRadioButton();
        importButton = new JRadioButton();
        designButton = new JRadioButton();

        contentPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));
        
        // random option
        randomButton.setText("Create random requests");
        randomButton.setSelected(true);
        buttonGroup.add(randomButton);
        jPanel1.add(randomButton);
        jPanel1.add(new JLabel());
        // import option
        importButton.setText("Import requests from file");
        buttonGroup.add(importButton);
        importButton.setEnabled(false);
        jPanel1.add(importButton);
        jPanel1.add(new JLabel());
        // design option
        designButton.setText("Design request graphically");
        buttonGroup.add(designButton);
        designButton.setEnabled(false);
        jPanel1.add(designButton);
        
        contentPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);

        return contentPanel1;
	}

	public void addListeners(ActionListener l) {
		randomButton.addActionListener(l);
		importButton.addActionListener(l);
		designButton.addActionListener(l);
    }
	
	public boolean isRandomButtonSelected() {
        return randomButton.isSelected();
    }
	
	public boolean isImportButtonSelected() {
        return importButton.isSelected();
    }
	
	public boolean isDesignButtonSelected() {
        return designButton.isSelected();
    }
	
}
