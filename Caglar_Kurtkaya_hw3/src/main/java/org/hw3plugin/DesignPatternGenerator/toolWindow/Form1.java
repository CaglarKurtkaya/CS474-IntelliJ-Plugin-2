package org.hw3plugin.DesignPatternGenerator.toolWindow;

import javax.swing.*;

/*
This class is used in the UserInputDialogWrapper class
It is a custom user interface for some of the design patterns
 */

public class Form1 {
    private JPanel panel;
    private JLabel classNameLabel;
    private JTextField classNameText;
    private JLabel productTypeLabel;
    private JTextField productTypeText;
    private JLabel productNameLabel;
    private JTextField productNameText;
    private JLabel packNameLabel;
    private JTextField packNameText;



    //Getters
    public JTextField getClassNameText() {
        return classNameText;
    }

    public JTextField getProductTypeText() {
        return productTypeText;
    }

    public JTextField getProductNameText() {
        return productNameText;
    }

    public JTextField getPackNameText() {
        return packNameText;
    }

    public JPanel getPanel(){
        return panel;
    }

}
