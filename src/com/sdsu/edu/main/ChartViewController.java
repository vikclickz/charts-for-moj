package com.sdsu.edu.main;

import javax.swing.*;
import java.awt.*;


public class ChartViewController extends JFrame {

    public void display(Container contentPane) {
        setPreferredSize(new Dimension(400, 300));
        setContentPane(contentPane);
        setTitle("THE CHARTING TOOL");
        // setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}

