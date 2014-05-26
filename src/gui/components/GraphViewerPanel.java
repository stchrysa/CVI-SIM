package gui.components;

import java.awt.Color;
import java.util.Collection;

import javax.swing.Icon;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import model.RequestLinkFactory;
import model.RequestNodeFactory;
import model.components.Link;
import model.components.Node;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.PickableEdgePaintTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableVertexIconTransformer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * This class is the responsible of the visualization
 * of the request and substrate graphs. It also creates desired listeners,
 * transforming methods and editing options
 */
public class GraphViewerPanel extends VisualizationViewer<Node, Link>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@SuppressWarnings("rawtypes")
//	DefaultModalGraphMouse gm;
	EditingModalGraphMouse gm;
	@SuppressWarnings("rawtypes")
	PopupVertexEdgeMenuMousePlugin mousePlugin;
	@SuppressWarnings("rawtypes")
	Factory nodeFactory;
	@SuppressWarnings("rawtypes")
	Factory linkFactory;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GraphViewerPanel(Layout<Node, Link> layout, Factory nodeFactory, Factory linkFactory) {
		super(layout);
		
		// Setup up icons for nodes
		PickableVertexIconTransformer<Node> vertexIcon = new PickableVertexIconTransformer<Node>(getPickedVertexState(), null, null) {
			public Icon transform(Node n) {
				if (!this.pi.isPicked(n))
					return n.getIcon();
				else return n.getPickedIcon();
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
				return n.getName();
			}
        };
        this.getRenderContext().setVertexLabelTransformer(vertexLabel);
        this.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);

        //Setting custom edges
//        Transformer<Link, String> linkLabel = new Transformer<Link, String>() {
//			public String transform(Link l) {
//				return l.getName();
//			}
//        };
//        this.getRenderContext().setEdgeLabelTransformer(linkLabel);
		this.getRenderContext().setEdgeShapeTransformer(new EdgeShape.CubicCurve());
		// Change color to green when selected
		this.getRenderContext().setEdgeDrawPaintTransformer(new PickableEdgePaintTransformer(getPickedEdgeState(), Color.black, Color.green));

		
		
        //Adding mouse interaction WITHOUT EDITION!
//        gm = new DefaultModalGraphMouse();
//        gm.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
//        this.addKeyListener(gm.getModeKeyListener());
//        this.setGraphMouse(gm);
        
		
		
        // Trying out our new popup menu mouse plugin... and enable EDITION!
		this.nodeFactory = nodeFactory;
		this.linkFactory = linkFactory;
		gm = new EditingModalGraphMouse(this.getRenderContext(), this.nodeFactory, this.linkFactory);
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING); // Default mode
		this.addKeyListener(gm.getModeKeyListener()); //Key listeners
        mousePlugin = new PopupVertexEdgeMenuMousePlugin();
        // Add some popup menus for the nodes and links to our mouse plugin.
        JPopupMenu edgeMenu = new MouseMenus.EdgeMenu();
        JPopupMenu vertexMenu = new MouseMenus.VertexMenu();
        mousePlugin.setEdgePopup(edgeMenu);
        mousePlugin.setVertexPopup(vertexMenu);
        gm.remove(gm.getPopupEditingPlugin());  // Removes the default popup editing plugin
        gm.add(mousePlugin);   // Add our plugin to the mouse
        this.setGraphMouse(gm);        
	}

	@SuppressWarnings("rawtypes")
	public EditingModalGraphMouse getGm() {
		return gm;
	}

	@SuppressWarnings("rawtypes")
	public void setGm(EditingModalGraphMouse gm) {
		this.gm = gm;
	}

	@SuppressWarnings("rawtypes")
	public PopupVertexEdgeMenuMousePlugin getMousePlugin() {
		return mousePlugin;
	}

	@SuppressWarnings("rawtypes")
	public void setMousePlugin(PopupVertexEdgeMenuMousePlugin mousePlugin) {
		this.mousePlugin = mousePlugin;
	}

	@SuppressWarnings("rawtypes")
	public Factory getNodeFactory() {
		return nodeFactory;
	}

	public void setNodeFactory(RequestNodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
	}

	@SuppressWarnings("rawtypes")
	public Factory getLinkFactory() {
		return linkFactory;
	}

	public void setLinkFactory(RequestLinkFactory linkFactory) {
		this.linkFactory = linkFactory;
	}
	
	public Collection<Link> getNodeLinks(Node n) {
		Collection<Link> links = this.getGraphLayout().getGraph().getIncidentEdges(n);
		return links;
	}
	
	
}
