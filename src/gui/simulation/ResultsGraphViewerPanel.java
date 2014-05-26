package gui.simulation;

import java.awt.Color;
import java.awt.Paint;
import java.util.Collection;

import javax.swing.Icon;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Transformer;

import model.components.Link;
import model.components.Node;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import model.components.Server;
import model.components.SubstrateLink;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import gui.SimulatorConstants;
import gui.components.MouseMenus;

/**
 * This class is the responsible of the visualization
 * of the graphs for the substrate results. It also creates desired listeners,
 * and transforming methods
 */
public class ResultsGraphViewerPanel extends VisualizationViewer<Node, Link>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	DefaultModalGraphMouse gm;
	@SuppressWarnings("rawtypes")
	ResultsMousePlugin mousePlugin;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResultsGraphViewerPanel(Layout<Node, Link> layout) {
		super(layout);
		
		// Setup up icons for nodes
		Transformer<Node, Icon> vertexIcon = new Transformer<Node,Icon>() {
			public Icon transform(Node n) {
				// Any resource under 20% of capacity is marked as RED
				if (n instanceof Server){
					if (n.getCpu()<SimulatorConstants.MAX_CPU*0.2 
							|| n.getMemory()<SimulatorConstants.MAX_MEMORY*0.2)
						return n.getRedIcon();
				}
				
				if (n instanceof SubstrateRouter)
					if (((SubstrateRouter)n).getLogicalInstances()<SimulatorConstants.MAX_LOGICAL_INSTANCES*0.2
							|| ((SubstrateRouter)n).getVlans()<SimulatorConstants.MAX_ROUTER_VLANS*0.2)
						return n.getRedIcon();
				else if (n instanceof SubstrateSwitch)
					if (((SubstrateSwitch)n).getMaxLogicalIfaces()<SimulatorConstants.MAX_LOGICAl_IFACES_SWITCH*0.2
							|| ((SubstrateSwitch)n).getVlans()<SimulatorConstants.MAX_SWITCH_VLANS*0.2)
						return n.getRedIcon();
				else if (n instanceof Server)
					if (((Server)n).getDiskSpace()<SimulatorConstants.MAX_DISK*0.2
							|| ((Server)n).getMaxLogicalIfaces()<SimulatorConstants.MAX_LOGICAl_IFACES_SERVER*0.2
							|| ((Server)n).getVlans()<SimulatorConstants.MAX_SERVER_VLANS*0.2)
						return n.getRedIcon();
				
				// Any resource under 100% available capacity is marked as YELLOW
			
				if (n instanceof Server){
					if (n.getCpu()<n.getAvailableCpu() 
							|| n.getMemory()<n.getAvailableMemory())
						return n.getYellowIcon();
				}
				
				if (n instanceof SubstrateRouter)
					if (((SubstrateRouter)n).getLogicalInstances()<SimulatorConstants.MAX_LOGICAL_INSTANCES
							|| ((SubstrateRouter)n).getVlans()<SimulatorConstants.MAX_ROUTER_VLANS)
						return n.getYellowIcon();
				else if (n instanceof SubstrateSwitch)
					if (((SubstrateSwitch)n).getMaxLogicalIfaces()<SimulatorConstants.MAX_LOGICAl_IFACES_SWITCH
							|| ((SubstrateSwitch)n).getVlans()<SimulatorConstants.MAX_SWITCH_VLANS)
						return n.getYellowIcon();
				else if (n instanceof Server)
					if (((Server)n).getDiskSpace()<SimulatorConstants.MAX_DISK
							|| ((Server)n).getMaxLogicalIfaces()<SimulatorConstants.MAX_LOGICAl_IFACES_SERVER
							|| ((Server)n).getVlans()<SimulatorConstants.MAX_SERVER_VLANS)
						return n.getYellowIcon();
				
				return n.getIcon();
			}
        };
        this.getRenderContext().setVertexIconTransformer(vertexIcon);
        
        //adapting node's shape to icons size and shape
//        Transformer<Node, Shape> shapeTransformer = new Transformer<Node, Shape>() {
//			public Shape transform(Node n) {
//				return new Rectangle(n.getIcon().getIconWidth(),n.getIcon().getIconHeight());
//			}
//        };
//		this.getRenderContext().setVertexShapeTransformer(shapeTransformer);
        
		//setting id of a node as a label
        Transformer<Node, String> vertexLabel = new Transformer<Node, String>() {
			public String transform(Node n) {
				return Integer.toString(n.getId());
			}
        };
        this.getRenderContext().setVertexLabelTransformer(vertexLabel);
        this.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);

        //Setting custom edges
	    Transformer<Link, String> linkLabel = new Transformer<Link, String>() {
			public String transform(Link l) {
				return Integer.toString(l.getId());
			}
	    };
	    this.getRenderContext().setEdgeLabelTransformer(linkLabel);
		this.getRenderContext().setEdgeShapeTransformer(new EdgeShape.CubicCurve());
		// Change color to red/yellow depending of resource capacity
		this.getRenderContext().setEdgeDrawPaintTransformer(new Transformer<Link, Paint>() {
			@Override
			public Paint transform(Link link) {
				if (link.getBandwidth()<SimulatorConstants.MAX_BW*0.2)
					return Color.RED;
				//else if (link.getBandwidth()<SimulatorConstants.MAX_BW)
				else if (link.getBandwidth()<((SubstrateLink) link).getAvailableBandwidth())
					return Color.YELLOW;
				else return Color.BLACK;
			}
		});	
		
        // Adding mouse interaction WITHOUT EDITION
        gm = new DefaultModalGraphMouse();
        gm.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
        this.addKeyListener(gm.getModeKeyListener());
        this.setGraphMouse(gm);
		
        // Popup menu mouse plugin... without edition
		this.addKeyListener(gm.getModeKeyListener()); //Key listeners
        mousePlugin = new ResultsMousePlugin();
        // Add some popup menus for the nodes and links to our mouse plugin.
        JPopupMenu edgeMenu = new MouseMenus.NonEditEdgeMenu();
        JPopupMenu vertexMenu = new MouseMenus.NonEditVertexMenu();
        mousePlugin.setEdgePopup(edgeMenu);
        mousePlugin.setVertexPopup(vertexMenu);
        gm.add(mousePlugin); // Add our plugin to the mouse
        this.setGraphMouse(gm);
        
	}

	@SuppressWarnings("rawtypes")
	public DefaultModalGraphMouse getGm() {
		return gm;
	}

	@SuppressWarnings("rawtypes")
	public void setGm(DefaultModalGraphMouse gm) {
		this.gm = gm;
	}

	@SuppressWarnings("rawtypes")
	public ResultsMousePlugin getMousePlugin() {
		return mousePlugin;
	}

	@SuppressWarnings("rawtypes")
	public void setMousePlugin(ResultsMousePlugin mousePlugin) {
		this.mousePlugin = mousePlugin;
	}
	
	public Collection<Link> getNodeLinks(Node n) {
		Collection<Link> links = this.getGraphLayout().getGraph().getIncidentEdges(n);
		return links;
	}
	
}
