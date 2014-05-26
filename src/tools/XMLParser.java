package tools;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import model.Substrate;
import model.components.Interface;
import model.components.Node;
import model.components.SubstrateLink;
import model.NetworkGraph;
import model.SubstrateNodeFactory;
import model.SubstrateLinkFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import java.io.File;

public class XMLParser {

	public static String parseXMLSubstrate(File file, Substrate subs) {
		
		String result = null;
		
		// TODO error control!!!!!!!!!!!
		
		try {
			// Parses XML files
			// Creating nodes
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			if (!doc.getDocumentElement().getNodeName().equals("rspec")) {
				//TODO Error control
			}
			// Nodes
			NodeList nodeLst = doc.getElementsByTagName("node");
			System.out.println("Information of all nodes: ");
			for (int j = 0; j < nodeLst.getLength(); j++) {
				org.w3c.dom.Node fstNode = nodeLst.item(j);
				if (fstNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					Element fstElmnt = (Element) fstNode;
					Node node = null;
					// Node id
					String nodeName = fstElmnt.getAttribute("virtual_id");
					System.out.println("Attribute virtual_id: "+fstElmnt.getAttribute("virtual_id"));
					// Node type
					NodeList nodeTypeList = fstElmnt.getElementsByTagName("node_type");
					org.w3c.dom.Node nodeTypeNode = nodeTypeList.item(0);
					if (nodeTypeNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
						Element nodeTypeElement = (Element) nodeTypeNode;
						String nodeType = nodeTypeElement.getAttribute("type_name");
						System.out.println("\t Attribute node_type: "+nodeType);
						/** TODO create node with type nodeType
						 * and see more DOM parse examples...
						 */
						if (nodeType.equalsIgnoreCase("server")) {
							node = ((SubstrateNodeFactory)subs.getNodeFactory()).create("server");
							node.setName(nodeName);
						} else if (nodeType.equalsIgnoreCase("router")) {
							node = ((SubstrateNodeFactory)subs.getNodeFactory()).create("router");
							node.setName(nodeName);
						} else if (nodeType.equalsIgnoreCase("switch")) {
							node = ((SubstrateNodeFactory)subs.getNodeFactory()).create("switch");
							node.setName(nodeName);
						} else {
							// error
						}
					}
					// Interfaces
					NodeList interfaceList = fstElmnt.getElementsByTagName("interface");
					for (int k = 0; k < interfaceList.getLength(); k++) {
						org.w3c.dom.Node ifaceNode = interfaceList.item(k);
						if (ifaceNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
							Element ifaceElement = (Element) ifaceNode;
							String ifaceName = ifaceElement.getAttribute("virtual_id");
							Interface iface = new Interface(node.getInterfaces().size(), node, null);
							iface.setName(ifaceName);
							node.addInterface(iface);
						}
					}
					// Adding node to the graph
					subs.getGraph().addVertex(node);
			    }
			}
			// Links
			NodeList linkList = doc.getElementsByTagName("link");
			System.out.println("Information of all links: ");
			for (int j = 0; j < linkList.getLength(); j++) {
				org.w3c.dom.Node linkNode = linkList.item(j);
				if (linkNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					Element linkElement = (Element) linkNode;
					String linkName = linkElement.getAttribute("virtual_id");
					// Link type not used
					@SuppressWarnings("unused")
					String ifaceType = linkElement.getAttribute("link_type");
					SubstrateLink link = ((SubstrateLinkFactory)subs.getLinkFactory()).create();
					link.setName(linkName);
					// Link connectivity
					NodeList connectList = linkElement.getElementsByTagName("interface_ref");
					if (connectList.getLength()!=2) {
						// error
					}
					String[][] connectivity = new String[2][2];
					for (int k = 0; k < connectList.getLength(); k++) {
						org.w3c.dom.Node connectNode = connectList.item(k);
						if (connectNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
							Element connectElement = (Element) connectNode;
							// Getting node
							String nodeName = connectElement.getAttribute("virtual_node_id");
							connectivity[k][0] = nodeName; 
							// Getting interface
							String ifaceName = connectElement.getAttribute("virtual_interface_id");
							connectivity[k][1] = ifaceName; 
						}
					}
					Node node1 = ((NetworkGraph) subs.getGraph()).getVertexByName(connectivity[0][0]);
					Interface iface1 = null;
					if (node1 != null) {
						iface1 = node1.getInterfaceByName(connectivity[0][1]);
						if (iface1==null) {
							// error
						}
					} else {
						// error
					}
					Node node2 = ((NetworkGraph) subs.getGraph()).getVertexByName(connectivity[1][0]);
					Interface iface2 = null;
					if (node2 != null) {
						iface2 = node2.getInterfaceByName(connectivity[1][1]);
						if (iface2==null) {
							// error
						}
					} else {
						// error
					}
					// Adding link to the graph
					((NetworkGraph) subs.getGraph()).addEdgeWithInterfaces(link, new Pair<Node>(node1,node2), new Pair<Interface>(iface1,iface2), EdgeType.UNDIRECTED);
				}
					
			}
		} catch (IOException ex) {
			// any IO errors occur:
			return ex.getMessage();
		} catch (SAXException ex) {
			// parse errors occur:
			return ex.getMessage();
		} catch (ParserConfigurationException ex) {
			// document-loader cannot be created which,
			// satisfies the configuration requested
			return ex.getMessage();
		} catch (FactoryConfigurationError ex) {
			// DOM-implementation is not available 
			// or cannot be instantiated:
			return ex.getMessage();
		}
		
		return result;
	}
	
}
