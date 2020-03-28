package org.hw3plugin.DesignPatternGenerator.toolWindow;

import javax.swing.*;


/*
This class is used in the UserInputDialogWrapper class
It is a custom user interface for some of the design patterns
 */

public class Form2 {
    private JPanel panel;
    private JFormattedTextField classNameText;
    private JLabel packNameLabel;
    private JFormattedTextField packNameText;
    private JLabel classNameLabel;



    // Getters
    public JTextField getClassNameText() {
        return classNameText;
    }
    public JTextField getPackNameText() {
        return packNameText;
    }
    public JPanel getPanel(){
        return panel;
    }


}