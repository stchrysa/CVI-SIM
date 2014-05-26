package gui.components;
/*
 * DeleteEdgeMenuItem.java
 *
 * Created on March 21, 2007, 2:47 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */


import edu.uci.ics.jung.visualization.VisualizationViewer;
import gui.Icons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * A class to implement the deletion of an edge from within a 
 * PopupVertexEdgeMenuMousePlugin.
 */
@SuppressWarnings("serial")
public class DeleteLinkMenuItem<E> extends JMenuItem implements EdgeMenuListener<E> {
    private E edge;
    @SuppressWarnings("rawtypes")
	private VisualizationViewer visComp;
    
    /** Creates a new instance of DeleteEdgeMenuItem */
    public DeleteLinkMenuItem() {
        super("Delete Link");
        this.addActionListener(new ActionListener(){
            @SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
                visComp.getPickedEdgeState().pick(edge, false);
                visComp.getGraphLayout().getGraph().removeEdge(edge);
                visComp.repaint();
            }
        });
    }

    /**
     * Implements the EdgeMenuListener interface to update the menu item with info
     * on the currently chosen edge.
     * @param edge 
     * @param visComp 
     */
    @SuppressWarnings("rawtypes")
	public void setEdgeAndView(E edge, VisualizationViewer visComp) {
        this.edge = edge;
        this.visComp = visComp;
        this.setText("Delete Link " + edge.toString());
        this.setIcon(Icons.DELETE);
    }
    
}
