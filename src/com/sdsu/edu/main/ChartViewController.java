package com.sdsu.edu.main;

import com.sdsu.edu.main.ChartController.MultiBarChartHPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


public class ChartViewController extends JFrame implements ActionListener {

  private JPanel contentPane;
  boolean sortflag = false;
  public ChartController.DataSet[] data1;
  public ChartController.DataSet[] sortedData1;
  private JLabel chartTitleLabel; // JLabel chartTitle;
  private JLabel xAxisLabel; // JLabel xAxis label;
  private JLabel yAxisLabel; // JLabel yAxis label;
  private JTextField chartTitleField; // Field to write the title
  private JTextField xAxisLabelField; // Field to write the xAxis label
  private JTextField yAxisLabelField; // Field to write the yAxis label
  private JButton sortBtn; // Button to sort the data
  private JButton titleChangeBtn; // Button to change the title of the chart
  private JButton xAxisLabelChangeBtn; // Button to change the xAxis label
  private JButton yAxisLabelChangeBtn; // Button to change the yAxis label
  private JButton printBtn; // Button to print the chart
  private JSplitPane splitPane;
  PrinterJob printJob;
  BufferedImage image = null;
  JFreeChart chart;


  public ChartViewController(ChartPanel contentPane, String title) {
    JPanel mainPanel = new JPanel();
    setBounds(200, 200, 700, 400);
    mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    mainPanel.setLayout(new BorderLayout(0, 0));
    this.chart = contentPane.getChart();

    // create a button to print the chart
    printBtn = new JButton("Print Chart");
    // create a button to change the title of the chart
    titleChangeBtn = new JButton("Change Title");
    // Create a field to enter new Title
    chartTitleField = new JTextField(10);
    // create new Label with "Horizontal Bar Chart" as default chart title.
    chartTitleLabel = new JLabel(title);
    // put fields and buttons in one panel and labels into a separate panel
    JPanel tmpPanel1 = new JPanel();
    tmpPanel1.add(printBtn);
    tmpPanel1.add(titleChangeBtn);
    tmpPanel1.add(chartTitleField);
    JPanel tmpPanel2 = new JPanel();
    tmpPanel2.add(chartTitleLabel);
    // finally. put all those into TopPane
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1, 2));
    topPanel.add(tmpPanel1);
    topPanel.add(tmpPanel2);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridLayout(1, 1));
    bottomPanel.add(contentPane);
    bottomPanel.setAutoscrolls(true);
    // Add TopPane to the frame
    mainPanel.add(topPanel, BorderLayout.PAGE_START);
    mainPanel.add(bottomPanel, BorderLayout.CENTER);
    // Set actions for the buttons
    printBtn.setActionCommand("printChart");
    printBtn.addActionListener(this);
    // Set actions for the buttons
    titleChangeBtn.setActionCommand("changeTitle");
    titleChangeBtn.addActionListener(this);
    setContentPane(mainPanel);
    setTitle("THE CHARTING TOOL");
    // setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
    setVisible(true);
    setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("printChart")) {
      try {
        Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension d = tool.getScreenSize();
        Rectangle rect = new Rectangle(d);
        Robot robot = new Robot();
        Thread.sleep(2000);
        File f = new File("screenshot.jpg");
        BufferedImage img = robot.createScreenCapture(rect);
        ImageIO.write(img, "jpeg", f);
        tool.beep();
        JOptionPane.showMessageDialog(null, "Set printer settings");
      } catch (Exception et) {
        et.printStackTrace();
      }
      try {
        image = ImageIO.read(new File("screenshot.jpg"));
      } catch (IOException e2) {
        e2.printStackTrace();
      }
      printJob = PrinterJob.getPrinterJob();
      PageFormat preformat = printJob.defaultPage();
      PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

      preformat = printJob.pageDialog(aset);
      printJob.setPrintable(new Printable() {

        public int print(Graphics g, PageFormat preformat, int pageIndex) throws PrinterException {

          if (pageIndex != 0) {
            return NO_SUCH_PAGE;
          } else {
            int pWidth = 0;
            int pHeight = 0;
            pWidth = (int) Math.min(preformat.getImageableWidth(), (double) image.getWidth());
            pHeight = pWidth * image.getHeight() / image.getWidth();
            g.drawImage(image, (int) preformat.getImageableX(), (int) preformat.getImageableY(),
                pWidth, pHeight, null);
            return PAGE_EXISTS;
          }
        }
      }, preformat);
      try {
        printJob.print();
      } catch (PrinterException e1) {
        e1.printStackTrace();
      }
    } else if (e.getActionCommand().equals("changeTitle")) {
      chartTitleLabel.setText(chartTitleField.getText());
      chart.setTitle(chartTitleField.getText());
      //chartTitleField.hide();
    } else if (e.getActionCommand().equals("changeXAxisLabel")) {
      xAxisLabelField.hide();
    } else if (e.getActionCommand().equals("changeYAxisLabel")) {
      yAxisLabelField.hide();
    }
  }
}

