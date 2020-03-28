package org.hw3plugin.DesignPatternGenerator.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/*
This class is used to prompt a dialog window to get user input
 */
public class UserInputDialogWrapper extends DialogWrapper {
    private static final Logger logger = LoggerFactory.getLogger("UserInputDialogWrapper");
    //Dynamic forms
    //Some design patterns require different user input window
    private Form1 f1 = new Form1();
    private Form2 f2 = new Form2();
    private JPanel panel1, panel2;

    //List of Java files in the open project
    ProjectJavaFiles javaFiles;

    private int choosePanel;


    //Getters
    public String getClassName() {
        return className;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductName() {
        return productName;
    }

    public String getPackName() {
        return packName;
    }
    public int getChoosePanel(){
        return this.choosePanel;
    }
    public Form1 getF1(){
        return f1;
    }
    public Form2 getF2(){
        return f2;
    }
    public JPanel getPanel1(){
        return panel1;
    }
    public JPanel getPanel2(){
        return panel2;
    }
    //Setter
    public void setChoosePanel(int i){
        this.choosePanel = i;
    }

    //String variables to get user input
    private String  className, productType,productName, packName;


    public UserInputDialogWrapper(int choose, Project p) {
        super(true); // use current window as parent

        this.choosePanel = choose;

        this.javaFiles = new ProjectJavaFiles(p);

        setTitle("USER INPUT");

        logger.info("UserInputDialogWrapper has been initialized");
        init();
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {

        createUIComponents();
        logger.debug("Panel {} returned ", this.choosePanel);
        return this.choosePanel == 1 ? panel1 : panel2;
    }



    /*
    Added for validating user input for hw3
     */

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if(choosePanel == 1){
            if(f1.getClassNameText().getText().length()==0){
                return new ValidationInfo("Class name cannot be null", f1.getClassNameText());
            }
            if(f1.getProductTypeText().getText().length()==0){
                return new ValidationInfo("Product type cannot be null", f1.getProductTypeText());
            }
            if(f1.getProductNameText().getText().length()==0){
                return new ValidationInfo("Product name cannot be null", f1.getProductNameText());
            }
            if(f1.getPackNameText().getText().length()==0){
                return new ValidationInfo("Package name cannot be null", f1.getPackNameText());
            }
            if(javaFiles.fileNames.contains(f1.getClassNameText().getText())){
                return new ValidationInfo("Java File name already exists in the project!", f1.getClassNameText());
            }
            if(javaFiles.fileNames.contains(f1.getProductTypeText().getText())){
                return new ValidationInfo("Java File name already exists in the project!", f1.getProductTypeText());
            }
            if(javaFiles.fileNames.contains(f1.getProductNameText().getText())){
                return new ValidationInfo("Java File name already exists in the project!", f1.getProductNameText());
            }
            if(javaFiles.packageNames.contains(f1.getPackNameText().getText())){
                return new ValidationInfo("Package name already exists in the project!", f1.getPackNameText());
            }

        }
        else if(choosePanel == 2){
            if(f2.getClassNameText().getText().length()==0){
                return new ValidationInfo("Class name cannot be null", f2.getClassNameText());
            }
            if(f2.getPackNameText().getText().length()==0){
                return new ValidationInfo("Package name cannot be null", f2.getPackNameText());
            }
            if(javaFiles.fileNames.contains(f2.getClassNameText().getText())){
                return new ValidationInfo("Java File name already exists in the project!", f2.getClassNameText());
            }
            if(javaFiles.packageNames.contains(f2.getPackNameText().getText())){
                return new ValidationInfo("Package name already exists in the project!", f2.getPackNameText());
            }

        }
        //Validation passes all check points!
        return null;
    }

    @Override
    protected void doOKAction() {
        //Convert the text field input into string
        if(this.choosePanel == 1){
            className = f1.getClassNameText().getText();
            productType = f1.getProductTypeText().getText();
            productName = f1.getProductNameText().getText();
            packName = f1.getPackNameText().getText();

            logger.debug("className = {}", className);
            logger.debug("productType = {}", productType);
            logger.debug("productName = {}", productName);
            logger.debug("packName = {}", packName);

        }

        if(this.choosePanel == 2){
            className = f2.getClassNameText().getText();
            packName = f2.getPackNameText().getText();

            logger.debug("className = {}", className);
            logger.debug("packName = {}", packName);
        }



        //Close the pop up window after user press the ok button
        close(0);
    }


    private void createUIComponents() {
        panel1 = f1.getPanel();
        panel2 = f2.getPanel();
    }
}