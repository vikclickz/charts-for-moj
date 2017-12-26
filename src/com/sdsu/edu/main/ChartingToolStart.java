package com.sdsu.edu.main;

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

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.sdsu.edu.main.ChartingToolStart.ChartTypeFrameGUI.BarPanelGUI;
import com.sdsu.edu.main.ChartingToolStart.ChartTypeFrameGUI.PiePanelGUI;

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

    JButton scatterbtn = new JButton("BAR", new ImageIcon("./src/data/scatter-plot.jpg"));
    List<String> numericList;
    List<String> charList;

    public ChartTypeFrameGUI() {
      setTitle("Charting");
      setSize(400, 400); // default size is 0,0
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
          add(new PiePanelGUI(numericList, charList));
          setVisible(true);
        }
      });
      barbtn.setPreferredSize(new Dimension(100, 100));
      firstPanel.add(barbtn);
      barbtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          add(new BarPanelGUI(numericList, charList));
          setVisible(true);
        }
      });

      scatterbtn.setPreferredSize(new Dimension(100, 100));
      firstPanel.add(scatterbtn);
      scatterbtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          add(new ScatterPanelGUI(numericList, charList));
          setVisible(true);
        }
      });
      setVisible(true);
    } // End Constructor

    /*
     * GUI for pie and bar selections
     */
    public class PiePanelGUI extends JPanel {
      private String[] chartColorTypes = {"Pastel", "Normal", "Rainbow"};
      private String[] characterNameTypes;
      private List<String> attributeNames;
      final DefaultListModel<String> attributeList;
      final JList<String> attributeSelectList;
      public List<String> selectedFields;
      private JComboBox<String> chartcolorjcb;
      private JComboBox<String> charNamejcb;
      private JButton selectbtn;
      String chartSType = "Pie";
      String chartColorSType;
      String characterNameSType;

      public PiePanelGUI(List<String> numericNameList, List<String> charNameList) {
       characterNameTypes = charNameList.toArray(new String[charNameList.size()]);
        attributeNames = numericNameList;
        setSize(500, 500);
        attributeList = new DefaultListModel<String>();
        for (int i = 0; i < attributeNames.size(); i++) {
          attributeList.addElement(attributeNames.get(i));
        }
        // set layout
        setLayout(new GridLayout(2, 4));
        // set combobox for chartcolor type
        chartcolorjcb = new JComboBox<String>(chartColorTypes);
        chartcolorjcb.setAutoscrolls(getVerifyInputWhenFocusTarget());
        add(chartcolorjcb);
        // set combobox for char Name type
        charNamejcb = new JComboBox<String>(characterNameTypes);
        charNamejcb.setAutoscrolls(getVerifyInputWhenFocusTarget());
        add(charNamejcb);
        // things to do upon selecting the type of chartcolor that is needed
        chartcolorjcb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JComboBox<String> chartColorType = (JComboBox<String>) e.getSource();
            chartColorSType = (String) chartColorType.getSelectedItem();
          }
        });

        // things to do upon selecting the type of chart that is needed
        charNamejcb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JComboBox<String> characterNameType = (JComboBox<String>) e.getSource();
            characterNameSType = (String) characterNameType.getSelectedItem();
          }
        });
        // set the list for numeric attributes available to select
        JScrollPane scrollPane = new JScrollPane();
        attributeSelectList = new JList<String>(attributeList);
        attributeSelectList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        attributeSelectList.setSelectedIndex(0);
        attributeSelectList.setVisibleRowCount(5);
        attributeSelectList.getAutoscrolls();
        attributeSelectList.setAutoscrolls(getVerifyInputWhenFocusTarget());
        scrollPane.setViewportView(attributeSelectList);
        add(scrollPane);
        // add a button to show the list of attributes selected
        selectbtn = new JButton("Select Done");
        add(selectbtn);
        selectbtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            selectedFields = new ArrayList<String>();
            String data = "";
            if (attributeSelectList.getSelectedIndex() != -1) {
              data += "attribute selected: ";
              for (Object obj : attributeSelectList.getSelectedValues()) {
                data += obj + ", ";
                selectedFields.add((String) obj);
              }
              DbfReadController dbfread = DbfReadController.getInstance();
              dbfread.dataHandler(selectedFields, chartSType, characterNameSType, chartColorSType);
            }
          }
        });
      } // MyPiePanel Constructor
    } // End MyPiePanel Class

    public class BarPanelGUI extends JPanel {
      private String[] barchartTypes = {"Horizontal", "Vertical"};
      private String[] chartColorTypes = {"Normal", "Pastel", "Rainbow"};
      private String[] characterNameTypes;
      private List<String> attributeNames;
      final DefaultListModel<String> attributeList;
      final JList<String> attributeSelectList;
      public List<String> selectedFields;
      private JComboBox<String> chartjcb;
      private JComboBox<String> chartcolorjcb;
      private JComboBox<String> charNamejcb;
      private JButton selectbtn;
      String barchartSType;
      String chartColorSType;
      String characterNameSType;

      public BarPanelGUI(List<String> numericNameList, List<String> charNameList) {
        characterNameTypes = charNameList.toArray(new String[charNameList.size()]);
        attributeNames = numericNameList;
        setSize(500, 500);
        attributeList = new DefaultListModel<String>();
        for (int i = 0; i < attributeNames.size(); i++) {
          attributeList.addElement(attributeNames.get(i));
        }
        // set layout
        setLayout(new GridLayout(2, 4));
        // set combobox for chart type
        chartjcb = new JComboBox<String>(barchartTypes);
        chartjcb.setAutoscrolls(getVerifyInputWhenFocusTarget());
        add(chartjcb);
        // set combobox for chartcolor type
        chartcolorjcb = new JComboBox<String>(chartColorTypes);
        chartcolorjcb.setAutoscrolls(getVerifyInputWhenFocusTarget());
        add(chartcolorjcb);
        // set combobox for char Name type
        charNamejcb = new JComboBox<String>(characterNameTypes);
        charNamejcb.setAutoscrolls(getVerifyInputWhenFocusTarget());
        add(charNamejcb);
        // things to do upon selecting the type of chart that is needed
        chartjcb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JComboBox<String> chartType = (JComboBox<String>) e.getSource();
            barchartSType = (String) chartType.getSelectedItem();
          }
        });
        // things to do upon selecting the type of chartcolor that is needed
        chartcolorjcb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JComboBox<String> chartColorType = (JComboBox) e.getSource();
            chartColorSType = (String) chartColorType.getSelectedItem();
          }
        });
        // things to do upon selecting the type of chart that is needed
        charNamejcb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JComboBox<String> characterNameType = (JComboBox<String>) e.getSource();
            characterNameSType = (String) characterNameType.getSelectedItem();
          }
        });
        // set the list for numeric attributes available to select
        JScrollPane scrollPane = new JScrollPane();
        attributeSelectList = new JList<String>(attributeList);
        attributeSelectList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        attributeSelectList.setSelectedIndex(0);
        attributeSelectList.setVisibleRowCount(5);
        attributeSelectList.getAutoscrolls();
        attributeSelectList.setAutoscrolls(getVerifyInputWhenFocusTarget());
        scrollPane.setViewportView(attributeSelectList);
        add(scrollPane);
        // add a button to show the list of attributes selected
        selectbtn = new JButton("Select Done");
        add(selectbtn);
        selectbtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            selectedFields = new ArrayList<String>();
            String data = "";
            if (attributeSelectList.getSelectedIndex() != -1) {
              data += "attribute selected: ";
              for (Object obj : attributeSelectList.getSelectedValues()) {
                data += obj + ", ";
                selectedFields.add((String) obj);
              }
              DbfReadController dbfread = DbfReadController.getInstance();
              dbfread.dataHandler(selectedFields, barchartSType, characterNameSType,
                  chartColorSType);
            }
          }
        });
      } // constructor
    } // End MyBarPanel class


    public class ScatterPanelGUI extends JPanel {
      private String[] barchartTypes = {"Horizontal", "Vertical"};
      private String[] chartColorTypes = {"Normal", "Pastel", "Rainbow"};
      private String[] characterNameTypes;
      private List<String> attributeNames;
      final DefaultListModel<String> attributeList;
      final JList<String> attributeSelectList;
      public List<String> selectedFields;
      private JComboBox<String> chartjcb;
      private JComboBox<String> chartcolorjcb;
      private JComboBox<String> charNamejcb;
      private JButton selectbtn;
      String barchartSType;
      String chartColorSType;
      String characterNameSType;

      public ScatterPanelGUI(List<String> numericNameList, List<String> charNameList) {
        characterNameTypes = charNameList.toArray(new String[charNameList.size()]);
        attributeNames = numericNameList;
        setSize(500, 500);
        attributeList = new DefaultListModel<String>();
        for (int i = 0; i < attributeNames.size(); i++) {
          attributeList.addElement(attributeNames.get(i));
        }
        // set layout
        setLayout(new GridLayout(2, 4));
        // set combobox for chart type
        chartjcb = new JComboBox<String>(barchartTypes);
        chartjcb.setAutoscrolls(getVerifyInputWhenFocusTarget());
        add(chartjcb);
        // set combobox for chartcolor type
        chartcolorjcb = new JComboBox<String>(chartColorTypes);
        chartcolorjcb.setAutoscrolls(getVerifyInputWhenFocusTarget());
        add(chartcolorjcb);
        // set combobox for char Name type
        charNamejcb = new JComboBox<String>(characterNameTypes);
        charNamejcb.setAutoscrolls(getVerifyInputWhenFocusTarget());
        add(charNamejcb);
        // things to do upon selecting the type of chart that is needed
        chartjcb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JComboBox<String> chartType = (JComboBox<String>) e.getSource();
            barchartSType = (String) chartType.getSelectedItem();
          }
        });
        // things to do upon selecting the type of chartcolor that is needed
        chartcolorjcb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JComboBox<String> chartColorType = (JComboBox) e.getSource();
            chartColorSType = (String) chartColorType.getSelectedItem();
          }
        });
        // things to do upon selecting the type of chart that is needed
        charNamejcb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JComboBox<String> characterNameType = (JComboBox<String>) e.getSource();
            characterNameSType = (String) characterNameType.getSelectedItem();
          }
        });
        // set the list for numeric attributes available to select
        JScrollPane scrollPane = new JScrollPane();
        attributeSelectList = new JList<String>(attributeList);
        attributeSelectList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        attributeSelectList.setSelectedIndex(0);
        attributeSelectList.setVisibleRowCount(5);
        attributeSelectList.getAutoscrolls();
        attributeSelectList.setAutoscrolls(getVerifyInputWhenFocusTarget());
        scrollPane.setViewportView(attributeSelectList);
        add(scrollPane);
        // add a button to show the list of attributes selected
        selectbtn = new JButton("Select Done");
        add(selectbtn);
        selectbtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            selectedFields = new ArrayList<String>();
            String data = "";
            if (attributeSelectList.getSelectedIndex() != -1) {
              data += "attribute selected: ";
              for (Object obj : attributeSelectList.getSelectedValues()) {
                data += obj + ", ";
                selectedFields.add((String) obj);
              }
              DbfReadController dbfread = DbfReadController.getInstance();
              dbfread.dataHandler(selectedFields, characterNameSType,
                      chartColorSType);
            }
          }
        });
      } // constructor
    } // End Scatter class
  }
}
