package com.sdsu.edu.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.esri.mo2.file.shp.ShapefileWriter;

public class CreateShapeDialog extends JDialog {
  String name = "";
  String path = "";
  JButton ok = new JButton("OK");
  JButton cancel = new JButton("Cancel");
  JTextField nameField = new JTextField("enter layer name here, then hit ENTER", 25);
  int shT=0;
  com.esri.mo2.map.dpy.FeatureLayer selectedlayer;

  ActionListener lis = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
          Object o = ae.getSource();
          if (o == nameField) {
              name = nameField.getText().trim();
              path = "C:\\Users\\NandithaMurthy\\Desktop\\JAVA-apl\\GIS\\FinalProject\\";
              System.out.println(path + "    " + name);
          }

          else if (o == cancel)
              setVisible(false);
          else {
              try {
                  //  int shT = UsCricketLeague.layer4.getLayerInfo().getType();
                  if (shT == 2) {
                      ShapefileWriter.writeFeatureLayer(selectedlayer, path, name, 2);
                  } else if (shT == 0) {
                      ShapefileWriter.writeFeatureLayer(selectedlayer, path, name, 0);
                  } else if (shT == 1) {
                      ShapefileWriter.writeFeatureLayer(selectedlayer, path, name, 1);
                  }
              } catch (Exception e) {
                  System.out.println("write error" + e);
                  e.printStackTrace();
              }
              setVisible(false);
          }
      }
  };


  JPanel panel1 = new JPanel();
  JLabel centerlabel = new JLabel();

  // centerlabel;
  CreateShapeDialog(com.esri.mo2.map.dpy.FeatureLayer layer5, int shType) {
      shT=shType;
      selectedlayer = layer5;
      setBounds(40, 350, 450, 150);
      setTitle("Create new shapefile?");
      addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
              setVisible(false);
          }
      });
      nameField.addActionListener(lis);
      ok.addActionListener(lis);
      cancel.addActionListener(lis);
      String s = "<HTML> To make a new shapefile from the new layer, enter<BR>"
              + "the new name you want for the layer and click OK.<BR>"
              + "You can then add it to the map in the usual way.<BR>"
              + "Click ENTER after replacing the text with your layer name";
      centerlabel.setHorizontalAlignment(JLabel.CENTER);
      centerlabel.setText(s);
      getContentPane().add(centerlabel, BorderLayout.CENTER);
      panel1.add(nameField);
      panel1.add(ok);
      panel1.add(cancel);
      getContentPane().add(panel1, BorderLayout.SOUTH);
  }
}
