package gui.simulation;

import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.collections15.Transformer;

import model.Simulation;
import model.Substrate;
import model.components.Link;
import model.components.Node;

@SuppressWarnings("serial")
public class ResultsFrame extends JFrame {
	
	private List<ResultsGraphViewerPanel> graphViewerPanels;
	private JSlider slider;
	private JPanel pane;
	private JLabel current;
	private int prevPosition;
	
	public ResultsFrame(JPanel relativeTo, String title,
			final List<Substrate> substrates) {
		setTitle(title);
//		setLocationRelativeTo(relativeTo);
		setAlwaysOnTop(false);
		setPreferredSize(new Dimension(900, 640));
		
		//Init
		graphViewerPanels = new ArrayList<ResultsGraphViewerPanel>();
//		Layout<Node, Link> layout = new FRLayout2<Node, Link>(substrates.get(0).getGraph());
		for (Substrate subs : substrates) {
//			Layout<Node,Link> layout = subs.getGraphLayout();
//			layout.setGraph(subs.getGraph());
		//	Layout<Node, Link> layout = new FRLayout2<Node, Link>(subs.getGraph());
			Layout<Node, Link> layout = new StaticLayout<Node, Link>(subs.getGraph(), new Transformer<Node, Point2D>() {
				@Override
				public Point2D transform(Node node) {
					switch (node.getId()) {
						case 0:
							return new Point(415,205);
						case 1:
							return new Point(340,155);
						case 2:
							return new Point(660,510);
						case 3:
							return new Point(415,395);
						case 4:
							return new Point(100,210);
						case 5:
							return new Point(130,350);
						case 6:
							return new Point(460,580);
						case 7:
							return new Point(415,140);
						case 8:
							return new Point(470,375);
						case 9:
							return new Point(775,335);
						case 10:
							return new Point(505,35);
						case 11:
							return new Point(565,190);
						case 12:
							return new Point(235,450);
						case 13:
							return new Point(830,400);
						case 14:
							return new Point(200,230);
						case 15:
							return new Point(90,510);
						case 16:
							return new Point(430,65);
						case 17:
							return new Point(120,55);
						case 18:
							return new Point(280,280);
						case 19:
							return new Point(550,500);
						case 20:
							return new Point(700,465);
						case 21:
							return new Point(710, 50);
						case 22:
							return new Point(325,35);
						case 23:
							return new Point(180,400);
						case 24:
							return new Point(280,350);
						case 25:
							return new Point(405,325);
						case 26:							
							return new Point(770,455);	
						case 27:
							return new Point(120,600);
						case 28:
							return new Point(560,300);
						case 29:
							return new Point(390,480);
						case 30:
							return new Point(140,290);
						case 31:
							return new Point(690,135);
						case 32:
							return new Point(840,560);
						case 33:
							return new Point(300,475);
						case 34:
							return new Point(650,305);
						case 35:
							return new Point(780,170);
						case 36:
							return new Point(180,530);
						case 37:
							return new Point(620,600);
						case 38:
							return new Point(80,450);					
						case 39:
							return new Point(830,60);
						case 40:
							return new Point(700,195);
						case 41: 
							return new Point(570,370);
						case 42:
							return new Point(345,255);
						case 43:
							return new Point(630,255);
						case 44:
							return new Point(120,110);
						case 45:
							return new Point(735,280);
						case 46:
							return new Point(520,480);
						case 47:
							return new Point(630,105);
						case 48:
							return new Point(560,85);
						case 49:
							return new Point(320,560);
						}
					
					return new Point(850,300);
				}
			});
			layout.setSize(this.getSize());
//			layout.setSize(new Dimension(870, 530)); // sets the initial size of the space
			ResultsGraphViewerPanel gvp = new ResultsGraphViewerPanel(layout);
			gvp.setPreferredSize(new Dimension(870, 530)); //Sets the viewing area size
			gvp.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
			gvp.setBackground(Color.GRAY);
			graphViewerPanels.add(gvp);
		}
		
		slider = new JSlider();
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setPaintTrack(true);
		slider.setPaintLabels(true);
		slider.setMinimum(0);
		slider.setMaximum(substrates.size()-1);
		slider.setValue(0);
		prevPosition = 0;
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int position = slider.getValue();
				pane.remove(graphViewerPanels.get(prevPosition));
				pane.add(graphViewerPanels.get(position), BorderLayout.CENTER);
				pane.repaint();
				current.setText(substrates.get(position).getId());
				
				prevPosition = position;
			}
		});
		
		current = new JLabel(substrates.get(0).getId());
		
		//Painting
		pane = new JPanel(new BorderLayout());
		pane.add(current, BorderLayout.NORTH);
		pane.add(graphViewerPanels.get(0), BorderLayout.CENTER);
		pane.add(slider, BorderLayout.SOUTH);
		
		getContentPane().add(pane);
	}
}