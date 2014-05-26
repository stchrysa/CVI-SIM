package gui.simulation;  
       
       
import javax.swing.JTabbedPane;
import org.jfree.chart.ChartPanel;   
import org.jfree.chart.JFreeChart;   
import org.jfree.chart.axis.NumberAxis;   
import org.jfree.chart.plot.XYPlot;   
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;   
import org.jfree.ui.ApplicationFrame;   
  
       
    /**  
     * A demo showing one way to fit regression lines to XY data.  
     */   
    public class Charts_Utilization extends ApplicationFrame {   
       
    	
        public Charts_Utilization(String title, XYDataset cpu, XYDataset mem, XYDataset disk, XYDataset rout, XYDataset link) {
            super(title);   
            getContentPane().add(createContent(cpu, mem, disk, rout, link));   
        }   
       
        /**  
         * Creates a tabbed pane for displaying sample charts.  
         *  
         * @return the tabbed pane.  
         */   
        private JTabbedPane createContent(XYDataset cpu, XYDataset mem, XYDataset disk, XYDataset rout, XYDataset link) {   
            JTabbedPane tabs = new JTabbedPane();   
            tabs.add("CPU", createChartPanel1(cpu));   
            tabs.add("Memory", createChartPanel2(mem));
            tabs.add("Disk", createChartPanel3(disk));
            tabs.add("Router", createChartPanel4(rout));
            tabs.add("Link", createChartPanel5(link));
            return tabs;   
        }   
       
        /**  
         * Creates a chart based on the first dataset, with a fitted linear regression line.  
         *  
         * @return the chart panel.  
         */   
        private ChartPanel createChartPanel1(XYDataset cpu) {   
       
            // create plot...   
            NumberAxis xAxis = new NumberAxis("Time");   
           // xAxis.setAutoRangeIncludesZero(false);   
            NumberAxis yAxis = new NumberAxis("CPU Utilization");   
           // yAxis.setAutoRangeIncludesZero(false);   
       
            XYItemRenderer renderer1 = new StandardXYItemRenderer();
            XYPlot plot = new XYPlot(cpu, xAxis, yAxis, renderer1);   
            
           // System.out.println("cpu is ="+cpu.getItemCount(1));
       
       
            // create and return the chart panel...   
            JFreeChart chart = new JFreeChart("CPU Utilization",    
                    JFreeChart.DEFAULT_TITLE_FONT, plot, true);   
            ChartPanel chartPanel = new ChartPanel(chart, false);   
            return chartPanel;   
       
        }   

        private ChartPanel createChartPanel2(XYDataset mem) {   
       
            // create plot   
            NumberAxis xAxis = new NumberAxis("Time");   
            //xAxis.setAutoRangeIncludesZero(false);   
            NumberAxis yAxis = new NumberAxis("Memory Utilization");   
            //yAxis.setAutoRangeIncludesZero(false);   
       
            XYItemRenderer renderer1 = new StandardXYItemRenderer();
            XYPlot plot = new XYPlot(mem, xAxis, yAxis, renderer1);   
       
               
       
            // create and return the chart panel...   
            JFreeChart chart = new JFreeChart("Memory Utilization",    
                    JFreeChart.DEFAULT_TITLE_FONT, plot, true);   
            ChartPanel chartPanel = new ChartPanel(chart, false);   
            return chartPanel;   
       
        }
        
        private ChartPanel createChartPanel3(XYDataset disk) {   
            
            // create plot    
            NumberAxis xAxis = new NumberAxis("Time");   
           // xAxis.setAutoRangeIncludesZero(false);   
            NumberAxis yAxis = new NumberAxis("Disk Utilization");   
           // yAxis.setAutoRangeIncludesZero(false);   
       
            XYItemRenderer renderer1 = new StandardXYItemRenderer();
            XYPlot plot = new XYPlot(disk, xAxis, yAxis, renderer1);   

       
            // create and return the chart panel...   
            JFreeChart chart = new JFreeChart("Disk Utilization",    
                    JFreeChart.DEFAULT_TITLE_FONT, plot, true);   
            ChartPanel chartPanel = new ChartPanel(chart, false);   
            return chartPanel;   
       
        } 
        
        private ChartPanel createChartPanel4(XYDataset rout) {   
            
            // create plot   
            NumberAxis xAxis = new NumberAxis("Time");   
           // xAxis.setAutoRangeIncludesZero(false);   
            NumberAxis yAxis = new NumberAxis("Router Utilization");   
           // yAxis.setAutoRangeIncludesZero(false);   
       
            XYItemRenderer renderer1 = new StandardXYItemRenderer();
            XYPlot plot = new XYPlot(rout, xAxis, yAxis, renderer1);        

            // create and return the chart panel...   
            JFreeChart chart = new JFreeChart("Router Utilization",    
                    JFreeChart.DEFAULT_TITLE_FONT, plot, true);   
            ChartPanel chartPanel = new ChartPanel(chart, false);   
            return chartPanel;   
       
        } 
        
        private ChartPanel createChartPanel5(XYDataset link) {
        	
            // create plot   
            NumberAxis xAxis = new NumberAxis("Time");   
           // xAxis.setAutoRangeIncludesZero(false);   
            NumberAxis yAxis = new NumberAxis("Link Utilization");   
           // yAxis.setAutoRangeIncludesZero(false);   
       
            XYItemRenderer renderer1 = new StandardXYItemRenderer();   
            XYPlot plot = new XYPlot(link, xAxis, yAxis, renderer1);   
       
                  // create and return the chart panel...   
            JFreeChart chart = new JFreeChart("Link Utilization",    
                    JFreeChart.DEFAULT_TITLE_FONT, plot, true);   
            ChartPanel chartPanel = new ChartPanel(chart, false);   
            return chartPanel;   
       
        } 
       
    }   


