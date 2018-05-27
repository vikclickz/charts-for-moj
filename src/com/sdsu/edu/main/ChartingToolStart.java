package com.sdsu.edu.main;

import static com.sdsu.edu.main.constant.GUILabelConstants.BAR_CHART_TOOL_TIP;
import static com.sdsu.edu.main.constant.GUILabelConstants.CHARTING_LABEL;
import static com.sdsu.edu.main.constant.GUILabelConstants.LINEAR_REGRESSION_TOOL_TIP;
import static com.sdsu.edu.main.constant.GUILabelConstants.PIE_CHART_TOOL_TIP;
import static com.sdsu.edu.main.constant.GUILabelConstants.POLYNOMIAL_REGRESSION_TOOL_TIP;
import static com.sdsu.edu.main.constant.GUILabelConstants.POWER_REGRESSION_TOOL_TIP;
import static com.sdsu.edu.main.constant.GUILabelConstants.THREE_DIM_TOOL_TIP;

import com.sdsu.edu.main.controller.db.DbfReadController;
import com.sdsu.edu.main.gui.BarPanelGUI;
import com.sdsu.edu.main.gui.LinearRegressionGUI;
import com.sdsu.edu.main.gui.PiePanelGUI;
import com.sdsu.edu.main.gui.PolynomialPanelGUI;
import com.sdsu.edu.main.gui.PowerRegressionGUI;
import com.sdsu.edu.main.gui.ThreeDimensionalPanelGUI;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * Charting Tool Facility: Thesis
 */
class ChartingToolStart {

  /*
   * The Chart GUI for selecting Pie or Bar, and respective choices to chart
   */
  public class ChartTypeFrameGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JFileChooser jfc;
    JPanel firstPanel = new JPanel();
    JButton piebtn = new JButton("PIE", new ImageIcon("./src/data/piechartbtn.jpg"));
    JButton barbtn = new JButton("BAR", new ImageIcon("./src/data/barchartbtn.jpg"));

    JButton scatterbtn = new JButton("SCATTER", new ImageIcon("./src/data/linear_reg.png"));
    JButton nonLinearBtn = new JButton("NLNR", new ImageIcon("./src/data/nonlinear.png"));
    JButton polyBtn = new JButton("POLY", new ImageIcon("./src/data/poly_reg.png"));
    JButton threeDBtn = new JButton("3D", new ImageIcon("./src/data/three_dim.png"));
    List<String> numericList;
    List<String> charList;
    LinearRegressionGUI linearRegressionGUI;
    BarPanelGUI barPanelGUI;
    PiePanelGUI piePanelGUI;
    PowerRegressionGUI powerRegressionGUI;
    PolynomialPanelGUI polynomialPanelGUI;
    ThreeDimensionalPanelGUI threeDimensionalPanelGUI;
    private List<JPanel> panelList = new ArrayList<>();

    public ChartTypeFrameGUI() {
      setTitle(CHARTING_LABEL);
      setSize(800, 600); // default size is 0,0
      setLocation(50, 200); // default is 0,0 (top left corner)
      setLayout(new GridLayout(3, 2));
      // Browse for the DBF File
      // jFileChooser
      jfc = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("*.dbf", "dbf");
      jfc.setFileFilter(filter);
      int openDialog = 0;
      do {
        openDialog = jfc.showOpenDialog(this);
      } while (openDialog == JFileChooser.CANCEL_OPTION || null == jfc.getSelectedFile());

      File file = jfc.getSelectedFile();
      DbfReadController dbfread = DbfReadController.getInstance();
      numericList = dbfread.readnumericdbf(file);
      charList = dbfread.readchardbf(file);

      // Window Listeners
      addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
        } // windowClosing
      });
      Container contentPane = getContentPane();
      contentPane.add(firstPanel);
      piebtn.setPreferredSize(new Dimension(100, 100));
      piebtn.setFocusable(true);
      piebtn.setToolTipText(PIE_CHART_TOOL_TIP);
      firstPanel.add(piebtn);
      piebtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          clearPanelList();
          piePanelGUI = new PiePanelGUI(numericList, charList);
          panelList.add(piePanelGUI);
          add(piePanelGUI);
          setVisible(true);
        }
      });
      barbtn.setPreferredSize(new Dimension(100, 100));
      barbtn.setToolTipText(BAR_CHART_TOOL_TIP);
      firstPanel.add(barbtn);
      barbtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          clearPanelList();
          barPanelGUI = new BarPanelGUI(numericList, charList);
          panelList.add(barPanelGUI);
          add(barPanelGUI);
          setVisible(true);
        }
      });

      scatterbtn.setPreferredSize(new Dimension(100, 100));
      scatterbtn.setToolTipText(LINEAR_REGRESSION_TOOL_TIP);
      firstPanel.add(scatterbtn);
      scatterbtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            clearPanelList();
            linearRegressionGUI = new LinearRegressionGUI(numericList, charList);
            panelList.add(linearRegressionGUI);
            add(linearRegressionGUI);
            setVisible(true);
        }
      });

      nonLinearBtn.setPreferredSize(new Dimension(100, 100));
      nonLinearBtn.setToolTipText(POWER_REGRESSION_TOOL_TIP);
      firstPanel.add(nonLinearBtn);
      nonLinearBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          clearPanelList();
          powerRegressionGUI = new PowerRegressionGUI(numericList, charList);
          panelList.add(powerRegressionGUI);
          add(powerRegressionGUI);
          setVisible(true);
        }
      });

      polyBtn.setPreferredSize(new Dimension(100, 100));
      polyBtn.setToolTipText(POLYNOMIAL_REGRESSION_TOOL_TIP);
      firstPanel.add(polyBtn);
      polyBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          clearPanelList();
          polynomialPanelGUI = new PolynomialPanelGUI(numericList, charList);
          panelList.add(polynomialPanelGUI);
          add(polynomialPanelGUI);
          setVisible(true);
        }
      });

      threeDBtn.setPreferredSize(new Dimension(100, 100));
      threeDBtn.setToolTipText(THREE_DIM_TOOL_TIP);
      firstPanel.add(threeDBtn);
      threeDBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          clearPanelList();
          threeDimensionalPanelGUI = new ThreeDimensionalPanelGUI(numericList, charList);
          panelList.add(threeDimensionalPanelGUI);
          add(threeDimensionalPanelGUI);
          setVisible(true);
        }
      });
      setVisible(true);
    } // End Constructor

    private void clearPanelList() {
      for (JPanel panel : panelList) {
          remove(panel);
      }
    }

  }
}
