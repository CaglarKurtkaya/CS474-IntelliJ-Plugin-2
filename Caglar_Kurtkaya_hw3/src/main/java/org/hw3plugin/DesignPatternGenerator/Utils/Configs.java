package org.hw3plugin.DesignPatternGenerator.Utils;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


public class Configs {
    public Config config =  ConfigFactory.load("application.conf");



//    public String getAbstractInterfaceName() {
//        return abstractInterfaceName;
//    }
//
//    public String getMethodGetName() {
//        return methodGetName;
//    }
//
//    public String getMethodCreate() {
//        return methodCreate;
//    }

//    public String getVariableChildren() {
//        return variableChildren;
//    }
//
//    public String getVariableChild() {
//        return variableChild;
//    }
//
//    public String getMethodAdd() {
//        return methodAdd;
//    }
//
//    public String getMethodIterator() {
//        return methodIterator;
//    }
//
//    public String getMethodBuildPartA() {
//        return methodBuildPartA;
//    }
//
//    public String getMethodGetResult() {
//        return methodGetResult;
//    }
//
//    public String getInterfaceBuilder() {
//        return interfaceBuilder;
//    }
//
//    public String getFieldCo() {
//        return fieldCo;
//    }
//
//    public String getClassBuilder1() {
//        return classBuilder1;
//    }
//
//    public String getClassCreator() {
//        return classCreator;
//    }
//
//    public String getFieldProduct() {
//        return fieldProduct;
//    }
//
//    public String getMethodFactoryMethod() {
//        return methodFactoryMethod;
//    }
//
//    public String getMethodFoperation() {
//        return methodFoperation;
//    }
//
//    public String getClassFacade() {
//        return classFacade;
//    }
//
//    public String getMethodFacadeOperation() {
//        return methodFacadeOperation;
//    }
//
//    public String getClassReceiver() {
//        return classReceiver;
//    }
//
//    public int getNumberOfClass() {
//        return numberOfClass;
//    }
//
//    public String getFieldSuccessor() {
//        return fieldSuccessor;
//    }
//
//    public String getMethodHandleRequest() {
//        return methodHandleRequest;
//    }
//
//    public String getMethodCanHandle() {
//        return methodCanHandle;
//    }
//
//    public String getClassMediator() {
//        return classMediator;
//    }
//
//    public String getClassColleague() {
//        return classColleague;
//    }
//
//    public String getVariableColleague() {
//        return variableColleague;
//    }
//
//    public String getVariableState() {
//        return variableState;
//    }
//
//    public String getVariableMediator() {
//        return variableMediator;
//    }
//
//    public String getMethodMediate() {
//        return methodMediate;
//    }
//
//    public String getMethodSetColleagues() {
//        return methodSetColleagues;
//    }
//
//    public String getMethodGetState() {
//        return methodGetState;
//    }
//
//    public String getMethodSetState() {
//        return methodSetState;
//    }
//
//    public String getMethodAction() {
//        return methodAction;
//    }
//
//    public String getVisitorName() {
//        return visitorName;
//    }
//
//    public String getElementName() {
//        return elementName;
//    }
//
//    public String getVariableVisitor() {
//        return variableVisitor;
//    }
//
//    public String getVariableElement() {
//        return variableElement;
//    }
//
//    public String getMethodAccept() {
//        return methodAccept;
//    }
//
//    public String getMethodOperation() {
//        return methodOperation;
//    }
//
//    public String getMethodVisitElement() {
//        return methodVisitElement;
//    }
//
//    public int getNumberOfVisitorMethods() {
//        return numberOfVisitorMethods;
//    }
//
//    public String getTemplateName() {
//        return templateName;
//    }
//
//    public String getMethodName1() {
//        return methodName1;
//    }
//
//    public String getMethodName2() {
//        return methodName2;
//    }

    //Abstract Factory Pattern
//    public  String abstractInterfaceName = config.getString("AbstractFactory.interfaceName");
//    public  String methodGetName = config.getString("AbstractFactory.methodName1");
//    public  String methodCreate= config.getString("AbstractFactory.methodName2");

//    //Builder Design Pattern
//    public  String variableChildren = config.getString("Builder.variableName1");
//    public  String variableChild = config.getString("Builder.variableName2");
//    public  String methodAdd = config.getString("Builder.methodName1");
//    public  String methodIterator = config.getString("Builder.methodName2");
//    public  String methodBuildPartA = config.getString("Builder.methodName3");
//    public  String methodGetResult = config.getString("Builder.methodName4");
//    public  String interfaceBuilder = config.getString("Builder.interfaceName");
//    public  String fieldCo = config.getString("Builder.fieldName");
//    public  String classBuilder1 = config.getString("Builder.classBuilder1");
//
//    //Factory
//    public String classCreator = config.getString("Factory.factoryAbstractClassName");
//    public String fieldProduct = config.getString("Factory.fieldProduct");
//    public String methodFactoryMethod = config.getString("Factory.methodName1");
//    public String methodFoperation = config.getString("Factory.methodName2");
//
//    //Facade
//    public String classFacade = config.getString("Facade.facadeAbstractClassName");
//    public String methodFacadeOperation = config.getString("Facade.methodName1");
//
//    //Chain
//    public String classReceiver = config.getString("Chain.className");
//    public int numberOfClass = config.getInt("Chain.numberOfClass");
//    public String fieldSuccessor = config.getString("Chain.field1");
//    public String methodHandleRequest = config.getString("Chain.methodName1");
//    public String methodCanHandle = config.getString("Chain.methodName2");
//
//
//    //Mediator
//    public String classMediator = config.getString("Mediator.className");
//    public String classColleague = config.getString("Mediator.className2");
//
//    public String variableColleague = config.getString("Mediator.variableName");
//    public String variableState = config.getString("Mediator.variableName2");
//    public String variableMediator = config.getString("Mediator.variableName3");
//    public String methodMediate = config.getString("Mediator.methodName");
//    public String methodSetColleagues = config.getString("Mediator.methodName2");
//    public String methodGetState = config.getString("Mediator.methodName3");
//    public String methodSetState = config.getString("Mediator.methodName4");
//    public String methodAction = config.getString("Mediator.methodName5");
//
//
//    //Visitor Pattern
//    public String visitorName = config.getString("Visitor.abstractVisitorName");
//    public String elementName = config.getString("Visitor.abstractElementName");
//    public String variableVisitor = config.getString("Visitor.variableName");
//    public String variableElement = config.getString("Visitor.variableName2");
//    public String methodAccept = config.getString("Visitor.methodName1");
//    public String methodOperation = config.getString("Visitor.methodName2");
//    public String methodVisitElement = config.getString("Visitor.methodName3");
//    public int numberOfVisitorMethods = config.getInt("Visitor.numberOfVisitorMethods");
//
//
//    //Template Pattern
//    public String templateName = config.getString("Template.abstractTemplateName");
//    public String methodName1 = config.getString("Template.methodName1");
//    public String methodName2 = config.getString("Template.methodName2");
}
