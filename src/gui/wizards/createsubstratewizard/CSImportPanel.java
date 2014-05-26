package gui.wizards.createsubstratewizard;

import gui.Icons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

public class CSImportPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Title panel elements
	private JPanel titlePanel;
    private JLabel title;
    private JSeparator separator;
	private JPanel contentPanel;
	
	// Main panel elements
    private JButton openButton;
    private JTextField openedTextField;
    private JFileChooser fileChooser;
	private JPanel jPanel1;
    
    // Error panel
    JPanel errorPanel;
    private JLabel errorLabel;
    
    // File
    File file;
    
    public CSImportPanel() {
        
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
        title.setText("Select file to import substrate(s)");
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
        
		openButton = new JButton("Open File...",Icons.OPEN);
		openedTextField = new JTextField();
		openedTextField.setPreferredSize(new Dimension(200,20));
		openedTextField.setEditable(false);
		
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new XmlFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
        
        errorPanel = new JPanel();
    	errorLabel = new JLabel("");
    	
        contentPanel1.setLayout(new BorderLayout());
        JPanel auxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // General information
        jPanel1.setLayout(new GridLayout(0,1));
        Border blackline = BorderFactory.createLineBorder(Color.black);
        TitledBorder title = BorderFactory.createTitledBorder(
 		       blackline, "Choose file to import");
        title.setTitleJustification(TitledBorder.CENTER);
        jPanel1.setBorder(title);
        // name
        auxPanel.add(openButton);
        auxPanel.add(openedTextField);
        jPanel1.add(auxPanel);
        
        // Error message
        errorPanel.add(errorLabel);
        errorLabel.setForeground(Color.RED);
        
        contentPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);
        contentPanel1.add(errorPanel, java.awt.BorderLayout.SOUTH);
        
        return contentPanel1;
	}

	public void addListeners(ActionListener al) {
		// Button listener
		openButton.addActionListener(al);
	}

	public boolean canFinish() {
		errorLabel.setText("");
		
		if (openedTextField.getText().equals(""))
			return false;
		else if (!checkOpenedFile()) {
			errorLabel.setText("Selected File does not exists");
			return false;
		}
		else return true;

	}

	private boolean checkOpenedFile() {
		if (file==null) return false;
		else if (file.exists()) return true;
		else return false;
	}
	
	public void setOpenedFileText(String text) {
		this.openedTextField.setText(text);
	}
	
	public JButton getOpenButton() {
		return openButton;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File selectedFile) {
		this.file = selectedFile;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}
}

class XmlFilter extends FileFilter {

	public final static String xml = "xml";
	public final static String xsd = "xsd";

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
		String extension = getExtension(f);
	    if (extension != null) {
			if (extension.equals(xml) ||
					extension.equals(xsd))
				return true;
			else
			    return false;
	    }
		return false;
	}

	@Override
	public String getDescription() {
		return "XML files";
	}
	
	private String getExtension(File f) {
		String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
	}
}
