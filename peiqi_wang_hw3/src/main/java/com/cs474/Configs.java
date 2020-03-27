package com.cs474;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.nio.file.Paths;

public class Configs {
    // load the config Factory in the /src/main/resources/application.config
    public static Config config = ConfigFactory.load();

    // Because I can't figure the error of not found in Configuration file, so I have to do by this way with the hardcode
    public static String Facade = "class Facade {\n\tprivate Subsystem s;\n\tvoid doALOTofThings(){\n\n\t}\n}\n\nclass Subsystem{\n\tvoid doSomething(){\n\n\t}\n}\n";//config.getString("Facade");
    public static String AbstractFactory = "interface CarFactory {\n   Car createProduct();\n}\n\ninterface Car {\n    void doThings();\n}\n\nclass BenzFactory implements CarFactory {\n   @Override\n    public Car createProduct() {\n        return null;\n    }\n}\n\nclass BmwFactory implements CarFactory {\n    @Override\n    public Car createProduct() {\n        return null;\n    }\n}\n\nclass BenzCar implements Car {\n    @Override\n    public void doThings() {\n    }\n}\n\nclass FactoryProducer {\npublic static CarFactory getFactory() {\n        return null;\n    }\n}\n\nclass BmwCar implements Car {\n    @Override\n    public void doThings() {\n    }\n}\n\nclass BenzSportsCar implements Car {\n    @Override\n    public void doThings() {\n    }\n}\n\nclass BmwSportsCar implements Car {\n    @Override\n    public void doThings() {\n    }\n}";
    public static String Factory = "abstract class CarFactory {\n\tabstract Car createProduct();\n}\n\nclass Benz extends CarFactory {\n\t@Override\n\tCar createProduct() {\n\t\treturn new ConcreteProduct();\n\t}\n}\n\ninterface Car {\n\tvoid doThings();\n}\n\nclass BenzCar implements Car {\n\t@Override\n\tpublic void doThings() {\n\t}\n}\n\nclass BmwCar implements Car {\n\t@Override\n\tpublic void doThings() {\n\t}\n}\n\nclass Bmw extends CarFactory {\n\t@Override\n\tCar createProduct() {\n\t\treturn new ConcreteProduct();\n\t}\n}\n";
    public static String Builder = "\nabstract interface Builder {\n\tvoid BuildCpu();\n\tvoid BuildMainboard();\n\tComputer getComputer();\n}\n\n\nstatic class Director {\n\tpublic void Construct(Builder b){\n\t\tb.BuildCpu();\n\t\tb.BuildMainboard();\n\t}\n}\n\n\nstatic class Computer {\n\tprivate List<String> components = new ArrayList<>();\n\n\tpublic void addComponent(String s) {\n\t\tcomponents.add(s);\n\t}\n\n\tpublic String getComponent(int position) {\n\t\treturn components.get(position);\n\t}\n}\n\nstatic class ComputerBuilder implements Builder {\n\tComputer product = new Computer();\n\n\t@Override\tpublic void BuildCpu() {\n\t\tproduct.addComponent(Configs.cpu);\n\t}\n\n\t@Override\tpublic void BuildMainboard() {\n\t\tproduct.addComponent(Configs.mainBoard);\n\t}\n\n\t@Override\tpublic Computer getComputer() {\n\t\treturn product;\n\t}\n}\n";
    public static String Mediator = "interface Mediator {\n\tvoid notifyColleagueA();\n\tvoid notifyColleagueB();\n}\n\nclass ConcreteMediator implements Mediator {\n\tpublic void notifyColleagueA(){}\n\tpublic void notifyColleagueB(){}\n}\n\ninterface Colleague {\n\tvoid operation();\n}\n\nclass ConcreteColleagueA implements Colleague {\n\t@Override\n\tpublic void operation() {}\n}\nclass ConcreteColleagueB implements Colleague {\n\t@Override\n\tpublic void operation() {\n}\n}\n";
    public static String Visitor = "interface Visitor {\n\tvoid visit();\n}\n\ninterface Visitable {\n\tvoid accept(Visitor visitor);\n}\n\nclass b implements Visitable {\n\t@Override\n\tpublic void accept(Visitor visitor) {\n\t}\n}\n\nclass a implements Visitor {\n\t@Override\n\n\tpublic void visit() {\n\t}\n}\n\nclass c implements Visitable {\n\t@Override\n\tpublic void accept(Visitor visitor) {\n\t}\n}\n";
    public static String Chain = "interface Handler {\n\n\tpublic abstract void setNext(Handler nextInChain);\n\tpublic abstract void process();\n}\n\nclass Handler1 implements Handler {\n\t@Override\n\tpublic void setNext(Handler nextInChain) {\n\t}\n\n\t@Override\n\tpublic void process() {\n\t}\n}\n\nclass Handler2 implements Handler {\n\t@Override\n\tpublic void setNext(Handler nextInChain) {\n\t}\n\n\t@Override\n\tpublic void process() {\n\t}\n}\n";
    public static String TemplateMethod = "abstract class Template {\n\tpublic final void templateMethod() {\n\t}\n\n\tpublic abstract void abstractMethod();\n}\n\nclass ConcreateA extends Template {\n\t@Override\n\tpublic void abstractMethod() {\n\t}\n}\n\nclass ConcreateA extends Template {\n\t@Override\n\tpublic void abstractMethod() {\n\t}\n}\n";
    public static String test = "test";
    public static String[] FacadeList = {"Facade", "Subsystem", "doALOTofThings", "doSomething"};
    public static String[] AbstractFactoryList = {"CarFactory", "Car", "BenzFactory", "BmwFactory", "BenzCar", "createProduct", "doThings", "FactoryProducer", "getFactory", "BmwCar", "BenzSportsCar", "BmwSportsCar"};
    public static String[] FactoryList = {"CarFacotry", "createProduct", "Car", "BenzCar", "BmwCar", "Bmw", "doThings"};
    public static String[] BuilderList = {"Builder", "BuildCpu", "BuildMainboard", "getComputer", "Director", "Construct", "Computer", "addComponent", "getComponent", "ComputerBuilder"};
    public static String[] MediatorList = {"Mediator", "ConcreteMediator", "Colleague", "notifyColleagueA", "notifyColleagueB", "ConcreteColleagueA", "ConcreteColleagueB", "operation"};
    public static String[] VisitorList = {"Visitor", "Visitable", "accept", "b", "a", "c"};
    public static String[] ChainList = {"Handler", "setNext", "process", "Handler1", "Handler2"};
    public static String[] TemplateMethodList = {"Template", "templateMethod", "abstractMethod", "ConcreatedA"};
}
