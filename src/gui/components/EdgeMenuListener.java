package gui.components;
/*
 * EdgeMenuListener.java
 *
 * Created on March 21, 2007, 2:41 PM; Updated May 29, 2007
 * Copyright March 21, 2007 Grotto Networking
 */


import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * An interface for menu items that are interested in knowning the currently selected edge and
 * its visualization component context.  Used with PopupVertexEdgeMenuMousePlugin.
 */
public interface EdgeMenuListener<E> {
    /**
     * Used to set the edge and visulization component.
     * @param e 
     * @param visComp 
     */
     @SuppressWarnings("rawtypes")
	void setEdgeAndView(E e, VisualizationViewer visView); 
    
}
