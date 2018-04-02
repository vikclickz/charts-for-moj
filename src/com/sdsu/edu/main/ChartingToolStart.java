package com.sdsu.edu.main;

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

    JButton scatterbtn = new JButton("SCATTER", new ImageIcon("./src/data/scatterplot3.png"));
    JButton nonLinearBtn = new JButton("NLNR", new ImageIcon("./src/data/nonlinear.png"));
    JButton polyBtn = new JButton("POLY", new ImageIcon("./src/data/polyregression.png"));
    JButton threeDBtn = new JButton("3D", new ImageIcon("./src/data/threed.png"));
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
      setTitle("Charting");
      setSize(800, 600); // default size is 0,0
      setLocation(50, 200); // default is 0,0 (top left corner)
      setLayout(new GridLayout(3, 2));
      // Browse for the DBF File
      // jFileChooser
      jfc = new JFileChooser();
      //jfc.showOpenDialog(this);
      jfc.setSelectedFile(new File("./src/data/USA/states.dbf"));
      if (jfc.getSelectedFile() != null) {
        File file = jfc.getSelectedFile();
        DbfReadController dbfread = DbfReadController.getInstance();
        numericList = dbfread.readnumericdbf(file);
        charList = dbfread.readchardbf(file);
      }
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
