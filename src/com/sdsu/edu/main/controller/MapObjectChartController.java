package com.sdsu.edu.main.controller;

import com.sdsu.edu.main.controller.db.DbfReadController;
import com.sdsu.edu.main.model.ChartModel;
import com.sdsu.edu.main.view.ChartViewController;
import java.awt.Color;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.function.PolynomialFunction2D;
import org.jfree.data.function.PowerFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class MapObjectChartController {

  private static MapObjectChartController mapObjectChartController = new MapObjectChartController();
  private ChartViewController chartViewController = new ChartViewController();

  public static MapObjectChartController getInstance() {
    return mapObjectChartController;
  }

  public void createPolynomialRegressionChart(List<String> selectedFields,
      String xAxisLabel, Integer order) {
    String yAxisLabel = selectedFields.get(0);

    ChartModel chartModel = xyDatasetBuilder(selectedFields, xAxisLabel,
        "Polynomial Regression Chart", yAxisLabel);

    String title = xAxisLabel + " vs " + yAxisLabel;

    JFreeChart chart = ChartFactory.createScatterPlot(title,
        xAxisLabel, yAxisLabel, chartModel.getXyDataset());

    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.WHITE);

    plot.setDomainAxis(chartModel.getDomainAxis());
    ChartPanel panel = new ChartPanel(chart);
    ChartViewController chartViewController = new ChartViewController();
    chartViewController.displayChart(panel, title);

    drawPolyRegressionLine(chartModel.getXyDataset(), chart, order);
  }

  public void createPowerRegressionChart(List<String> selectedFields,
      String xAxisLabel, String chartColorSType) {

    String yAxisLabel = selectedFields.get(0);

    ChartModel chartModel = xyDatasetBuilder(selectedFields, xAxisLabel,
        "Power Regression Chart", yAxisLabel);

    String title = xAxisLabel + " vs " + yAxisLabel;

    JFreeChart chart = ChartFactory.createScatterPlot(
        title, xAxisLabel, yAxisLabel, chartModel.getXyDataset());

    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.WHITE);

    plot.setDomainAxis(chartModel.getDomainAxis());
    ChartPanel panel = new ChartPanel(chart);
    ChartViewController chartViewController = new ChartViewController();
    chartViewController.displayChart(panel, title);

    drawPowerRegressionLine(chartModel.getXyDataset(), chart);
  }

  public void createLinearRegressionChart(List<String> selectedFields,
      String xAxisLabel) {
    String yAxisLabel = selectedFields.get(0);

    ChartModel chartModel = xyDatasetBuilder(selectedFields, xAxisLabel,
        "Linear Regression Chart", yAxisLabel);

    String title = xAxisLabel + " vs " + yAxisLabel;

    JFreeChart chart = ChartFactory.createScatterPlot(
        title,
        xAxisLabel, yAxisLabel, chartModel.getXyDataset());

    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.WHITE);
    plot.setDomainAxis(chartModel.getDomainAxis());
    ChartPanel panel = new ChartPanel(chart);
    chartViewController.displayChart(panel, title);

    drawRegressionLine(chartModel.getXyDataset(), chart);
  }

  private void drawPolyRegressionLine(XYDataset inputData, JFreeChart chart, Integer order) {
    // Get the parameters 'a' and 'b' for an equation y = a + b * x,
    // fitted to the inputData using ordinary least squares regression.
    // a - regressionParameters[0], b - regressionParameters[1]
    double regressionParameters[] = Regression.getPolynomialRegression(inputData,
        0, order);

    double myArr[] = new double[regressionParameters.length - 1];

    for (int i = 0; i < regressionParameters.length - 1; i++) {
      myArr[i] = regressionParameters[i];
    }

    // Prepare a line function using the found parameters
    Function2D curve = new PolynomialFunction2D(myArr);

    XYDataset dataset = DatasetUtilities.sampleFunction2D(curve,
        0.0, 50.0, 100, "Poly Regression Line");

    XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true,
        false);
    // Draw the line dataset
    XYPlot xyplot = chart.getXYPlot();
    xyplot.setDataset(1, dataset);
    renderer2.setSeriesPaint(0, Color.BLACK);
    xyplot.setRenderer(1, renderer2);

  }

  private void drawPowerRegressionLine(XYDataset inputData, JFreeChart chart) {
    // Get the parameters 'a' and 'b' for an equation y = a + b * x,
    // fitted to the inputData using ordinary least squares regression.
    // a - regressionParameters[0], b - regressionParameters[1]
    double regressionParameters[] = Regression.getPowerRegression(inputData,
        0);

    // Prepare a line function using the found parameters
    Function2D curve = new PowerFunction2D(regressionParameters[0],
        regressionParameters[1]);

    XYDataset dataset = DatasetUtilities.sampleFunction2D(curve,
        0.0, 50.0, 100, "Power Regression Line");

    XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true,
        false);
    // Draw the line dataset
    XYPlot xyplot = chart.getXYPlot();
    xyplot.setDataset(1, dataset);
    renderer2.setSeriesPaint(0, Color.BLACK);
    xyplot.setRenderer(1, renderer2);
  }


  private void drawRegressionLine(XYDataset inputData, JFreeChart chart) {
    // Get the parameters 'a' and 'b' for an equation y = a + b * x,
    // fitted to the inputData using ordinary least squares regression.
    // a - regressionParameters[0], b - regressionParameters[1]
    double regressionParameters[] = Regression.getOLSRegression(inputData,
        0);

    // Prepare a line function using the found parameters
    LineFunction2D linefunction2d = new LineFunction2D(
        regressionParameters[0], regressionParameters[1]);

    // Creates a dataset by taking sample values from the line function
    XYDataset dataset = DatasetUtilities.sampleFunction2D(linefunction2d,
        0D, 300, 100, "Linear Regression Line");

    // Draw the line dataset
    XYPlot xyplot = chart.getXYPlot();
    xyplot.setDataset(1, dataset);
    XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(
        true, false);
    xylineandshaperenderer.setSeriesPaint(0, Color.BLACK);
    xyplot.setRenderer(1, xylineandshaperenderer);
  }


  private ChartModel xyDatasetBuilder(List<String> selectedFields,
      String characterNameSType, String chartTitle, String yAxisLabel) {

    ChartModel chartModel = new ChartModel();

    List<String> stateList = DbfReadController.getInstance().getCharRecord().fieldAndValues
        .get(characterNameSType);
    List<Double> stateValues = DbfReadController.getInstance().getNumericRecord().fieldAndValues
        .get(yAxisLabel);

    String[] stockArr = new String[stateList.size()];
    stockArr = stateList.toArray(stockArr);

    ValueAxis xAxis = new SymbolAxis(characterNameSType, stockArr);
    xAxis.setVerticalTickLabels(true);

    XYSeries series1 = new XYSeries(chartTitle);

    if (chartTitle.contains("Linear")) {
      for (int i = 1; i <= 51; i++) {
        series1.add(i, stateValues.get(i - 1));
      }
    } else {
      for (int i = 2; i <= 52; i++) {
        series1.add(i, stateValues.get(i - 2));
      }
    }
    chartModel.setDomainAxis(xAxis);
    chartModel.setXyDataset(new XYSeriesCollection(series1));
    return chartModel;
  }
}
