package com.sdsu.edu.main;

import com.sdsu.edu.main.print.PrintUtility;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


public class ChartViewController extends JFrame implements ActionListener {

  private JPanel contentPane;
  private JLabel chartTitleLabel; // JLabel chartTitle;
  private JTextField chartTitleField; // Field to write the title
  private JTextField xAxisLabelField; // Field to write the xAxis label
  private JTextField yAxisLabelField; // Field to write the yAxis label
  private JButton titleChangeBtn; // Button to change the title of the chart
  private JButton xAxisLabelChangeBtn; // Button to change the xAxis label
  private JButton yAxisLabelChangeBtn; // Button to change the yAxis label
  private JButton printBtn; // Button to print the chart
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


    // create a button to change the title of the chart
    xAxisLabelChangeBtn = new JButton("Change X Axis");
    // Create a field to enter new Title
    xAxisLabelField = new JTextField(10);


    // create a button to change the title of the chart
    yAxisLabelChangeBtn = new JButton("Change Y Axis");
    // Create a field to enter new Title
    yAxisLabelField = new JTextField(10);

    // create new Label with "Horizontal Bar Chart" as default chart title.
    chartTitleLabel = new JLabel(title);
    // put fields and buttons in one panel and labels into a separate panel
    JPanel tmpPanel1 = new JPanel();
    tmpPanel1.add(printBtn);
    tmpPanel1.add(titleChangeBtn);
    tmpPanel1.add(chartTitleField);

    tmpPanel1.add(xAxisLabelChangeBtn);
    tmpPanel1.add(xAxisLabelField);

    tmpPanel1.add(yAxisLabelChangeBtn);
    tmpPanel1.add(yAxisLabelField);
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

    xAxisLabelChangeBtn.setActionCommand("changeXAxisLabel");
    xAxisLabelChangeBtn.addActionListener(this);

    yAxisLabelChangeBtn.setActionCommand("changeYAxisLabel");
    yAxisLabelChangeBtn.addActionListener(this);

    setContentPane(mainPanel);
    setTitle("THE CHARTING TOOL");
    // setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
    setVisible(true);
    setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("printChart")) {
      new PrintUtility(this.chart).createChartPrintJob();
    } else if (e.getActionCommand().equals("changeTitle")) {
      chartTitleLabel.setText(chartTitleField.getText());
      chart.setTitle(chartTitleField.getText());
    } else if (e.getActionCommand().equals("changeXAxisLabel")) {
      chart.getXYPlot().getDomainAxis().setLabel(xAxisLabelField.getText());
    } else if (e.getActionCommand().equals("changeYAxisLabel")) {
      chart.getXYPlot().getRangeAxis().setLabel(yAxisLabelField.getText());
    }
  }
}

