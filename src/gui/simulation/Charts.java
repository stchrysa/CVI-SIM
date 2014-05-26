package gui.simulation;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

public class Charts extends ApplicationFrame{
	
	  public Charts(String title, XYDataset accept, XYDataset cost, XYDataset rev, XYDataset hop) {
	        super(title);
	        final JFreeChart chart = createCombinedChart(accept, cost, rev, hop);
	        final ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
	        panel.setPreferredSize(new java.awt.Dimension(1000, 270));
	        setContentPane(panel);

	    }


	    private JFreeChart createCombinedChart(XYDataset accept, XYDataset cost, XYDataset rev, XYDataset hop) {

	        // create subplot 1...
	        final XYItemRenderer renderer1 = new StandardXYItemRenderer();
	        final NumberAxis rangeAxis1 = new NumberAxis("Acceptance Ratio");
	        final XYPlot subplot1 = new XYPlot(accept, null, rangeAxis1, renderer1);
	        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

	        // create subplot 2...
	        final XYItemRenderer renderer2 = new StandardXYItemRenderer();
	        final NumberAxis rangeAxis2 = new NumberAxis("Embedding Cost");
	      // rangeAxis2.setAutoRangeIncludesZero(false);
	        final XYPlot subplot2 = new XYPlot(cost, null, rangeAxis2, renderer2);
	        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
	        
	        // create subplot 3...
	        final XYItemRenderer renderer3 = new StandardXYItemRenderer();
	        final NumberAxis rangeAxis3 = new NumberAxis("Embedding Revenue");
	    //    rangeAxis3.setAutoRangeIncludesZero(false);
	        final XYPlot subplot3 = new XYPlot(rev, null, rangeAxis3, renderer3);
	        subplot3.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
	        
	        // create subplot 4...
	        final XYItemRenderer renderer4 = new StandardXYItemRenderer();
	        final NumberAxis rangeAxis4 = new NumberAxis("Average Hop Number");
	      //  rangeAxis4.setAutoRangeIncludesZero(false);
	        final XYPlot subplot4 = new XYPlot(hop, null, rangeAxis4, renderer4);
	        subplot4.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

	        // parent plot...
	        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Time"));
	        plot.setGap(10.0);
	        
	        // add the subplots...
	        plot.add(subplot1, 1);
	        plot.add(subplot2, 1);
	        plot.add(subplot3, 1);
	        plot.add(subplot4, 1);
	        plot.setOrientation(PlotOrientation.VERTICAL);

	        // return a new chart containing the overlaid plot...
	        return new JFreeChart("Simulation Results",
	                              JFreeChart.DEFAULT_TITLE_FONT, plot, true);

	    }
	    

}
