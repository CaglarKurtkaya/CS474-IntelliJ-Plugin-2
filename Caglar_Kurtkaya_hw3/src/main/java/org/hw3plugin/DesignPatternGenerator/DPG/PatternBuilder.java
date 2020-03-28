package org.hw3plugin.DesignPatternGenerator.DPG;

import org.hw3plugin.DesignPatternGenerator.Utils.Configs;
import com.squareup.javapoet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PatternBuilder implements PatternGenerator {

    private String patternName;
    private String className;
    private String productType;
    private String productName;
    private String packName;
    private String path;
    private Configs configs;

    private static final Logger logger = LoggerFactory.getLogger("PatternBuilder");


    private PatternBuilder(pBuilder myBuilder){
        this.patternName = myBuilder.patterName;
        this.className = myBuilder.className;
        this.productName = myBuilder.productName;
        this.productType = myBuilder.productType;
        this.packName = myBuilder.packName;
        this.path = myBuilder.pathName;
        this.configs = myBuilder.configs;

    }

    //Static Inner Class
    public static class pBuilder{
        //Required fields
        private String patterName;
        private String className;
        private String pathName;
        private Configs configs;

        //Optional fields
        private  String productType;
        private String productName;
        private String packName;



        public pBuilder(String patterName, String pathName, String className){
            this.patterName = patterName;
            this.className = className;
            this.pathName = pathName;
            this.configs = new Configs();
        }
        public pBuilder withProductType(String productType){
            this.productType = productType;
            return this;
        }

        public pBuilder withProductName(String productname){
            this.productName = productname;
            return this;
        }
        public pBuilder withPackName(String packname){
            this.packName = packname;
            return this;
        }

        public PatternBuilder build(){
            return new PatternBuilder(this);
        }

    }//End of inner class

    //--------------------------------------------------------------------------------------
    //Getters
    public String getPatternName() {
        return patternName;
    }

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

    //--------------------------------------------------------------------------------------
    /*Abstract Factory
        Groups object factories that have a common theme.
     */

    //Generates an Interface for Product
    //Product name is set by the Client(user input) @productType
    private void generateProductInterface(){
        TypeSpec intrFace = TypeSpec.interfaceBuilder(productType)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("getName")
                        .returns(String.class)
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();

        logger.debug("Product Interface: productType = {}", productType);
        logger.info("Product interface generated with -> productType = {}", productType);
        logger.info("Product interface generated with -> methodGetName = {}", "getName");



        buildJavaFile(packName, intrFace);

    }

    //Generates an AbstractFactory Interface
    private void generateAbstractFactoryInterface(){
        //Get the ProductInterface as a class
        ClassName prInterface = ClassName.get(packName, productType);


        //Create AbstractFactory interface
        TypeSpec intrFace = TypeSpec.interfaceBuilder("AbstractFactory")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("create" + productType)
                        .returns(prInterface)
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();

        logger.debug("Abstract Factory Interface: productType = {}", productType);
        logger.info("AbstractFactory Interface generated with -> methodGetName = {}", "create" + productType);
        logger.info("AbstractFactory Interface has a method with the return type -> {}", productType);



        buildJavaFile(packName,intrFace);

    }

    //Generates a Factory class which implements the AbstractFactory interface
    //User defines className, productType, productName, packName
    private void generateFactory(){
        ClassName prInterface = ClassName.get(packName, productType);
        ClassName productClass = ClassName.get(packName, productName);
        ClassName abstractInterface = ClassName.get(packName, "AbstractFactory");

        TypeSpec factoryClass = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("create" + productType)
                        .returns(prInterface)
                        .addAnnotation(Override.class)
                        .addStatement("return new $T()", productClass)
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .addSuperinterface(abstractInterface)
                .build();

        logger.debug("Factory Class: className = {}", className);
        logger.debug("Factory Class: productType = {}", productType);
        logger.debug("Factory Class: productName = {}", productName);

        logger.info("Factory Class generated with -> methodGetName = {}", "create" + productType);
        logger.info("Factory Class has a method({}) with the return type -> {}", "create" + productType,  productType);
        logger.info("Factory Class implements -> {}", "AbstractFactory");




        buildJavaFile(packName,factoryClass);

    }



    //Generates a Product Class which implements Product Interface
    private void generateProduct(){
        ClassName prInterface = ClassName.get(packName, productType);

        TypeSpec productClass = TypeSpec.classBuilder(productName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("getName")
                        .returns(String.class)
                        .addAnnotation(Override.class)
                        .addStatement("return $S", productName)
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .addSuperinterface(prInterface)
                .build();


        logger.debug("Product Class: productType = {}", productType);
        logger.debug("Product Class: productName = {}", productName);

        logger.info("Product Class generated with -> methodGetName = {}", "getName");
        logger.info("Product Class has a method({}) with the return type -> {}", "getName", String.class.getSimpleName());
        logger.info("Product Class implements -> {}", prInterface);


        buildJavaFile(packName, productClass);



    }

    //--------------------------------------------------------------------------------------
    /*Builder Design Pattern Functions
        Builder constructs complex objects by separating construction and representation.
     */

//    //Generates a ComplexObject Class
    private void generateComplexObject() {

        FieldSpec field = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(packName, productType)) , "children")
                .addModifiers(Modifier.PRIVATE)
                .initializer("new $T()", ParameterizedTypeName.get(ClassName.get(ArrayList.class), ClassName.get(packName, productType)))
                .build();

        TypeSpec complexClass = TypeSpec.classBuilder(className)
                .addField(field)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("add")
                        .returns(boolean.class)
                        .addParameter(ClassName.get(packName,productType), "child")
                        .addStatement("return $N.add($N)", "children", "child")
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .addMethod(MethodSpec.methodBuilder("iterator")
                        .returns(ParameterizedTypeName.get(ClassName.get(Iterator.class),ClassName.get(packName,productType)))
                        .addStatement("return $N.$N()", "children", "iterator")
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .build();

        logger.debug("ComplexObject Class: className = {}", className);
        logger.debug("ComplexObject Class: productType = {}", productType);
        logger.debug("ComplexObject Class: packName = {}", packName);

        logger.info("ComplexObject Class generated with -> methodGetName = {}", "add");
        logger.info("ComplexObject Class has a method({}) with the return type -> {}", "add", boolean.class.getSimpleName());
        logger.info("ComplexObject Class generated with -> methodGetName = {}", "iterator");
        logger.info("ComplexObject Class has a method({}) with the return type -> {}", "iterator", ClassName.get(Iterator.class));


        buildJavaFile(packName, complexClass);


    }

    //Generates a Builder Interface
    private void generateBuilderInterface(){

        TypeSpec intrFace = TypeSpec.interfaceBuilder("Builder")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("buildPartA")
                        .returns(TypeName.VOID)
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .addMethod(MethodSpec.methodBuilder("getResult")
                        .returns(ClassName.get(packName,className))
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();

        logger.debug("BuilderInterface: className = {}", "Builder");

        logger.info("BuilderInterface generated with -> methodBuildPartA = {}", "buildPartA");
        logger.info("BuilderInterface has a method({}) with the return type -> {}", "buildPartA", TypeName.VOID);
        logger.info("BuilderInterface generated with -> methodGetResult = {}", "getResult");
        logger.info("BuilderInterface has a method({}) with the return type -> {}", "getResult",  ClassName.get(packName,className));

        buildJavaFile(packName, intrFace);

    }

    //Generates a concrete Builder1 class which implements Builder interface
    private void generateBuilderClass(){

        FieldSpec field = FieldSpec.builder(ClassName.get(packName, className) , "co")
                .addModifiers(Modifier.PRIVATE)
                .initializer("new $T()", ClassName.get(packName, className))
                .build();

        TypeSpec builderClass = TypeSpec.classBuilder("Builder1")
                .addField(field)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("buildPartA")
                        .returns(TypeName.VOID)
                        .addStatement("$N.add(new $T())", "co", ClassName.get(packName, productName))
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .addMethod(MethodSpec.methodBuilder("getResult")
                        .returns(ClassName.get(packName,className))
                        .addStatement("return $N", "co")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .addSuperinterface(ClassName.get(packName, "Builder"))
                .build();

        logger.debug("BuilderClass: classBuilder1 = {}","Builder1");
        logger.debug("BuilderClass: className = {}", className);
        logger.debug("BuilderClass: packName = {}", packName);


        logger.info("BuilderClass generated with -> fieldCo = {}", "co");
        logger.info("BuilderClass has a method({}) with the return type -> {}", "buildPartA", TypeName.VOID);
        logger.info("BuilderClass generated with -> methodGetResult = {}", "getResult");
        logger.info("BuilderClass has a method({}) with the return type -> {}", "getResult",  ClassName.get(packName,className));


        buildJavaFile(packName, builderClass);

    }


   //-----------------------------------------------------------------------------------
    /* Factory Design Pattern
        Facade provides a simplified interface to a large body of code
     */

    /*



    //  generateFactoryAbstractClass() -> Generates an abstract class Creator

        public abstract class Creator {
            private Human product;
            public abstract Human factoryMethod();
            public void operation() {
                product = factoryMethod();
            }
       }
     */
    private void generateFactoryAbstractClass(){

        TypeSpec abstractCreator = TypeSpec.classBuilder("Creator")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addField(FieldSpec.builder(ClassName.get(packName, productType), "product")
                        .addModifiers(Modifier.PRIVATE)
                        .build())
                .addMethod(MethodSpec.methodBuilder("factoryMethod")
                        .returns(ClassName.get(packName, productType))
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .addMethod(MethodSpec.methodBuilder("operation")
                        .returns(void.class)
                        .addJavadoc("To be implemented by the user.")
                        .addModifiers(Modifier.PUBLIC)
                        .addCode("$N = $N();", "product", "factoryMethod")
                        .build())
                .build();
        logger.debug("FactoryAbstractClass: classCreator = {}", "Creator");
        logger.debug("FactoryAbstractClass: productType = {}", productType);
        logger.debug("FactoryAbstractClass: packName = {}",packName);

        logger.info("FactoryAbstractClass generated with -> fieldProduct = {}", "product");
        logger.info("FactoryAbstractClass has a method({}) with the return type -> {}", "factoryMethod", ClassName.get(packName, productType));
        logger.info("FactoryAbstractClass generated with -> methodFoperation = {}", "operation");
        logger.info("FactoryAbstractClass has a method({}) with the return type -> {}", "operation", TypeName.VOID);

        buildJavaFile(packName, abstractCreator);
    }

    //Generates a Factory class which implements the abstract factory class(Creator)
    private void generateFactoryClass(){

        TypeSpec factory = TypeSpec.classBuilder(className)
                .addField(FieldSpec.builder(ClassName.get(packName, productType), "product")
                        .addModifiers(Modifier.PRIVATE)
                        .build())
                .addMethod(MethodSpec.methodBuilder("factoryMethod")
                        .returns(ClassName.get(packName, productType))
                        .addAnnotation(Override.class)
                        .addCode("return new $T();", ClassName.get(packName, productName))
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .superclass(ClassName.get(packName, "Creator"))
                .build();

        logger.debug("FactoryClass: className = {}",className);
        logger.debug("FactoryClass: productType = {}", productType);
        logger.debug("FactoryClass: productName = {}", productName);
        logger.debug("FactoryClass: packName = {}",packName);

        logger.info("FactoryClass generated with -> fieldProduct = {}", "product");
        logger.info("FactoryClass has a method({}) with the return type -> {}", "factoryMethod", ClassName.get(packName, productType));
        logger.info("FactoryClass implements {}",ClassName.get(packName, "Creator") );

        buildJavaFile(packName, factory);

    }

    //-----------------------------------------------------------------------------------
    /* Facade Design Pattern
        Facade provides a simplified abstract class to a large body of code
     */


    //Generates an Abstract Facade class
    private void generateAbstractFacade(){
        TypeSpec abstractFacade = TypeSpec.classBuilder("Facade")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addMethod(MethodSpec.methodBuilder("operation")
                        .returns(void.class)
                        .addJavadoc("To be implemented by the user.")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();

        logger.debug("AbstractFacade: classFacade = {}", "Facade");
        logger.info("AbstractFacadeClass has a method({}) with the return type -> {}", "operation", TypeName.VOID);


        buildJavaFile(packName, abstractFacade);
    }

    private void generateFacadeClass(){

        TypeSpec facade = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("operation")
                        .returns(void.class)
                        .addAnnotation(Override.class)
                        .addJavadoc("To be implemented by the user.")
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .superclass(ClassName.get(packName, "Facade"))
                .build();

        logger.debug("FacadeClass: className = {}",className);
        logger.info("FacadeClass has a method({}) with the return type -> {}", "operation", TypeName.VOID);



        buildJavaFile(packName, facade);
    }


    //-----------------------------------------------------------------------------------
    /*Chain Design Pattern
        Chain of responsibility delegates commands to a chain of processing objects
     */

    private void generateAbstractHandler(){

        TypeSpec abstractHandler = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addField(FieldSpec.builder(ClassName.get(packName, className), "successor")
                        .addModifiers(Modifier.PRIVATE)
                        .build())
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ClassName.get(packName, className), "successor")
                        .addStatement("this.$N = $N", "successor", "successor")
                        .build())
                .addMethod(MethodSpec.methodBuilder("handleRequest")
                        .returns(void.class)
                        .beginControlFlow("if($N != null)", "successor")
                        .addCode("$N.$N();", "successor", "handleRequest")
                        .endControlFlow()
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .addMethod(MethodSpec.methodBuilder("canHandleRequest")
                        .returns(boolean.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addComment("Checking run-time conditions ... ")
                        .addStatement("return false")
                        .build())
                .build();

        logger.debug("AbstractHandler: className = {}",className);
        logger.debug("AbstractHandler: packName = {}", packName);

        logger.info("AbstractHandler has a method({}) with the return type -> {}", "handleRequest", TypeName.VOID);
        logger.info("AbstractHandler has a method({}) with the return type -> {}", "canHandleRequest", boolean.class);



        buildJavaFile(packName, abstractHandler);
    }

    //Returns a class
    private TypeSpec generateReceiverClass(String name, int x){
        TypeSpec receiverClass;
        logger.debug("In generateReceiverClass function name = {}", name);
        logger.debug("In generateReceiverClass function x = {}", x);
        //This constructor will not be added to the last receiver
        //See GOF book Chain example code
        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(packName, className),"successor")
                .addStatement("super($N)", "successor")
                .build();

        //Last receiver in the chain must handle the request
        if(x == 3){
            receiverClass = TypeSpec.classBuilder(name)
                    .addMethod(MethodSpec.methodBuilder("handleRequest")
                            .returns(void.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class)
                            .addComment(" Must handle the request unconditionally")
                            .build())
                    .superclass(ClassName.get(packName, className))
                    .build();
            logger.info("In generateReceiverClass last sub Receiver class has been created");
        }
        else {
            receiverClass = TypeSpec.classBuilder(name)
                    .addMethod(constructor)
                    .addMethod(MethodSpec.methodBuilder("handleRequest")
                            .returns(void.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class)
                            .beginControlFlow("if($N())", "canHandleRequest")
                            .addComment("Handle Request Here!")
                            .endControlFlow()
                            .beginControlFlow("else")
                            .addStatement("super.$N()", "handleRequest")
                            .endControlFlow()
                            .build())
                    .superclass(ClassName.get(packName, className))
                    .build();
        }

        return receiverClass;
    }

    // Generates Receiver sub-classes
    private void generateReceiverClasses(int numberOfReceivers, String name){
        logger.debug("In generateReceiverClasses function numberOfReceivers = {}", numberOfReceivers);
        logger.debug("In generateReceiverClasses function name = {}", name);

        for(int i = 1; i <= numberOfReceivers; i++){
            logger.debug("In generateReceiverClasses inside the for loop i = {}", i);
            TypeSpec t = generateReceiverClass(name+i, i);
            buildJavaFile(packName,t);
        }
    }

    //-----------------------------------------------------------------------------------
    /*Mediator Design Pattern
        Mediator allows loose coupling between classes by being the only class that has detailed knowledge of their methods.
     */

    private void generateAbstractMediator(){
        TypeSpec abstractMediator = TypeSpec.classBuilder("Mediator")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addMethod(MethodSpec.methodBuilder("mediate")
                        .returns(void.class)
                        .addParameter(ClassName.get(packName, "Colleague"), "colleague")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();

        logger.info("AbstractMediator class has a method({}) with the return type -> {}", "mediate", TypeName.VOID);

        buildJavaFile(packName, abstractMediator);

    }

    private void generateMediatorClass(){

        FieldSpec colleague1 = FieldSpec.builder(ClassName.get(packName, "Colleague"+1), "colleague"+1)
                .addModifiers(Modifier.PRIVATE)
                .build();
        FieldSpec colleague2 = FieldSpec.builder(ClassName.get(packName, "Colleague"+2), "colleague"+2)
                .addModifiers(Modifier.PRIVATE)
                .build();


        TypeSpec concreteMediator = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addField(colleague1)
                .addField(colleague2)
                .addMethod(MethodSpec.methodBuilder("setColleagues")
                        .returns(void.class)
                        .addParameter(ClassName.get(packName, "Colleague"+1), "colleague"+1)
                        .addParameter(ClassName.get(packName, "Colleague"+2), "colleague"+2)
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("this.$N = $N", "colleague"+1, "colleague"+1 )
                        .addStatement("this.$N = $N", "colleague"+2, "colleague"+2)
                        .build())
                .addMethod(MethodSpec.methodBuilder("mediate")
                        .returns(void.class)
                        .addAnnotation(Override.class)
                        .addParameter(ClassName.get(packName, "Colleague"),"colleague")
                        .addModifiers(Modifier.PUBLIC)
                        .beginControlFlow("if ($N == $N)", "colleague", "colleague"+1)
                        .addComment("Performing an action on colleague2")
                        .addStatement("String state = $N.getState()", "colleague"+1)
                        .addStatement("colleague2.action2(state)")
                        .endControlFlow()
                        .beginControlFlow("if (colleague == colleague2)")
                        .addComment("Performing an action on colleague1")
                        .addStatement("String state = colleague2.getState()")
                        .addStatement("colleague1.action1(state)")
                        .endControlFlow()
                        .build())
                .superclass(ClassName.get(packName,  "Mediator"))
                .build();

        logger.debug("MediatorClass: className = {}",className);
        logger.debug("MediatorClass: packName = {}", packName);

        logger.info("MediatorClass has a method({}) with the return type -> {}", "setColleagues", TypeName.VOID);
        logger.info("MediatorClass has a method({}) with the return type -> {}", "mediate", TypeName.VOID);
        logger.info("MediatorClass has a super class -> {}", ClassName.get(packName, "Mediator"));

        buildJavaFile(packName, concreteMediator);

    }

    private void generateAbstractColleague(){


        TypeSpec abstractColleague = TypeSpec.classBuilder("Colleague")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addField( FieldSpec.builder(ClassName.get(packName, "Mediator"), "mediator")
                        .build())
                .addMethod(MethodSpec.constructorBuilder()
                        .addParameter(ClassName.get(packName, "Mediator"), "mediator")
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("this.$N = $N", "mediator", "mediator")
                        .build())
                .build();

        logger.info("AbstractColleague name = {}", "Colleague");

        buildJavaFile(packName, abstractColleague);

    }

    private void generateColleagueClass(){
        //Creates two concrete Colleague classes
        for(int i = 1; i<3; i++) {
            TypeSpec concreteColleague = TypeSpec.classBuilder("Colleague" + i)
                    .addModifiers(Modifier.PUBLIC)
                    .addField(FieldSpec.builder(String.class, "state")
                            .addModifiers(Modifier.PRIVATE)
                            .build())
                    .addMethod(MethodSpec.constructorBuilder()
                            .addParameter(ClassName.get(packName, "Mediator"), "mediator")
                            .addModifiers(Modifier.PUBLIC)
                            .addStatement("super($N)", "mediator")
                            .build())
                    .addMethod(MethodSpec.methodBuilder("getState")
                            .returns(String.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addStatement("return $N","state")
                            .build())
                    .addMethod(MethodSpec.methodBuilder("setState")
                            .returns(void.class)
                            .addParameter(String.class, "state")
                            .beginControlFlow("if ($N != this.$N)", "state", "state" )
                            .addStatement("this.$N = $N", "state", "state")
                            .addComment("Implement your code here")
                            .addStatement("$N.$N(this)","mediator", "mediate")
                            .endControlFlow()
                            .build())
                    .addMethod(MethodSpec.methodBuilder("action" + i)
                            .returns(void.class)
                            .addParameter(String.class, "state")
                            .addComment("For example, synchronizing and displaying state")
                            .addStatement("this.$N = $N",  "state",  "state")
                            .addComment("Implement your code here")
                            .build())
                    .superclass(ClassName.get(packName, "Colleague"))
                    .build();
            logger.debug("In generateColleagueClass function  i = {}",i);

            buildJavaFile(packName, concreteColleague);
        }
    }

    //-----------------------------------------------------------------------------------
    /* Visitor Design Pattern
        Visitor separates an algorithm from an object structure by moving the hierarchy of methods into one object.
     */

    //Builds public abstract class Element
    private void generateAbstractElement(){
        TypeSpec abstractElement = TypeSpec.classBuilder("Element")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addMethod(MethodSpec.methodBuilder("accept")
                        .returns(void.class)
                        .addParameter(ClassName.get(packName, "Visitor"), "visitor")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();
        buildJavaFile(packName, abstractElement);
    }

    //Builds 2 concrete (Element1 and Element2) classes that extend abstract Element class
    private void generateElementClass(){
        for(int i = 1; i < 3; i++){
            TypeSpec concreteElement = TypeSpec.classBuilder("Element" + i)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(MethodSpec.methodBuilder("accept")
                            .returns(void.class)
                            .addAnnotation(Override.class)
                            .addParameter(ClassName.get(packName, "Visitor"), "visitor")
                            .addStatement("visitor.$N(this)", "visitElement" + i)
                            .addModifiers(Modifier.PUBLIC)
                            .build())
                    .addMethod(MethodSpec.methodBuilder("operation" + i)
                            .returns(String.class)
                            .addStatement("return \"Hello World from $N\"", "Element" + i )
                            .addModifiers(Modifier.PUBLIC)
                            .build())
                    .superclass(ClassName.get(packName, "Element"))
                    .build();

            buildJavaFile(packName, concreteElement);
        }

    }

    // Builds public abstract class Visitor with two abstract void methods
    private void generateAbstractVistor(){

        TypeSpec abstractVisitor = TypeSpec.classBuilder("Visitor")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                //Calling giveMeAbstractMethods to get two methods in a ArrayList<MethodSpec>
                .addMethods(giveMeAbstractMethods("visitElement", 2))
                .build();
        buildJavaFile(packName, abstractVisitor);
    }

    //Builds abstract methods giving by a name and a number to specify how many methods should be created.
    private List<MethodSpec> giveMeAbstractMethods(String name, int count){
        List<MethodSpec> m1 = new ArrayList<>();
        for(int i = 1; i <= count; i++){
            MethodSpec methodSpec = MethodSpec.methodBuilder(name+i)
                    .returns(void.class)
                    .addParameter(ClassName.get(packName,  "Element"+i), "element")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build();
            m1.add(methodSpec);
        }
        return m1;
    }

    //Builds concrete methods that was created in giveMeAbstractMethods
    private List<MethodSpec> giveMeConcreteMethods(String name, int count){
        List<MethodSpec> m2 = new ArrayList<>();
        for(int i = 1; i <= count; i++){
            MethodSpec methodSpec = MethodSpec.methodBuilder(name+i)
                    .returns(void.class)
                    .addAnnotation(Override.class)
                    .addParameter(ClassName.get(packName, "Element"+i), "element")
                    .addModifiers(Modifier.PUBLIC)
                    .build();
            m2.add(methodSpec);
        }
        return m2;
    }

    private void generateConcreteVisitor(){

        TypeSpec concreteVisitor = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                //Calling giveMeConcreteMethods to get two methods in a ArrayList<MethodSpec>
                .addMethods(giveMeConcreteMethods("visitElement", 2))
                .superclass(ClassName.get(packName, "Visitor"))
                .build();
        buildJavaFile(packName, concreteVisitor);

    }

    //-----------------------------------------------------------------------------------
    /*Template Design Pattern
        Template method defines the skeleton of an algorithm as an abstract class,
        allowing its subclasses to provide concrete behavior.
     */

    private void generateAbstractTemplate(){

        TypeSpec abstractTemplate = TypeSpec.classBuilder("Template")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addMethod(MethodSpec.methodBuilder("primitive1")
                        .returns(void.class)
                        .addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
                        .build())
                .addMethod(MethodSpec.methodBuilder("primitive2")
                        .returns(void.class)
                        .addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
                        .build())
                //Calling giveMeAbstractMethods to get two methods in a ArrayList<MethodSpec>
                .build();
        logger.debug("generateAbstractTemplate()-> Abstract Template templateName = {}", "Template");

        buildJavaFile(packName, abstractTemplate);

    }

    private void generateConcreteTemplate(){
        TypeSpec abstractTemplate = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("primitive1")
                        .returns(void.class)
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PROTECTED)
                        .build())
                .addMethod(MethodSpec.methodBuilder("primitive2")
                        .returns(void.class)
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PROTECTED)
                        .build())
                .superclass(ClassName.get(packName, "Template"))
                //Calling giveMeAbstractMethods to get two methods in a ArrayList<MethodSpec>
                .build();

        logger.debug("generateConcreteTemplate()-> Template className = {}", className);

        buildJavaFile(packName, abstractTemplate);
    }



    //-----------------------------------------------------------------------------------
    //Builds a java file
    private void buildJavaFile(String packName, TypeSpec typeSpec){
       try{
            JavaFile javaFile = JavaFile.builder(packName, typeSpec)
                    .addFileComment("AUTO_GENERATED BY Caglar Kurtkaya")
                    .build();

           //Variable for file path
           File filePath = new File(path);
            javaFile.writeTo(filePath);//root maven source
        } catch (NullPointerException ex1){
           System.out.println("Please enter a valid package " + ex1.getMessage());
           logger.error("packName is null! ", ex1.getMessage() );
       }catch (IOException ex2) {
            System.out.println("Cannot write the file to path" + ex2.getMessage());
            logger.debug("Cannot write the file to path", ex2.getMessage());
        }
    }

    //-----------------------------------------------------------------------------------
    @Override
    public void generateCode() {
        //Generate Abstract Factory Design Pattern
        if(this.patternName.equalsIgnoreCase("AFDP")){
            generateAFDP();
        }
       //Generate Builder Design Pattern
        else if(this.patternName.equalsIgnoreCase("BDP")){
            generateBDP();
        }
       //Generate Factory Design Pattern
        else if(this.patternName.equalsIgnoreCase("FDP")){
            generateFDP();
        }
        //Generate Facade Design Pattern
        else if(this.patternName.equalsIgnoreCase("FCDP")){
            generateFCDP();
        }
        //Generate Chain Design Pattern
        else if(this.patternName.equalsIgnoreCase("CDP")){
            generateCDP();
        }
        //Generate Mediator Design Pattern
        else if(this.patternName.equalsIgnoreCase("MDP")){
            generateMDP();
        }
        //Generate Visitor Design Pattern
        else if(this.patternName.equalsIgnoreCase("VDP")){
            generateVDP();
        }
        //Generate Template Design Pattern
        else if(this.patternName.equalsIgnoreCase("TDP")){
            generateTDP();
        }

        else{

            System.out.println("I am not generating!");
        }

    }

    //-----------------------------------------------------------------------------------
    //Abstract Factory Design Pattern
    private void generateAFDP(){
        generateProductInterface();
        generateProduct();
        generateAbstractFactoryInterface();
        generateFactory();
    }
   //Builder Design Pattern
    private void generateBDP(){
        generateComplexObject();
        generateBuilderInterface();
        generateBuilderClass();
        generateProductInterface();
        generateProduct();
    }

    //Factory Design Pattern
    private void generateFDP(){
        generateProductInterface();
        generateProduct();
        generateFactoryAbstractClass();
        generateFactoryClass();
    }

    //Facade Design Pattern
    private void generateFCDP(){
        generateAbstractFacade();
        generateFacadeClass();
    }

    //Chain Design Pattern
    private void generateCDP(){
        generateReceiverClasses(3, "Receiver");
        generateAbstractHandler();
    }

    //Mediator Design Pattern
    private void generateMDP(){
        generateAbstractMediator();
        generateMediatorClass();
        generateAbstractColleague();
        generateColleagueClass();
    }

    //Visitor Design Pattern
    private void generateVDP(){
        generateAbstractElement();
        generateElementClass();
        generateAbstractVistor();
        generateConcreteVisitor();
    }

    //Template Design Pattern
    private void generateTDP(){
        generateAbstractTemplate();
        generateConcreteTemplate();

    }


}
