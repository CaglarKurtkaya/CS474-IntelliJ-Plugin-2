# Homework 3
### Name: An Extension of the Intellij Generate Design Pattern Code Plugin
### Contributor: Peiqi Wang
### NetID: pwang53

## Description: 
This project is to create a code generate action as a plugin and then to check if the name of classes/interfaces/methods are clashing or not

It is used to generate 8 different design pattern and create the related java file in the src folder of your java project. 8 Design Pattern are: Facade, Abstract Factory, Factory, Builder, Mediator, Visitor, Chain of Responsibility and Template Method.

## How to use:
To use this extension of Generator Plugin, there are 5 steps:

1. Start and run the plugin. Choose the Plugin and click the run icon. If the run Plugin doesn't bring you to a new runide, then go to your Gradle window which locates at the right side of your window and double click runide under Intellij.

2. Find the position of the plugin. After you get into the new ide environment, go to Tools -> DesignPatternGenerator

3. Use the plugin. After you click the DesignPatternGenerator, a small popup window contains a list of Design Pattern name will show in the center of you current Window. There has 8 different Design Patterns for you to use which include Facade, Abstract Factory, Factory, Builder, Mediator, Visitor, Chain of Responsibility and Template Method.

4. Get the DesignPattern java file under /src. After you choose one of the design pattern, the plugin will automatically create a java file start with the name of the design pattern you chose. You can open the .java file and get your code and you can also move the .java to the directory you want.

5. If the file is existing, it will give you the message of that. And also if the same class/interface/method names clash, it also will give you the message which and where you should change the name.

## Design, Build, Deployment and Test documentation:
1. Design: The desgin idea is to help people save time so that they don't need to spend much more time to write duplicate code of design pattern. And it also can help new programmer to get familiar with the structure of different desgin patterns.

2. Build: To achieve the design goal, I started to doing some research on plugin and went through the simple plugin to learn the structure and deployment of a gradle plugin project. The user feels much more comfortable when they get something with just a click. So I come out with that to build a list contains names of different design patterns. The user just need click the name of the design patther and the plugin will automatically create the java file and generate the code under /src folder. The project contains logs direction which is the folder to save .log files. In the src/main/java/com/cs474 contains the three main files CodeGeneratorAction, Configs and CreateFile. Files in /org/intellij/sdk/action are the example of plugin which are not actived. hw3.java under src/main/java is the class to do examples of SLF4J. Under src/main/resources, it contains the application.conf which is used to store the generated code. log4j.properties and logback.xml are used for the log. In the src/main/resources/META-INF, here has the plugin.xml which register the action for my plugin. In the src/test/java/com/cs474, it has the testCases.java which contains 6 test cases. README.md are also included under the project folder.

3. Deployment: My plugin class is called CodeGeneratorAction which extends AnAction. In the actionPerformed, I override by using a JPPopupFactory which is different from the Messager popup object. It can allow the developer create a Popup pane and use createListPopup to set the a list inside of that. However, a simple list doesn't work for the createListPopup method. After reading the source code and study from the internet, I create an new object which is an instace of BaseListPopupStep class. It take a list the parameter and the title. Inside of this object, the developer needs to override the onChosen method to determine which item in the list is chosen by the user. It will return a value that the user selected. The developer can compare this value with the value pre-setup and do some action. Here I create a new file and get the directory of the absolute path for the /src folder in the user project by using e.getProject().getBasePath. After creating the new File, we need to write the generate code into those java file by using configuration. In the configure file, we set the desgin pattern we need as a String and then will be utilized by the CreateFile class tp write code in these java files. In the plugin.xml, the developer also need to create and register the action so that it can be used in the user IDE.

4. Test: 
    My JUnit Test File contains SIX test cases to test my Gradle Plug-in Project
    I.   TestCase1: Test the correctness of config. Because the plugin needs to write the correct Design Pattern generated code which is store in the application.conf file, so it is necessary to test if the Configuration is good to use.
    II.  TestCase2: Test the information which outputs to the special Test Log with tag Info. Need to test the SLF4J logger is correct to use and output the log into a local file which is better for the developer to debug. 
    III. TestCase3: Test the information which output to the special Test Log with tag Error. Test the Error to check the log type is not getting wrong.
    IV. TestCase4: Test the file name that generated by plug-in is the correct name with correct extension.
    V.  TestCase5: Test the content in the file that generated by plug-in is in the correct content.
    VI. TestCase6: Test the file that generated by plug-in is in the correct path. 
    VII. TestCase7: Test AST parser file traversal all
    VIII. TestCase8: Test AST parser file traversal correctly
    IX. TestCase9: Test Class name clashes
    X. TestCase10: Test Method name clashes
    
## PootScript:
1. This Plugin is located at Tools -> GenrateDesignPattern
2. After you choose the specific design pattern you will need to wait for a while to see the new JAVA file shown in your IDE directory.
3. When doing the test, it will output the warning: "SLF4J: Class path contains multiple SLF4J bindings." which is normal. Please just ignore it, it doesn't affect any functionality.

That's all, Professor & TA!
