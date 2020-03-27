/** Name: Peiqi Wang
    NetId: pwang53
    Description: This class is to create a code generate action as
                 as a plugin which extends the abstract class AnAction.
                 This action is called Design Pattern Generator

                 It is used to generate 8 different design pattern
                 and create the related java file in the src folder
                 of your java project.

                8 Design Pattern are: Facade, Abstract Factory, Factory,
                Builder, Mediator, Visitor, Chain of Responsibility
                and Template Method

                It also has the ability to check whether the upcoming
                created file name clashes with the existing file in
                the project path.

                Besides, it also check whether the upcoming created file
                contains same name of class/interface/method compared to
                the existing file in your project path.

                AST javaparser is used to implement it.
 **/
package com.cs474;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Extend the AnAction class so that can be register as an action in the plugin.xml
public class CodeGeneratorAction extends AnAction {
    // CreateFile object to create a new file and generate the code
    CreateFile file;

    // User select pattern
    String name;

    // prepare for the Config
    String config;

    // logger for log
    static final Logger logger = LoggerFactory.getLogger("testLog");

    // Data Structure to store already existed class, interface, method name;
    Set<String> classAndInterface = new HashSet<>();
    Set<String> method = new HashSet<>();
    //Set<String> clashes = new HashSet<>();
    StringBuilder builder;

    // Array to store the name of class, interface and method
    String[] names;

    // Set a flag to see if the class, interface or method names are clashing
    boolean flag = false;
    boolean fileFlag = false;

    // Set the list to check if the file name is clashing
    List<File> fileArray;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        // Uus JB Popup Factory to create a list of choice for user
        JBPopupFactory.getInstance().createListPopup(
                new BaseListPopupStep<String>("Chosen", "Facade", "AbstractFactory", "Factory"
                                                , "Builder", "Mediator", "Visitor", "Chain", "TemplateMethod") {
                    public PopupStep onChosen(String selectedValue, boolean finalChoice) {
                        // Get the path of the project uses the plugin
                        String path = e.getProject().getBasePath();

                        // Java parser to get parse the file
                        ASTParser ast = new ASTParser();

                        // Get all files in the path which are Java files (end with .java)
                        fileArray = ast.traverseAllFiles(path);

                        // Parse the template and store all class, interface and method name into hash set
                        ast.parseTemplate(path);

                        // Get all class/interface names
                        classAndInterface = ast.getSetForClassOrInterface();

                        // Get all method names
                        method = ast.getSetForMethod();

                        // Set the flag true for successfully creating a file
                        // If the flag is false means the file need to be created has name clash with existing file
                        flag = false;
                        fileFlag = false;

                        // Initialize the String builder
                        builder = new StringBuilder();

                        // Override the PopupStep method,
                        // If the user select one of the design pattern
                        // then create the name.java file and use Configs.name
                        // to write the configuration to that java file
                        if ("Facade".equals(selectedValue)) {
                            // Facade Design Pattern
                            name = "Facade";
                            config = Configs.Facade;
                            names = Configs.FacadeList;
                        } else if ("AbstractFactory".equals(selectedValue)) {
                            // Abstract Factory Design Pattern
                            name = "AbstractFactory";
                            config = Configs.AbstractFactory;
                            names = Configs.AbstractFactoryList;
                        } else if ("Factory".equals(selectedValue)) {
                            // Factory Design Pattern
                            name = "Factory";
                            config = Configs.Factory;
                            names = Configs.FactoryList;
                        } else if ("Builder".equals(selectedValue)) {
                            // Builder Design Pattern
                            name = "Builder";
                            config = Configs.Builder;
                            names = Configs.BuilderList;
                        } else if ("Mediator".equals(selectedValue)) {
                            // Mediator Design Pattern
                            name = "Mediator";
                            config = Configs.Mediator;
                            names = Configs.MediatorList;
                        } else if ("Visitor".equals(selectedValue)) {
                            // Visitor Design Pattern
                            name = "Visitor";
                            config = Configs.Visitor;
                            names = Configs.VisitorList;
                        } else if ("Chain".equals(selectedValue)) {
                            // Chain of Responsibility Design Pattern
                            name = "Chain";
                            config = Configs.Chain;
                            names = Configs.ChainList;
                        } else if ("TemplateMethod".equals(selectedValue)) {
                            // Template Method Design Pattern
                            name = "TemplateMethod";
                            config = Configs.TemplateMethod;
                            names = Configs.TemplateMethodList;
                        }

                        // if class has name class, return true
                        whetherFileNameClash(new StringBuffer(name + ".java").toString());
                        whetherClassOrMethodNameClash(names);

                        // if file name clashes, do something else
                        // if class, interface or method have name class, do something else
                        // Otherwise, create the file
                        if (fileFlag) {
                            doSomethingElse(e, "File name: " + name +".java clashes. \n\nPLEASE RENAME EXISTING FILE");
                        } else if (!fileFlag && flag) {
                            doSomethingElse(e, builder.append("\nPLEASE RENAME EXISTING CLASS/INTERFACE/METHOD ABOVE\n").toString());
                        } else {
                            // Create a new file
                            file = new CreateFile();

                            // Use the generateCode method in the object CreateFile
                            file.generateCode(path, name, config);

                            // Message out the successfully built
                            BuildSuccessfullyMessage(e);

                            // Put into the log
                            logger.debug("Successfully Created");
                        }

                        // If the user chooses something, then log the choice.
                        // Otherwise, it will log the error message
                        if (name != null || name.length() != 0) {
                            logger.info("User chose {}" + name + " Design Pattern");
                        } else {
                            logger.error("User didn't choose anything or something wrong happened");
                        }

                        // return the value of select and final choice
                        return super.onChosen(selectedValue, finalChoice);
                    }
                }
        ).showCenteredInCurrentWindow(e.getProject());  // show the docked window in the center
    }

    // Check whether class/interface/method name clashes
    public void whetherClassOrMethodNameClash(String[] s) {
        for (String str : s) {
            if (classAndInterface.contains(str)) {
                //clashes.add("Class/Interface clash:" + str);
                builder.append("Class/Interface clash: " + str + "\n\n");
                logger.error("Already Contains Class or Interface: " + str);
                flag = true;
            }

            if (method.contains(str)) {
                //clashes.add("Method clash:" + str);
                builder.append("Method clash: " + str + "\n\n");
                logger.error("Already Contains Method: " + str);
                flag = true;
            }
        }
    }

    // Check whether file name clashes
    public void whetherFileNameClash(String name) {
        for (File f : fileArray) {
            if (f.getName().equals(name)) {
                fileFlag = true;
            }
        }
    }

    // Message popup the error
    public void doSomethingElse(@NotNull AnActionEvent e, String message) {
        Messages.showMessageDialog(e.getProject(), message, "Error", Messages.getInformationIcon());
    }

    public void BuildSuccessfullyMessage(@NotNull AnActionEvent e) {
        Messages.showMessageDialog(e.getProject(), "File Successfully Built", "Congratulations!", Messages.getInformationIcon());
    }
}
