package gui.wizards.createrequestwizard;

import gui.components.ColorTextPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class CRFinishPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Main panel elements
	private JPanel contentPanel;
	private JPanel jPanel1;
	private JLabel jLabel1;
    private ColorTextPane progressDescription;
    private JProgressBar progressSent;
    
    // Title elements
    private JSeparator separator;
    private JLabel titleLabel;
    private JPanel titlePanel;
    
    public CRFinishPanel() {
        
        super();
                
        contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        
        titlePanel = new JPanel();
        titleLabel = new JLabel();
        separator = new JSeparator();

        setLayout(new java.awt.BorderLayout());

        // Setting title
        titlePanel.setLayout(new java.awt.BorderLayout());
        titlePanel.setBackground(Color.gray);
        
        titleLabel.setFont(new Font("MS Sans Serif", Font.BOLD, 14));
        titleLabel.setText("Creating requests");
        titleLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        titleLabel.setOpaque(true);
        
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(separator, BorderLayout.SOUTH);

        add(titlePanel, BorderLayout.NORTH);
        JPanel secondaryPanel = new JPanel();
        secondaryPanel.add(contentPanel, BorderLayout.NORTH);
        add(secondaryPanel, BorderLayout.CENTER);
        
    }
    
    public void setProgressValue(int i) {
        progressSent.setValue(i);
    }
    public void setProgressMinimum(int i) {
    	progressSent.setMinimum(i);
    }
    public void setProgressMaximum(int i) {
    	progressSent.setMaximum(i);
    }
    
    private JPanel getContentPanel() {            
        
        JPanel contentPanel1 = new JPanel();

        jPanel1 = new JPanel();
        progressSent = new JProgressBar();
        progressDescription = new ColorTextPane();
        
        jLabel1 = new JLabel();
        
        contentPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new BoxLayout(jPanel1,BoxLayout.PAGE_AXIS));

        progressSent.setStringPainted(true);
        jPanel1.add(progressSent);

        // adding gap
        jPanel1.add(new JLabel(" "));
        progressDescription.setFont(new java.awt.Font("MS Sans Serif", 1, 11));

        JScrollPane logScrollPane = new JScrollPane(progressDescription);
        logScrollPane.setPreferredSize(new Dimension(50,100));
        logScrollPane.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
        jPanel1.add(logScrollPane);

        // adding gaps
        jPanel1.add(new JLabel(" "));
        jPanel1.add(new JLabel(" "));

        contentPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);

        jLabel1.setText("After the creation is completed, press Finish button to exit.");
        contentPanel1.add(jLabel1, java.awt.BorderLayout.SOUTH);
        
        return contentPanel1;
    }
    
    public void setProgressText(String s) {
        progressDescription.setText(s);
    }
    
    public void addProgressText(Color c, String newText) {
		progressDescription.append(c,newText);
	}

	public ColorTextPane getProgressDescription() {
		return progressDescription;
	}
	
}
