package gui.components;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import gui.Icons;

import java.awt.Font;
import java.util.Collection;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.components.Link;
import model.components.SubstrateLink;
import model.components.Node;
import model.components.Server;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import model.components.VirtualMachine;

/**
 * A collection of classes used to assemble popup mouse menus for the custom
 * edges and vertices.
 */
public class MouseMenus {
    
	// Link menu
    @SuppressWarnings("serial")
	public static class EdgeMenu extends JPopupMenu {        
        // private JFrame frame; 
        public EdgeMenu() {
            super("Link Menu");
            this.add(new LinkIdDisplay());
            this.addSeparator();
            this.add(new DeleteLinkMenuItem<Link>());
            this.addSeparator();
            this.add(new BandwidthDisplay());           
        }
        
    }
    
    // Link menu (without editing)
    @SuppressWarnings("serial")
	public static class NonEditEdgeMenu extends JPopupMenu {        
        // private JFrame frame; 
        public NonEditEdgeMenu() {
            super("Link Menu");
            this.add(new LinkIdDisplay());
            this.addSeparator();
            this.add(new BandwidthDisplay());           
        }
        
    }
    
    @SuppressWarnings("serial")
	public static class LinkIdDisplay extends JMenuItem implements EdgeMenuListener<Link> {
        @SuppressWarnings("rawtypes")
		public void setEdgeAndView(Link l, VisualizationViewer visComp) {
            this.setText("Link ID: "+l.toString());
            this.setFont(new Font(this.getFont().getName(),Font.BOLD,this.getFont().getSize()));
            this.setIcon(Icons.LINK);
        }
    }
    
    @SuppressWarnings("serial")
	public static class BandwidthDisplay extends JMenuItem implements EdgeMenuListener<Link> {
        @SuppressWarnings("rawtypes")
		public void setEdgeAndView(Link l, VisualizationViewer visComp) {
            this.setText("Bandwidth = "+l.getBandwidth());
        }
    }
    
    @SuppressWarnings("serial")
	public static class AvailableBandwidthDisplay extends JMenuItem implements EdgeMenuListener<Link> {
        @SuppressWarnings("rawtypes")
		public void setEdgeAndView(Link l, VisualizationViewer visComp) {
            this.setText("Available Bandwidth = "+((SubstrateLink)l).getAvailableBandwidth());
        }
    }
    
    // Node menu
    @SuppressWarnings("serial")
	public static class VertexMenu extends JPopupMenu {
        public VertexMenu() {
            super("Node Menu");
            this.add(new NodeIdDisplay());
            this.addSeparator();
            this.add(new DeleteNodeMenuItem<Node>());
            this.addSeparator();
           // this.add(new CpuDisplay());
           // this.add(new MemoryDisplay());
        }
    }
    
    // Node menu (without editing)
    @SuppressWarnings("serial")
	public static class NonEditVertexMenu extends JPopupMenu {
        public NonEditVertexMenu() {
            super("Node Menu");
            this.add(new NodeIdDisplay());
            this.addSeparator();
            //this.add(new CpuDisplay());
            //this.add(new MemoryDisplay());
        }
    }
    
    @SuppressWarnings("serial")
	public static class NodeIdDisplay extends JMenuItem implements VertexMenuListener<Node> {
        @SuppressWarnings("rawtypes")
		public void setVertexAndView(Node n, VisualizationViewer visComp) {
            this.setText("Node ID: "+n.toString());
            this.setFont(new Font(this.getFont().getName(),Font.BOLD,this.getFont().getSize()));
            this.setIcon(n.getSmallIcon());
        }
    }
    
    @SuppressWarnings("serial")
	public static class CpuDisplay extends JMenuItem implements VertexMenuListener<Node> {
        @SuppressWarnings("rawtypes")
		public void setVertexAndView(Node n, VisualizationViewer visComp) {
            this.setText("CPU = "+n.getCpu());
        }
    }
    
    @SuppressWarnings("serial")
	public static class MemoryDisplay extends JMenuItem implements VertexMenuListener<Node> {
        @SuppressWarnings("rawtypes")
		public void setVertexAndView(Node n, VisualizationViewer visComp) {
            this.setText("Memory = "+n.getMemory());
        }
    }
    
    @SuppressWarnings("serial")
	public static class VlansDisplay extends JMenuItem implements VertexMenuListener<Node> {
		@SuppressWarnings("rawtypes")
		public void setVertexAndView(Node n, VisualizationViewer visComp) {
			this.setText("VLANs = "+n.getVlans());
        }
    }
    
    @SuppressWarnings("serial")
	public static class DiskSpaceDisplay extends JMenuItem implements VertexMenuListener<Node> {
		@SuppressWarnings("rawtypes")
		public void setVertexAndView(Node n, VisualizationViewer visComp) {
            if (n instanceof VirtualMachine)
            	this.setText("Disk space = "+((VirtualMachine) n).getDiskSpace());
            else if (n instanceof Server)
            	this.setText("Disk space = "+((Server) n).getDiskSpace());
            else return;
        }
    } 
    
    @SuppressWarnings("serial")
  	public static class LogicalInterfaceDisplay extends JMenuItem implements VertexMenuListener<Node> {
  		@SuppressWarnings("rawtypes")
  		public void setVertexAndView(Node n, VisualizationViewer visComp) {
  			if (n instanceof SubstrateRouter){
  				this.setText("Log. Interf. = "+((SubstrateRouter)n).getLogicalInstances());
  			}
          }
      }
    
    @SuppressWarnings("serial")
	public static class AvailableCpuDisplay extends JMenuItem implements VertexMenuListener<Node> {
		@SuppressWarnings("rawtypes")
		public void setVertexAndView(Node n, VisualizationViewer visComp) {
        	this.setText("Available CPU = "+n.getAvailableCpu());
        }
    }
    
    @SuppressWarnings("serial")
	public static class AvailableMemoryDisplay extends JMenuItem implements VertexMenuListener<Node> {
		@SuppressWarnings("rawtypes")
		public void setVertexAndView(Node n, VisualizationViewer visComp) {
        	this.setText("Available Memory = "+n.getAvailableMemory());
        }
    }
    
    @SuppressWarnings("serial")
	public static class AvailableDiskSpaceDisplay extends JMenuItem implements VertexMenuListener<Node> {
		@SuppressWarnings("rawtypes")
		public void setVertexAndView(Node n, VisualizationViewer visComp) {
			if (n instanceof Server)
				this.setText("Available Disk space = "+((Server) n).getAvailableDiskSpace());
        }
    }
    
    @SuppressWarnings("serial")
	public static class AvailableVlansDisplay extends JMenuItem implements VertexMenuListener<Node> {
		@SuppressWarnings("rawtypes")
		public void setVertexAndView(Node n, VisualizationViewer visComp) {
			Collection<Link> links = ((GraphViewerPanel) visComp).getNodeLinks(n);
			if (n instanceof SubstrateSwitch ||
					n instanceof SubstrateRouter ||
					n instanceof Server)
				this.setText("Available VLANs = "+n.getAvailableVlans(links));
        }
    }
    
}
